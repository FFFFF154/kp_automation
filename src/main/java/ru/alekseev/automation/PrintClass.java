package ru.alekseev.automation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class PrintClass extends ApplicationFrame {

    private final List<List<Double>> yT;
    //private List<List<Double>> list;

    private XYSeriesCollection dataset = new XYSeriesCollection();

    public PrintClass(String title, List<List<Double>> yT) {
        super(title);
        this.yT = yT;
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
        //XYSeries series = new XYSeries("t");
//        for (int i = 0; i < y_t.size(); i++) {
//            double x = i * 0.001;
//            series.add(x, y_t.get(i));
//        }

//        for (List<Double> y : yT) { //TODO Сделать через стрим
//            XYSeries series = new XYSeries("");
////            IntStream.range(0, y.size())
////                    .forEach(j -> series.add(j * 0.001, y.get(j)));
//            dataset.addSeries(series);
//        }
        AtomicInteger k = new AtomicInteger();
        yT.stream()
                .map(elem -> {
                    k.getAndIncrement();
                    XYSeries series = new XYSeries("" + k);

                    IntStream.range(0, elem.size())
                            .forEach(j -> series.add(j * 0.001, elem.get(j)));
                    return series;
                })
                .forEach(elem ->dataset.addSeries(elem) );

        return dataset;
    }


}
