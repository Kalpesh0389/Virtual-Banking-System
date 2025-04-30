import javax.swing.*;
import java.awt.*;
import java.sql.*;

class Profile extends JFrame {

    Profile(String username) {
        Font f = new Font("Futura", Font.BOLD, 35);
        Font f2 = new Font("Calibri", Font.PLAIN, 20);

        // Custom JPanel to handle background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background image
                ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource("icon/profile.jpg")); // Replace "as.jpg" with your image file
                if (backgroundImage.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    Image image = backgroundImage.getImage();
                    // Draw the image scaled to fit the panel size
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                } else {
                    System.out.println("Background image not found or could not be loaded.");
                }
            }
        };
        backgroundPanel.setLayout(null); // Use null layout for absolute positioning

        // Title and components
        JLabel title = new JLabel("Profile Settings", JLabel.CENTER);
        title.setFont(f);
        title.setForeground(Color.WHITE); // Set title color to white

        JLabel l1 = new JLabel("Select Field to Update:");
        JComboBox<String> box = new JComboBox<>(new String[]{"Username", "Password", "Phone", "Email"});

        JLabel l2 = new JLabel("Enter New Value:");
        JTextField t1 = new JTextField(15);

        JButton b1 = new JButton("Update");
        JButton b2 = new JButton("Back");

        l1.setFont(f2);
        l1.setForeground(Color.WHITE); // Set label color to white

        box.setFont(f2);

        l2.setFont(f2);
        l2.setForeground(Color.WHITE); // Set label color to white

        t1.setFont(f2);

        b1.setFont(f2);
        b2.setFont(f2);

        // Button actions
        b2.addActionListener(
                a -> {
                    new Home(username);
                    dispose();
                }
        );

        b1.addActionListener(
                a -> {
                    String s1 = box.getSelectedItem().toString().toLowerCase();
                    String s2 = t1.getText();

                    if (s2.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Field cannot be empty");
                        return;
                    }

                    // Database connection
                    String url = "jdbc:mysql://localhost:3306/batch2";
                    try (Connection con = DriverManager.getConnection(url, "root", "PHW#84#jeor")) {
                        String sql = "UPDATE users SET " + s1 + " =? WHERE username =?";
                        try (PreparedStatement pst = con.prepareStatement(sql)) {
                            pst.setString(1, s2);
                            pst.setString(2, username);
                            pst.executeUpdate();

                            JOptionPane.showMessageDialog(null, "Updated successfully");
                            t1.setText("");
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                        return;
                    }

                    // If username is updated
                    if (s1.equals("username")) {
                        dispose();
                        new Profile(s2);
                        try (Connection con = DriverManager.getConnection(url, "root", "PHW#84#jeor")) {
                            String sql = "UPDATE transactions SET username =? WHERE username =?";
                            try (PreparedStatement pst = con.prepareStatement(sql)) {
                                pst.setString(1, s2);
                                pst.setString(2, username);
                                pst.executeUpdate();
                            }

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e.getMessage());
                        }
                    }
                }
        );

        // Set component bounds
        title.setBounds(250, 20, 300, 40);
        l1.setBounds(200, 100, 200, 30);
        box.setBounds(400, 100, 200, 30);
        l2.setBounds(200, 160, 200, 30);
        t1.setBounds(400, 160, 200, 30);
        b1.setBounds(250, 220, 120, 40);
        b2.setBounds(400, 220, 120, 40);

        // Add components to the background panel
        backgroundPanel.add(title);
        backgroundPanel.add(l1);
        backgroundPanel.add(box);
        backgroundPanel.add(l2);
        backgroundPanel.add(t1);
        backgroundPanel.add(b1);
        backgroundPanel.add(b2);

        // Set the background panel as the content pane
        setContentPane(backgroundPanel);

        // Frame settings
        setVisible(true);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Profile Settings");
    }

    public static void main(String[] args) {
        new Profile("username");
    }
}
