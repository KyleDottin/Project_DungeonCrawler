import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * PnjSprite deals with the sprite of the pnj and where he appears
 */

public class PnjSprite extends DynamicSprite {
    private final String pathname;


    public PnjSprite(BufferedImage image, double x, double y, double width, double height, String pathname) {
        super(image, x, y, width, height);
        this.pathname=pathname;
    }

    public void Draw(Graphics g) {
        if(pathname.equals("./data/BeginningMap.txt")){
            g.drawImage(image,(int) x, (int) y, (int) width, (int) height,null);

        }
    }

}
