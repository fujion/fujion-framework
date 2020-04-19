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
package org.fujion.gmaps;

import java.util.ArrayList;
import java.util.List;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * A collection of selectors and stylers that define how the map should be styled. Selectors specify
 * the map features and/or elements that should be affected, and stylers specify how those features
 * and elements should be modified. Usage details may be found <a href=
 * "https://developers.google.com/maps/documentation/javascript/style-reference#style-elements">here</a>.
 */
public class MapTypeStyleOptions extends Options {
    
    /**
     * The element to which a styler should be applied. An element is a visual aspect of a feature
     * on the map. Example: a label, an icon, the stroke or fill applied to the geometry, and more.
     * Optional. If elementType is not specified, the value is assumed to be 'all'.
     * <p>
     * Elements are subdivisions of a feature. A road, for example, consists of the graphical line
     * (the geometry) on the map, and also the text denoting its name (a label). The following
     * elements are available, but note that a specific feature may support none, some, or all, of
     * the elements:
     * <ul>
     * <li><b>all</b> (default) selects all elements of the specified feature.</li>
     * <li><b>geometry</b> selects all geometric elements of the specified feature.</li>
     * <li><b>geometry.fill</b> selects only the fill of the feature's geometry.</li>
     * <li><b>geometry.stroke</b> selects only the stroke of the feature's geometry.</li>
     * <li><b>labels</b> selects the textual labels associated with the specified feature.</li>
     * <li><b>labels.icon</b> selects only the icon displayed within the feature's label.</li>
     * <li><b>labels.text</b> selects only the text of the label.</li>
     * <li><b>labels.text.fill</b> selects only the fill of the label. The fill of a label is
     * typically rendered as a colored outline that surrounds the label text.</li>
     * <li><b>labels.text.stroke</b> selects only the stroke of the label's text.</li>
     * </ul>
     */
    @Option
    public String elementType;

    /**
     * The feature, or group of features, to which a styler should be applied. Optional. If
     * featureType is not specified, the value is assumed to be 'all'.
     * <p>
     * Features, or feature types, are geographic characteristics on the map, including roads,
     * parks, bodies of water, businesses, and more. The features form a category tree, with all as
     * the root. If you don't specify a feature, all features are selected. Specifying a feature of
     * all has the same effect. Some features contain child features you specify using a dot
     * notation. For example, landscape.natural or road.local. If you specify only the parent
     * feature, such as road, the styles you specify for the parent apply to all its children, such
     * as road.local and road.highway. Note that parent features may include some elements that are
     * not included in all of their child features. The following features are available:
     * <ul>
     * <li><b>all</b> (default) selects all features.</li>
     * <li><b>administrative</b> selects all administrative areas. Styling affects only the labels
     * of administrative areas, not the geographical borders or fill.</li>
     * <li><b>administrative.country</b> selects countries.</li>
     * <li><b>administrative.land_parcel</b> selects land parcels.</li>
     * <li><b>administrative.locality</b> selects localities.</li>
     * <li><b>administrative.neighborhood</b> selects neighborhoods.</li>
     * <li><b>administrative.province</b> selects provinces.</li>
     * <li><b>landscape</b> selects all landscapes.</li>
     * <li><b>landscape.man_made</b> selects structures built by humans.</li>
     * <li><b>landscape.natural</b> selects natural features.</li>
     * <li><b>landscape.natural.landcover</b> selects landcover features.</li>
     * <li><b>landscape.natural.terrain</b> selects terrain features.</li>
     * <li><b>poi</b> selects all points of interest.</li>
     * <li><b>poi.attraction</b> selects tourist attractions.</li>
     * <li><b>poi.business</b> selects businesses.</li>
     * <li><b>poi.government</b> selects government buildings.</li>
     * <li><b>poi.medical</b> selects emergency services, including hospitals, pharmacies, police,
     * doctors, and others.</li>
     * <li><b>poi.park</b> selects parks.</li>
     * <li><b>poi.place_of_worship</b> selects places of worship, including churches, temples,
     * mosques, and others.</li>
     * <li><b>poi.school</b> selects schools.</li>
     * <li><b>poi.sports_complex</b> selects sports complexes.</li>
     * <li><b>road</b> selects all roads.</li>
     * <li><b>road.arterial</b> selects arterial roads.</li>
     * <li><b>road.highway</b> selects highways.</li>
     * <li><b>road.highway.controlled_access</b> selects highways with controlled access.</li>
     * <li><b>road.local</b> selects local roads.</li>
     * <li><b>transit</b> selects all transit stations and lines.</li>
     * <li><b>transit.line</b> selects transit lines.</li>
     * <li><b>transit.station</b> selects all transit stations.</li>
     * <li><b>transit.station.airport</b> selects airports.</li>
     * <li><b>transit.station.bus</b> selects bus stops.</li>
     * <li><b>transit.station.rail</b> selects rail stations.
     * <li><b>water</b> selects bodies of water.</li>
     * </ul>
     */
    @Option
    public String featureType;
    
    /**
     * The style rules to apply to the selected map features and elements.
     * <p>
     * Style rules are applied in the order that you specify. Do not combine multiple operations
     * into a single style operation. Instead, define each operation as a separate entry in the
     * style array. Note: Order is important, as some operations are not commutative. Features
     * and/or elements that are modified through style operations (usually) already have existing
     * styles. The operations act on those existing styles, if present.
     */
    @Option
    public final List<Styler<?>> stylers = new ArrayList<>();
}
