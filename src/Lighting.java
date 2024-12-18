import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Lighting deals with the light effect when reaching a map that needs it. Here, the maps are
 * CaveMap1 and CaveMap2.
 */

public class Lighting implements Displayable {
    String pathname;
    protected double x,y,width,height;
    protected DynamicSprite dynamicSprite;
    private String Map1 = "./data/CaveMap1.txt";
    private String Map2 = "./data/CaveMap2.txt";

    public Lighting(double x, double y, double width, double height, String pathname, DynamicSprite dynamicSprite) {
        this.pathname = pathname;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dynamicSprite = dynamicSprite;
    }

    public void Draw(Graphics g) {
        if(pathname.equals(Map1) || pathname.equals(Map2)) {

            Area screenArea = new Area(new Rectangle2D.Double(x,y,width,height));
            double centerx=dynamicSprite.x;
            double centery=dynamicSprite.y;
            double circleSize=100;
            double xcircle = centerx-circleSize/4;
            double ycircle = centery-circleSize/4;

            Shape circleShape= new Ellipse2D.Double(xcircle,ycircle,circleSize,circleSize);

            Area lightArea = new Area(circleShape);

            screenArea.subtract(lightArea);

            g.setColor(new Color(0, 0, 0,230));
            Graphics2D g2d= (Graphics2D) g;
            g2d.fill(screenArea);
        }
    }

    public void paintComponents(Graphics g) {

    }


}
