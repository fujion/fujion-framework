/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;
import org.fujion.common.StrUtil;
import org.springframework.util.Assert;

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

        File template = (File) cmd.getParsedOptionValue("template");
        Assert.isTrue(template.exists(), "Template file not found at " + template);
        File props = (File) cmd.getParsedOptionValue("config");
        Assert.isTrue(props.exists(), "Configuration file not found at " + props);
        File outdir = (File) cmd.getParsedOptionValue("outdir");
        String version = SchemaGenerator.formatVersion(cmd.getOptionValue("version"));
        List<String> schemas = new ArrayList<>();
        
        for (String prop : FileUtils.readLines(props, StrUtil.UTF8)) {
            prop = prop.trim().replace("=", ",");

            if (!prop.isEmpty() && !prop.startsWith("#")) {
                String[] pcs = prop.split("\\,", 3);
                schemas.add(pcs[0]);
                generateSchema(outdir, version, pcs[0], pcs[1], pcs.length == 2 ? null : pcs[2]);
            }
        }

        File outfile = new File(outdir, "fsp.xsd");

        try (FileOutputStream out = new FileOutputStream(outfile);) {
            for (String tmpl : FileUtils.readLines(template, StrUtil.UTF8)) {
                tmpl += "\n";

                if (tmpl.contains("${version}")) {
                    tmpl = tmpl.replace("${version}", version);
                } else if (tmpl.contains("${name}")) {
                    for (String schema : schemas) {
                        String line = tmpl.replace("${name}", schema);
                        out.write(line.getBytes());
                    }

                    continue;
                }
                
                out.write(tmpl.getBytes());
            }
        }
    }

    private static void generateSchema(File outdir, String version, String name, String title,
                                       String pkgs) throws Exception {
        SchemaGenerator.main(pkgs == null ? "-r" : "-p=" + pkgs, "-v=" + version, "-t=" + title,
            outdir.getAbsolutePath() + "/fsp-" + name + ".xsd");
    }
}
