import java.awt.image.BufferedImage;

/**
 * SolidSprite is the class for the Sprite where the character can't go through.
 */

public class SolidSprite extends Sprite{
    public SolidSprite(BufferedImage image, double x, double y, double width, double height) {
        super(image, x, y, width, height);
    }

}
