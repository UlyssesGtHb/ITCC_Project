import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RegistrationForm extends JFrame {
    private JPanel mainPanel;
    private JTextField nameField;
    private JTextField idField;
    private JComboBox<String> courseComboBox;
    private JLabel slotsLabel;
    private JButton registerButton;
    private JButton button1;
    private JTextArea confirmationArea;

    private final String username;
    private final Map<String, Course> courses = new HashMap<>();

    public RegistrationForm(String username) {
        this.username = username;
        initializeCourses();
        setTitle("Course Registration");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        initializeComponents();
    }

    private void initializeCourses() {
        courses.put("ITCC121 - Introduction to Programming", new Course("ITCC121", "Introduction to Programming", 30, 25));
        courses.put("ITPE1 - Data Structures", new Course("ITPE1", "Data Structures", 20, 15));
        courses.put("ITCZ221 - Game Analysis", new Course("ITCZ1", "Game Analysis", 20, 10));
        courses.put("ITCZ222 - Data Gathering I", new Course("ITCZ222", "Data Gathering I", 25, 5));
        courses.put("ITCC122 - Information Management I", new Course("ITCC122", "Information Managament I", 20, 18));

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(courses.keySet().toArray(new String[0]));
        courseComboBox.setModel(model);
    }

    private void initializeComponents() {
        courseComboBox.addActionListener(e -> updateSlotsDisplay());
        registerButton.addActionListener(this::registerStudent);
        button1.addActionListener(e -> dispose());
        updateSlotsDisplay();
        confirmationArea.setEditable(false);
        confirmationArea.setBorder(BorderFactory.createTitledBorder("Registration Confirmation"));
    }

    private void updateSlotsDisplay() {
        String selectedCourse = (String) courseComboBox.getSelectedItem();
        Course course = courses.get(selectedCourse);

        if (course != null && slotsLabel != null) {
            slotsLabel.setText(course.getAvailableSlots() + " / " + course.getTotalSlots());
        }
    }

    private void registerStudent(ActionEvent e) {
        String name = nameField.getText().trim();
        String id = idField.getText().trim();
        String selectedCourse = (String) courseComboBox.getSelectedItem();
        if (name.isEmpty() || id.isEmpty()) {
            showError("Please fill in all student information fields");
            return;
        }

        Course course = courses.get(selectedCourse);
        if (course == null || course.getAvailableSlots() <= 0) {
            showError("No available slots for this course");
            return;
        }
        processRegistration(name, id, selectedCourse, course);
    }

    private void processRegistration(String name, String id, String courseName, Course course) {
        course.setAvailableSlots(course.getAvailableSlots() - 1);
        String confirmation = String.format(
                "REGISTRATION CONFIRMATION%n%n" +
                        "Student Name: %s%n" +
                        "Student ID: %s%n" +
                        "Course: %s%n" +
                        "Registration Date: %s%n%n" +
                        "Your registration has been successfully processed.%n" +
                        "Available slots remaining: %d",
                name, id, courseName, LocalDate.now(), course.getAvailableSlots()
        );

        confirmationArea.setText(confirmation);
        updateSlotsDisplay();

        JOptionPane.showMessageDialog(this,
                "Registration successful!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Registration Error",
                JOptionPane.ERROR_MESSAGE);
    }
    private static class Course {
        private final String code;
        private final String title;
        private final int totalSlots;
        private int availableSlots;

        public Course(String code, String title, int totalSlots, int availableSlots) {
            this.code = code;
            this.title = title;
            this.totalSlots = totalSlots;
            this.availableSlots = availableSlots;
        }

        public int getTotalSlots() {
            return totalSlots;
        }

        public int getAvailableSlots() {
            return availableSlots;
        }

        public void setAvailableSlots(int availableSlots) {
            this.availableSlots = availableSlots;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistrationForm form = new RegistrationForm("testuser");
            form.setVisible(true);
        });
    }
}
