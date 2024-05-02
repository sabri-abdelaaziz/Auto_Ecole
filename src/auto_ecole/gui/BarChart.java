package auto_ecole.gui;

import auto_ecole.database.ChartDataDao;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;

public class BarChart extends JPanel {

    public BarChart() throws SQLException {
        // Create a dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Fetch data from database
        ChartDataDao chartDataDAO = new ChartDataDao();
        Map<String, Integer> data = chartDataDAO.getBarChartData();

        // Add data to the dataset
        data.forEach((month, totalRevenues) -> dataset.addValue(totalRevenues, "Revenues", month));

        // Create the chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Monthly Revenues",
                "Month/Year",
                "Total Revenues",
                dataset
        );

        // Customize the chart (optional)
        chart.setBackgroundPaint(Color.white);

        // Create a panel to display the chart
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 400));

        // Add the chart panel to the JPanel
        add(chartPanel);

        setVisible(true);
    }

    
}
