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

package eu.cloud4soa.frontend.commons.server.services.soa;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import eu.cloud4soa.api.datamodel.core.qos.CPULoadInstance;
import eu.cloud4soa.api.datamodel.core.qos.CloudResponseTimeInstance;
import eu.cloud4soa.api.datamodel.core.qos.ContainerResponseTimeInstance;
import eu.cloud4soa.api.datamodel.core.qos.LatencyInstance;
import eu.cloud4soa.api.datamodel.core.qos.MemoryLoadInstance;
import eu.cloud4soa.api.datamodel.core.qos.ServiceQualityInstance;
import eu.cloud4soa.api.datamodel.core.qos.UptimeInstance;
import eu.cloud4soa.api.governance.monitoring.IMonitoringMetric.MetricKey;
import eu.cloud4soa.api.util.exception.soa.SOAException;
import eu.cloud4soa.frontend.commons.client.datamodel.frontend.monitoring.IMonitoringMetric;
import eu.cloud4soa.frontend.commons.client.datamodel.frontend.monitoring.MonitoringMetric;
import eu.cloud4soa.frontend.commons.client.datamodel.frontend.monitoring.MonitoringMetricType;
import eu.cloud4soa.frontend.commons.client.services.soa.MonitoringService;
import eu.cloud4soa.soa.MonitoringModule;

/**
 * @author Yosu Gorroñogoitia (Atos)
 */
@SuppressWarnings({ "GwtServiceNotRegistered" })
@Secured("IS_AUTHENTICATED_FULLY")
public class MonitoringServiceImpl extends RemoteServiceServlet implements
		MonitoringService {
	final Logger logger = LoggerFactory.getLogger(MonitoringServiceImpl.class);

    @Qualifier("soaMonitoringModule")
    @Autowired
	private MonitoringModule monitoringModule;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}

	@Override
	public void startMonitoring(String applicationUriId) {
		logger.debug("Starting monitoring for application " + applicationUriId);

		try {
			monitoringModule.startMonitoring(applicationUriId);
		} catch (SOAException e) {
			String msg = "Error in startMonitoring for application "
					+ applicationUriId;
			logger.error(msg, e);
			throw new RuntimeException(msg, e);
		}
	}

	@Override
	public void stopMonitoring(String applicationUriId) {
		logger.debug("Stopping monitoring for application " + applicationUriId);

		try {
			monitoringModule.stopMonitoring(applicationUriId);
		} catch (SOAException e) {
			String msg = "Error in stopMonitoring for application "
					+ applicationUriId;
			logger.error(msg, e);
			throw new RuntimeException(msg, e);
		}
	}


	private Map<MonitoringMetricType, List<IMonitoringMetric>> getMonitoringStatisticsWhithinRangeLimited(
			String applicationUriId, String paasOfferingId, Date start, Date end, int maxResults,
			int monitoringMaxNumDisplayableValues) {
		logger.debug("Getting monitoring statistics for application "
				+ applicationUriId + " between dates " + start.toString()
				+ " and " + end.toString() + " with maxResults " + maxResults);

		Map<MonitoringMetricType, List<IMonitoringMetric>> tempResult = new HashMap<MonitoringMetricType, List<IMonitoringMetric>>();
		
		try {
			//Getting statistics 
			//Obtaining supported metrics
            List<ServiceQualityInstance> sms = monitoringModule.getSupportedMetrics(paasOfferingId);
    		List<MonitoringMetricType> supportedMetrics = translateServiceQualityInstance(sms);
			
    		for (MonitoringMetricType supportedMetric: supportedMetrics){
    			List<IMonitoringMetric> metrics = getMonitoringMetricsWhithinRangeLimited (applicationUriId, supportedMetric, start, end, maxResults, monitoringMaxNumDisplayableValues);
    			tempResult.put (supportedMetric, metrics);
    		}
			
		} catch (SOAException e) {
			String msg = "Error in getMonitoringStatisticsWhithinRangeLimited for application "
					+ applicationUriId;
			logger.error(msg, e);
			throw new RuntimeException(msg, e);
		}
		
		//Processing metrics 
		Map<MonitoringMetricType, List<IMonitoringMetric>> result = new HashMap<MonitoringMetricType, List<IMonitoringMetric>>();
		for (MonitoringMetricType type: tempResult.keySet()){
			List<IMonitoringMetric> metrics = tempResult.get(type);
			
			// Invert (time scale).
			metrics = invertTimeScale(metrics);
	
			// Filtering not valid statistics
			metrics = filterNotValidMetrics(metrics);
	
			// Resampling statistics
			metrics = resamplingStatistics(metrics,
					monitoringMaxNumDisplayableValues);
			
			result.put(type, metrics);
		}
		
		return result;
	}
	
	public List<IMonitoringMetric> getMonitoringMetricsWhithinRangeLimited(
			String applicationUriId, MonitoringMetricType metricKey, Date start, Date end, int maxResults,
			int monitoringMaxNumDisplayableValues) {
		logger.debug("Getting monitoring metrics for application "
				+ applicationUriId + " and metric " + metricKey + " between dates " + start.toString()
				+ " and " + end.toString() + " with maxResults " + maxResults);

		List<eu.cloud4soa.api.governance.monitoring.IMonitoringMetric> monitoringMetrics;
		try {
			monitoringMetrics = monitoringModule
					.getMonitoringMetricsWhithinRangeLimited(applicationUriId, getMetricKey(metricKey), start, end, maxResults);

			return translateMonitoringMetrics(metricKey, monitoringMetrics);
		} catch (SOAException e) {
			String msg = "Error in getMonitoringStatisticsWhithinRangeLimited for application "
					+ applicationUriId;
			logger.error(msg, e);
			throw new RuntimeException(msg, e);
		}
	}

	/**
	 * Helperclass to obtain the metrickey from MetricKey
	 * @param metricKey
	 * @return
	 */
	private MetricKey getMetricKey(MonitoringMetricType metricKey) {
		MetricKey result = null;
		switch (metricKey){
		case CloudResponseTimeInstance:
			result = MetricKey.CloudResponseTime;
			break;
		case ContainerResponseTimeInstance:
			result = MetricKey.ContainerResponseTime;
			break;
		case CPULoadInstance:
			result = MetricKey.CPU_Load;
			break;
		case MemoryLoadInstance:
			result = MetricKey.Memory_Load;
			break;
		case LatencyInstance:
			result = MetricKey.responseTime;
			break;
		case UptimeInstance:
			result = MetricKey.statusCode;
			break;
		}
		return result;
	}

	private <T> List<T> resamplingStatistics(List<T> list,
			int monitoringMaxNumDisplayableValues) {

		if (list.size() <= monitoringMaxNumDisplayableValues)
			return list; // Resampling not required

		List<T> result = new ArrayList<T>();

		float delta = (float) list.size()
				/ (float) monitoringMaxNumDisplayableValues;
		float i = 0;
		while (i < list.size()) {
			result.add(list.get((int) Math.round(Math.floor(i))));
			i += delta;
		}

		return result;
	}
	
	/**
	 * Monitoring module offers monitoring metrics/statistics in time reverse order (for
	 * presentation), newest times before. This method reverts the time scale,
	 * starting with oldest times.
	 * 
	 * @param list Monitoring metrics/statistics
	 * @return
	 */
	private <T> List<T> invertTimeScale(List<T> list) {
		List<T> result = new ArrayList<T>();

		for (int i = list.size() - 1; i >= 0; i--)
			result.add(list.get(i));

		return result;
	}

	@Override
	public Map<String, Map<MonitoringMetricType, List<IMonitoringMetric>>> getMonitoringStatisticsWhithinRangeLimited(
			Map<String, String> appData, Date start, Date end, int maxResults, int monitoringMaxNumDisplayableValues) {
		logger.debug("Getting monitoring statistics for selected applications"
				+ " between dates " + start.toString() + " and "
				+ end.toString() + " with maxResults " + maxResults);

		Map<String, Map<MonitoringMetricType, List<IMonitoringMetric>>> results = new HashMap<String, Map<MonitoringMetricType, List<IMonitoringMetric>>>();
		for (String applicationUriId : appData.keySet()) {
			results.put(
					applicationUriId,  
					getMonitoringStatisticsWhithinRangeLimited(
							applicationUriId, appData.get (applicationUriId), start, end, maxResults,
							monitoringMaxNumDisplayableValues));
		}

		return results;
	}
	
	private List<IMonitoringMetric> filterNotValidMetrics(List<IMonitoringMetric> monitoringMetrics) {
		List<IMonitoringMetric> metrics = new ArrayList<IMonitoringMetric>();

		for (IMonitoringMetric metric : monitoringMetrics) {
			// Filtering on valid response times
			if (metric.getMetricValue() >= 0) {
				metrics.add(metric);
			}
		}

		return metrics;
	}

//	public Map<MonitoringMetricType, List<IMonitoringMetric>> translateMonitoringStatistic(
//			List<eu.cloud4soa.api.governance.monitoring.IMonitoringStatistic> monitoringStatistics) {
//		List<IMonitoringMetric> uptimeMetrics = new ArrayList<IMonitoringMetric>();
//		List<IMonitoringMetric> responseTimeMetrics = new ArrayList<IMonitoringMetric>();
//		Map<MonitoringMetricType, List<IMonitoringMetric>> result = new HashMap<MonitoringMetricType, List<IMonitoringMetric>>();
//		
//		for (eu.cloud4soa.api.governance.monitoring.IMonitoringStatistic monitoringStatistic : monitoringStatistics) {
//			MonitoringMetric uptimeMetric = new MonitoringMetric();
//			MonitoringMetric responseTimeMetric = new MonitoringMetric();
//			
//			uptimeMetric.setDate(monitoringStatistic.getDate());
//			uptimeMetric.setMetricKey(MonitoringMetricType.UptimeInstance);
//			uptimeMetric.setMetricValue(monitoringStatistic.getResponseCode());
//			uptimeMetrics.add (uptimeMetric);
//			
//			responseTimeMetric.setDate(monitoringStatistic.getDate());
//			responseTimeMetric.setMetricKey(MonitoringMetricType.LatencyInstance);
//			responseTimeMetric.setMetricValue(monitoringStatistic.getResponseTime());
//			responseTimeMetrics.add (responseTimeMetric);			
//		}
//
//		result.put(MonitoringMetricType.UptimeInstance, uptimeMetrics);
//		result.put(MonitoringMetricType.LatencyInstance, responseTimeMetrics);
//		return result;
//	}
	
	public List<IMonitoringMetric> translateMonitoringMetrics(MonitoringMetricType metricKey, 
			List<eu.cloud4soa.api.governance.monitoring.IMonitoringMetric> monitoringMetrics) {
		List<IMonitoringMetric> lms = new ArrayList<IMonitoringMetric>();
		for (eu.cloud4soa.api.governance.monitoring.IMonitoringMetric monitoringMetric : monitoringMetrics) {
			MonitoringMetric ms = new MonitoringMetric();
			ms.setDate(monitoringMetric.getDate());
			ms.setMetricKey(metricKey);
			ms.setMetricValue(Double.valueOf(monitoringMetric.getMetricValue()));
			
			lms.add(ms);
		}

		return lms;
	}
	
	@Override
	public List<MonitoringMetricType> getSupportedMetrics(String paasOfferingId) {
		logger.debug("Getting supported metrics for offering "+ paasOfferingId );

		List<ServiceQualityInstance> supportedMetrics;
		try {
			 supportedMetrics = monitoringModule.getSupportedMetrics(paasOfferingId);
			return translateServiceQualityInstance(supportedMetrics);
		} catch (SOAException e) {
			String msg = "Error in getSupportedMetrics for offering "
					+ paasOfferingId;
			logger.error(msg, e);
//			throw new RuntimeException(msg, e);
			List<MonitoringMetricType> sms = new ArrayList<MonitoringMetricType>();
			sms.add (MonitoringMetricType.LatencyInstance);
			sms.add (MonitoringMetricType.UptimeInstance);
			return sms;
		}

	}
	
	private List<MonitoringMetricType> translateServiceQualityInstance(List<ServiceQualityInstance> supportedMetrics) {
		List<MonitoringMetricType> sms = new ArrayList<MonitoringMetricType>();
		for (ServiceQualityInstance sm: supportedMetrics)
			if (sm instanceof LatencyInstance){
				sms.add(MonitoringMetricType.LatencyInstance);
			}else if (sm instanceof UptimeInstance){
				sms.add(MonitoringMetricType.UptimeInstance);
			}else if (sm instanceof CPULoadInstance){
				sms.add(MonitoringMetricType.CPULoadInstance);
			}else if (sm instanceof CloudResponseTimeInstance){
				sms.add(MonitoringMetricType.CloudResponseTimeInstance);
			}else if (sm instanceof ContainerResponseTimeInstance){
				sms.add(MonitoringMetricType.ContainerResponseTimeInstance);
			}else if (sm instanceof MemoryLoadInstance){
				sms.add(MonitoringMetricType.MemoryLoadInstance);
			}
			
		return sms;
	}

}
