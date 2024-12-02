import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StartScreen extends JPanel implements KeyListener, Engine {

    JFrame frame;
    protected volatile boolean isStarted;
    protected BufferedImage Background;
    protected BufferedImage Title;

    void LaunchStartScreen(){

        frame = new JFrame();
        frame.setTitle("Start Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(590, 613);

        frame.add(this);

        new Thread(() -> {
            while (!isStarted) {
                Thread.onSpinWait();
            }
            frame.dispose();
        }).start();

        frame.addKeyListener(this);
        frame.setVisible(true);

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            isStarted=true;
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Draw(g);
    }

    @Override
    public void Draw(Graphics g) {
        try {
            Background = ImageIO.read(new File("./img/TitleScreen.png"));
            Title = ImageIO.read(new File("./img/Title.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g.drawImage(Background, 0, 0, 590,613, null);

        g.drawImage(Title, 114, 200, 724/2,98/2, null);


        // Instructions
        g.setFont(new Font("Algerian", Font.PLAIN, 24));
        g.drawString("Press Space to Start", 164, 350);


    }

    @Override
    public void update() {
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
