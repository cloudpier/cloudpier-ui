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

package eu.cloud4soa.frontend.commons.client.datamodel.frontend.user;

import com.google.gwt.user.client.rpc.IsSerializable;

import eu.cloud4soa.frontend.commons.client.gxt.DynamicFormModel;
import eu.cloud4soa.frontend.commons.server.services.soa.MetadataMapper;

/**
 * GXT Model for the user credentials for paas.
 * 
 * @author Yosu Gorroñogoitia (Atos)
 */
public class UserCredentialsModel extends DynamicFormModel implements
		IsSerializable {

	public static final String USER_URIID = "userUriId";
	public static final String PAAS_URIID = "paasUriId";
	public static final String PUBLIC_KEY = "publicKey";
	public static final String PRIVATE_KEY = "privateKey";
	public static final String ACCOUNT_NAME = "accountName";

	public UserCredentialsModel() {
	}

	public UserCredentialsModel(String key, String value) {
		super(key, value, MetadataMapper.USER_PAAS_GENERIC_CREDENTIALS);
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;

		if (o instanceof UserCredentialsModel) {
			result = ((UserCredentialsModel) o).get(USER_URIID).equals(
					this.get(USER_URIID))
					&& ((UserCredentialsModel) o).get(PAAS_URIID).equals(
							this.get(PAAS_URIID));
		}

		return result;
	}
	
	//Object clone is not supported by GWT
	public UserCredentialsModel createClone(){
		UserCredentialsModel result = new UserCredentialsModel();
		result.set(USER_URIID, this.get(USER_URIID));
		result.set(PAAS_URIID, this.get(PAAS_URIID));
		result.set(PUBLIC_KEY, this.get(PUBLIC_KEY));
		result.set(PRIVATE_KEY, this.get(PRIVATE_KEY));
		result.set(ACCOUNT_NAME, this.get(ACCOUNT_NAME));
		return result;
	}
}
