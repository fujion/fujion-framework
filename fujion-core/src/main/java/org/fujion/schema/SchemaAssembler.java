/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * #L%
 */
package org.fujion.schema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;
import org.fujion.common.DateUtil;
import org.fujion.common.MiscUtil;
import org.fujion.common.StrUtil;

/**
 * Assemble a compound schema from individual schemas.
 */
public class SchemaAssembler {

    /**
     * Main entry point.
     *
     * @param args The command line arguments.
     * @throws Exception Unspecified exception.
     */
    public static void main(String... args) throws Exception {
        Options options = new Options();
        Option option;
        option = new Option("c", "config", true, "Configuration file");
        option.setType(File.class);
        option.setRequired(true);
        options.addOption(option);
        option = new Option("t", "template", true, "Schema template file");
        option.setType(File.class);
        option.setRequired(true);
        options.addOption(option);
        option = new Option("o", "outdir", true, "Output directory");
        option.setType(File.class);
        option.setRequired(true);
        options.addOption(option);
        option = new Option("v", "version", true, "Schema version");
        option.setRequired(true);
        options.addOption(option);
        option = new Option("h", "help", false, "This help message");
        options.addOption(option);
        CommandLine cmd = new DefaultParser().parse(options, args);

        if (cmd.hasOption("h")) {
            new HelpFormatter().printHelp("SchemaGenerator [options] ...", options);
            return;
        }

        new SchemaAssembler(cmd).run();
    }

    private final Map<String, BiConsumer<String, String>> tokens = new HashMap<>();

    private final List<String> schemas = new ArrayList<>();

    private final File template;

    private final File config;

    private final File outdir;

    private final String version;

    private OutputStream out;

    /**
     * Process command line parameters.
     *
     * @param cmd Command line parameters.
     * @throws Exception Unexpected exception.
     */
    private SchemaAssembler(CommandLine cmd) throws Exception {
        template = (File) cmd.getParsedOptionValue("template");
        assertExists(template, "Template file");
        config = (File) cmd.getParsedOptionValue("config");
        assertExists(config, "Configuration file");
        outdir = (File) cmd.getParsedOptionValue("outdir");
        version = cmd.getOptionValue("version");
    }

    /**
     * Run the schema generation process.
     *
     * @throws Exception Unexpected exception.
     */
    private void run() throws Exception {
        initTokenConsumers();
        generateIndividualSchemas();
        generateCompositeSchema();
    }

    /**
     * Assert that the file exists, throwing an exception if it does not.
     *
     * @param file The file to test.
     * @param type The descriptive file type.
     * @throws FileNotFoundException Thrown if file does not exist.
     */
    private void assertExists(File file, String type) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException(type + " not found at " + file);
        }
    }
    
    /**
     * Writes the line of data to the output stream. Automatically appends a new line character.
     *
     * @param data Data to write.
     */
    private void write(String data) {
        try {
            out.write(data.getBytes());
            out.write('\n');
        } catch (IOException e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * Initialize the token consumer. A token consumer replaces its associated token with a computed
     * value and writes the result to the output stream.
     */
    private void initTokenConsumers() {
        tokens.put("${schema-name}", (token, value) -> {
            for (String schema : schemas) {
                write(value.replace(token, schema));
            }
        });

        tokens.put("${schema-version}", (token, value) -> {
            write(value.replace(token, SchemaGenerator.formatVersion(version)));
        });

        tokens.put("${app-version}", (token, value) -> {
            write(value.replace(token, version));
        });

        tokens.put("${generated-on}", (token, value) -> {
            write(value.replace(token, DateUtil.formatDate(new Date())));
        });
    }
    
    /**
     * Generate individual schema files from configuration entries.
     *
     * @throws Exception Unexpected exception
     */
    private void generateIndividualSchemas() throws Exception {
        for (String prop : FileUtils.readLines(config, StrUtil.UTF8)) {
            prop = prop.trim().replace("=", ",");
            
            if (!prop.isEmpty() && !prop.startsWith("#")) {
                String[] pcs = prop.split("\\,", 3);
                schemas.add(pcs[0]);
                generateSchema(pcs[0], pcs[1], pcs.length == 2 ? null : pcs[2]);
            }
        }
    }
    
    /**
     * Generate the composite schema from template.
     *
     * @throws Exception Unexpected exception.
     */
    private void generateCompositeSchema() throws Exception {
        System.out.println("Generating composite schema...");
        File outfile = new File(outdir, "fsp.xsd");
        
        try (OutputStream strm = new FileOutputStream(outfile)) {
            out = strm;

            for (String tmpl : FileUtils.readLines(template, StrUtil.UTF8)) {
                boolean found = false;
                
                for (String token : tokens.keySet()) {
                    if (found = tmpl.contains(token)) {
                        tokens.get(token).accept(token, tmpl);
                        break;
                    }
                }
                
                if (!found) {
                    write(tmpl);
                }
            }
        }
    }

    /**
     * Generates an individual schema by calling the schema generator.
     *
     * @param name The schema name.
     * @param title The schema descriptive title.
     * @param pkgs The packages to scan. If null, generates a root schema.
     * @throws Exception Unexpected exception.
     */
    private void generateSchema(String name, String title, String pkgs) throws Exception {
        System.out.println("Generating schema for " + title + "...");
        SchemaGenerator.main(pkgs == null ? "-r" : "-p=" + pkgs, "-v=" + version, "-t=" + title,
            outdir.getAbsolutePath() + "/fsp-" + name + ".xsd");
    }
}
