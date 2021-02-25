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
import org.fujion.gmaps.GoogleMap;
import org.fujion.gmaps.LatLng;
import org.fujion.gmaps.event.CenterChangeEvent;
import org.fujion.gmaps.event.MapLocationEvent;

/**
 * Sample controller to demonstrate Google Maps component.
 */
public class MapsController extends BaseController {

    @WiredComponent
    private GoogleMap gmap;
    
    @WiredComponent
    private Doublebox latitude;
    
    @WiredComponent
    private Doublebox longitude;

    @Override
    public void afterInitialized(BaseComponent root) {
        super.afterInitialized(root);
        updateControls(gmap.getCenter());
        gmap.run();
    }
    
    private void updateControls(LatLng location) {
        latitude.setValue(location.getLatitude());
        longitude.setValue(location.getLongitude());
    }

    @EventHandler(value = "click", target = "btnPanTo")
    private void onClick$btnPanTo() {
        gmap.panTo(currentPos());
    }
    
    @EventHandler(value = MapLocationEvent.TYPE, target = "@gmap")
    private void onClick$gmap(MapLocationEvent event) {
        updateControls(event.getLocation());
    }
    
    @EventHandler(value = CenterChangeEvent.TYPE, target = "@gmap")
    private void onTest$gmap(CenterChangeEvent event) {
        updateControls(event.getLocation());
    }

    private LatLng currentPos() {
        return new LatLng(latitude.getValue(), longitude.getValue());
    }
}
