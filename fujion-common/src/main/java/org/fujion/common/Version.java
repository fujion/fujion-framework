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
package org.fujion.common;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Representation of a standard 4-part version number in the format:
 * <p>
 * <code>major.minor.release.build</code>
 * <p>
 * where any missing components are assumed to be 0.
 */
public class Version implements Comparable<Version> {
    
    public static final int NOVAL = -1;
    
    /**
     * Specifies a version part.
     */
    public enum VersionPart {
        MAJOR, MINOR, RELEASE, BUILD
    }
    
    private final int seq[] = new int[] { NOVAL, NOVAL, NOVAL, NOVAL };
    
    public Version() {
    }
    
    public Version(String value) {
        value = StringUtils.trimToNull(value);
        
        if (value != null) {
            String[] pcs = value.split("\\.", 4);
            
            for (int i = 0; i < pcs.length; i++) {
                setPart(VersionPart.values()[i], StrUtil.extractInt(pcs[i].trim()));
            }
        }
    }
    
    public Version(int major) {
        this(major, NOVAL, NOVAL, NOVAL);
    }
    
    public Version(int major, int minor) {
        this(major, minor, NOVAL, NOVAL);
    }
    
    public Version(int major, int minor, int release) {
        this(major, minor, release, NOVAL);
    }
    
    public Version(int major, int minor, int release, int build) {
        setPart(VersionPart.MAJOR, major);
        setPart(VersionPart.MINOR, minor);
        setPart(VersionPart.RELEASE, release);
        setPart(VersionPart.BUILD, build);
    }
    
    /**
     * Returns the value of the specified part.
     *
     * @param part The part whose value is sought.
     * @return The value of the specified part, or -1 if no value.
     */
    public int getPart(VersionPart part) {
        return seq[part.ordinal()];
    }

    /**
     * Sets the value of the specified version part.
     *
     * @param part The version part.
     * @param value The value for the part. A negative or null value is interpreted as no value.
     */
    private void setPart(VersionPart part, int value) {
        seq[part.ordinal()] = Math.max(NOVAL, value);
    }
    
    /**
     * Returns the level of specificity for the version.
     *
     * @return Level of specificity, or null if the version was null or empty.
     */
    public VersionPart getSpecificity() {
        int i = ArrayUtils.indexOf(seq, NOVAL);
        return i == -1 ? VersionPart.BUILD : i == 0 ? null : VersionPart.values()[i - 1];
    }
    
    @Override
    public boolean equals(Object v) {
        return v instanceof Version && compareTo((Version) v) == 0;
    }
    
    @Override
    /**
     * Does a full comparison of all version parts.
     */
    public int compareTo(Version v) {
        return compareTo(v, VersionPart.BUILD);
    }
    
    /**
     * Compare two versions up to and including the specified version part.
     *
     * @param v The version to compare.
     * @param part Compare up to and including this version part.
     * @return Result of the comparison.
     */
    public int compareTo(Version v, VersionPart part) {
        int diff = 0;
        int max = part.ordinal();
        
        for (int i = 0; i <= max; i++) {
            diff = seq[i] - v.seq[i];
            
            if (diff != 0) {
                break;
            }
        }
        
        return diff;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(seq).hashCode();
    }
    
    /**
     * Returns the text representation of the version up to and including the specified version
     * part. If the version part exceeds the specificity of the version, the result will be padded
     * with zero values.
     *
     * @param part Pad up to and including this version part. Null means no padding.
     * @return The text representation.
     */
    public String toString(VersionPart part) {
        StringBuilder sb = new StringBuilder();
        int max = part == null ? VersionPart.BUILD.ordinal() : part.ordinal();
        
        for (int i = 0; i <= max; i++) {
            int j = seq[i];
            
            if (j < 0) {
                if (part == null) {
                    break;
                } else {
                    j = 0;
                }
            }

            sb.append(i == 0 ? "" : ".").append(j);
        }
        
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return toString(null);
    }
    
}
