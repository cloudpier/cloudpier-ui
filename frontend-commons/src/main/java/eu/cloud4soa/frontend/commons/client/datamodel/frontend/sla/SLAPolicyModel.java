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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

import eu.cloud4soa.frontend.commons.client.gxt.DynamicFormModel;
import eu.cloud4soa.frontend.commons.server.services.soa.MetadataMapper;

/**
 * GXT model for a C4S SLA Policy
 * 
 * @author Yosu Gorroñogoitia (Atos)
 */
public class SLAPolicyModel extends DynamicFormModel implements
		IsSerializable {

	// Metric Type
	public final static String AVAILABLE_METRICS = "available-metrics";
	
	// Metric Type
	public final static String METRIC_TYPE = "metric-type";

	// Duration
	public final static String DURATION = "duration";

	// Available Durations
	public enum SLAPolicyDurations { 
		ONE_SEC (1), FIVE_SEC (5), TEN_SEC (10), 
		ONE_MIN (1*60, "1 min"), 
		FIVE_MIN(5*60, "5 min"),
		TEN_MIN (10*60, "10 min"),
		ONE_HOUR(60*60, "1 hour"),
		ONE_DAY (24*60*60, "1 day"),
		ONE_WEEK(7*24*60*60, "1 week");
		
		private int duration;
		private String description;
		
		SLAPolicyDurations (int seconds){
			this.duration = seconds * 1000;
			this.description = seconds + " sec";
		}
		
		SLAPolicyDurations(int seconds, String description) {
		    this.duration = seconds * 1000;
		    this.description = description;
		}
		@Override
		public String toString(){
			return description;
		}
		
		public Integer getDuration (){
			return duration;
		}
	};
	List<SLAPolicyDurationModel> availableDurations;
	
	// Number of breaches
	public final static String NUMBER_OF_BREACHES = "number-of-breaches";
	
	// Expression
	public final static String EXPRESSION = "expression";
	
	// Available Expressions
	public enum SLAPolicyExpressions { 
		MIGRATION ("Migration"), WARNING ("Warning"), SOFT_VIOLATION ("Soft violation") ;
		
		private String name;
		SLAPolicyExpressions (String name){
			this.name = name;
		}
		
		@Override
		public String toString(){
			return name;
		}
	};
	List<SLAPolicyExpressionModel> availableExpressions;
	
	public SLAPolicyModel() {
		super("", "", MetadataMapper.FORM_SLA_POLICY);
		generateExpressions();
		generateDurations();
	}

	public SLAPolicyModel(String key, String value) {
		super(key, value, MetadataMapper.FORM_SLA_POLICY);
		generateExpressions();
		generateDurations();
	}

	public List<SLAMetricModel> getAvailableMetrics() {
		return get(AVAILABLE_METRICS);
	}

	public void setAvailableMetrics(List<SLAMetricModel> metrics) {
		set(AVAILABLE_METRICS, metrics);
	}

	public String getMetricType() {
		return get(METRIC_TYPE)!=null?((SLAMetricModel)get(METRIC_TYPE)).getTitle():null;
	}

	public Integer getDuration() {
		return get(DURATION)!=null?((SLAPolicyDurationModel)get(DURATION)).getDuration():null;
	}
	
	public String getNumberOfBreaches() {
		return get(NUMBER_OF_BREACHES);
	}
	
	public String getExpression() {
		return get(EXPRESSION)!=null?((SLAPolicyExpressionModel)get(EXPRESSION)).getTitle():null;
	}

	
	/**
	 * 
	 */
	private void generateExpressions() {
		availableExpressions = new ArrayList<SLAPolicyExpressionModel>(SLAPolicyExpressions.values().length);
		SLAPolicyExpressions[] expressions = SLAPolicyExpressions.values();
		for (int i=0; i<expressions.length; i++){
			SLAPolicyExpressionModel exp = new SLAPolicyExpressionModel();
			exp.setTitle(expressions[i].toString());
			availableExpressions.add(exp);
		}
	}
	
	
	/**
	 * @return
	 */
	public void generateDurations() {
		availableDurations = new ArrayList<SLAPolicyDurationModel>(SLAPolicyDurations.values().length);
		SLAPolicyDurations[] durations = SLAPolicyDurations.values();
		for (int i=0; i<durations.length; i++){
			SLAPolicyDurationModel exp = new SLAPolicyDurationModel();
			exp.setDuration(durations[i]);
			availableDurations.add(exp);
		}
	}

	/**
	 * @return
	 */
	public List<? extends SLAPolicyDurationModel> getDurations() {
		return availableDurations;
	}

	/**
	 * @return
	 */
	public List<? extends SLAPolicyExpressionModel> getExpressions() {
		return availableExpressions;
	}

}
