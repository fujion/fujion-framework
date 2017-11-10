/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
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
package org.fujion.theme;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Theme implementation that specifies URL rewrites for theme resources.
 */
public class Theme {

    private static final Log log = LogFactory.getLog(Theme.class);

    /**
     * Represents a single URL mapping.
     */
    private class Mapping {
        
        final Pattern fromPattern;
        
        final String toPattern;
        
        Mapping(String fromPattern, String toPattern) {
            this.fromPattern = toRegEx(fromPattern);
            this.toPattern = StringUtils.trimToNull(toPattern);
        }
        
        /**
         * Convert text pattern to a regular expression.
         *
         * @param pattern Text pattern (regex or glob format)
         * @return A regular expression pattern.
         */
        Pattern toRegEx(String pattern) {
            if (pattern.startsWith("^")) {
                return Pattern.compile(pattern);
            }

            StringBuilder regex = new StringBuilder("^");
            int last = pattern.length() - 1;
            String literal = "";
            
            for (int i = 0; i <= last; i++) {
                char c = pattern.charAt(i);
                String token = "";
                
                switch (c) {
                    case '*':
                        if (i < last && pattern.charAt(i + 1) == '*') {
                            i++;
                            token = "(.*)";
                        } else {
                            token = "([^\\/]*)";
                        }
                        
                        break;
                    
                    case '?':
                        token = "(.)";
                        break;
                    
                    default:
                        literal += c;
                        
                        if (i < last) {
                            continue;
                        }
                }
                
                if (!literal.isEmpty()) {
                    regex.append("\\Q").append(literal).append("\\E");
                    literal = "";
                }
                
                regex.append(token);
            }
            
            return Pattern.compile(regex.append('$').toString());
        }
        
        /**
         * If path matches the fromPattern, convert to the toPattern.
         *
         * @param path Path to check.
         * @return Returns null if not a match, empty string if a match but no toPattern, otherwise
         *         the translated path.
         */
        String translate(String path) {
            Matcher from = fromPattern.matcher(path);

            if (from.matches()) {
                return toPattern == null ? "" : from.replaceAll(toPattern.replace("$0", name));
            }

            return null;
        }
    }
    
    private final String name;
    
    private final Map<String, Mapping> urlMap = new LinkedHashMap<>();
    
    /**
     * Create a theme.
     *
     * @param name The unique theme name.
     */
    public Theme(String name) {
        this.name = name;
    }
    
    /**
     * Returns the unique theme name.
     *
     * @return The unique theme name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Merges URL pattern mappings from another like-named theme.
     *
     * @param theme Theme providing additional URL pattern mappings.
     */
    protected void merge(Theme theme) {
        Map<String, Mapping> srcMap = theme.urlMap;
        
        for (String pattern : srcMap.keySet()) {
            dupCheck(pattern);
            urlMap.put(pattern, srcMap.get(pattern));
        }
    }
    
    /**
     * Merge a set of mappings into existing mappings.
     *
     * @param mappings Mappings to merge.
     */
    public void setMappings(Map<String, String> mappings) {
        for (String fromPattern : mappings.keySet()) {
            addMapping(fromPattern, mappings.get(fromPattern));
        }
    }

    /**
     * Add a single mapping to existing mappings.
     *
     * @param fromPattern The source pattern against which URL will be matched.
     * @param toPattern The target pattern to which the URL will be converted.
     */
    public void addMapping(String fromPattern, String toPattern) {
        dupCheck(fromPattern);
        urlMap.put(fromPattern, new Mapping(fromPattern, toPattern));
    }
    
    /**
     * Displays a warning if a mapping is being overwritten.
     *
     * @param pattern Pattern to check.
     */
    private void dupCheck(String pattern) {
        if (log.isWarnEnabled() && urlMap.containsKey(pattern)) {
            log.warn(String.format("Overwriting URL pattern \"%s\" in theme \"%s\"", pattern, name));
        }
    }
    
    /**
     * If the input path matches one of the theme's mapped patterns, return the translated path.
     * Otherwise, return null.
     *
     * @param path The path to translate.
     * @return The translated path, or null.
     */
    public String translatePath(String path) {
        for (Mapping mapping : urlMap.values()) {
            String newPath = mapping.translate(path);
            
            if (newPath != null) {
                return newPath;
            }
        }
        
        return null;
    }
    
}
