package org.fujion.convert;

import org.apache.commons.lang3.ClassUtils;
import org.fujion.ancillary.JavaScript;
import org.fujion.common.MiscUtil;

/**
 * These are supplemental converters used in addition to the default converters provided by Spring.
 */
@SuppressWarnings("rawtypes")
public class Converters {

    /**
     * Converts a string to a JavaScript Snippet
     */
    public static class JavaScriptConverter extends TypedConverter<String, JavaScript> {

        public JavaScriptConverter() {
            super(String.class, JavaScript.class);
        }

        @Override
        public JavaScript convert(String source) {
            return new JavaScript(source);
        }
    }

    /**
     * Converts a string to a class where the string represents the class's fully specified name.
     */
    public static class StringToClassConverter extends TypedConverter<String, Class> {

        public StringToClassConverter() {
            super(String.class, Class.class);
        }

        @Override
        public Class convert(String source) {
            try {
                return ClassUtils.getClass(source);
            } catch (Exception e) {
                throw MiscUtil.toUnchecked(e);
            }
        }
    }

    /**
     * Converts an object to a string using its <code>toString</code> method.
     */
    public static class ObjectToStringConverter extends TypedConverter<Object, String> {

        public ObjectToStringConverter() {
            super(Object.class, String.class);
        }

        @Override
        public String convert(Object source) {
            return source instanceof String ? (String) source : source.toString();
        }
    }

    private Converters() {

    }
}
