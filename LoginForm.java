import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoginForm extends JFrame {
    private JPanel mainPanel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JButton submitButton;
    private JButton clearButton;
    private JLabel titleLabel;
    private JTextArea introductionArea;
    private JComboBox<String> departmentComboBox;  // Specify String type for the combo box
    public LoginForm() {
        try {
            // Verify main panel initialization
            if (mainPanel == null) {
                throw new IllegalStateException("Main panel is null. Check form initialization.");
            }

            setTitle("Course Registration System - Login");
            setContentPane(mainPanel);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 400);
            setLocationRelativeTo(null);

            // Initialize components
            initializeComponents();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Form initialization failed: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Exit if initialization fails
        }
    }

    private void initializeComponents() {
        // Verify and initialize department combo box
        if (departmentComboBox == null) {
            System.err.println("Error: departmentComboBox is null!");
            departmentComboBox = new JComboBox<>();
            mainPanel.add(departmentComboBox);
        }
        initializeDepartmentComboBox();

        // Verify buttons
        if (submitButton == null || clearButton == null) {
            System.err.println("Error: Buttons not initialized!");
        } else {
            submitButton.addActionListener(this::handleLogin);
            clearButton.addActionListener(this::handleClear);
        }
    }

    private void initializeDepartmentComboBox() {
        try {
            String[] departments = {
                    "Select Department",
                    "Computer Engineering",
                    "Electrical Engineering",
                    "Mechanical Engineering",
                    "Information Technology",
                    "Civil Engineering"
            };

            departmentComboBox.removeAllItems();
            for (String dept : departments) {
                departmentComboBox.addItem(dept);
            }
            departmentComboBox.setSelectedIndex(0);
        } catch (Exception e) {
            System.err.println("Error initializing combo box: " + e.getMessage());
            throw e; // Re-throw to be caught by constructor
        }
    }

    private void handleLogin(ActionEvent e) {
        try {
            // Get and validate input
            String firstname = firstNameField.getText().trim();
            String lastname = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String department = departmentComboBox.getSelectedItem().toString().trim();

            // Validation
            if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty()) {
                showError("Please fill in all fields", "Incomplete Form");
                return;
            }

            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                showError("Please enter a valid email address (e.g., user@example.com)", "Invalid Email");
                return;
            }

            if (department.equalsIgnoreCase("Select Department")) {
                showError("Please select a department", "Department Required");
                return;
            }

            // Verify DashboardForm exists
            try {
                Class.forName("DashboardForm");
            } catch (ClassNotFoundException ex) {
                showError("Application component missing. Please reinstall.", "System Error");
                return;
            }

            // Proceed to dashboard
            dispose();
            new DashboardForm("firstname").setVisible(true);

        } catch (Exception ex) {
            System.err.println("Login error: " + ex.getMessage());
            ex.printStackTrace();
            showError("An unexpected error occurred. Please try again.", "System Error");
        }
    }

    private void showError(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    private void handleClear(ActionEvent e) {
        int response = JOptionPane.showConfirmDialog(
                this,
                "Clear all fields or exit application?",
                "Clear/Exit",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            clearFields();
        }
        else if (response == JOptionPane.NO_OPTION) {
            dispose(); // Close the window
        }
        // (Cancel option does nothing)
    }
    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        firstNameField.requestFocus(); // Return focus to first field
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Failed to launch application: " + e.getMessage(),
                        "Fatal Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}