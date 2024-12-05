import jdk.dynalink.DynamicLinker;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class MobSprite extends DynamicSprite { //Deals with the Simple pattern Mobs Sprite
    private String pathname;
    private DynamicSprite dynamicSprite;
    private int timeBetweenFrame=100;
    private int spriteSheetNumberofColumn=12;
    private int mobSpeed=2;
    protected Direction mobDirection=Direction.EAST;
    private int healthBar=2;
    private int InvicibilityPeriod=1200;
    private long InvicibilityFrame;
    private boolean differentHealth;
    private int recoilDamage=30;


    public MobSprite(BufferedImage image, double x, double y, double width, double height, String pathname, DynamicSprite dynamicSprite) {
        super(image, x, y, width, height);
        this.pathname = pathname;
        this.dynamicSprite = dynamicSprite;
    }

    public void Draw(Graphics g) {
        if((pathname.equals("./data/BeginningMap.txt") || pathname.equals("./data/WindyMap.txt")) && healthBar!=0){
            if (dynamicSprite.damageMob && (System.currentTimeMillis() - InvicibilityFrame)>=InvicibilityPeriod) {
                InvicibilityFrame= System.currentTimeMillis();
                healthBar -= 1;
                differentHealth=true;
            }

            dynamicSprite.mobDead=false;
            int index = (int) (System.currentTimeMillis() / timeBetweenFrame % spriteSheetNumberofColumn);

            int width1 = index * (int) width;
            int width2 = (index + 1) * (int) width;

            Rectangle2D.Double Hitbox = HitboxMob();
            g.setColor(Color.RED);
            g.drawRect((int) Hitbox.x, (int) Hitbox.y, (int) Hitbox.width, (int) Hitbox.height);

            g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                    width1, 0, width2, (int) height, null);
        }else {
            dynamicSprite.mobDead = true;
        }
            switch (mobDirection) {
                case EAST -> this.x = x + mobSpeed;
                case WEST -> this.x = x - mobSpeed;
                case NORTH -> this.y = y - mobSpeed;
                case SOUTH -> this.y = y + mobSpeed;
            }
        if(differentHealth){ // Recoil damage
            switch (dynamicSprite.direction) {
                case EAST -> this.x = x + recoilDamage + mobSpeed;
                case WEST -> this.x = x - recoilDamage - mobSpeed;
                case NORTH -> this.y = y - recoilDamage - mobSpeed;
                case SOUTH -> this.y = y + recoilDamage + mobSpeed;
            }
            differentHealth=false;
        }
    }

    protected Rectangle2D.Double HitboxMob(){
        return switch (mobDirection) {
                case EAST -> new Rectangle2D.Double(this.x + 13 + mobSpeed, this.y,
                        this.width / 2, this.height / 1.2);
                case WEST -> new Rectangle2D.Double(this.x + 13 - mobSpeed, this.y,
                        this.width / 2, this.height / 1.2);
                case SOUTH -> new Rectangle2D.Double(this.x + 13, this.y + mobSpeed,
                        this.width / 2, this.height / 1.2);
                case NORTH -> new Rectangle2D.Double(this.x + 13, this.y - mobSpeed,
                        this.width / 2, this.height / 1.2);
            };
    }

    void isMobMovingPossible(ArrayList<Sprite> environment){ //Random movement
            Rectangle2D.Double hitbox = HitboxMob();
            for (Sprite E : environment) {
                if (E instanceof SolidSprite && E != this) {
                    Rectangle2D.Double elementHitBox = new Rectangle2D.Double(
                            E.x, E.y, E.width, E.height);
                    if (hitbox.intersects(elementHitBox)) {
                        Random rand = new Random();
                        Direction newDirection = Direction.values()[rand.nextInt(Direction.values().length)];
                        while (newDirection == mobDirection) {
                            rand = new Random();
                            newDirection = Direction.values()[rand.nextInt(Direction.values().length)];
                        }
                        mobDirection = newDirection;
                    }
                }
            }
    }
}
