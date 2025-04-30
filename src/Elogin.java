import javax.swing.*;
import java.awt.*;
import java.sql.*;

class Elogin extends JFrame {
    Elogin() {
        Font f = new Font("Futura", Font.BOLD, 40);
        Font f2 = new Font("Calibri", Font.PLAIN, 22);

        // Custom JPanel for the background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background image
                ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource("icon/elogin.jpg"));
                if (backgroundImage.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    Image image = backgroundImage.getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                } else {
                    System.out.println("Background image not found or could not be loaded.");
                }
            }
        };
        backgroundPanel.setLayout(null); // Use null layout for absolute positioning

        JLabel title = new JLabel("Login", JLabel.CENTER);
        JLabel l1 = new JLabel("Enter Username");
        JTextField t1 = new JTextField(10);
        JLabel l2 = new JLabel("Enter Password");
        JPasswordField t2 = new JPasswordField(10);
        JButton b1 = new JButton("Submit");
        JButton b2 = new JButton("Back");

        // Set fonts
        title.setFont(f);
        l1.setFont(f2);
        t1.setFont(f2);
        l2.setFont(f2);
        t2.setFont(f2);
        b1.setFont(f2);
        b2.setFont(f2);

        // Set white text for labels
        l1.setForeground(Color.WHITE);
        l2.setForeground(Color.WHITE);
        title.setForeground(Color.WHITE); // Title also in white

        // Set white background and black text for text fields
        t1.setForeground(Color.BLACK); // Black text
        t1.setBackground(Color.WHITE); // White background
        t2.setForeground(Color.BLACK); // Black text
        t2.setBackground(Color.WHITE); // White background

        // Remove the default border for better styling (optional)
        t1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        t2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Set bounds for components
        title.setBounds(250, 30, 300, 50);
        l1.setBounds(250, 100, 300, 30);
        t1.setBounds(250, 140, 300, 30);
        l2.setBounds(250, 200, 300, 30);
        t2.setBounds(250, 240, 300, 30);
        b1.setBounds(300, 300, 200, 40);
        b2.setBounds(300, 360, 200, 40);

        // Add components to the background panel
        backgroundPanel.add(title);
        backgroundPanel.add(l1);
        backgroundPanel.add(t1);
        backgroundPanel.add(l2);
        backgroundPanel.add(t2);
        backgroundPanel.add(b1);
        backgroundPanel.add(b2);

        // Set the background panel as the content pane
        setContentPane(backgroundPanel);

        b1.addActionListener(
                a -> {
                    String url = "jdbc:mysql://localhost:3306/batch2";
                    try (Connection con = DriverManager.getConnection(url, "root", "PHW#84#jeor")) {
                        String sql = " select * from users where username=? and password=?";
                        try (PreparedStatement pst = con.prepareStatement(sql)) {
                            pst.setString(1, t1.getText());
                            String s1 = new String(t2.getPassword());
                            pst.setString(2, s1);

                            ResultSet rs = pst.executeQuery();
                            if (rs.next()) {
                                JOptionPane.showMessageDialog(null, "Welcome!");
                                new Home(t1.getText());
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Invalid username or password!");
                            }
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
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
        setTitle("Login");
    }

    public static void main(String[] args) {
        new Elogin();
    }
}
