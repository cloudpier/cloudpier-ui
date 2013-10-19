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

import com.google.gwt.user.client.rpc.IsSerializable;
import eu.cloud4soa.frontend.commons.client.datamodel.frontend.EditType;
import eu.cloud4soa.frontend.commons.client.datamodel.frontend.EntityMetadata;
import eu.cloud4soa.frontend.commons.client.datamodel.frontend.FieldMetadata;

import static eu.cloud4soa.frontend.commons.client.datamodel.frontend.EditType.COMBOBOX;
import static eu.cloud4soa.frontend.commons.client.datamodel.frontend.FieldMetadata.COMBO_STORAGE_CATEGORY;
import static eu.cloud4soa.frontend.commons.client.datamodel.frontend.application.HardwareComponentModel.*;
import static eu.cloud4soa.frontend.commons.client.datamodel.frontend.application.RangeModel.MIN;
import static eu.cloud4soa.frontend.commons.client.datamodel.frontend.application.RangeModel.REQUIRED;

/**
 * Storage resource metadata.
 *
 * @author Stefano Travelli (Cyntelix)
 */
public class ApplicationEditorHardwareStorageMetadata extends EntityMetadata implements IsSerializable {

    public ApplicationEditorHardwareStorageMetadata() {

        field(FieldMetadata.create(HARDWARE_CATEGORY)
                .label("Storage category")
                .editType(COMBOBOX)
                .tooltip("Type of storage")
                .relatedEntityType(COMBO_STORAGE_CATEGORY)
        );

        // bandwidth
        field(FieldMetadata.create(BANDWIDTH, MIN)
                .label("Bandwidth")
                .editType(EditType.EDIT_MEASURE_VALUE)
                .withRequiredFlag(BANDWIDTH, REQUIRED)
                .allowDecimals(false)
                .allowNegative(false)
                .tooltip("Mininum bandwidth required for the communication with the storage component")
        );

        // capacity
        field(FieldMetadata.create(CAPACITY, MIN)
                .label("Capacity")
                .editType(EditType.EDIT_MEASURE_VALUE)
                .withRequiredFlag(CAPACITY, REQUIRED)
                .allowDecimals(false)
                .allowNegative(false)
                .tooltip("Minimum capacity of the storage component")
        );

    }
}
