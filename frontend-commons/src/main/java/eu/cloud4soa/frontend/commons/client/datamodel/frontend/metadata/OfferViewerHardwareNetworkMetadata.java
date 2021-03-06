/*
 * This file is part of Cloud4SOA Frontend.
 *
 *     Cloud4SOA Frontend is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cloud4SOA Frontend is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cloud4SOA Frontend.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.cloud4soa.frontend.commons.client.datamodel.frontend.metadata;

import static eu.cloud4soa.frontend.commons.client.datamodel.frontend.EditType.COMBOBOX;
import static eu.cloud4soa.frontend.commons.client.datamodel.frontend.EditType.EDIT_MEASURE_RANGE;
import static eu.cloud4soa.frontend.commons.client.datamodel.frontend.FieldMetadata.COMBO_COMMUNICATION_CATEGORY;
import static eu.cloud4soa.frontend.commons.client.datamodel.frontend.application.HardwareComponentModel.BANDWIDTH;
import static eu.cloud4soa.frontend.commons.client.datamodel.frontend.application.HardwareComponentModel.HARDWARE_CATEGORY;
import static eu.cloud4soa.frontend.commons.client.datamodel.frontend.application.HardwareComponentModel.LATENCY;

import com.google.gwt.user.client.rpc.IsSerializable;

import eu.cloud4soa.frontend.commons.client.datamodel.frontend.EntityMetadata;
import eu.cloud4soa.frontend.commons.client.datamodel.frontend.FieldMetadata;

/**
 * Offer editor network resource metadata.
 * 
 * @author Stefano Travelli (Cyntelix)
 */
public class OfferViewerHardwareNetworkMetadata extends EntityMetadata
		implements IsSerializable {

	public OfferViewerHardwareNetworkMetadata() {

		field(FieldMetadata.create(HARDWARE_CATEGORY).label("Category")
				.editType(COMBOBOX).tooltip("Computation component category")
				.relatedEntityType(COMBO_COMMUNICATION_CATEGORY).readonly(true));

		// latency
		field(FieldMetadata.create(LATENCY).label("Latency")
				.editType(EDIT_MEASURE_RANGE)
				.tooltip("Type the offered latency").readonly(true));

		// bandwidth
		field(FieldMetadata.create(BANDWIDTH).label("Bandwidth")
				.editType(EDIT_MEASURE_RANGE)
				.tooltip("Type the required min bandwidth").readonly(true));
	}
}
