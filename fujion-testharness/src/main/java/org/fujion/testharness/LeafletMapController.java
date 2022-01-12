/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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
package org.fujion.testharness;

import org.fujion.annotation.EventHandler;
import org.fujion.annotation.WiredComponent;
import org.fujion.component.BaseComponent;
import org.fujion.component.Doublebox;
import org.fujion.lmaps.LatLng;
import org.fujion.lmaps.LeafletMap;
import org.fujion.lmaps.LocateOptions;
import org.fujion.lmaps.event.LocationFoundEvent;
import org.fujion.lmaps.event.MapClickEvent;

/**
 * Sample controller to demonstrate Google Maps component.
 */
public class LeafletMapController extends BaseController {

    @WiredComponent
    private LeafletMap lmap;

    @WiredComponent
    private Doublebox latitude;

    @WiredComponent
    private Doublebox longitude;

    @Override
    public void afterInitialized(BaseComponent root) {
        super.afterInitialized(root);
        updateControls(lmap.getCenter());
        lmap.run();
    }

    private void updateControls(LatLng location) {
        latitude.setValue(location.getLatitude());
        longitude.setValue(location.getLongitude());
    }

    @EventHandler(value = "click", target = "btnPanTo")
    private void onClick$btnPanTo() {
        lmap.panTo(currentPos());
    }

    @EventHandler(value = "click", target = "btnReset")
    private void onClick$btnReset() {
        lmap.run();
    }

    @EventHandler(value = "click", target = "btnMyLocation")
    private void onClick$btnMyLocation() {
        LocateOptions options = new LocateOptions();
        options.setView = true;
        options.maximumAge = 60000;
        lmap.locate(options);
    }

    @EventHandler(value = MapClickEvent.TYPE, target = "@lmap")
    private void onClick$lmap(MapClickEvent event) {
        updateControls(event.getLocation());
    }

    @EventHandler(value = LocationFoundEvent.TYPE, target = "@lmap")
    private void onLocationFound$lmap(LocationFoundEvent event) {
        updateControls(event.getLocation());
    }

    private LatLng currentPos() {
        return new LatLng(latitude.getValue(), longitude.getValue());
    }

}
