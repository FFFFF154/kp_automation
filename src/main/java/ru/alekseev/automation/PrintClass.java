package ru.alekseev.automation;

import org.apache.commons.math3.complex.Complex;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class PrintClass extends ApplicationFrame {

    private final List<Double> y_t;
    //private List<List<Double>> list;

    private XYSeriesCollection dataset = new XYSeriesCollection();

    public PrintClass(String title, List<Double> y_t) {
        super(title);
        this.y_t = y_t;
        JFreeChart lineChart = ChartFactory.createXYLineChart(
                title,
                "t", "y(t)",
                dataset = createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    private XYSeriesCollection createDataset() {
        //XYSeries series = new XYSeries("Series1");
        XYSeries series = new XYSeries("t");
//        for (int i = 0; i < y_t.size(); i++) {
//            double x = i * 0.001;
//            series.add(x, y_t.get(i));
//        }
        IntStream.range(0, y_t.size())
                .forEach(i -> series.add(i * 0.001, y_t.get(i)));
        dataset.addSeries(series);
        return dataset;
    }
}
