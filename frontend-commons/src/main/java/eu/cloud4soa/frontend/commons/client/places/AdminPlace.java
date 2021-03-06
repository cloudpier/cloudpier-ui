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

package eu.cloud4soa.frontend.commons.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import eu.cloud4soa.frontend.commons.client.Strings;

/**
 * Contains the tool for administering C4S.
 *
 * @author Stefano Travelli (Cyntelix)
 */
public class AdminPlace extends Place implements UserAware {

    private String userUriId;

    public AdminPlace() {
        this(Strings.EMPTY);
    }

    public AdminPlace(String userUriId) {
        this.userUriId = userUriId;
    }

    @Override
    public String getUserUriId() {
        return userUriId;
    }

    @Prefix("admin")
    public static class Tokenizer implements PlaceTokenizer<AdminPlace> {

        @Override
        public AdminPlace getPlace(String token) {
            return new AdminPlace(token);
        }

        @Override
        public String getToken(AdminPlace place) {
            return Strings.defaultString(place.getUserUriId());
        }
    }
}
