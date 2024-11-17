import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HealthSprite extends DynamicSprite{
    protected final int MaxHealth=3;
    protected int index=0;
    protected DynamicSprite dynamicSprite;

    public HealthSprite(BufferedImage image, double x, double y, double width, double height, DynamicSprite dynamicSprite) {
        super(image, x, y, width, height);
        this.dynamicSprite = dynamicSprite;
    }


    @Override
    public void Draw(Graphics g) {
        if (dynamicSprite.getHit) {
            index += 1;
        }

        int attitude =0;
        int width1 = index * (int) width;
        int height1 = attitude * (int) height;
        int width2 = (index + 1) * (int) width;
        int height2 = (attitude + 1) * (int) height;


        g.drawImage(image, (int) x/8, (int) y/8, (int) (x + width)/8, (int) (y + height)/8,
                width1, height1, width2, height2, null);

        if (index==MaxHealth-1) {
            index=0;
        }
    }

}

