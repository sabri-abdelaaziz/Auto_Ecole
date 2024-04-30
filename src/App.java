import auto_ecole.gui.LoginApp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.SwingUtilities;

public class App  {
    
    public static void main(String[] args) throws Exception {
         ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            new LoginApp().setVisible(true);
        });

        // Shutdown the executor after the task is completed
        executor.shutdown();
    
    
    }
}
 
