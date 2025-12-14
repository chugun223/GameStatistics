package games_project.view_desktop.chart;

import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Map;

public class chartMapper {

    public static DefaultCategoryDataset mapToDataset(Map<String, Double> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), "Мировые продажи", entry.getKey());
        }

        return dataset;
    }
}
