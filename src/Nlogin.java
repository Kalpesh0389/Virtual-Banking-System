import javax.swing.*;
import java.awt.*;
import java.sql.*;

class Nlogin extends JFrame {
    Nlogin() {
        Font f = new Font("Futura", Font.BOLD, 30);
        Font f2 = new Font("Calibri", Font.PLAIN, 18);

        // Custom JPanel to handle background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the image
                ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource("icon/nlogin.jpg"));
                if (backgroundImage.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    Image image = backgroundImage.getImage();
                    // Draw the image scaled to fit the panel
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                } else {
                    System.out.println("Background image not found or could not be loaded.");
                }
            }
        };
        backgroundPanel.setLayout(null); // Use null layout for absolute positioning

        // Labels
        JLabel l1 = new JLabel("Set Username");
        JLabel l2 = new JLabel("Set Password");
        JLabel l3 = new JLabel("Confirm Password");
        JLabel l4 = new JLabel("Phone");
        JLabel l5 = new JLabel("Email");
        JLabel l6 = new JLabel("Gender");

        // Text fields and combo box
        JTextField t1 = new JTextField(10);
        JTextField t2 = new JTextField(10);
        JTextField t3 = new JTextField(10);
        JTextField t4 = new JTextField(15);
        JTextField t5 = new JTextField(20);
        JComboBox<String> genderBox = new JComboBox<>(new String[]{"male", "female", "other"});

        // Buttons
        JButton b1 = new JButton("Submit");
        JButton b2 = new JButton("Back");

        // Title
        JLabel title = new JLabel("Signup", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.white); // Blue color for the title

        // Set fonts and white color for labels
        l1.setFont(f2);
        l1.setForeground(Color.WHITE);

        l2.setFont(f2);
        l2.setForeground(Color.WHITE);

        l3.setFont(f2);
        l3.setForeground(Color.WHITE);

        l4.setFont(f2);
        l4.setForeground(Color.WHITE);

        l5.setFont(f2);
        l5.setForeground(Color.WHITE);

        l6.setFont(f2);
        l6.setForeground(Color.WHITE);

        t1.setFont(f2);
        t2.setFont(f2);
        t3.setFont(f2);
        t4.setFont(f2);
        t5.setFont(f2);
        genderBox.setFont(f2);
        b1.setFont(f2);
        b2.setFont(f2);

        // Component bounds
        int labelX = 200, fieldX = 400, yStart = 80, width = 150, height = 30, gap = 40;

        title.setBounds(300, 10, 200, 40);

        l1.setBounds(labelX, yStart, width, height);
        t1.setBounds(fieldX, yStart, width, height);

        l2.setBounds(labelX, yStart + gap, width, height);
        t2.setBounds(fieldX, yStart + gap, width, height);

        l3.setBounds(labelX, yStart + 2 * gap, width, height);
        t3.setBounds(fieldX, yStart + 2 * gap, width, height);

        l4.setBounds(labelX, yStart + 3 * gap, width, height);
        t4.setBounds(fieldX, yStart + 3 * gap, width, height);

        l5.setBounds(labelX, yStart + 4 * gap, width, height);
        t5.setBounds(fieldX, yStart + 4 * gap, width, height);

        l6.setBounds(labelX, yStart + 5 * gap, width, height);
        genderBox.setBounds(fieldX, yStart + 5 * gap, width, height);

        b1.setBounds(250, yStart + 6 * gap, 120, 40);
        b2.setBounds(400, yStart + 6 * gap, 120, 40);

        // Add components to the background panel
        backgroundPanel.add(title);
        backgroundPanel.add(l1);
        backgroundPanel.add(t1);
        backgroundPanel.add(l2);
        backgroundPanel.add(t2);
        backgroundPanel.add(l3);
        backgroundPanel.add(t3);
        backgroundPanel.add(l4);
        backgroundPanel.add(t4);
        backgroundPanel.add(l5);
        backgroundPanel.add(t5);
        backgroundPanel.add(l6);
        backgroundPanel.add(genderBox);
        backgroundPanel.add(b1);
        backgroundPanel.add(b2);

        // Set the background panel as the content pane
        setContentPane(backgroundPanel);

        b1.addActionListener(
                a -> {
                    if (t2.getText().equals(t3.getText())) {
                        String url = "jdbc:mysql://localhost:3306/batch2";
                        try (Connection con = DriverManager.getConnection(url, "root", "PHW#84#jeor")) {
                            String sql = "INSERT INTO users(username,password,phone,email,gender) VALUES(?, ? , ?, ?, ?)";
                            try (PreparedStatement pst = con.prepareStatement(sql)) {
                                pst.setString(1, t1.getText());
                                pst.setString(2, t2.getText());
                                pst.setString(3, t4.getText());
                                pst.setString(4, t5.getText());
                                pst.setString(5, genderBox.getSelectedItem().toString());

                                pst.executeUpdate();
                                JOptionPane.showMessageDialog(null, "Signup Successful");
                                new Home(t1.getText());
                                dispose();
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Passwords do not match");
                    }
                }
        );

        b2.addActionListener(
                a -> {
                    new Landing();
                    dispose();
                }
        );

        setVisible(true);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Signup");
    }

    public static void main(String[] args) {
        new Nlogin();
    }
}
