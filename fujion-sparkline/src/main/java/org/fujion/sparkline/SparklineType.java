package org.fujion.sparkline;

import org.apache.commons.lang.StringUtils;
import org.fujion.common.MiscUtil;

/**
 * Available sparkline types.
 */
public enum SparklineType {
    LINE(LinePlot.class), BAR(BarPlot.class), TRISTATE(TristatePlot.class), DISCRETE(DiscretePlot.class), BULLET(
            BulletPlot.class), PIE(PiePlot.class), BOX(BoxPlot.class);

    private Class<? extends AbstractPlot> optionClass;
    
    public static SparklineType fromClass(Class<? extends AbstractPlot> clazz) {
        return SparklineType.valueOf(StringUtils.removeEnd(clazz.getSimpleName().toUpperCase(), "PLOT"));
    }

    SparklineType(Class<? extends AbstractPlot> optionClass) {
        this.optionClass = optionClass;
    }
    
    public AbstractPlot createPlot() {
        try {
            return optionClass.newInstance();
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
