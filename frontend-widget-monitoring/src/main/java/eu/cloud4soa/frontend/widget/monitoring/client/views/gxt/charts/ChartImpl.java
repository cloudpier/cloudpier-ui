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

package eu.cloud4soa.frontend.widget.monitoring.client.views.gxt.charts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.charts.client.model.ChartModel;
import com.extjs.gxt.charts.client.model.Legend;
import com.extjs.gxt.charts.client.model.Legend.Position;
import com.extjs.gxt.charts.client.model.axis.XAxis;
import com.extjs.gxt.charts.client.model.axis.YAxis;
import com.extjs.gxt.charts.client.model.charts.LineChart;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;

import eu.cloud4soa.frontend.commons.client.datamodel.frontend.monitoring.IMonitoringMetric;

public class ChartImpl implements ILineChart {

	private ChartModel cm;
	private int maxNumberMonitoringStatisticSamples;

	public ChartImpl(String title, int maxNumberMonitoringStatisticSamples) {
		this.maxNumberMonitoringStatisticSamples = maxNumberMonitoringStatisticSamples;
		cm = new ChartModel(title, "font-size: 12px; font-family: Verdana;");
		cm.setBackgroundColour("#ffffff");

		// LEYEND
		Legend legend = new Legend(Position.TOP, true);
		legend.setPadding(10);
		cm.setLegend(legend);
	}

	@Override
	public ChartModel getChartModel() {
		return cm;
	}

	@Override
	public List<List<ChartPoint>> setCharModelAxes(
			Map<String, List<IMonitoringMetric>> metrics,
			int numberHorizontalLabels, int numberVerticalLabels) {
		// AXIS
		// FIXME (Assumed all charts have the same horizontal values, which may
		// not be true)
		List<List<ChartPoint>> chartValues = getChartValues(metrics);
		if (chartValues.size() == 0)
			return chartValues; // No values to set axes.

		XAxis xa = new XAxis();

		if (chartValues.get(0).size() < numberHorizontalLabels) // Recalculating
																// the number of
																// x axis labels
		numberHorizontalLabels = chartValues.get(0).size();
		List<String> xAxisLabels = getXAxisLabels(chartValues.get(0),
				numberHorizontalLabels);
		xa.setLabels(xAxisLabels);
		int deltax = chartValues.get(0).size() / (numberHorizontalLabels - 1);
		xa.setSteps(deltax);

		cm.setXAxis(xa);

		YAxis ya = new YAxis();
		double minY = getYMinForMultipleCharts(chartValues);
		double maxY = getYMaxForMultipleCharts(chartValues);
		
		//Extending Y Axis range using margins
		double YAXIS_RANGE_MARGIN = 10.0; //PERCENTAGE
		double yrange = maxY-minY;
		if (yrange == 0.0) yrange = maxY/2; //Horizontal graph
		if (yrange == 0.0 && maxY == 0.0) yrange = 1.0; //Horizontal graph, 0 value
		double ymargin = yrange*YAXIS_RANGE_MARGIN / 100.0;
		
		minY -= ymargin; if (minY <= 0) minY = 0.0;
		maxY += ymargin;

		List<String> yAxisLabels = getYAxisLabels(minY, maxY,
				numberVerticalLabels);
		ya.setLabels(yAxisLabels);
		double deltay = (maxY - minY) / (numberVerticalLabels - 1);
		ya.setSteps(deltay);

		// Set Y axis range [min, max]
		ya.setRange(minY, maxY);

		cm.setYAxis(ya);
		return chartValues;
	}

	@Override
	public void addLineChart(List<ChartPoint> values, String color, String label) {
		LineChart graph = new LineChart();
		graph.setText(label);
		graph.setColour(color);

		for (ChartPoint value : values) {
			graph.addValues(value.getY());
		}
		cm.addChartConfig(graph);
	}



	private List<ChartPoint> getChartValues(
			List<IMonitoringMetric> metrics) {
		List<ChartPoint> values = new ArrayList<ChartPoint>();
		// FIXME: , int maxNumberMonitoringStatisticSamples

		// Displaying the last RTCHART_MAX_NUM_DISPLAYABLE_VALUES
		DateTimeFormat fmt = DateTimeFormat.getFormat("dd/MM/yyy HH:mm:ss");
		int start = metrics.size() - maxNumberMonitoringStatisticSamples;
		if (start < 0) {
			start = 0;
		}
		for (int i = start; i < metrics.size(); i++) {
			IMonitoringMetric metric = metrics.get(i);
			values.add(new ChartPoint(fmt.format(metric.getDate()), metric.getMetricValue()));
		}
		return values;
	}

	private List<List<ChartPoint>> getChartValues(
			Map<String, List<IMonitoringMetric>> metrics) {
		List<List<ChartPoint>> result = new ArrayList<List<ChartPoint>>();

		for (List<IMonitoringMetric> m : metrics.values()) {
			result.add(getChartValues(m));
		}

		return result;
	}


	private double getYMinForMultipleCharts(List<List<ChartPoint>> values) {
		double result = Double.POSITIVE_INFINITY;
		for (List<ChartPoint> vals : values) {
			if (vals.isEmpty())
				continue;
			double min = getYMin(vals);
			if (min < result)
				result = min;
		}
		return result;
	}

	private double getYMaxForMultipleCharts(List<List<ChartPoint>> values) {
		double result = Double.NEGATIVE_INFINITY;
		for (List<ChartPoint> vals : values) {
			if (vals.isEmpty())
				continue;
			double max = getYMax(vals);
			if (max > result)
				result = max;
		}
		return result;
	}


	private List<String> getXAxisLabels(List<ChartPoint> values,
			int maxNumberLabels) {
		List<String> xValues = new ArrayList<String>();
		if (values.isEmpty())
			return xValues;

		int delta = values.size() / (maxNumberLabels - 1);
		int i = 0;
		for (ChartPoint value : values) {
			if (i % delta == 0)
				xValues.add(value.getX());
			else
				xValues.add("");
			i++;
		}
		return xValues;
	}

	private List<String> getYAxisLabels(double minY, double maxY,
			int maxNumberLabels) {
		NumberFormat fmt = getNumberFormat (maxY - minY);
		List<String> yValues = new ArrayList<String>();
		double delta = (maxY - minY) / (maxNumberLabels - 1);
		double label = minY;

		int i = 0;
		while (label <= (maxY + delta) && i < maxNumberLabels) {
			yValues.add(fmt.format(label));
			label += delta;
			i++;
		}

		return yValues;
	}
	
	private NumberFormat getNumberFormat (double delta){
		NumberFormat fmt = NumberFormat.getFormat("###");
		if (delta <= 0.01) fmt = NumberFormat.getFormat("###.###");
		else if (delta <= 0.1) fmt = NumberFormat.getFormat("###.##");
		else if (delta <= 1.0) fmt = NumberFormat.getFormat("###.#");
		return fmt;
	}

	private double getYMax(List<ChartPoint> values) {
		double max = values.get(0).getY();
		for (ChartPoint cp : values) {
			if (cp.getY() > max)
				max = cp.getY();
		}
		return max;
	}

	private double getYMin(List<ChartPoint> values) {
		double min = values.get(0).getY();
		for (ChartPoint cp : values) {
			if (cp.getY() < min)
				min = cp.getY();
		}
		return min;
	}

	@Override
	public String getColorCodes(int i) {
		return htmlColorCodes[i];
	}
}