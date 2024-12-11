import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RupeeSprite extends DynamicSprite { //Class for the Fairy Sprite
    private DynamicSprite dynamicSprite;
    private MobSprite mobSprite;
    private int spriteSheetNumberofColumn=3;
    private int index=0;


    public RupeeSprite(BufferedImage image, double x, double y, double width, double height, MobSprite mobSprite, DynamicSprite dynamicSprite) {
        super(image, x, y, width, height);
        this.dynamicSprite = dynamicSprite;
        this.mobSprite = mobSprite;
    }

    public void Draw(Graphics g) {
        if(!dynamicSprite.mobDead) {
            double randomnumber= Math.random() *spriteSheetNumberofColumn;
            index = (int) randomnumber;
            dynamicSprite.beenRecover=false;
        }
        if(dynamicSprite.mobDead && !dynamicSprite.touchRupee && !dynamicSprite.beenRecover) {
            x= (int) mobSprite.x;
            y= (int) mobSprite.y;

            int width1 = index * (int) width;
            int width2 = (index + 1) * (int) width;


            g.drawImage(image,(int) x,(int) y, (int) (x + width), (int) (y + height),
                    width1, 0, width2, (int) height, null);

        }
    }

}
