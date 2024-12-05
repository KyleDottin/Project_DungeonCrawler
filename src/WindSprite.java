import java.awt.*;
import java.awt.image.BufferedImage;

public class WindSprite extends DynamicSprite { //class for the wind sprite
    private final String pathname;
    protected DynamicSprite dynamicSprite;
    private int timeBetweenFrame=100;
    private int spriteSheetNumberofColumn=12;


    public WindSprite(BufferedImage image, double x, double y, double width, double height, String pathname, DynamicSprite dynamicSprite) {
        super(image, x, y, width, height);
        this.pathname=pathname;
        this.dynamicSprite=dynamicSprite;

    }

    public void Draw(Graphics g) {
        if(pathname.equals("./data/WindyMap.txt")){
            dynamicSprite.isWind=true;
            int index = (int) (System.currentTimeMillis() / timeBetweenFrame % spriteSheetNumberofColumn);

            int width1 = index * (int) width;
            int width2 = (index + 1) * (int) width;


            g.drawImage(image,(int) x,(int) y, (int) (x + width), (int) (y + height),
                    width1, 0, width2, (int) height, null);
        }else{
            dynamicSprite.isWind=false;
        }
    }
}
