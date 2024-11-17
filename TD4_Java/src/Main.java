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

    public Main(String path) throws Exception {
        // Initialize game components
        this.path = path;
        System.out.println(path);

        // Sprite of the hero
        hero = new DynamicSprite(ImageIO.read(new File("./img/heroTileSheetLowRes.png")),
                250, 100, 48, 50);

        // Sprite of heart
        HealthSprite heart = new HealthSprite(ImageIO.read(new File("./img/heart.png")),
                20, 10, 247, 221, hero);

        // Initialization of the Engines
        E = new RenderEngine();
        G = new GameEngine(hero);
        P = new PhysicEngine(this);

        // Timers
        Timer renderTimer = new Timer(50, (time) -> E.update());
        Timer gameTimer = new Timer(50, (time) -> G.update());
        Timer physicTimer = new Timer(50, (time) -> P.update());

        gameTimer.start();
        renderTimer.start();
        physicTimer.start();

        // print what is in RenderEngine and GameRender
        frame = new JFrame("Project ENSEA");
        frame.setSize(590, 613);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(E);
        frame.addKeyListener(G);
        frame.setVisible(true);

        loadMap(path);
    }


    public void loadMap(String newPath) {
        this.path = newPath;
        // Create the new level
        Playground level = new Playground(newPath);

        // Clear existing render
        E.clearRenderList();
        P.clearPhysicList();

        // Add new Engine
        for (Displayable D : level.getSpriteList()) {
            E.addTorenderList(D);
        }
        E.addTorenderList(hero);
        try {
            E.addTorenderList(new HealthSprite(ImageIO.read(new File("./img/heart.png")),
                    20, 10, 247, 221, hero));
        }catch (IOException e) {
            e.printStackTrace();
        }
        P.addTomovingSpriteList(hero);
        P.setEnvironment(level.getSolidSpriteList());

        for (TrapSprite trap : level.getDamageObject()) {
            P.addToHealthSprite(trap);
        }
        for (DoorSprite door : level.getDoorList()) {
            P.addToDoorSprite(door);
        }
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main("./data/level1.txt");
    }
}
