/*******************************************************************************
 * Copyright (c) 2013 Atos Spain S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the LGPLv3 Lesser General Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Contributors:
 *     Atos Spain S.A. - initial API and implementation
 *******************************************************************************/
package eu.cloud4soa.frontend.commons.client.datamodel.frontend.monitoring;


/**
 * Project: c4s-frontend-commons
 * @author "Yosu Gorroñogoitia"
 * Creation time: Aug 27, 2013
 */
public enum MonitoringMetricType {	
	LatencyInstance("Latency", "ms"), 
	UptimeInstance("Uptime", "%"), 
	CPULoadInstance("CPU Load",  "%"), 
	CloudResponseTimeInstance ("Cloud Response Time", "ms"), 
	ContainerResponseTimeInstance ("Container Response Time", "ns"), 
	MemoryLoadInstance ("Memory Load", "bytes");
	
	private String name;
	private String unit;
	
	private MonitoringMetricType(String name, String unit){
		this.name = name;
		this.unit = unit;
	}
	
	public String getName(){
		return name;
	}
	
	public String getUnit(){
		return unit;
	}
}
