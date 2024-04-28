import auto_ecole.gui.LoginApp;
import javax.swing.SwingUtilities;

public class App  {
    
    public static void main(String[] args) throws Exception {
          SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginApp().setVisible(true);
            }
        });
    
    
    }
}
 
