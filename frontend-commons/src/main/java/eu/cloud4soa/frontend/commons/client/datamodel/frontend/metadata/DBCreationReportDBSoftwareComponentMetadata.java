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

/*******************************************************************************
 * Copyright (c) 2012 Atos Spain S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the LGPLv3 Lesser General Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Contributors:
 *     Atos Spain S.A. - initial API and implementation
 *******************************************************************************/
package eu.cloud4soa.frontend.commons.client.datamodel.frontend.metadata;

import static eu.cloud4soa.frontend.commons.client.datamodel.frontend.EditType.TEXT;

import com.google.gwt.user.client.rpc.IsSerializable;

import eu.cloud4soa.frontend.commons.client.datamodel.frontend.EntityMetadata;
import eu.cloud4soa.frontend.commons.client.datamodel.frontend.FieldMetadata;
import eu.cloud4soa.frontend.commons.client.datamodel.frontend.application.SoftwareComponentModel;

/**
 * Project: c4s-frontend-commons
 * 
 * @author "Yosu Gorroñogoitia" Creation time: Sep 25, 2012
 */
public class DBCreationReportDBSoftwareComponentMetadata extends EntityMetadata
		implements IsSerializable {
	public DBCreationReportDBSoftwareComponentMetadata() {

		field(FieldMetadata.create(SoftwareComponentModel.DB_NAME)
				.label("Database Name").editType(TEXT).tooltip("Database name")
				.readonly(true));
		field(FieldMetadata.create(SoftwareComponentModel.DB_USER)
				.label("Database User").editType(TEXT).tooltip("Database user")
				.readonly(true));
		field(FieldMetadata.create(SoftwareComponentModel.DB_HOST)
				.label("Database Host").editType(TEXT).tooltip("Database Host")
				.readonly(true));
		field(FieldMetadata.create(SoftwareComponentModel.DB_PORT)
				.label("Database Port").editType(TEXT).tooltip("Database Port")
				.readonly(true));
		field(FieldMetadata
				.create(SoftwareComponentModel.DB_DEPLOYMENT_LOCATION_URL)
				.label("Database URL").editType(TEXT).tooltip("Database URL")
				.readonly(true));

	}
}
