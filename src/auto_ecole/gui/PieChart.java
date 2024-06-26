package auto_ecole.gui;
import auto_ecole.database.ChartDataDao;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PieChart extends JPanel {

    public PieChart() {
        try {
            // Create a dataset
            DefaultPieDataset dataset = new DefaultPieDataset();
            
            // Fetch data from database
            ChartDataDao chartDataDAO = new ChartDataDao();
            Map<String, Double> data = chartDataDAO.getPieChartData();
            
            // Add data to the dataset
            data.forEach(dataset::setValue);
            
            // Create the chart
            JFreeChart chart = ChartFactory.createPieChart(
                    "Revenues par Mode de paiement",
                    dataset,
                    true, // include legend
                    true, // include tooltips
                    false // do not include URLs
            );
            
            // Customize the chart (optional)
            chart.setBackgroundPaint(Color.white);
            
            // Create a panel to display the chart
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(600, 320));
            
        setBorder(new CompoundBorder(new LineBorder(new Color(18, 34, 34)), new EmptyBorder(10, 10, 10, 10)));
            // Add the chart panel to this JPanel
            add(chartPanel);
        } catch (SQLException ex) {
            Logger.getLogger(PieChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new PieChart());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
