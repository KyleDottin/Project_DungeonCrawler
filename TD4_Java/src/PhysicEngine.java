import java.io.IOException;
import java.util.ArrayList;

public class PhysicEngine implements Engine{
    private ArrayList<DynamicSprite> movingSpriteList;
    private ArrayList <Sprite> environment;
    private ArrayList<TrapSprite> trapSprite;
    private ArrayList<DoorSprite> doorSprite;
    private Main main;  // Reference to the Main class

    public PhysicEngine(Main main) {
        movingSpriteList = new ArrayList<>();
        environment = new ArrayList<>();
        trapSprite = new ArrayList<>();
        doorSprite = new ArrayList<>();
        this.main = main;  // Pass Main instance when creating PhysicEngine
    }

    public void addToEnvironmentList(Sprite sprite){
        if (!environment.contains(sprite)){
            environment.add(sprite);
        }
    }

    public void setEnvironment(ArrayList<Sprite> environment){
        this.environment = environment;
    }

    public void addTomovingSpriteList(DynamicSprite sprite){
        if (!movingSpriteList.contains(sprite)){
            movingSpriteList.add(sprite);
        }
    }

    public void addToHealthSprite(TrapSprite sprite){
        if (!trapSprite.contains(sprite)){
            trapSprite.add(sprite);
        }
    }

    public void addToDoorSprite(DoorSprite sprite){
        if (!doorSprite.contains(sprite)){
            doorSprite.add(sprite);
        }
    }

    public void clearPhysicList(){
        movingSpriteList.clear();
        trapSprite.clear();
        doorSprite.clear();
    }

    @Override
    public void update() {
        for (DynamicSprite dynamicSprite : movingSpriteList) {
            dynamicSprite.moveIfPossible(environment);
            dynamicSprite.checkIfHit(trapSprite);
            dynamicSprite.checkMap(doorSprite);

            // Check if map change is triggered
            if (dynamicSprite.mapChangeTriggered) {
                if(main.path.equals("./data/level1.txt")) {

                    main.loadMap("./data/level2.txt");
                }
                else if(main.path.equals("./data/level2.txt")) {
                    main.loadMap("./data/level1.txt");
                }
            }
        }
    }

}
