import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StartScreen extends JPanel implements KeyListener, Engine { //Deals with the start screen

    JFrame frame;
    protected volatile boolean isStarted;
    private int width=576;
    private int heigth=576;
    private int x=0;
    private int y=0;
    private BufferedImage Background;
    private int index=0;
    private BufferedImage Title;
    private long timeBetweenFrame=5000;
    private int spriteSheetNumberofColumn=7;
    private long waiting=System.currentTimeMillis();

    void LaunchStartScreen(){

        frame = new JFrame();
        frame.setTitle("Start Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(590, 613);

        frame.add(this);

        new Thread(() -> {
            while (!isStarted) {
                repaint();
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
            if(index==0 && ((System.currentTimeMillis()-waiting)>=timeBetweenFrame)){
                index+=1;
                timeBetweenFrame=2000;
                waiting=System.currentTimeMillis();
            }
            if(index<=2 && index!=0 && ((System.currentTimeMillis()-waiting)>=timeBetweenFrame)){
                index+=1;
                timeBetweenFrame=500;
                waiting=System.currentTimeMillis();
            }

            int width1 = index * width;
            int width2 = (index + 1) * width;


            g.drawImage(Background, x, y, x+width, y+heigth,
                    width1, 0, width2, heigth, null);


            g.drawImage(Title, 114, 200, 724 / 2, 98 / 2, null);

            g.setFont(new Font("Algerian", Font.PLAIN, 24));
            g.drawString("Press Space to Start", 164, 350);

            if(index>2 && ((System.currentTimeMillis()-waiting)>=timeBetweenFrame)){
                index+=1;
                waiting=System.currentTimeMillis();
            }
            if(index==7){
                index=2;
                waiting=System.currentTimeMillis();
            }

    }

    @Override
    public void update() {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
