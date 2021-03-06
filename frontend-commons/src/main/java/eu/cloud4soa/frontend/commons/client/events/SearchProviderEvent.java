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

package eu.cloud4soa.frontend.commons.client.events;

import java.util.List;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import eu.cloud4soa.frontend.commons.client.datamodel.frontend.application.ApplicationModel;
import eu.cloud4soa.frontend.commons.client.datamodel.frontend.paas.PaaSOfferingModel;

public class SearchProviderEvent extends GwtEvent<SearchProviderEvent.Handler>{

	
	public interface Handler extends EventHandler{
		void onProviderSearchRetrieved(SearchProviderEvent s);
	}
	
	public static final GwtEvent.Type<SearchProviderEvent.Handler> TYPE = new GwtEvent.Type<SearchProviderEvent.Handler>();

	@Override
	protected void dispatch(Handler handler) {
		handler.onProviderSearchRetrieved(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
	
	private List<PaaSOfferingModel> offeringList;

    private ApplicationModel application;
	
	public List<PaaSOfferingModel> getOfferingList(){
		return this.offeringList;
	}

    public ApplicationModel getApplication() {
        return application;
    }

    public SearchProviderEvent(ApplicationModel application, List<PaaSOfferingModel> offeringList) {
        this.application = application;
        this.offeringList = offeringList;
    }

}
