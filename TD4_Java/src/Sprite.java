import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite implements Displayable {
    protected BufferedImage image;
    protected double x;
    protected double y;
    protected double height;
    protected double width;

    public Sprite(BufferedImage image, double x, double y, double width, double height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    @Override
    public void Draw(Graphics g){
        g.drawImage(image,(int)x,(int)y,null);
    }

    @Override
    public void paintComponents(Graphics g) {

    }
}
