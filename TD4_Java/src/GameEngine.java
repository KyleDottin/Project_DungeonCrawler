import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameEngine implements Engine,KeyListener {
    private final DynamicSprite hero;
    private boolean isUp;
    private boolean isDown;
    private boolean isLeft;
    private boolean isRight;

    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                isUp = true;
                break;
            case KeyEvent.VK_DOWN:
                isDown = true;
                break;
            case KeyEvent.VK_LEFT:
                isLeft = true;
                break;
            case KeyEvent.VK_RIGHT:
                isRight = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                isUp = false;
                break;
            case KeyEvent.VK_DOWN:
                isDown = false;
                break;
            case KeyEvent.VK_LEFT:
                isLeft = false;
                break;
            case KeyEvent.VK_RIGHT:
                isRight = false;
                break;
            default:
                break;
        }
    }

    public void Walk(){
        if(isUp){
            hero.startWalking();
            hero.setDirection(Direction.NORTH);
        }
        else if(isDown){
            hero.startWalking();
            hero.setDirection(Direction.SOUTH);
        }
        else if(isLeft){
            hero.startWalking();
            hero.setDirection(Direction.WEST);
        }
        else if(isRight){
            hero.startWalking();
            hero.setDirection(Direction.EAST);
        }
        else{
            hero.isWalking = false;
        }
    }

    @Override
    public void update(){
        Walk();
    }
}