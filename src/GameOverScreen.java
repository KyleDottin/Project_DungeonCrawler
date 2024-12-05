import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameOverScreen extends JPanel implements KeyListener,Engine { //Deals with the Game Over Screen

    JFrame frame;
    protected BufferedImage GameOverTitle;
    protected boolean isPressed = false;

    void LaunchGameOverScreen(){

        frame = new JFrame();
        frame.setTitle("Game Over");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(590, 613);

        frame.add(this);

        new Thread(() -> {
            while (!isPressed) {
                Thread.onSpinWait();
            }
            frame.dispose();
            try {
                Main mainGame = new Main("./data/BeginningMap.txt");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();

        frame.addKeyListener(this);
        frame.setVisible(true);

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Draw(g);
    }

    @Override
    public void Draw(Graphics g) {

        g.fillRect(0, 0, 590,613);

        try {
            GameOverTitle= ImageIO.read(new File("./img/GameOverTitle.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g.drawImage(GameOverTitle, 44, 238, 483,98, null);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Algerian", Font.PLAIN, 24));
        g.drawString("Press Space to Restart", 134, 380);



    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            isPressed=true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void update() {
        repaint();
    }

}
