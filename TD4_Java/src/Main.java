    import javax.imageio.ImageIO;
    import javax.swing.*;
    import java.io.File;
    import java.io.IOException;
    import java.util.ArrayList;

    public class Main {

        JFrame frame;
        RenderEngine E;
        GameEngine G;
        PhysicEngine P;
        ArrayList<SolidSprite> environment = new ArrayList<>();


        public Main() throws Exception{

            // Creation of the window
            JFrame frame = new JFrame("Project ENSEA");
            frame.setSize(590,613);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Sprite of the hero
            DynamicSprite hero = new DynamicSprite(ImageIO.read(new File("./img/heroTileSheetLowRes.png")),
                    250, 100, 48, 50);

            //Sprite of heart
            HealthSprite heart= new HealthSprite(ImageIO.read(new File("./img/heart.png")),
                    20, 10, 224,224);

            //Initialization of the Engines
            E=new RenderEngine();
            G= new GameEngine(hero);
            P= new PhysicEngine();


            //timers
            Timer renderTimer = new Timer(50,(time)-> E.update());
            Timer gameTimer = new Timer(50,(time)-> G.update());
            Timer physicTimer = new Timer(50,(time)-> P.update());

            gameTimer.start();
            renderTimer.start();
            physicTimer.start();

            //To print what is in RenderEngine and GameRender
            frame.getContentPane().add(E);
            //To make the keyPressed works
            frame.addKeyListener(G);

            //To show
            frame.setVisible(true);

            Playground level = new Playground("./data/level1.txt");
            //Add Engine
            for (Displayable D : level.getSpriteList()){
                E.addTorenderList(D);
            }
            E.addTorenderList(hero);
            E.addTorenderList(heart);
            P.addTomovingSpriteList(hero);
            P.setEnvironment(level.getSolidSpriteList());
            for (HealthSprite i : level.getDecreaseHealth()){
                P.addToHealthSprite(i);
            }

        }

        public static void main(String[] args) throws Exception {
            Main main = new Main();
        }



    }