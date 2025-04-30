import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class Transfer extends JFrame {
    Transfer(String username) {
        Font f = new Font("Futura", Font.BOLD, 30);
        Font f2 = new Font("Calibri", Font.PLAIN, 18);

        // Custom JPanel for the background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background image
                ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource("icon/transfer.jpg"));
                if (backgroundImage.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    Image image = backgroundImage.getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                } else {
                    System.out.println("Background image not found or could not be loaded.");
                }
            }
        };
        backgroundPanel.setLayout(null); // Use null layout for absolute positioning

        JLabel title = new JLabel("Transfer Funds", JLabel.CENTER);
        JLabel l1 = new JLabel("Receiver:");
        JTextField t1 = new JTextField(10);

        JLabel l2 = new JLabel("Amount:");
        JTextField t2 = new JTextField(10);

        JButton b1 = new JButton("Transfer");
        JButton b2 = new JButton("Back");

        title.setFont(f);
        l1.setFont(f2);
        t1.setFont(f2);
        l2.setFont(f2);
        t2.setFont(f2);
        b1.setFont(f2);
        b2.setFont(f2);

        // Set foreground (text) colors for labels and title
        title.setForeground(Color.WHITE);
        l1.setForeground(Color.WHITE);
        l2.setForeground(Color.WHITE);

        // Set background and foreground for text fields
        t1.setBackground(Color.WHITE);
        t1.setForeground(Color.BLACK);
        t2.setBackground(Color.WHITE);
        t2.setForeground(Color.BLACK);

        // Remove borders for a cleaner look (optional)
        t1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        t2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        int labelX = 200, fieldX = 400, yStart = 80, width = 150, height = 30, gap = 40;

        title.setBounds(250, 20, 300, 40);

        l1.setBounds(labelX, yStart, width, height);
        t1.setBounds(fieldX, yStart, width, height);

        l2.setBounds(labelX, yStart + gap, width, height);
        t2.setBounds(fieldX, yStart + gap, width, height);

        b1.setBounds(250, yStart + 2 * gap, 120, 40);
        b2.setBounds(400, yStart + 2 * gap, 120, 40);

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

        b2.addActionListener(
                a -> {
                    new Home(username);
                    dispose();
                }
        );

        b1.addActionListener(
                a -> {
                    String samnewala = t1.getText();
                    String s2 = t2.getText();

                    if (samnewala.isEmpty() || s2.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Fields cannot be empty!");
                        return;
                    }

                    // Round 1: Fetch balance
                    double balance = fetchBalance(username);
                    double amount = Double.parseDouble(s2);

                    if (amount > balance) {
                        JOptionPane.showMessageDialog(null, "Insufficient balance!");
                        return;
                    }
                    double result = balance - amount;

                    // Round 2: Update sender's balance and passbook
                    updateBalance(username, result);
                    updatePassbook(username, "Transfer to " + samnewala, -amount, balance - amount);

                    // Round 3: Update receiver's balance and passbook
                    balance = fetchBalance(samnewala);
                    double total = amount + balance;

                    updateBalance(samnewala, total);
                    updatePassbook(samnewala, "Transfer from " + username, amount, balance + amount);

                    JOptionPane.showMessageDialog(null, "Transfer successful!");

                    t1.setText("");
                    t2.setText("");
                }
        );

        setVisible(true);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Transfer Funds");
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

    double fetchBalance(String username) {
        double balance = 0.0;
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
        return balance;
    }

    void updateBalance(String username, double total) {
        String url = "jdbc:mysql://localhost:3306/batch2";
        try (Connection con = DriverManager.getConnection(url, "root", "PHW#84#jeor")) {
            String sql = "UPDATE users SET balance = ? WHERE username = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setDouble(1, total);
                pst.setString(2, username);
                pst.executeUpdate();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Transfer("username");
    }
}
