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
package org.fujion.multimedia;

import org.fujion.annotation.Component;
import org.fujion.common.MiscUtil;
import org.fujion.component.BaseUIComponent;

/**
 * Base class for multimedia components.
 */
public abstract class BaseMultimediaComponent extends BaseUIComponent {

    private String src;

    private boolean muted;

    private boolean controls;

    private boolean loop;

    private double volume = 0.5;

    private double rate = 1.0;

    public BaseMultimediaComponent() {
        super();
    }

    /**
     * Fade volume over specified range and duration.
     *
     * @param from     Starting volume level (0 - 1).
     * @param to       Ending volume level (0 - 1).
     * @param duration Duration of fade operation (milliseconds).
     */
    public void fade(
            double from,
            double to,
            int duration) {
        _validateVolume(from, "From");
        _validateVolume(to, "To");
        invoke("fade", from, to, duration);
    }

    /**
     * Starts the audio track.
     */
    public void play() {
        invoke("play");
    }

    /**
     * Pauses the audio track.
     */
    public void pause() {
        invoke("pause");
    }

    /**
     * Sets the position of playback for the track.
     *
     * @param position Playback position in seconds.
     */
    public void seek(int position) {
        invoke("seek", position);
    }

    /**
     * Stops the audio track.
     */
    public void stop() {
        invoke("stop");
    }

    @Component.PropertyGetter(
            value = "src",
            description = "The URL of the external audio source."
    )
    public String getSrc() {
        return this.src;
    }

    @Component.PropertySetter(
            value = "src",
            description = "The URL of the external audio source."
    )
    public void setSrc(String src) {
        propertyChange("src", this.src, this.src = nullify(src), true);
    }

    @Component.PropertyGetter(
            value = "muted",
            description = "True to mute the audio track."
    )
    public boolean getMuted() {
        return muted;
    }

    @Component.PropertySetter(
            value = "muted",
            defaultValue = "false",
            description = "True to mute the audio track."
    )
    public void setMuted(boolean muted) {
        propertyChange("muted", this.muted, this.muted = muted, true);
    }

    @Component.PropertyGetter(
            value = "volume",
            description = "The volume (0 - 1) of the audio source."
    )
    public double getVolume() {
        return volume;
    }

    @Component.PropertySetter(
            value = "volume",
            defaultValue = "50",
            description = "The volume (0 - 1) of the audio source."
    )
    public void setVolume(double volume) {
        _validateVolume(volume, "Volume");
        propertyChange("volume", this.volume, this.volume = volume, true);
    }

    @Component.PropertyGetter(
            value = "loop",
            description = "True to loop the audio track."
    )
    public boolean getLoop() {
        return loop;
    }

    @Component.PropertySetter(
            value = "loop",
            defaultValue = "false",
            description = "True to loop the audio track."
    )
    public void setLoop(boolean loop) {
        propertyChange("loop", this.loop, this.loop = loop, true);
    }

    @Component.PropertyGetter(
            value = "rate",
            description = "The playback speed (0.5 - 4.0)."
    )
    public double getRate() {
        return rate;
    }

    @Component.PropertySetter(
            value = "rate",
            defaultValue = "1.0",
            description = "The playback speed (0.5 - 4.0)."
    )
    public void setRate(double rate) {
        MiscUtil.assertTrue(rate >= 0.5 && rate <= 4.0, () -> "Rate must be between 0.5 and 4.0, inclusive");
        propertyChange("rate", this.rate, this.rate = rate, true);
    }

    @Component.PropertyGetter(
            value = "controls",
            description = "If true, show multimedia controls."
    )
    public boolean getControls() {
        return controls;
    }

    @Component.PropertySetter(
            value = "controls",
            defaultValue = "false",
            description = "If true, show multimedia controls."
    )
    public void setControls(boolean controls) {
        propertyChange("controls", this.controls, this.controls = controls, true);
    }

    private void _validateVolume(
            double value,
            String propName) {
        MiscUtil.assertTrue(value >= 0.0 && value <= 1.0, () -> propName + " must be between 0 and 1, inclusive");
    }

}
