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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class LineChart extends JPanel {

    public LineChart(String titre, String xlabel, String ylabel) throws SQLException {
        // Call the super constructor to set the layout
        super(new BorderLayout());

        // Create a dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
       setBorder(new CompoundBorder(new LineBorder(new Color(18, 34, 34)), new EmptyBorder(10, 10, 10, 10)));
        // Fetch data from database
        ChartDataDao chartDataDAO = new ChartDataDao();
        Map<String, Float> data = chartDataDAO.getLineChartData();
        
        // Add data to the dataset
        data.forEach((month, totalRevenues) -> dataset.addValue(totalRevenues, "Revenues", month));

        // Create the chart
        JFreeChart chart = ChartFactory.createLineChart(
                titre,
                xlabel,
                ylabel,
                dataset
        );

        // Customize the chart (optional)
        chart.setBackgroundPaint(Color.white);

        // Create a panel to display the chart
        ChartPanel chartPanel = new ChartPanel(chart);
        
            chartPanel.setPreferredSize(new Dimension(600, 320));

        // Add the chart panel to this JPanel
        add(chartPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Line Chart Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            try {
                frame.getContentPane().add(new LineChart("Revenus Mensuels", "Mois", "Revenus"));
            } catch (SQLException ex) {
                Logger.getLogger(LineChart.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
