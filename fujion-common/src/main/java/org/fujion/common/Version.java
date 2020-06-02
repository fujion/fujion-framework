/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    private final int[] seq = new int[] { NOVAL, NOVAL, NOVAL, NOVAL };

    private final String classifier;

    public Version() {
        classifier = null;
    }

    public Version(String value) {
        String classifier = null;
        value = StringUtils.trimToNull(value);

        if (value != null) {
            String[] pcs = value.split("\\.", 4);

            for (int i = 0; i < pcs.length; i++) {
                String pc = pcs[i].trim();
                pc = pc.isEmpty() ? "0" : pc;

                if (classifier != null) {
                    classifier += "." + pc;
                } else {
                    String vpart = StrUtil.extractIntPrefix(pc);
                    pc = StringUtils.substring(pc, vpart.length());

                    if (!vpart.isEmpty()) {
                        setPart(VersionPart.values()[i], Integer.parseInt(vpart));
                    }

                    if (!pc.isEmpty()) {
                        classifier = (i > 0 && vpart.isEmpty() ? "." : "") + pc;
                    }
                }
            }
        }

        this.classifier = classifier;
    }

    public Version(int major) {
        this(major, NOVAL, NOVAL, NOVAL, null);
    }

    public Version(int major, int minor) {
        this(major, minor, NOVAL, NOVAL, null);
    }

    public Version(int major, int minor, int release) {
        this(major, minor, release, NOVAL, null);
    }

    public Version(int major, int minor, int release, int build) {
        this(major, minor, release, build, null);
    }

    public Version(int major, int minor, int release, int build, String classifier) {
        setPart(VersionPart.MAJOR, major);
        setPart(VersionPart.MINOR, minor);
        setPart(VersionPart.RELEASE, release);
        setPart(VersionPart.BUILD, build);
        this.classifier = classifier;
    }

    /**
     * Returns the value of the specified part.
     *
     * @param part The part whose value is sought.
     * @return The value of the specified part, or -1 if no value. Note that the classifier version
     *         part will always return -1. To retrieve the classifier part, use
     *         {@link #getClassifier()}.
     */
    public int getPart(VersionPart part) {
        return seq[part.ordinal()];
    }
    
    /**
     * Returns the version classifier, if any.
     *
     * @return The version classifier, if any.
     */
    public String getClassifier() {
        return classifier;
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

    /**
     * Two version are considered equal if all their respective parts are equal and their
     * classifiers are equal.
     */
    @Override
    public boolean equals(Object v) {
        return v instanceof Version && compareTo((Version) v) == 0;
    }

    /**
     * Does a full comparison of all version parts. Classifiers are used in the comparison.
     */
    @Override
    public int compareTo(Version v) {
        return compareTo(v, VersionPart.BUILD, true);
    }

    /**
     * Does a full comparison of all version parts.
     *
     * @param v The version to compare
     * @param includeClassifier If true, include classifiers in the comparison.
     * @return Result of the comparison.
     */
    public int compareTo(Version v, boolean includeClassifier) {
        return compareTo(v, VersionPart.BUILD, includeClassifier);
    }

    /**
     * Compare two versions up to and including the specified version part. Classifiers are not used
     * in the comparison.
     *
     * @param v The version to compare.
     * @param part Compare up to and including this version part.
     * @return Result of the comparison.
     */
    public int compareTo(Version v, VersionPart part) {
        return compareTo(v, part, false);
    }

    /**
     * Compare two versions up to and including the specified version part.
     *
     * @param v The version to compare.
     * @param part Compare up to and including this version part.
     * @param includeClassifier If true, a case-sensitive string comparison of the classifiers will
     *            be used in the event that all parts are equal.
     * @return Result of the comparison.
     */
    public int compareTo(Version v, VersionPart part, boolean includeClassifier) {
        int diff = 0;
        int max = part.ordinal();

        for (int i = 0; i <= max; i++) {
            diff = seq[i] - v.seq[i];

            if (diff != 0) {
                break;
            }
        }

        if (diff == 0 && includeClassifier) {
            diff = ObjectUtils.compare(classifier, v.classifier);
        }
        
        return diff;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(seq).append(classifier).hashCode();
    }

    /**
     * Returns the text representation of the version up to and including the specified version
     * part. If the version part exceeds the specificity of the version, the result will be padded
     * with zero values. If a classifier is present and the specified part is null, it will be
     * appended to the end of the returned value.
     *
     * @param part Pad up to and including this version part. Null means no padding.
     * @return The text representation.
     */
    public String toString(VersionPart part) {
        return toString(part, part == null);
    }

    /**
     * Returns the text representation of the version up to and including the specified version
     * part. If the version part exceeds the specificity of the version, the result will be padded
     * with zero values.
     *
     * @param part Pad up to and including this version part. Null means no padding.
     * @param includeClassifier If true, include the classifier, if any.
     * @return The text representation.
     */
    public String toString(VersionPart part, boolean includeClassifier) {
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

        if (includeClassifier && classifier != null) {
            sb.append(classifier);
        }
        
        return sb.toString();
    }

    @Override
    public String toString() {
        return toString(null);
    }

}
