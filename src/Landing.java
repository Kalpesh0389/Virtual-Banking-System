import javax.swing.*;
import java.awt.*;

class Landing extends JFrame {
    Landing() {
        Font f = new Font("Futura", Font.BOLD, 40); // Title font: bold and larger size
        Font f2 = new Font("Calibri", Font.PLAIN, 22); // Font for buttons

        // Custom JPanel to handle background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the image
                ImageIcon backgroundImage = new ImageIcon(ClassLoader.getSystemResource("icon/landing.jpg"));
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

        // Title label with bold font and white color
        JLabel l1 = new JLabel("Virtual Banking System", JLabel.CENTER);
        l1.setFont(f);
        l1.setForeground(Color.WHITE); // Set text color to white
        l1.setBounds(150, 50, 500, 50);

        // Buttons
        JButton b1 = createRoundedButton("Admin");
        JButton b2 = createRoundedButton("Existing Customer");
        JButton b3 = createRoundedButton("New Customer");

        // Set fonts for buttons
        b1.setFont(f2);
        b2.setFont(f2);
        b3.setFont(f2);

        // Set button positions at the bottom (horizontal arrangement)
        int buttonWidth = 200;
        int buttonHeight = 50;
        int gap = 20; // Space between buttons

        int totalWidth = (buttonWidth * 3) + (gap * 2);
        int startX = (800 - totalWidth) / 2; // Center the buttons horizontally
        int startY = 450; // Y-coordinate for the buttons

        b1.setBounds(startX, startY, buttonWidth, buttonHeight);
        b2.setBounds(startX + buttonWidth + gap, startY, buttonWidth, buttonHeight);
        b3.setBounds(startX + (buttonWidth + gap) * 2, startY, buttonWidth, buttonHeight);

        // Add components to the background panel
        backgroundPanel.add(l1);
        backgroundPanel.add(b1);
        backgroundPanel.add(b2);
        backgroundPanel.add(b3);

        // Set the background panel as the content pane
        setContentPane(backgroundPanel);

        // Button actions
        b1.addActionListener(a -> {
            new Alogin();
            dispose(); // Close the current page
        });
        b2.addActionListener(a -> {
            new Elogin();
            dispose();
        });
        b3.addActionListener(a -> {
            new Nlogin();
            dispose();
        });

        // Frame settings
        setVisible(true);
        setSize(800, 550);
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Landing Page");
    }

    // Helper method to create rounded buttons with customized color
    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                // Use anti-aliasing for smoother edges
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Set background color with transparency (subtle and complementary to the background)
                g2.setColor(new Color(0, 102, 204, 150)); // Light teal with some transparency
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Rounded rectangle (arc width & height: 30)

                // Draw button text
                g2.setColor(Color.WHITE); // Text color
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 4;
                g2.drawString(getText(), x, y);

                // Prevent default button painting
                setContentAreaFilled(false);
                setBorderPainted(false);
            }
        };

        // Remove default border and make it transparent
        button.setFocusPainted(false);
        button.setOpaque(false);
        return button;
    }

    public static void main(String[] args) {
        new Landing();
    }
}
