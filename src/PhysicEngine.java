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
    private final ArrayList<SwordSprite> swordSprite;
    private final ArrayList<RupeeSprite> rupeeSprite;
    private final Main main;
    protected boolean isPause;
    protected int beginLine;

    public void pause(){
        isPause=true;
    }

    public PhysicEngine(Main main) { //Deals with the Physic of the game
        movingSpriteList = new ArrayList<>();
        environment = new ArrayList<>();
        rupeeSprite = new ArrayList<>();
        trapSprite = new ArrayList<>();
        doorSprite = new ArrayList<>();
        fairySprite = new ArrayList<>();
        mobSprite = new ArrayList<>();
        cliffSprite = new ArrayList<>();
        pnjSprite = new ArrayList<>();
        swordSprite = new ArrayList<>();
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

    public void addToSwordSpriteList(SwordSprite sprite){
        if (!swordSprite.contains(sprite)){
            swordSprite.add(sprite);
        }
    }

    public void addToRupeeSpriteList(RupeeSprite sprite){
        if (!rupeeSprite.contains(sprite)){
            rupeeSprite.add(sprite);
        }
    }

    public void clearPhysicList(){ //Clear the list for the new  level
        movingSpriteList.clear();
        trapSprite.clear();
        doorSprite.clear();
        mobSprite.clear();
        cliffSprite.clear();
    }

    public void setbeganLine(DynamicSprite dynamicSprite){ //Method to read write lines based on the door taken
        if (dynamicSprite.xd==512 && dynamicSprite.yd==256){//Case if door is right
            beginLine=0;
        }
        if (dynamicSprite.xd==0 && dynamicSprite.yd==256){ //Case if door is left
            beginLine=3;
        }
        if (dynamicSprite.xd==256 && dynamicSprite.yd==0){ //Case if door is up
            beginLine=6;
        }
        if (dynamicSprite.xd==256 && dynamicSprite.yd==512){ //Case if door is down
            beginLine=9;
        }
        if (dynamicSprite.xd==192 && dynamicSprite.yd==512){ //Case if left bottom door
            beginLine=12;
        }
        if (dynamicSprite.xd==384 && dynamicSprite.yd==512){ //Case if right bottom door
            beginLine=15;
        }
        if (dynamicSprite.xd==192 && dynamicSprite.yd==0){ //Case if left upper door
            beginLine=18;
        }
        if (dynamicSprite.xd==384 && dynamicSprite.yd==0){//Case if right upper door
            beginLine=21;
        }
    }

    @Override
    public void Draw(Graphics g) {

    }

    @Override
    public void update() { //Check all the interactions with different objects
        if(isPause){ //Not run the physic if the character is dealing with a npc
            return;
        }
        for (MobSprite mob: mobSprite ){
            mob.isMobMovingPossible(environment);
        }
        for (SwordSprite sword : swordSprite){
            sword.checkIfDamageMob(mobSprite);
        }
        for (DynamicSprite dynamicSprite : movingSpriteList) {
            dynamicSprite.isTouchingRupee(rupeeSprite);
            dynamicSprite.moveIfPossible(environment);
            dynamicSprite.checkIfHitTrap(trapSprite);
            dynamicSprite.checkMap(doorSprite);
            dynamicSprite.isTalkingFairy(fairySprite);
            dynamicSprite.checkIfHitpnj(pnjSprite);
            if(main.path.equals("./data/BeginningMap.txt") || main.path.equals("./data/WindyMap.txt")) {
                dynamicSprite.checkIfHitMob(mobSprite);
            }
            dynamicSprite.checkIfFall(cliffSprite);

            if (dynamicSprite.mapChangeTriggered) { // Check if map change is triggered
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
