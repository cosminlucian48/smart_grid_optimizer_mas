package dreamsoftware.smartgridoptimizer.gui.panel.impl;

import java.awt.Dimension;
import java.io.Serial;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.Range;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;

public final class DynamicTimeSeriesChart extends JPanel {
	
	@Serial
    private static final long serialVersionUID = 1L;
	
	private final DynamicTimeSeriesCollection dataset;

    public DynamicTimeSeriesChart(final String title, final Integer valueMin, final Integer valueMax) {
		dataset = new DynamicTimeSeriesCollection(1, 1000, new Second());
		Calendar c = Calendar.getInstance();
        dataset.setTimeBase(new Second(c.getTime()));
        dataset.addSeries(new float[1], 0, title);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                title, "Time", title, dataset, true, true, false);
        
        final XYPlot plot = chart.getXYPlot();
        // Domain AXIS
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setFixedAutoRange(10000);
        axis.setDateFormatOverride(new SimpleDateFormat("ss.SS"));

        ValueAxis range = plot.getRangeAxis();
        range.setRange(new Range(valueMin, valueMax));
        
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        add(chartPanel);
	}
	
	public void update(float value) {
		float[] newData = new float[1];
		newData[0] = value;
        dataset.advanceTime();
        dataset.appendData(newData);
    }
}
