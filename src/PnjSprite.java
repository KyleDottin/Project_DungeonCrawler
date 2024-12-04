import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class PnjSprite extends DynamicSprite {
    private String pathname;
    private DynamicSprite dynamicSprite;


    public PnjSprite(BufferedImage image, double x, double y, double width, double height, String pathname, DynamicSprite dynamicSprite) {
        super(image, x, y, width, height);
        this.pathname=pathname;
        this.dynamicSprite=dynamicSprite;
    }

    public void Draw(Graphics g) {
        if(pathname.equals("./data/BeginningMap.txt")){
            g.drawImage(image,(int) x, (int) y, (int) width, (int) height,null);

        }
    }

}
