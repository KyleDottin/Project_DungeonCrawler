import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class HitSprite extends Sprite {
    private int spriteSheetNumberofColumn=8;
    private int width=40;
    private int height=40;
    private SwordSprite swordSprite;
    private DynamicSprite dynamicSprite;
    private int index=0;

    public HitSprite(BufferedImage image, double x, double y, double width, double height, SwordSprite swordSprite, DynamicSprite dynamicSprite) {
        super(image, x, y, width, height);
        this.swordSprite = swordSprite;
        this.dynamicSprite = dynamicSprite;
    }

    public void Draw(Graphics g) {
        if(dynamicSprite.damageMob || index!=0) {

            int width1 = index * width;
            int width2 = (index + 1) * width;
            if(dynamicSprite.isAttacking) {
                switch (dynamicSprite.direction) {
                    case EAST -> {
                        x = (int) swordSprite.x;
                        y = (int) swordSprite.y-47;
                    }
                    case WEST -> {
                        x = (int) swordSprite.x-100;
                        y = (int) swordSprite.y-37;
                    }
                    case SOUTH -> {
                        x = (int) swordSprite.x-40;
                        y = (int) swordSprite.y+3;
                    }
                    case NORTH -> {
                        x = (int) swordSprite.x -74;
                        y = (int) swordSprite.y-90;
                    }
                }
            }

            g.drawImage(image, (int) x, (int) y, (int) (x + width*4), (int) (y + height*4),
                    width1, 0, width2, height, null);
            index+=1;
            if(index==spriteSheetNumberofColumn){
                index=0;
            }
        }
    }
}
