package pacman;
import javax.swing.JFrame;

public class pacmann extends JFrame {

    public pacmann() {
        add(new model());
        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 420);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            pacmann pac = new pacmann();
            pac.setVisible(true);
        });
    }
}
