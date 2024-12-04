import java.awt.*;
import java.util.ArrayList;

public class PhysicEngine implements Engine{
    private final ArrayList<DynamicSprite> movingSpriteList;
    private ArrayList <Sprite> environment;
    private final ArrayList<TrapSprite> trapSprite;
    private final ArrayList<DoorSprite> doorSprite;
    private final ArrayList<FairySprite> fairySprite;
    private final ArrayList<MobSprite> mobSprite;
    private final ArrayList<CliffSprite> cliffSprite;
    private final ArrayList<PnjSprite> pnjSprite;
    private final Main main;
    protected boolean isPause;
    protected int beginLine;

    public void pause(){
        isPause=true;
    }

    public PhysicEngine(Main main) {
        movingSpriteList = new ArrayList<>();
        environment = new ArrayList<>();
        trapSprite = new ArrayList<>();
        doorSprite = new ArrayList<>();
        fairySprite = new ArrayList<>();
        mobSprite = new ArrayList<>();
        cliffSprite = new ArrayList<>();
        pnjSprite = new ArrayList<>();
        this.main = main;
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

    public void addToCliffSprite(CliffSprite sprite){
        if (!cliffSprite.contains(sprite)){
            cliffSprite.add(sprite);
        }
    }

    public void addToFairySprite(FairySprite sprite){
        if (!fairySprite.contains(sprite)){
            fairySprite.add(sprite);
        }
    }

    public void addToMobSpriteList(MobSprite sprite){
        if (!mobSprite.contains(sprite)){
            mobSprite.add(sprite);
        }
    }

    public void addToPnjSpriteList(PnjSprite sprite){
        if (!pnjSprite.contains(sprite)){
            pnjSprite.add(sprite);
        }
    }

    public void clearPhysicList(){
        movingSpriteList.clear();
        trapSprite.clear();
        doorSprite.clear();
        mobSprite.clear();
        cliffSprite.clear();
    }

    public void setbeganLine(DynamicSprite dynamicSprite){
        //Case if door is right
        if (dynamicSprite.xd==512 && dynamicSprite.yd==256){
            beginLine=0;
        }
        //Case if door is left
        if (dynamicSprite.xd==0 && dynamicSprite.yd==256){
            beginLine=3;
        }
        //Case if door is up
        if (dynamicSprite.xd==256 && dynamicSprite.yd==0){
            beginLine=6;
        }
        //Case if door is down
        if (dynamicSprite.xd==256 && dynamicSprite.yd==512){
            beginLine=9;
        }
        if (dynamicSprite.xd==192 && dynamicSprite.yd==512){ //left bottom door
            beginLine=12;
        }
        if (dynamicSprite.xd==384 && dynamicSprite.yd==512){ //right bottom door
            beginLine=15;
        }
        if (dynamicSprite.xd==192 && dynamicSprite.yd==0){ //left upper door
            beginLine=18;
        }
        if (dynamicSprite.xd==384 && dynamicSprite.yd==0){//right upper door
            beginLine=21;
        }
        //Do other case for door lower on the left, lower on the right, etc...
    }

    @Override
    public void Draw(Graphics g) {

    }

    @Override
    public void update() {
        if(isPause){
            return;
        }
        for (MobSprite mob: mobSprite ){
            mob.isMobMovingPossible(environment);
        }
        for (DynamicSprite dynamicSprite : movingSpriteList) {
            dynamicSprite.moveIfPossible(environment);
            dynamicSprite.checkIfHitTrap(trapSprite);
            dynamicSprite.checkMap(doorSprite);
            dynamicSprite.isTalkingFairy(fairySprite);
            dynamicSprite.checkIfHitpnj(pnjSprite);
            if(main.path.equals("./data/BeginningMap.txt") || main.path.equals("./data/WindyMap.txt")) {
                dynamicSprite.checkIfHitMob(mobSprite);
            }
            dynamicSprite.checkIfFall(cliffSprite);

            // Check if map change is triggered
            if (dynamicSprite.mapChangeTriggered) {
                Playground playground = new Playground(main.path);
                setbeganLine(dynamicSprite);
                String Map=playground.NextMap(main.path,beginLine);
                int Newx=playground.NextPosition(main.path,beginLine+1).get(0);
                dynamicSprite.lastDoorx = Newx;
                int Newy=playground.NextPosition(main.path,beginLine+1).get(1);
                dynamicSprite.lastDoory = Newy;
                for (DynamicSprite dyna : movingSpriteList) {
                    dyna.xh= dyna.xh-dyna.x+Newx;
                    dyna.x = Newx;
                    dyna.yh= dyna.yh-dyna.y +Newy;
                    dyna.y = Newy;
                }
                main.loadMap(Map);
            }
        }
    }

}
