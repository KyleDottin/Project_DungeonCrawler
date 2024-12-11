import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * HealthSprite is the class that deals with the drawing of the health of the character.
 */

public class HealthSprite extends DynamicSprite{
    protected int MaxHealth;
    protected final int Health=2;
    protected int index=0;
    protected DynamicSprite dynamicSprite;
    protected long InvicibilityFrame;
    protected long InvicibilityPeriod=1200;
    protected boolean firstTime=true;
    protected JFrame frame;
    private final GameOverScreen gameOverScreen=new GameOverScreen();

    public HealthSprite(BufferedImage image, double x, double y, double width, double height, DynamicSprite dynamicSprite, JFrame frame) {
        super(image, x, y, width, height);
        this.dynamicSprite = dynamicSprite;
        this.frame=frame;
    }

    @Override
    public void paintComponents(Graphics g){
        g.setColor(new Color(255, 0, 0,64));
        g.fillRect(0,0,576,576);
    }

    @Override
    public void Draw(Graphics g) {
        if(dynamicSprite.index0){
            firstTime=true;
            dynamicSprite.index0=false;
        }
        if (dynamicSprite.getHit && (System.currentTimeMillis() - InvicibilityFrame)>=InvicibilityPeriod
                || dynamicSprite.getHitMob && (System.currentTimeMillis() - InvicibilityFrame)>=InvicibilityPeriod
                || dynamicSprite.getFall && (System.currentTimeMillis() - InvicibilityFrame)>=InvicibilityPeriod) {
            InvicibilityFrame= System.currentTimeMillis();
            index += 1;
        }

        if (dynamicSprite.getHit && !((System.currentTimeMillis() - InvicibilityFrame)>=InvicibilityPeriod)
        || dynamicSprite.getHitMob && !((System.currentTimeMillis() - InvicibilityFrame)>=InvicibilityPeriod)
                || dynamicSprite.getFall && !((System.currentTimeMillis() - InvicibilityFrame)>=InvicibilityPeriod)){ //Use the paintcomponents method if the character is taking damage
            paintComponents(g);
        }

        int attitude =0;
        int width1 = index * (int) width;
        int width_no1 = 2 * (int) width;
        int height1 = attitude * (int) height;
        int width2 = (index + 1) * (int) width;
        int width_no2 = 3 * (int) width;
        int height2 = (attitude + 1) * (int) height;

        if (firstTime) { //Print the full heart
            index=0;
            MaxHealth=0;
            g.drawImage(image, (int) x / 8, (int) y / 8, (int) (x + width) / 8, (int) (y + height) / 8,
                    0, height1, width2, height2, null);
            g.drawImage(image, (int) (16 * x / 8), (int) y / 8, (int) (16 * x + width) / 8, (int) (y + height) / 8,
                    0, height1, width2, height2, null);
            g.drawImage(image, (int) (32 * x / 8), (int) y / 8, (int) (32 * x + width) / 8, (int) (y + height) / 8,
                    0, height1, width2, height2, null);
            firstTime=false;
        }
        if(MaxHealth==0) { //If no heart is empty
            g.drawImage(image, (int) (32*x / 8), (int) y / 8, (int) (32*x + width) / 8, (int) (y + height) / 8,
                    width1, height1, width2, height2, null);
            g.drawImage(image, (int) x / 8, (int) y / 8, (int) (x + width) / 8, (int) (y + height) / 8,
                    0, height1, (int) width, height2, null);
            g.drawImage(image, (int) (16 * x / 8), (int) y / 8, (int) (16 * x + width) / 8, (int) (y + height) / 8,
                    0, height1, (int) width, height2, null);
        }
        if(MaxHealth==1){ //If the first heart is empty
            g.drawImage(image, (int) (16*x / 8), (int) y / 8, (int) (16*x + width) / 8, (int) (y + height) / 8,
                    width1, height1, width2, height2, null);
            g.drawImage(image, (int) x / 8, (int) y / 8, (int) (x + width) / 8, (int) (y + height) / 8,
                    0, height1, (int) width, height2, null);
            g.drawImage(image, (int) (32 * x / 8), (int) y / 8, (int) (32 * x + width) / 8, (int) (y + height) / 8,
                    width_no1, height1, width_no2, height2, null);
        }
        if(MaxHealth==2){ //If the second heart is empty
            g.drawImage(image, (int) x/8, (int) y / 8, (int) (x + width) / 8, (int) (y + height) / 8,
                    width1, height1, width2, height2, null);
            g.drawImage(image, (int) (16*x / 8), (int) y / 8, (int) (16*x + width) / 8, (int) (y + height) / 8,
                    width_no1, height1, width_no2, height2, null);
            g.drawImage(image, (int) (32 * x / 8), (int) y / 8, (int) (32 * x + width) / 8, (int) (y + height) / 8,
                    width_no1, height1, width_no2, height2, null);
        }

        if (index==Health) { //If a heart is empty, reset the index
            MaxHealth+=1;
            index=0;

            if (MaxHealth==3) { //If there is no heart left
                frame.dispose();
                gameOverScreen.LaunchGameOverScreen();
            }
        }
    }


}

