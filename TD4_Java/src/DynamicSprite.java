import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

class DynamicSprite extends SolidSprite{
    protected boolean isWalking;
    protected double speed=7;
    protected final int spriteSheetNumberofColumn=10;
    protected int timeBetweenFrame=50;
    protected Direction direction=Direction.SOUTH;
    protected boolean getHit=false;

    public DynamicSprite(BufferedImage image, double x, double y, double width, double height) {
        super(image, x, y, width, height);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void resetHit(){
        getHit=false;
    }

    @Override
    public void Draw(Graphics g) {
        if (isWalking) {
            int index = (int) System.currentTimeMillis() / timeBetweenFrame % spriteSheetNumberofColumn;

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
            int width1 = index * (int) width;
            int height1 = attitude * (int) height;
            int width2 = (index+1) * (int) width;
            int height2 = (attitude + 1) * (int) height;
            g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                    (int) width1, (int) height1, (int) width2, height2, null);
        }
    }

    private void move(){
        if (isWalking) {
            switch (direction) {
                case EAST:
                    this.x += speed;
                    break;
                case WEST:
                    this.x -= speed;
                    break;
                case SOUTH:
                    this.y += speed;
                    break;
                case NORTH:
                    this.y -= speed;
                    break;
                default:
                    break;
            }
        }
    }
    public Rectangle2D.Double Hitbox(){
        Rectangle2D.Double hitbox = switch (direction) {
            case EAST -> new Rectangle2D.Double(this.x + speed, this.y,
                    this.width, this.height);
            case WEST -> new Rectangle2D.Double(this.x - speed, this.y,
                    this.width, this.height);
            case SOUTH -> new Rectangle2D.Double(this.x, this.y + speed,
                    this.width, this.height);
            case NORTH -> new Rectangle2D.Double(this.x, this.y - speed,
                    this.width, this.height);
        };
        return hitbox;
    }
    public boolean isMovingPossible(ArrayList<Sprite> environment){
        Rectangle2D.Double hitbox=Hitbox();
        for (Sprite E : environment){
            if (E instanceof SolidSprite && E != this){
                Rectangle2D.Double elementHitBox = new Rectangle2D.Double(
                        E.x+5, E.y, E.width-10, E.height-5);

                if (hitbox.intersects(elementHitBox)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void moveIfPossible(ArrayList<Sprite> environment){
        if (isMovingPossible(environment)){
            move();
        }
    }

    public void startWalking(){
        isWalking=true;
    }

    void checkIfHit(ArrayList<HealthSprite> healthSprite) {
        Rectangle2D.Double hitbox = Hitbox();
        for (HealthSprite E : healthSprite) {
            Rectangle2D.Double elementHitBox = new Rectangle2D.Double(
                    E.x, E.y, E.width, E.height);
            if (hitbox.intersects(elementHitBox)) {
                System.out.println("Hit");
                getHit=true;
                break;
            }
        }
        getHit=false;
    }

    public boolean isGetHit() {
        System.out.println(getHit);
        return getHit;
    }
}