import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * ButtonSprite visually represents the button or key the player needs to press, providing clear guidance.
 */

public class ButtonSprite extends Sprite {


    public ButtonSprite(BufferedImage image, double x, double y, double width, double height) {
        super(image, x, y, width, height);

    }

    public void Draw(Graphics g) {
            g.drawImage(image,(int) x,(int) y, (int) width, (int) height,null);

    }
}
