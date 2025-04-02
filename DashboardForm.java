import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DashboardForm extends JFrame {

    private JPanel mainPanel;
    private JButton fileButton;
    private JButton coursesButton;
    private JButton helpButton;
    private JLabel welcomeLabel;
    private JButton viewCoursesBtn;
    private JButton registerBtn;

    private final String username;

    public DashboardForm(String username) {
        this.username = username;
        setTitle("Course Registration System - Dashboard");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        initializeComponents();
    }

    private void initializeComponents() {
        if (mainPanel == null) {
            JOptionPane.showMessageDialog(null,
                    "Main panel not initialized! Check GUI designer bindings.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + username + "!");
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
            welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        if (fileButton != null) {
            fileButton.addActionListener(this::showFileOptions);
        }
        if (coursesButton != null) {
            coursesButton.addActionListener(this::showCoursesOptions);
        }
        if (helpButton != null) {
            helpButton.addActionListener(e -> showHelp());
        }
        if (viewCoursesBtn != null) {
            viewCoursesBtn.addActionListener(this::showAvailableCourses);
        }
        if (registerBtn != null) {
            registerBtn.addActionListener(this::showRegistrationForm);
        }
    }

    private void showFileOptions(ActionEvent e) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(ev -> {
            dispose();
            new LoginForm().setVisible(true);
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(ev -> System.exit(0));

        popupMenu.add(logoutItem);
        popupMenu.addSeparator();
        popupMenu.add(exitItem);
        popupMenu.show(fileButton, 0, fileButton.getHeight());
    }

    private void showCoursesOptions(ActionEvent e) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem viewItem = new JMenuItem("View Subject");
        viewItem.addActionListener(this::showAvailableCourses);

        JMenuItem registerItem = new JMenuItem("Register");
        registerItem.addActionListener(this::showRegistrationForm);

        popupMenu.add(viewItem);
        popupMenu.add(registerItem);
        popupMenu.show(coursesButton, 0, coursesButton.getHeight());
    }

    private void showHelp() {
        JOptionPane.showMessageDialog(this,
                "Course Registration System\nVersion 1.0",
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAvailableCourses(ActionEvent e) {
        String[] courses = {
                "CS101 - Introduction to Programming - Slots: 25/30",
                "CS201 - Data Structures - Slots: 15/20",
                "CS301 - Algorithms - Slots: 10/20",
                "MATH101 - Calculus I - Slots: 5/25",
                "PHYS101 - Physics I - Slots: 18/20"
        };

        JList<String> courseList = new JList<>(courses);
        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(courseList);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JOptionPane.showMessageDialog(this, scrollPane, "Available Courses", JOptionPane.PLAIN_MESSAGE);
    }

    private void showRegistrationForm(ActionEvent e) {
        RegistrationForm registrationForm = new RegistrationForm(username);
        registrationForm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashboardForm form = new DashboardForm("testuser");
            form.setVisible(true);
        });
    }
}
