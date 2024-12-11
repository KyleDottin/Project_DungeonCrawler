import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * CliffSprite is used for PhysicEngine to know what does a cliff is.
 */

public class CliffSprite extends DynamicSprite {


    public CliffSprite(BufferedImage image, double x, double y, double width, double height) {
        super(image, x, y, width, height);
    }
}
