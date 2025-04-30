import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class Deposit extends JFrame {
    Deposit(String username) {
        Font f = new Font("Futura", Font.BOLD, 40);
        Font f2 = new Font("Calibri", Font.PLAIN, 22);

        // Custom JPanel for background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background image
                ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource("icon/deposit.jpg"));
                if (backgroundImage.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    Image image = backgroundImage.getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                } else {
                    System.out.println("Background image not found or could not be loaded.");
                }
            }
        };
        backgroundPanel.setLayout(null); // Use null layout for absolute positioning

        JLabel title = new JLabel("Deposit Money", JLabel.CENTER);
        JLabel label = new JLabel("Enter Amount:");
        JTextField t1 = new JTextField(10);
        JButton b1 = new JButton("Deposit");
        JButton b2 = new JButton("Back");

        title.setFont(f);
        label.setFont(f2);
        t1.setFont(f2);
        b1.setFont(f2);
        b2.setFont(f2);

        // Set foreground colors for labels and title
        title.setForeground(Color.WHITE);
        label.setForeground(Color.WHITE);

        // Set background and foreground for text fields
        t1.setBackground(Color.WHITE);
        t1.setForeground(Color.BLACK);

        // Remove borders for a cleaner look (optional)
        t1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

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

        b2.addActionListener(
                a -> {
                    new Home(username);
                    dispose();
                }
        );

        b1.addActionListener(
                a -> {
                    double balance = 0.0;
                    double total = 0.0;
                    String url = "jdbc:mysql://localhost:3306/batch2";
                    try (Connection con = DriverManager.getConnection(url, "root", "PHW#84#jeor")) {
                        String sql = "SELECT balance FROM users WHERE username = ?";
                        try (PreparedStatement pst = con.prepareStatement(sql)) {
                            pst.setString(1, username);

                            ResultSet rs = pst.executeQuery();
                            if (rs.next()) {
                                balance = rs.getDouble("balance");
                            }
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }

                    // Part 2
                    double amount = 0.0;
                    String s1 = t1.getText();
                    if (s1.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter an amount.");
                    } else {
                        amount = Double.parseDouble(s1);
                        total = amount + balance;
                    }

                    // Part 3
                    try (Connection con = DriverManager.getConnection(url, "root", "PHW#84#jeor")) {
                        String sql = "UPDATE users SET balance = ? WHERE username = ?";
                        try (PreparedStatement pst = con.prepareStatement(sql)) {
                            pst.setDouble(1, total);
                            pst.setString(2, username);
                            pst.executeUpdate();

                            JOptionPane.showMessageDialog(null, "Amount successfully deposited!");
                            t1.setText("");

                            updatePassbook(username, "Deposit", amount, balance + amount);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                }
        );

        setVisible(true);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Deposit Money");
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
        new Deposit("username");
    }
}
