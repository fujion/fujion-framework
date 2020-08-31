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
package org.fujion.audio;

import org.fujion.annotation.Component;
import org.fujion.common.MiscUtil;
import org.fujion.component.BaseComponent;

/**
 * Fujion wrapper for howler.js library.
 */
@Component(
        tag = "audio",
        widgetModule = "fujion-audio",
        widgetClass = "Audio",
        parentTag = "*",
        description = "Fujion wrapper for howler.js library.")
public class Audio extends BaseComponent {

    private String src;

    private boolean mute;

    private int volume = 50;

    private boolean html5 = true;

    private boolean loop;

    private double rate = 1.0;

    public Audio() {
        super();
    }

    /**
     * Fade volume over specified range and duration.
     *
     * @param from     Starting volume level (0 - 100).
     * @param to       Ending volume level (0 - 100).
     * @param duration Duration of fade operation (milliseconds).
     */
    public void fade(
            int from,
            int to,
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
     * @param position Playback position in milliseconds.
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
        src = this.nullify(src);
        propertyChange("src", this.src, this.src = src, true);
    }

    @Component.PropertyGetter(
            value = "mute",
            description = "True to mute the audio track."
    )
    public boolean isMute() {
        return mute;
    }

    @Component.PropertySetter(
            value = "mute",
            defaultValue = "false",
            description = "True to mute the audio track."
    )
    public void setMute(boolean mute) {
        propertyChange("mute", this.mute, this.mute = mute, true);
    }

    @Component.PropertyGetter(
            value = "volume",
            description = "The volume (0 - 100) of the audio source."
    )
    public int getVolume() {
        return volume;
    }

    @Component.PropertySetter(
            value = "volume",
            defaultValue = "50",
            description = "The volume (0 - 100) of the audio source."
    )
    public void setVolume(int volume) {
        _validateVolume(volume, "Volume");
        propertyChange("volume", this.volume, this.volume = volume, true);
    }

    @Component.PropertyGetter(
            value = "html5",
            description = "True to force HTML5 audio."
    )
    public boolean isHtml5() {
        return html5;
    }

    @Component.PropertySetter(
            value = "html5",
            defaultValue = "true",
            description = "True to force HTML5 audio."
    )
    public void setHtml5(boolean html5) {
        propertyChange("html5", this.html5, this.html5 = html5, true);
    }

    @Component.PropertyGetter(
            value = "loop",
            description = "True to loop the audio track."
    )
    public boolean isLoop() {
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

    private void _validateVolume(
            int value,
            String propName) {
        MiscUtil.assertTrue(value >= 0 && value <= 100, () -> propName + " must be between 0 and 100, inclusive");
    }

}
