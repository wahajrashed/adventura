import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Custom JPanel to paint the background image
class BackgroundPanel extends JPanel {
    private BufferedImage image;

    // Constructor to initialize the panel with a background image from the given file path
    public BackgroundPanel(String filePath) {
        try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Overridden method to paint the background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
