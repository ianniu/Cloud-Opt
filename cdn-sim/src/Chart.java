import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.List;

public class Chart extends ApplicationFrame{
    public Chart(String applicationTitle, String chartTitle, String xLabel, String yLabel, List<double[]> dataSet) {
        super(applicationTitle);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (double[] arr : dataSet) {
            dataset.addValue(arr[1], "TTL", String.valueOf(arr[0]));
        }
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                xLabel,yLabel,
                dataset,
                PlotOrientation.VERTICAL,
                false,true,false);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
        chartPanel.setDomainZoomable(true);
    }

    public void plot() {
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }
}
