import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class MobSprite extends DynamicSprite {
    private String pathname;
    private int timeBetweenFrame=100;
    private int spriteSheetNumberofColumn=12;
    private int mobSpeed=2;
    protected Direction mobDirection=Direction.EAST;


    public MobSprite(BufferedImage image, double x, double y, double width, double height, String pathname, DynamicSprite dynamicSprite) {
        super(image, x, y, width, height);
        this.pathname = pathname;
    }

    public void Draw(Graphics g) {
        if(pathname.equals("./data/BeginningMap.txt") || pathname.equals("./data/WindyMap.txt")){
            int index = (int) (System.currentTimeMillis() / timeBetweenFrame % spriteSheetNumberofColumn);

            int width1 = index * (int) width;
            int width2 = (index + 1) * (int) width;

            Rectangle2D.Double Hitbox = HitboxMob();
            g.setColor(Color.RED);
            g.drawRect((int) Hitbox.x, (int) Hitbox.y, (int) Hitbox.width, (int) Hitbox.height);

            g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                    width1, 0, width2, (int) height, null);
            switch (mobDirection) {
                case EAST -> this.x = x + mobSpeed;
                case WEST -> this.x = x - mobSpeed;
                case NORTH -> this.y = y - mobSpeed;
                case SOUTH -> this.y = y + mobSpeed;
            }
        }
    }

    protected Rectangle2D.Double HitboxMob(){
        Rectangle2D.Double hitbox= switch (mobDirection) {
            case EAST -> new Rectangle2D.Double(this.x+13+ mobSpeed, this.y,
                    this.width/2, this.height/1.2);
            case WEST -> new Rectangle2D.Double(this.x +13- mobSpeed, this.y,
                    this.width/2, this.height/1.2);
            case SOUTH -> new Rectangle2D.Double(this.x+13, this.y + mobSpeed,
                    this.width/2, this.height/1.2);
            case NORTH -> new Rectangle2D.Double(this.x+13, this.y - mobSpeed,
                    this.width/2, this.height/1.2);
        };
        return hitbox;
    }

    void isMobMovingPossible(ArrayList<Sprite> environment){
        Rectangle2D.Double hitbox=HitboxMob();
        for (Sprite E : environment){
            if (E instanceof SolidSprite && E != this){
                Rectangle2D.Double elementHitBox = new Rectangle2D.Double(
                        E.x, E.y, E.width, E.height);
                if (hitbox.intersects(elementHitBox)) {
                    Random rand=new Random();
                    Direction newDirection = Direction.values()[rand.nextInt(Direction.values().length)];
                    while(newDirection == mobDirection){
                        rand=new Random();
                        newDirection = Direction.values()[rand.nextInt(Direction.values().length)];
                    }
                    mobDirection=newDirection;
                }
            }
        }
    }
}
