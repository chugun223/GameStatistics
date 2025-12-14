package games_project.view_desktop.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class chartDrawer {

    public static void drawBarChart(DefaultCategoryDataset dataset, String title, String xLabel, String yLabel) {
        JFreeChart chart = ChartFactory.createBarChart(
                title,
                xLabel,
                yLabel,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setContentPane(new ChartPanel(chart));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public String saveBarChart(DefaultCategoryDataset dataset, String title, String x, String y, String path) throws IOException {
        JFreeChart chart = ChartFactory.createBarChart(title, x, y, dataset);

        File file = new File(path);
        ChartUtils.saveChartAsPNG(file, chart, 800, 600);

        return file.getAbsolutePath();
    }
}
