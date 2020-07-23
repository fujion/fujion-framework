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
package org.fujion.sparkline;

import org.apache.commons.lang3.StringUtils;
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
