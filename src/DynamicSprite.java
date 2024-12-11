import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * DynamicSprite si the class that deals with all the interactions the character has.
 */

class DynamicSprite extends SolidSprite{
    protected boolean isWalking;
    protected double speed=7;
    protected final int spriteSheetNumberofColumn=10;
    protected int timeBetweenFrame=50;
    protected Direction direction=Direction.SOUTH;
    protected boolean getHit;
    protected boolean getHitMob;
    protected boolean mapChangeTriggered;
    protected boolean fairyDialogTrigger;
    protected boolean pnjDialogTrigger;
    protected boolean index0=false;
    protected double xh=x+5;
    protected double yh=y+16;
    private final double widthh=width-10;
    private final double heighth=height-20;
    protected int xd;
    protected int yd;
    protected int lastDoorx;
    protected int lastDoory;
    protected boolean isWind;
    private Main main;
    protected boolean isAttacking;
    protected boolean damageMob;
    protected boolean getFall;
    protected boolean mobDead;
    protected boolean touchRupee;
    protected boolean beenRecover;

    public DynamicSprite(BufferedImage image, double x, double y, double width, double height) {
        super(image, x, y, width, height);
    }

    public DynamicSprite(BufferedImage image, double x, double y, double width, double height, Main main) {
        super(image, x, y, width, height);
        this.main = main;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void Draw(Graphics g) {
        if(main.path.equals("./data/CaveMap1.txt") || main.path.equals("./data/CaveMap2.txt")) {
            try {
                this.image = ImageIO.read(new File("./img/heroTileSheetLowResTorch.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            try {
                this.image = ImageIO.read(new File("./img/heroTileSheetLowRes.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (isWalking) {
            if (isWind){
                speed=4;
            }else{
                speed=7;
            }
            int index = (int) (System.currentTimeMillis() / timeBetweenFrame % spriteSheetNumberofColumn);

            int attitude = direction.getFrameLineNumber();
            int width1 = index * (int) width;
            int height1 = attitude * (int) height;
            int width2 = (index + 1) * (int) width;
            int height2 = (attitude + 1) * (int) height;


            g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                    (int) width1, (int) height1, (int) width2, height2, null);
        }
        else{
            int attitude= direction.getFrameLineNumber();
            int index=0;
            int height1 = attitude * (int) height;
            int width2 = (index+1) * (int) width;
            int height2 = (attitude + 1) * (int) height;
            g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                    0, height1, width2, height2, null);
        }
        g.setColor(Color.BLUE);
        Rectangle2D.Double hitbox = Hitbox();
        g.drawRect((int) hitbox.x, (int) hitbox.y,
                (int) hitbox.width, (int) hitbox.height);

        if (pnjDialogTrigger || fairyDialogTrigger) {
            String Interact = "Interact";
            int x_interact = 15;
            int y_interact = 75;
            g.setColor(Color.BLACK);
            g.setFont(new Font("Microsoft PhagsPa", Font.BOLD, 12));
            g.drawString(Interact, x_interact, y_interact);
        }
    }

    private void move(){
        if (isWalking) {
            switch (direction) {
                case EAST:
                    this.x += speed;
                    this.xh += speed;
                    if (isWind){
                        this.y += speed/2;
                        this.yh+=speed/2;
                    }
                    break;
                case WEST:
                    this.x -= speed;
                    this.xh -= speed;
                    if (isWind){
                        this.y += speed/2;
                        this.yh+=speed/2;
                    }
                    break;
                case SOUTH:
                    this.y += speed;
                    this.yh += speed;
                    break;
                case NORTH:
                    this.y -= speed;
                    this.yh -= speed;
                    break;
                default:
                    break;
            }
        }
    }
    public Rectangle2D.Double Hitbox(){
        return switch (direction) {
            case EAST -> new Rectangle2D.Double(this.xh + speed, this.yh,
                    this.widthh, this.heighth);
            case WEST -> new Rectangle2D.Double(this.xh - speed, this.yh,
                    this.widthh, this.heighth);
            case SOUTH -> new Rectangle2D.Double(this.xh, this.yh + speed,
                    this.widthh, this.heighth);
            case NORTH -> new Rectangle2D.Double(this.xh, this.yh - speed,
                    this.widthh, this.heighth);
        };
    }

    void checkMap(ArrayList<DoorSprite> doorSprite) { //Check if a door is touch
        Rectangle2D.Double hitbox = Hitbox();
        for (DoorSprite E : doorSprite) {
            Rectangle2D.Double elementHitBox = new Rectangle2D.Double(
                    E.x, E.y, E.width, E.height);
            if (hitbox.intersects(elementHitBox)) {
                xd=(int) E.x;
                yd=(int) E.y;
                mapChangeTriggered=true;
                return;
            }
        }
        mapChangeTriggered=false;
    }

    void checkIfHitTrap(ArrayList<TrapSprite> trapSprite) { //Check if a trap is touch
        Rectangle2D.Double hitbox = Hitbox();
        for (TrapSprite E : trapSprite) {
            Rectangle2D.Double elementHitBox = new Rectangle2D.Double(
                    E.x, E.y, E.width, E.height);
            if (hitbox.intersects(elementHitBox)) {
                speed=3;
                getHit=true;
                return;
            }
        }
        getHit=false;
        speed=7;
    }

    void checkIfHitMob(ArrayList<MobSprite> mobSprite) { //Check if a mob is touch
        if(mobDead){
            getHitMob=false;
            return; //Don't use check if hit if the mob is dead
        }
        Rectangle2D.Double hitbox = Hitbox();
        for (MobSprite E : mobSprite) {
            Rectangle2D.Double elementHitBox = new Rectangle2D.Double(
                    E.x, E.y, E.width, E.height);
            if (hitbox.intersects(elementHitBox)) {
                getHitMob=true;
                return;
            }
        }
        getHitMob=false;
    }

    void checkIfFall(ArrayList<CliffSprite> cliffSprite) { //Check if a cliff is touch
        Rectangle2D.Double hitbox = Hitbox();
        for (CliffSprite E : cliffSprite) {
            Rectangle2D.Double elementHitBox = new Rectangle2D.Double(
                    E.x+16, E.y+16, E.width-32, E.height-32);
            if (hitbox.intersects(elementHitBox)) {
                x=lastDoorx;
                xh=lastDoorx;
                y=lastDoory;
                yh=lastDoory;
                getFall=true;
                return;
            }
        }
        getFall=false;
    }

    void checkIfHitpnj(ArrayList<PnjSprite> pnjSprite) { //Check if a pnj is touch
        if(main.path.equals("./data/BeginningMap.txt")){
        Rectangle2D.Double hitbox = Hitbox();
        for (PnjSprite E : pnjSprite) {
            Rectangle2D.Double elementHitBox = new Rectangle2D.Double(
                    E.x, E.y, E.width, E.height);
            if (hitbox.intersects(elementHitBox)) {
                pnjDialogTrigger = true;
                return;
            }
        }
        }
        pnjDialogTrigger=false;
    }

    public boolean isMovingPossible(ArrayList<Sprite> environment){ //Check if a solid sprite is touch
        Rectangle2D.Double hitbox=Hitbox();
        for (Sprite E : environment){
            if (E instanceof SolidSprite && E != this){
                Rectangle2D.Double elementHitBox = new Rectangle2D.Double(
                        E.x, E.y, E.width, E.height);
                if (hitbox.intersects(elementHitBox)) {
                    if (isWind){ // To avoid being trap in the Solid Sprites
                        this.y += speed/2;
                        this.yh+=speed/2;
                    }
                    return false;
                }
            }
        }
        if (isWind){
            this.y -= speed/2;
            this.yh-=speed/2;
        }
        return true;
    }

    void isTalkingFairy(ArrayList<FairySprite> fairysprite){ //Check if a fairy is touch
        if(main.path.equals("./data/FairyMap.txt")){
        Rectangle2D.Double hitbox=Hitbox();
        for (Sprite E : fairysprite){
            if (E instanceof SolidSprite && E != this) {
                Rectangle2D.Double elementHitBox = new Rectangle2D.Double(
                        E.x * 2, E.y * 2, E.width * 2, E.height * 2);
                if (hitbox.intersects(elementHitBox)) {
                    fairyDialogTrigger = true;
                    return;
                }
            }

            }
        }
        fairyDialogTrigger=false;

    }

    public void isTouchingRupee(ArrayList<RupeeSprite> rupees) { //Check if character touch rupee
        Rectangle2D.Double hitbox = Hitbox();
        for (Sprite E : rupees) {
                Rectangle2D.Double elementHitBox = new Rectangle2D.Double(
                        E.x, E.y, E.width, E.height);
                if (hitbox.intersects(elementHitBox)) {
                    touchRupee=true;
                    beenRecover=true;
                    return;
                }
        }
        touchRupee=false;
    }


    public void moveIfPossible(ArrayList<Sprite> environment){
        if (isMovingPossible(environment)){
            move();
        }
    }

    public void startWalking(){
        isWalking=true;
    }

}