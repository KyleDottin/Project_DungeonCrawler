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
    String path;
    DynamicSprite hero;
    FairySprite fairy;
    MobSprite blob;
    WindSprite wind;
    PnjSprite pnj01;
    Lighting light;


    public Main(String path) throws Exception {
        //Initialize Screen
        frame = new JFrame("Project ENSEA");
        frame.setSize(590, 613);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize game components
        this.path = path;

        // Sprite of the hero
        hero = new DynamicSprite(ImageIO.read(new File("./img/heroTileSheetLowRes.png")),
                250, 100, 48, 50,this);

        // Initialization of the Engines
        E = new RenderEngine();
        P = new PhysicEngine(this);
        G = new GameEngine(hero,P);

        // Sprite of the fairy
        //fairy = new FairySprite(ImageIO.read(new File("./img/FairySprite.png")),
        //        230, 100, 64, 64,path);
        //No usage if the fairy does not appear in the first map

        // Sprite of heart
        double x_heart=20;
        double y_heart=10;
        E.addToHealth(new HealthSprite(ImageIO.read(new File("./img/heart.png")),
                x_heart, y_heart, 247, 221, hero,frame));

        // Timers
        Timer renderTimer = new Timer(50, (time) -> E.update());
        Timer gameTimer = new Timer(50, (time) -> G.update());
        Timer physicTimer = new Timer(50, (time) -> P.update());

        gameTimer.start();
        renderTimer.start();
        physicTimer.start();

        // print what is in RenderEngine and GameRender
        frame.getContentPane().add(E);
        frame.addKeyListener(G);
        frame.setVisible(true);

        loadMap(path);
    }


    public void loadMap(String newPath) {
        this.path = newPath;

        // Create the new level
        Playground level = new Playground(newPath);

        //Mob Sprites
        try {
            blob = new MobSprite(ImageIO.read(new File("./img/BlobSprite.png")),
                    115, 264, 64, 64,this.path,hero);
            fairy = new FairySprite(ImageIO.read(new File("./img/FairySprite.png")),
                    115, 1, 64, 64,this.path, hero);
            wind = new WindSprite(ImageIO.read(new File("./img/wind.png")),
                    32, 64, 512, 512,this.path,hero);
            pnj01 = new PnjSprite(ImageIO.read(new File("./img/pnj01Sprite.png")),
                    437, 192, 80, 80,this.path, hero);
            light = new Lighting(0, 0, 576, 576,this.path, hero);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Clear existing render
        E.clearRenderList();
        P.clearPhysicList();

        // Add new Engine
        for (Displayable D : level.getSpriteList()) {
            E.addTorenderList(D);
        }
        E.addTorenderList(pnj01);
        E.addTorenderList(light);
        E.addTorenderList(wind);
        E.addTorenderList(hero);
        E.addTorenderList(fairy);
        E.addTorenderList(blob);

        for(Displayable d : E.HealthList){
            E.addTorenderList(d);
        }

//        try {
//            E.addTorenderList(new HealthSprite(ImageIO.read(new File("./img/heart.png")),
//                    x_heart, y_heart, 247, 221, hero));
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
        P.addTomovingSpriteList(hero);
        P.addToPnjSpriteList(pnj01);
        P.addToFairySprite(fairy);
        P.addToMobSpriteList(blob);
        P.setEnvironment(level.getSolidSpriteList());

        for (TrapSprite trap : level.getDamageObject()) {
            P.addToHealthSprite(trap);
        }
        for (DoorSprite door : level.getDoorList()) {
            P.addToDoorSprite(door);
        }
        for (CliffSprite cliff : level.getCliffList()) {
            P.addToCliffSprite(cliff);
        }
    }

    public static void main(String[] args) throws Exception {
        StartScreen startScreen = new StartScreen();
        startScreen.LaunchStartScreen();

        while (!startScreen.isStarted) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        Main mainGame = new Main("./data/BeginningMap.txt");

    }

}
// Ce que je peux encore faire
// Faire en sorte que ravins si on tombe on retourne au début et on perd 1/2 de coeur
// Map cave 1 & 2 seront en sprite cave et on verra que autour de soit a ~64 pixel
// Map Outside house on aura une maison sur la droite dans laquel on peut entrée et qui aura
// une feature spécial a trouvé et qui permettra d'ouvrir le grillage devant la porte qui amene à
// MazeMap
