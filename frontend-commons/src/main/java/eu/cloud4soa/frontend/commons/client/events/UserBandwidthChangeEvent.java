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

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Notify a change in the selected bandwidth on user management widget.
 *
 * @author Stefano Travelli (Cyntelix)
 */
public class UserBandwidthChangeEvent extends GwtEvent<UserBandwidthChangeEvent.Handler>
{
    public interface Handler extends EventHandler
    {
        void onUserBandwidthChanged(UserBandwidthChangeEvent event);
    }

    private String value;

    public UserBandwidthChangeEvent(String value)
    {
        this.value = value;
    }

    public static Type<Handler> TYPE = new Type<Handler>();

    @Override
    protected void dispatch(Handler handler)
    {
        handler.onUserBandwidthChanged(this);
    }

    @Override
    public Type<Handler> getAssociatedType()
    {
        return TYPE;
    }

    public String getValue()
    {
        return value;
    }
}
