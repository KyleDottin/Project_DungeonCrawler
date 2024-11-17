import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Sprite implements Displayable {
    protected BufferedImage image;
    protected double x;
    protected double y;
    protected double height;
    protected double width;
    protected char tile;

    public Sprite(BufferedImage image, double x, double y, double width, double height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Sprite(BufferedImage image, double x, double y, double width, double height, char tile) {
        this.image = image;
        this.tile = tile;
        this.width = width;
        this.height = height;
        this.y = y;
        this.x = x;
    }

    public char getTile() {
        return tile;
    }

    @Override
    public void Draw(Graphics g){
        g.drawImage(image,(int)x,(int)y,null);
    }
}
