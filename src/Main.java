import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main is the class where the code begins and is organized.
 */

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
    ButtonSprite button_x;
    SwordSprite sword;
    HitSprite hit;
    RupeeSprite rupee;


    public Main(String path) throws Exception { //Main of the program
        //Initialize Screen
        frame = new JFrame("Project Dungeon Crawler");
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

        //Initialization of different Sprites
        try {
            blob = new MobSprite(ImageIO.read(new File("./img/BlobSprite.png")),
                    115, 264, 64, 64,this.path,hero);
            fairy = new FairySprite(ImageIO.read(new File("./img/FairySprite.png")),
                    115, 1, 64, 64,this.path, hero);
            wind = new WindSprite(ImageIO.read(new File("./img/wind.png")),
                    32, 64, 512, 512,this.path,hero);
            pnj01 = new PnjSprite(ImageIO.read(new File("./img/pnj01Sprite.png")),
                    437, 192, 80, 80,this.path);
            light = new Lighting(0, 0, 576, 576,this.path, hero);
            sword = new SwordSprite(ImageIO.read(new File("./img/SwordSprite.png")),
                    hero.x, hero.y, 64, 100,hero);
            button_x = new ButtonSprite(ImageIO.read(new File("./img/X.png")),
                    16, 25, 40, 40);
            hit= new HitSprite(ImageIO.read(new File("./img/hitSprite.png")),
                    0,0,40,40,sword,hero);
            rupee = new RupeeSprite(ImageIO.read(new File("./img/rupees.png")),
                    0,0,21,64,blob,hero);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Clear existing Engine
        E.clearRenderList();
        P.clearPhysicList();

        // Add new RenderEngine
        for (Displayable D : level.getSpriteList()) {
            E.addTorenderList(D);
        }
        E.addTorenderList(pnj01);
        E.addTorenderList(light);
        E.addTorenderList(wind);
        E.addTorenderList(fairy);
        E.addTorenderList(blob);
        E.addTorenderList(button_x);
        E.addTorenderList(rupee);
        E.addTorenderList(hero);
        E.addTorenderList(sword);
        E.addTorenderList(hit);

        for(Displayable d : E.HealthList){
            E.addTorenderList(d);
        }

        // Add new PhysicEngine

        P.addTomovingSpriteList(hero);
        P.addToSwordSpriteList(sword);
        P.addToPnjSpriteList(pnj01);
        P.addToFairySprite(fairy);
        P.addToMobSpriteList(blob);
        P.addToRupeeSpriteList(rupee);
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

        while (!startScreen.isStarted) { // Wait until Space is press
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
// Note
// There is still a problem with the recoil damage of the mob, if there is solidsprite in the path,
// it will go through. Same idea with its movement, if he encounter a solidsprite, because the way
// it moves, there is a possibility he stucked in the solid sprite
// When waiting, if start is pressed, the character wake up, the text for space press disapear and
// the game starts