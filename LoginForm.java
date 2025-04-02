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
    private JComboBox<String> departmentComboBox;  
    public LoginForm() {
        try {
   
            if (mainPanel == null) {
                throw new IllegalStateException("Main panel is null. Check form initialization.");
            }

            setTitle("Course Registration System - Login");
            setContentPane(mainPanel);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 400);
            setLocationRelativeTo(null);

            
            initializeComponents();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Form initialization failed: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); 
        }
    }

    private void initializeComponents() {
        
        if (departmentComboBox == null) {
            System.err.println("Error: departmentComboBox is null!");
            departmentComboBox = new JComboBox<>();
            mainPanel.add(departmentComboBox);
        }
        initializeDepartmentComboBox();

        
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
            throw e; 
        }
    }

    private void handleLogin(ActionEvent e) {
        try {
            
            String firstname = firstNameField.getText().trim();
            String lastname = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String department = departmentComboBox.getSelectedItem().toString().trim();

            
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

            
            try {
                Class.forName("DashboardForm");
            } catch (ClassNotFoundException ex) {
                showError("Application component missing. Please reinstall.", "System Error");
                return;
            }

            
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
            dispose(); 
        }
    
    }
    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        firstNameField.requestFocus(); 
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
