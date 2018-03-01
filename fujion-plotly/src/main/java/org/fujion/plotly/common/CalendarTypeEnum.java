package org.fujion.plotly.common;

/**
 * Available calendar types.
 */
public enum CalendarTypeEnum {

    GREGORIAN, CHINESE, COPTIC, DISCWORLD, ETHIOPIAN, HEBREW, ISLAMIC, JULIAN, MAYAN, NANAKSHAHI, NEPALI, PERSIAN, JALALI, TAIWAN, THAI, UMMALQURA;
    
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
