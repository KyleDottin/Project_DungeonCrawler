import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SwordSprite extends DynamicSprite{ //Deals with the Sword Sprite
    private final DynamicSprite dynamicSprite;

    public SwordSprite(BufferedImage image, double x, double y, double width, double height,DynamicSprite dynamicSprite) {
        super(image, x, y, width, height);
        this.dynamicSprite = dynamicSprite;
    }

    public void Draw(Graphics g){

        if(!dynamicSprite.fairyDialogTrigger && !dynamicSprite.pnjDialogTrigger){
            int x_interact=2;
            int y_interact=8;
            int index = 4;
            int width1 = index * (int) width;
            int width2 = (index + 1) * (int) width;
            g.drawImage(image, x_interact, y_interact, (int) (x_interact + width), (int) (y_interact + height),
                     width1, 0, width2, (int) height, null);
            }
        if(dynamicSprite.isAttacking) {
            int index = dynamicSprite.direction.getFrameLineNumber();
            int width1 = index * (int) width;
            int width2 = (index + 1) * (int) width;
            if(index==1){
                x = dynamicSprite.x-10; //Solve problem of sword sprite for the case where index=1
            }else {
                x = dynamicSprite.x;
            }
            y = dynamicSprite.y;
            g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                    width1, 0, width2, (int) height, null);

            Rectangle2D.Double Hitbox= SwordHitbox();
            g.setColor(Color.GREEN);
            g.drawRect((int) Hitbox.x, (int) Hitbox.y, (int) Hitbox.width, (int) Hitbox.height);

        }

    }

    public Rectangle2D.Double SwordHitbox(){
        if(!dynamicSprite.isAttacking){
            return new Rectangle2D.Double(0,0,0,0);
        }
        return switch (direction) {
            case EAST -> new Rectangle2D.Double(this.x-5 + speed, this.y-15,
                    dynamicSprite.width*1.2, dynamicSprite.height*1.4);
            case WEST -> new Rectangle2D.Double(this.x -5- speed, this.y-15,
                    dynamicSprite.width*1.2, dynamicSprite.height*1.4);
            case SOUTH -> new Rectangle2D.Double(this.x-5, this.y -15+ speed,
                    dynamicSprite.width*1.2, dynamicSprite.height*1.4);
            case NORTH -> new Rectangle2D.Double(this.x-5, this.y -15- speed,
                    dynamicSprite.width*1.2, dynamicSprite.height*1.4);
        };
    }

    void checkIfDamageMob(ArrayList<MobSprite> mobSprite) {
        Rectangle2D.Double hitbox = SwordHitbox();
        for (MobSprite E : mobSprite) {
            Rectangle2D.Double elementHitBox = new Rectangle2D.Double(
                    E.x, E.y, E.width, E.height);
            if (hitbox.intersects(elementHitBox)) {
                dynamicSprite.damageMob=true;
                return;
            }
        }
        dynamicSprite.damageMob=false;
    }
}
