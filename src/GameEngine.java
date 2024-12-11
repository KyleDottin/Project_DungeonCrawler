import java.awt.event.*;
import java.awt.*;
import javax.swing.JOptionPane;

/**
 * GameEngine handles all the internal game specificity such as the spotting of the key pressed
 * or the dialog.
 */

public class GameEngine implements Engine,KeyListener { //Deals with the Game Specificity
    private final DynamicSprite hero;
    private final PhysicEngine P;
    private boolean isUp;
    private boolean isDown;
    private boolean isLeft;
    private boolean isRight;

    public GameEngine(DynamicSprite hero, PhysicEngine P) {
        this.hero = hero;
        this.P = P;
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
            case KeyEvent.VK_X: //Interact with npcs, objects or mobs
                FairyDialog();
                PnjDialog();
                hero.isAttacking = true;
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
            case KeyEvent.VK_X:
                hero.isAttacking = false;
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

    void FairyDialog(){ //Handles Fairy Dialog
        if(hero.fairyDialogTrigger){
            P.pause();
            int response = JOptionPane.showConfirmDialog(null,
                    "Ah, young traveler, I sense the weight of your pain, let me make your path ahead brigther",
                    " ", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                hero.index0=true;
            } else if (response == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(null,
                        "You are brave. If you change your mind, I will still be here to help you in your quest.");
            }
            P.isPause=false;
        }
    }

    void PnjDialog(){ // Handles Pnj Dialog
        if(hero.pnjDialogTrigger){
            P.pause();
            JOptionPane.showMessageDialog(null,
                    "Such a young child has no place here. Leave now, if you wish to survive!");
            P.isPause=false;
        }
    }

    @Override
    public void Draw(Graphics g) {

    }

    @Override
    public void update(){
        Walk();
    }
}