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

package eu.cloud4soa.frontend.commons.client.datamodel.frontend.monitoring;

import java.io.Serializable;
import java.util.Date;


/** 
 * Statistic object with metrics
 * 
 * @author yosu (jesus.gorronogoitia@atosresearch.eu) Adaptation to GWT
 */
public class MonitoringMetric implements IMonitoringMetric, Serializable{
	private static final long serialVersionUID = -3343340281569961762L;

	private Date date;
	private MonitoringMetricType metricKey;
	private double metricValue;
	
	@Override
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public MonitoringMetricType getMetricKey() {
		return this.metricKey;
	}
	
	public void setMetricKey(MonitoringMetricType key) {
		this.metricKey = key;
	}

	@Override
	public double getMetricValue() {
		return this.metricValue;
	}

	public void setMetricValue(double value) {
		this.metricValue = value;
	}

	@Override
	public String toString() {
		return "MonitoringMetric [date=" + date.toString() + ", key= " + metricKey + ", value=" + metricValue + "]";
	}

}
