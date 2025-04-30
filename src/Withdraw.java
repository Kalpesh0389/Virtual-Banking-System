import javax.swing.*;
import java.awt.*;
import java.sql.*;

class Withdraw extends JFrame {
    Withdraw(String username) {
        Font f = new Font("Futura", Font.BOLD, 40);
        Font f2 = new Font("Calibri", Font.PLAIN, 22);

        // Custom JPanel for the background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background image
                ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource("icon/withdraw.jpg"));
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

        // Title and labels
        JLabel title = new JLabel("Withdraw Money", JLabel.CENTER);
        JLabel label = new JLabel("Enter Amount:");
        JTextField t1 = new JTextField(10);
        JButton b1 = new JButton("Withdraw");
        JButton b2 = new JButton("Back");

        // Set fonts and colors
        title.setFont(f);
        title.setForeground(Color.BLACK); // Black color for the title

        label.setFont(f2);
        label.setForeground(Color.WHITE); // White font for better contrast

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
                    double balance = 0.0;
                    double wlimit = 0.0;
                    String url = "jdbc:mysql://localhost:3306/batch2";
                    try (Connection con = DriverManager.getConnection(url, "root", "PHW#84#jeor")) {
                        String sql = "SELECT balance, wlimit FROM users WHERE username = ?";
                        try (PreparedStatement pst = con.prepareStatement(sql)) {
                            pst.setString(1, username);

                            ResultSet rs = pst.executeQuery();
                            if (rs.next()) {
                                balance = rs.getDouble("balance");
                                wlimit = rs.getDouble("wlimit");
                            }
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }

                    // Validation and withdrawal logic
                    String s1 = t1.getText();
                    if (s1.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter an amount.");
                    } else {
                        Double wamount = Double.parseDouble(s1);
                        if (wamount > balance) {
                            JOptionPane.showMessageDialog(null, "Insufficient balance.");
                        } else if (wamount > wlimit) {
                            JOptionPane.showMessageDialog(null, "Withdrawal limit exceeded.");
                        } else {
                            double total = balance - wamount;
                            try (Connection con = DriverManager.getConnection(url, "root", "PHW#84#jeor")) {
                                String sql = "UPDATE users SET balance = ? WHERE username = ?";
                                try (PreparedStatement pst = con.prepareStatement(sql)) {
                                    pst.setDouble(1, total);
                                    pst.setString(2, username);
                                    pst.executeUpdate();

                                    JOptionPane.showMessageDialog(null, "Withdrawal successful!");
                                    t1.setText("");
                                    updatePassbook(username, "Withdraw", -wamount, balance - wamount);
                                }
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        }
                    }
                }
        );

        // Component layout
        title.setBounds(200, 30, 400, 50);
        label.setBounds(250, 120, 300, 30);
        t1.setBounds(250, 160, 300, 30);
        b1.setBounds(300, 220, 200, 40);
        b2.setBounds(300, 280, 200, 40);

        // Add components to the background panel
        backgroundPanel.add(title);
        backgroundPanel.add(label);
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
        setTitle("Withdraw Money");
    }

    void updatePassbook(String username, String desc, Double amount, double total) {
        String url = "jdbc:mysql://localhost:3306/batch2";
        try (Connection con = DriverManager.getConnection(url, "root", "PHW#84#jeor")) {
            String sql = "INSERT INTO transactions (username, description, amount, balance) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, username);
                pst.setString(2, desc);
                pst.setDouble(3, amount);
                pst.setDouble(4, total);

                pst.executeUpdate();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Withdraw("username");
    }
}
