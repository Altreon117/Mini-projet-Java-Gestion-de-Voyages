import javax.swing.*;
import java.awt.event.*;
public class Voyage extends JFrame implements Serializable{
    public Voyage() {
        setTitle("Voyage");

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };

        addWindowListener(l);

        JPanel panel = new JPanel();
        setContentPane(panel);

        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new Voyage();
    }
}
