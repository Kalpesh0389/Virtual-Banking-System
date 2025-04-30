import javax.swing.*;
import java.awt.*;

class Alogin extends JFrame {
    Alogin() {
        Font f = new Font("Futura", Font.BOLD, 40);
        Font f2 = new Font("Calibri", Font.PLAIN, 22);

        // Custom JPanel to handle background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background image
                ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource("icon/as.jpg"));
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
        JLabel title = new JLabel("Admin Login", JLabel.CENTER);
        JLabel l1 = new JLabel("Enter Username");
        JTextField t1 = new JTextField(10);
        JLabel l2 = new JLabel("Enter Password");
        JPasswordField p1 = new JPasswordField(10);

        // Buttons
        JButton b1 = new JButton("Submit");
        JButton b2 = new JButton("Back");

        // Set fonts
        title.setFont(f);
        title.setForeground(Color.WHITE); // Set title color to white

        l1.setFont(f2);
        l1.setForeground(Color.WHITE); // White color for the label

        t1.setFont(f2);

        l2.setFont(f2);
        l2.setForeground(Color.WHITE); // White color for the label

        p1.setFont(f2);

        b1.setFont(f2);
        b2.setFont(f2);

        // Set component bounds
        title.setBounds(250, 30, 300, 50);
        l1.setBounds(250, 100, 300, 30);
        t1.setBounds(250, 140, 300, 30);
        l2.setBounds(250, 200, 300, 30);
        p1.setBounds(250, 240, 300, 30);
        b1.setBounds(300, 300, 200, 40);
        b2.setBounds(300, 360, 200, 40);

        // Add components to the background panel
        backgroundPanel.add(title);
        backgroundPanel.add(l1);
        backgroundPanel.add(t1);
        backgroundPanel.add(l2);
        backgroundPanel.add(p1);
        backgroundPanel.add(b1);
        backgroundPanel.add(b2);

        // Set the background panel as the content pane
        setContentPane(backgroundPanel);

        // Button actions
        b1.addActionListener(
                a -> {
                    String s1 = new String(p1.getPassword());
                    if (t1.getText().equals("admin") && s1.equals("pass")) {
                        new Adashboard();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Admin login wrong");
                    }
                }
        );

        b2.addActionListener(
                a -> {
                    new Landing();
                    dispose();
                }
        );

        // Frame settings
        setVisible(true);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Admin Login");
    }

    public static void main(String[] args) {
        new Alogin();
    }
}
