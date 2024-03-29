package org.fujion.convert;

import org.fujion.common.Assert;
import org.fujion.common.StrUtil;
import org.fujion.component.BaseComponent;
import org.fujion.component.Page;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.ObjectUtils;
import org.w3c.dom.Element;

import java.util.*;

/**
 * Handles type conversions.  Encapsulates Spring's default conversion service and default converters.  This is a
 * singleton instance.
 */
public class ConversionService {

    private static final ConversionService instance = new ConversionService();

    public static ConversionService getInstance() {
        return instance;
    }

    private final DefaultConversionService service = (DefaultConversionService) DefaultConversionService.getSharedInstance();

    /**
     * Registers additional converters.
     */
    private ConversionService() {
        registerConverter(new Converters.JavaScriptConverter());
        registerConverter(new Converters.StringToClassConverter());
        registerConverter(new Converters.ObjectToStringConverter());
    }

    public <T, S> void registerConverter(TypedConverter<T, S> converter) {
        service.addConverter(converter.getSourceType(), converter.getTargetType(), converter);
    }

    /**
     * Converts an input value to a target type.
     *
     * @param <T>        The target type.
     * @param value      The value to convert.
     * @param targetType The type to which to convert.
     * @return The converted value.
     */
    public <T> T convert(Object value, Class<T> targetType) {
        return convert(value, targetType, null);
    }

    /**
     * Converts an input value to a target type.
     *
     * @param <T>        The target type.
     * @param value      The value to convert.
     * @param targetType The type to which to convert.
     * @param instance   The object instance whose property value is to be set (necessary when the
     *                   target type is a component and the value is the component name or id).
     * @return The converted value.
     */
    @SuppressWarnings("unchecked")
    public <T> T convert(Object value, Class<T> targetType, Object instance) {
        if (value == null || targetType == null || targetType.isInstance(value)) {
            return (T) value;
        }

        if (targetType.isEnum()) {
            return (T) convertToEnum(value, targetType);
        }

        if (BaseComponent.class.isAssignableFrom(targetType)) {
            return (T) convertToComponent(value, targetType, instance);
        }

        return service.convert(value, targetType);
    }

    /**
     * Converts the input value to an enumeration member. The input value must resolve to a string
     * which is then matched to an enumeration member by using a case-insensitive lookup.
     *
     * @param value    The value to convert.
     * @param enumType The enumeration type.
     * @return The enumeration member corresponding to the input value.
     */
    private Object convertToEnum(Object value, Class<?> enumType) {
        String val = convert(value, String.class, null);

        for (Object e : enumType.getEnumConstants()) {
            if (((Enum<?>) e).name().equalsIgnoreCase(val)) {
                return e;
            }
        }

        Assert.fail("The value \"%s\" is not a member of the enumeration %s", value, enumType.getName());
        return null;
    }

    /**
     * Converts the input value to component. The input value must resolve to a string which
     * represents the name or id of the component sought. This name is resolved to a component
     * instance by looking it up in the namespace of the provided component instance.
     *
     * @param value         The value to convert.
     * @param componentType The component type.
     * @param instance      The component whose namespace will be used for lookup.
     * @return The component whose name matches the input value.
     */
    private BaseComponent convertToComponent(Object value, Class<?> componentType, Object instance) {
        if (!(instance instanceof BaseComponent)) {
            Assert.fail("The property owner is not of the expected type (was %s but expected %s)",
                    instance.getClass().getName(), BaseComponent.class.getName());
        }

        String name = convert(value, String.class, instance);

        if (name.trim().isEmpty()) {
            return null;
        }

        BaseComponent container = (BaseComponent) instance;
        BaseComponent target = name.startsWith(Page.ID_PREFIX) ? container.getPage().findById(name)
                : container.findByName(name);
        Assert.notNull(target, "A component with name or id \"%s\" was not found", name);
        Assert.isTrue(componentType.isInstance(target),
                "The component with name or id \"%s\" is not of the expected type (was %s but expected %s)",
                name, target.getClass().getName(), componentType.getName());
        return target;
    }

    /**
     * Converts an arbitrary value to an iterable type.
     *
     * @param value Value to convert. This may be any iterable, an array, a map (in which case the
     *              map's entry set is used), a scalar value, or null.
     * @return The value as an iterable, or null if the value is null.
     */
    public Iterable<?> convertToIterable(Object value) {
        return value == null ? null
                : value instanceof Iterable ? (Iterable<?>) value
                : ObjectUtils.isArray(value) ? Arrays.asList(ObjectUtils.toObjectArray(value))
                : value instanceof Map ? ((Map<?, ?>) value).entrySet() : Collections.singletonList(value);
    }

    /**
     * Converts an array of string values to a set. This effectively removes duplicate and empty
     * entries.
     *
     * @param values      Array of string values.
     * @param ignoreEmpty If true, empty elements are ignored.
     * @return Corresponding set of values.
     */
    public Set<String> convertToSet(String[] values, boolean ignoreEmpty) {
        Set<String> set = new HashSet<>();

        for (String value : values) {
            if (value != null && (!ignoreEmpty || !value.isEmpty())) {
                set.add(value);
            }
        }

        return set;
    }

    public Object[] convertArgs(Object instance, Class<?>[] parameterTypes, Object... args) {
        int arglen = args == null ? 0 : args.length;

        Assert.isTrue(arglen == parameterTypes.length, () -> StrUtil.formatMessage("Incorrect number of arguments (provided %d but expected %d)", arglen,
                parameterTypes.length));

        Object[] out = new Object[arglen];

        for (int i = 0; i < arglen; i++) {
            out[i] = convert(args[i], parameterTypes[i], instance);
        }

        return out;
    }

    /**
     * Returns an attribute value from an XML element, coercing it to the requested type.
     *
     * @param <T>           The target type.
     * @param element       The XML element.
     * @param attributeName The attribute name.
     * @param targetType    The target type.
     * @return The attribute value, coerced to the requested type.
     */
    public <T> T getAttributeAs(Element element, String attributeName, Class<T> targetType) {
        return convert(element.getAttribute(attributeName), targetType);
    }

    /**
     * Returns an attribute value from a map, coercing it to the requested type.
     *
     * @param <T>           The target type.
     * @param map           The map.
     * @param attributeName The attribute name.
     * @param targetType    The target type.
     * @return The attribute value, coerced to the requested type.
     */
    public <T> T getAttributeAs(Map<?, ?> map, String attributeName, Class<T> targetType) {
        return convert(map.get(attributeName), targetType);
    }

}
