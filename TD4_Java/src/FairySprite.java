import java.awt.*;
import java.awt.image.BufferedImage;

public class FairySprite extends DynamicSprite {
    private String pathname;
    private int timeBetweenFrame=100;
    private int spriteSheetNumberofColumn=6;
    protected int xfairy=(int) x*2;
    protected int yfairy=(int) y*2;


    public FairySprite(BufferedImage image, double x, double y, double width, double height, String pathname, DynamicSprite dynamicSprite) {
        super(image, x, y, width, height);
        this.pathname=pathname;
    }

    public void Draw(Graphics g) {
        if(pathname.equals("./data/FairyMap.txt")){
            int index = (int) (System.currentTimeMillis() / timeBetweenFrame % spriteSheetNumberofColumn);

        int width1 = index * (int) width;
        int width2 = (index + 1) * (int) width;


        g.drawImage(image,xfairy, yfairy, (int) ((x + width)*2), (int) ((y + height)*2),
                width1, 0, width2, (int) height, null);

        }
    }


}
