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

package eu.cloud4soa.frontend.commons.client.datamodel.frontend.sla;

import com.google.gwt.user.client.rpc.IsSerializable;

import eu.cloud4soa.frontend.commons.client.datamodel.frontend.sla.SLAPolicyModel.SLAPolicyDurations;
import eu.cloud4soa.frontend.commons.client.gxt.DisplayableKeyValueModelData;
import eu.cloud4soa.frontend.commons.client.gxt.WithTitle;

/**
 * GXT model for a C4S SLA Policy
 * 
 * @author Yosu Gorroñogoitia (Atos)
 */
public class SLAPolicyDurationModel extends DisplayableKeyValueModelData implements
		IsSerializable, WithTitle {

    public SLAPolicyDurationModel() {
		
	}
	
	@Override
    public String getTitle() {
        return get(TITLE);
    }

    @Override
    public void setTitle(String title) {
        set(TITLE, title);
    }
    
    public Integer getDuration (){
    	return get ("duration");
    }
    
    public void setDuration (SLAPolicyDurations duration){
    	setTitle(duration.toString());
    	set("duration", duration.getDuration());
    }
}
