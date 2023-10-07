package org.fujion.convert;

import org.springframework.core.convert.converter.Converter;

/**
 * Base implementation of a converter that exposes the source and target types.
 *
 * @param <S> The source type.
 * @param <T> The target type.
 */
public abstract class TypedConverter<S, T> implements Converter<S, T> {

    private final Class<S> sourceType;

    private final Class<T> targetType;

    protected TypedConverter(Class<S> sourceType, Class<T> targetType) {
        this.sourceType = sourceType;
        this.targetType = targetType;
    }

    public Class<S> getSourceType() {
        return sourceType;
    }

    public Class<T> getTargetType() {
        return targetType;
    }
}
