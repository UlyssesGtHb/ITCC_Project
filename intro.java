import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class intro extends JFrame {
    private JPanel mainpanel;
    private JList list1;
    private JButton loginHereButton;

    public intro() {
        setTitle("Introduction");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainpanel);
        setLocationRelativeTo(null);
        loginHereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
new LoginForm().setVisible(true);
            }
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new intro().setVisible(true);
            }
        });
    }
}