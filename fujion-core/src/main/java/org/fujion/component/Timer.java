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
package org.fujion.component;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A timer component. Once started, fires a timer event at the specified interval and repeating for
 * the specified number of repetitions.
 */
@Component(tag = "timer", widgetClass = "Timer", parentTag = "*", description = "A timer component.")
public class Timer extends BaseComponent {

    private long interval;

    private int repeat = -1;

    private boolean running;

    /**
     * Returns the interval, in milliseconds, between timer events.
     *
     * @return The timer interval in milliseconds.
     */
    @PropertyGetter(value = "interval", description = "The timer interval in milliseconds.")
    public long getInterval() {
        return interval;
    }

    /**
     * Sets the interval, in milliseconds, between timer events.
     *
     * @param interval The timer interval in milliseconds. Any value less than or equal to zero will
     *            disable the timer.
     */
    @PropertySetter(value = "interval", defaultValue = "0", description = "The timer interval in milliseconds.")
    public void setInterval(long interval) {
        propertyChange("interval", this.interval, this.interval = interval, true);
    }

    /**
     * Returns the repetition count. This is the number of times the timer will fire additional
     * events after the initial event. A value of zero will cause the timer to fire only once (no
     * repetitions). A negative value will cause the timer to fire indefinitely.
     *
     * @return The repetition count.
     */
    @PropertyGetter(value = "repeat", description = "The number of times the timer will fire additional events after the initial event.")
    public int getRepeat() {
        return repeat;
    }

    /**
     * Sets the repetition count. This is the number of times the timer will fire additional events
     * after the initial event. A value of zero will cause the timer to fire only once (no
     * repetitions). A negative value will cause the timer to fire indefinitely.
     *
     * @param repeat The repetition count.
     */
    @PropertySetter(value = "repeat", defaultValue = "-1", description = "The number of times the timer will fire additional events after the initial event.")
    public void setRepeat(int repeat) {
        propertyChange("repeat", this.repeat, this.repeat = repeat, true);
    }

    /**
     * Returns true if the timer is running.
     *
     * @return True if the timer is running.
     */
    @PropertyGetter(value = "running", description = "True if the timer is running.")
    public boolean isRunning() {
        return running;
    }

    /**
     * Sets the run state of the timer.
     *
     * @param running The run state of the timer. Set to true to start the timer, false to stop it.
     */
    @PropertySetter(value = "running", defaultValue = "false", description = "True if the timer is running.")
    public void setRunning(boolean running) {
        if (interval > 0) {
            propertyChange("running", this.running, this.running = running, true);
        }
    }

    /**
     * A convenience method to start the timer.
     */
    public void start() {
        setRunning(true);
    }

    /**
     * A convenience method to stop the timer.
     */
    public void stop() {
        setRunning(false);
    }

    /**
     * A convenience method for restarting the timer.
     */
    public void restart() {
        stop();
        start();
    }
}
