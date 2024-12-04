import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Playground {
    protected ArrayList<Sprite> environment = new ArrayList<>();
    protected ArrayList<TrapSprite> Damage = new ArrayList<>();
    protected ArrayList<DoorSprite> Door = new ArrayList<>();
    protected ArrayList<CliffSprite> Cliff = new ArrayList<>();

    public Playground(String pathName) {
        try {
            // Load images
            final BufferedImage imageTree = ImageIO.read(new File("./img/tree.png"));
            final BufferedImage imageGrass = ImageIO.read(new File("./img/grass.png"));
            final BufferedImage imageGround = ImageIO.read(new File("./img/ground.png"));
            final BufferedImage imageGroundLeft = ImageIO.read(new File("./img/ground-grass_left.png"));
            final BufferedImage imageGroundRight = ImageIO.read(new File("./img/ground-grass_right.png"));
            final BufferedImage imageWall = ImageIO.read(new File("./img/wall.png"));
            final BufferedImage imageWallBottom = ImageIO.read(new File("./img/wall_bottom.png"));
            final BufferedImage imageWallRight = ImageIO.read(new File("./img/wall_right.png"));
            final BufferedImage imageWallLeft = ImageIO.read(new File("./img/wall_left.png"));
            final BufferedImage imageWallLilCorner = ImageIO.read(new File("./img/wall_lilcorner.png"));
            final BufferedImage imageWallLilCornerInv = ImageIO.read(new File("./img/wall_lilcorner_inverted.png"));
            final BufferedImage imageWallCornerRight = ImageIO.read(new File("./img/wall_corner_right.png"));
            final BufferedImage imageWallCornerRight2 = ImageIO.read(new File("./img/wall_corner_right2.png"));
            final BufferedImage imageWallCornerLeft = ImageIO.read(new File("./img/wall_corner_left.png"));
            final BufferedImage imageWallCornerLeft2 = ImageIO.read(new File("./img/wall_corner_left2.png"));
            final BufferedImage imageGroundGrass = ImageIO.read(new File("./img/ground-grass.png"));
            final BufferedImage imageRock = ImageIO.read(new File("./img/rock.png"));
            final BufferedImage imageTrap = ImageIO.read(new File("./img/trap.png"));
            final BufferedImage imageWaterRight = ImageIO.read(new File("./img/water_R.png"));
            final BufferedImage imageWaterBorder = ImageIO.read(new File("./img/water_border.png"));
            final BufferedImage imageWaterBorderRight = ImageIO.read(new File("./img/water_border_right.png"));
            final BufferedImage imageWaterBorderLeft = ImageIO.read(new File("./img/water_border_left.png"));
            final BufferedImage imageWaterDeep = ImageIO.read(new File("./img/water_deep.png"));
            final BufferedImage imageWaterLeft = ImageIO.read(new File("./img/water_L.png"));
            final BufferedImage imageCliff = ImageIO.read(new File("./img/cliff.png"));
            final BufferedImage imageCliffBorderRight = ImageIO.read(new File("./img/cliff_border_right.png"));
            final BufferedImage imageCliffBorderLeft = ImageIO.read(new File("./img/cliff_border_left.png"));
            final BufferedImage imageVoid = ImageIO.read(new File("./img/void.png"));

            // Get image dimensions
            final int imageTreeWidth = imageTree.getWidth(null);
            final int imageTreeHeight = imageTree.getHeight(null);
            final int imageGrassWidth = imageGrass.getWidth(null);
            final int imageGrassHeight = imageGrass.getHeight(null);
            final int imageRockWidth = imageRock.getWidth(null);
            final int imageRockHeight = imageRock.getHeight(null);
            final int imageTrapWidth = imageTrap.getWidth(null);
            final int imageTrapHeight = imageTrap.getHeight(null);
            final int imageWaterRightWidth = imageWaterRight.getWidth(null); // Renamed variable
            final int imageWaterRightHeight = imageWaterRight.getHeight(null); // Renamed variable
            final int imageWaterBorderWidth = imageWaterBorder.getWidth(null);
            final int imageWaterBorderHeight = imageWaterBorder.getHeight(null);
            final int imageWaterBorderRightWidth = imageWaterBorderRight.getWidth(null);
            final int imageWaterBorderRightHeight = imageWaterBorderRight.getHeight(null);
            final int imageWaterBorderLeftWidth = imageWaterBorderLeft.getWidth(null);
            final int imageWaterBorderLeftHeight = imageWaterBorderLeft.getHeight(null);
            final int imageWaterDeepWidth = imageWaterDeep.getWidth(null);
            final int imageWaterDeepHeight = imageWaterDeep.getHeight(null);
            final int imageWaterLeftWidth = imageWaterLeft.getWidth(null);
            final int imageWaterLeftHeight = imageWaterLeft.getHeight(null);
            final int imageCliffWidth = imageCliff.getWidth(null);
            final int imageCliffHeight = imageCliff.getHeight(null);
            final int imageCliffBorderRightWidth = imageCliffBorderRight.getWidth(null);
            final int imageCliffBorderRightHeight = imageCliffBorderRight.getHeight(null);
            final int imageCliffBorderLeftWidth = imageCliffBorderLeft.getWidth(null);
            final int imageCliffBorderLeftHeight = imageCliffBorderLeft.getHeight(null);

            // BufferedReader for reading the file
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line = bufferedReader.readLine();
            int lineNumber = 0;
            int columnNumber = 0;

            // Start reading the file line by line
            while (line != null) {
                for (byte element : line.getBytes(StandardCharsets.UTF_8)) {
                    switch (element) {
                        case 'T':
                            environment.add(new SolidSprite(imageTree, columnNumber * imageTreeWidth,
                                    lineNumber * imageTreeHeight, imageTreeWidth, imageTreeHeight));
                            break;
                        case ' ':
                            environment.add(new Sprite(imageGrass, columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight, imageGrassWidth, imageGrassHeight));
                            break;
                        case 'R':
                            environment.add(new SolidSprite(imageRock, columnNumber * imageRockWidth,
                                    lineNumber * imageRockHeight, imageRockWidth, imageRockHeight));
                            break;
                        case 't':
                            environment.add(new Sprite(imageTrap, columnNumber * imageTrapWidth,
                                    lineNumber * imageTrapHeight, imageTrapWidth, imageTrapHeight));
                            Damage.add(new TrapSprite(imageTrap, columnNumber * imageTrapWidth,
                                    lineNumber * imageTrapHeight, imageTrapWidth, imageTrapHeight));
                            break;
                        case '.':
                            environment.add(new Sprite(imageGrass, columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight, imageGrassWidth, imageGrassHeight));
                            Door.add(new DoorSprite(imageGrass, columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight, imageGrassWidth, imageGrassHeight));
                            break;
                        case ',':
                            environment.add(new Sprite(imageGround, columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight, imageGrassWidth, imageGrassHeight));
                            Door.add(new DoorSprite(imageGround, columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight, imageGrassWidth, imageGrassHeight));
                            break;
                        case '[':
                            environment.add(new SolidSprite(imageWaterRight, columnNumber * imageWaterRightWidth,
                                    lineNumber * imageWaterRightHeight, imageWaterRightWidth, imageWaterRightHeight));
                            break;
                        case '-':
                            environment.add(new SolidSprite(imageWaterBorder, columnNumber * imageWaterBorderWidth,
                                    lineNumber * imageWaterBorderHeight, imageWaterBorderWidth, imageWaterBorderHeight));
                            break;
                        case ')':  // Case for water border right
                            environment.add(new SolidSprite(imageWaterBorderRight, columnNumber * imageWaterBorderRightWidth,
                                    lineNumber * imageWaterBorderRightHeight, imageWaterBorderRightWidth, imageWaterBorderRightHeight));
                            break;
                        case '(':  // Case for water border right
                            environment.add(new SolidSprite(imageWaterBorderLeft, columnNumber * imageWaterBorderLeftWidth,
                                    lineNumber * imageWaterBorderLeftHeight, imageWaterBorderLeftWidth, imageWaterBorderLeftHeight));
                            break;
                        case 'd':  // Case for water deep
                            environment.add(new SolidSprite(imageWaterDeep, columnNumber * imageWaterDeepWidth,
                                    lineNumber * imageWaterDeepHeight, imageWaterDeepWidth, imageWaterDeepHeight));
                            break;
                        case ']':  // New case for water left
                            environment.add(new SolidSprite(imageWaterLeft, columnNumber * imageWaterLeftWidth,
                                    lineNumber * imageWaterLeftHeight, imageWaterLeftWidth, imageWaterLeftHeight));
                            break;
                        case 'o':  // New case for cliff
                            environment.add(new Sprite(imageCliff, columnNumber * imageCliffWidth,
                                    lineNumber * imageCliffHeight, imageCliffWidth, imageCliffHeight));
                            Cliff.add(new CliffSprite(imageCliff, columnNumber * imageCliffWidth,
                                    lineNumber * imageCliffHeight, imageCliffWidth, imageCliffHeight));
                            break;
                        case 'g':  // New case for cliff border right
                            environment.add(new Sprite(imageCliffBorderRight, columnNumber * imageCliffBorderRightWidth,
                                    lineNumber * imageCliffBorderRightHeight, imageCliffBorderRightWidth, imageCliffBorderRightHeight));
                            Cliff.add(new CliffSprite(imageCliff, columnNumber * imageCliffWidth,
                                    lineNumber * imageCliffHeight, imageCliffWidth, imageCliffHeight));
                            break;
                        case 'p':  // New case for cliff border left
                            environment.add(new Sprite(imageCliffBorderLeft, columnNumber * imageCliffBorderLeftWidth,
                                    lineNumber * imageCliffBorderLeftHeight, imageCliffBorderLeftWidth, imageCliffBorderLeftHeight));
                            Cliff.add(new CliffSprite(imageCliff, columnNumber * imageCliffWidth,
                                    lineNumber * imageCliffHeight, imageCliffWidth, imageCliffHeight));
                            break;
                        case 'x':
                            environment.add(new SolidSprite(imageGrass, columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight, imageGrassWidth, imageGrassHeight));
                            break;
                        case 'a':
                            environment.add(new Sprite(imageGround, columnNumber * imageCliffBorderLeftWidth,
                                    lineNumber * imageCliffBorderLeftHeight, imageCliffBorderLeftWidth, imageCliffBorderLeftHeight));
                            break;
                        case 'L':
                            environment.add(new Sprite(imageGroundLeft, columnNumber * imageCliffBorderLeftWidth,
                                    lineNumber * imageCliffBorderLeftHeight, imageCliffBorderLeftWidth, imageCliffBorderLeftHeight));
                            break;
                        case 'K':
                            environment.add(new Sprite(imageGroundRight, columnNumber * imageCliffBorderLeftWidth,
                                    lineNumber * imageCliffBorderLeftHeight, imageCliffBorderLeftWidth, imageCliffBorderLeftHeight));
                            break;
                        case '~':
                            environment.add(new Sprite(imageGroundGrass, columnNumber * imageCliffBorderLeftWidth,
                                    lineNumber * imageCliffBorderLeftHeight, imageCliffBorderLeftWidth, imageCliffBorderLeftHeight));
                            break;
                        case 'w':
                            environment.add(new SolidSprite(imageWall, columnNumber * imageCliffBorderLeftWidth,
                                    lineNumber * imageCliffBorderLeftHeight, imageCliffBorderLeftWidth, imageCliffBorderLeftHeight));
                            break;
                        case 'q':
                            environment.add(new SolidSprite(imageWallBottom, columnNumber * imageCliffBorderLeftWidth,
                                    lineNumber * imageCliffBorderLeftHeight, imageCliffBorderLeftWidth, imageCliffBorderLeftHeight));
                            break;
                        case 'r':
                            environment.add(new SolidSprite(imageWallCornerRight, columnNumber * imageCliffBorderLeftWidth,
                                    lineNumber * imageCliffBorderLeftHeight, imageCliffBorderLeftWidth, imageCliffBorderLeftHeight));
                            break;
                        case 'e':
                            environment.add(new SolidSprite(imageWallCornerRight2, columnNumber * imageCliffBorderLeftWidth,
                                    lineNumber * imageCliffBorderLeftHeight, imageCliffBorderLeftWidth, imageCliffBorderLeftHeight));
                            break;
                        case 'l':
                            environment.add(new SolidSprite(imageWallCornerLeft, columnNumber * imageCliffBorderLeftWidth,
                                    lineNumber * imageCliffBorderLeftHeight, imageCliffBorderLeftWidth, imageCliffBorderLeftHeight));
                            break;
                        case 'm':
                            environment.add(new SolidSprite(imageWallCornerLeft2, columnNumber * imageCliffBorderLeftWidth,
                                    lineNumber * imageCliffBorderLeftHeight, imageCliffBorderLeftWidth, imageCliffBorderLeftHeight));
                            break;
                        case '}':
                            environment.add(new SolidSprite(imageWallRight, columnNumber * imageCliffBorderLeftWidth,
                                    lineNumber * imageCliffBorderLeftHeight, imageCliffBorderLeftWidth, imageCliffBorderLeftHeight));
                            break;
                        case '{':
                            environment.add(new SolidSprite(imageWallLeft, columnNumber * imageCliffBorderLeftWidth,
                                    lineNumber * imageCliffBorderLeftHeight, imageCliffBorderLeftWidth, imageCliffBorderLeftHeight));
                            break;
                        case '*':
                            environment.add(new SolidSprite(imageWallLilCorner, columnNumber * imageCliffBorderLeftWidth,
                                    lineNumber * imageCliffBorderLeftHeight, imageCliffBorderLeftWidth, imageCliffBorderLeftHeight));
                            break;
                        case '$':
                            environment.add(new SolidSprite(imageWallLilCornerInv, columnNumber * imageCliffBorderLeftWidth,
                                    lineNumber * imageCliffBorderLeftHeight, imageCliffBorderLeftWidth, imageCliffBorderLeftHeight));
                            break;
                        case 'V':
                            environment.add(new SolidSprite(imageVoid, columnNumber * imageCliffBorderLeftWidth,
                                    lineNumber * imageCliffBorderLeftHeight, imageCliffBorderLeftWidth, imageCliffBorderLeftHeight));
                            break;
                    }
                    columnNumber++;
                }
                columnNumber = 0;
                lineNumber++;
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String NextMap(String pathName, int beginLine) {
        String levelPath = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(pathName))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber >= beginLine) {
                    if (line.contains("./")) {
                        levelPath = line.substring(line.indexOf("./"));
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return levelPath;
    }

    public ArrayList<Integer> NextPosition(String pathName, int beginLine) {
        ArrayList<Integer> Position = new ArrayList<>();
        String level = null;

        try {
            level = Files.readString(Paths.get(pathName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] lines = level.split("\n");
        int currentLine = 0;

        for (String line : lines) {
            currentLine++;
            if (currentLine < beginLine) {
                continue;
            }

            String Regex = "x=(\\d+)";
            String Regey = "y=(\\d+)";

            Matcher Matcherx = Pattern.compile(Regex).matcher(line);
            Matcher Matchery = Pattern.compile(Regey).matcher(line);

            if (Matcherx.find()) {
                Position.add(Integer.parseInt(Matcherx.group(1)));
            }
            if (Matchery.find()) {
                Position.add(Integer.parseInt(Matchery.group(1)));
            }
        }

        return Position;
    }

    public ArrayList<Sprite> getSolidSpriteList() {
        ArrayList<Sprite> solidSpriteArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite) solidSpriteArrayList.add(sprite);
        }
        return solidSpriteArrayList;
    }

    public ArrayList<Displayable> getSpriteList() {
        ArrayList<Displayable> displayableArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            displayableArrayList.add((Displayable) sprite);
        }
        return displayableArrayList;
    }

    public ArrayList<TrapSprite> getDamageObject() {
        return Damage;
    }

    public ArrayList<DoorSprite> getDoorList() {
        return Door;
    }

    public ArrayList<CliffSprite> getCliffList(){
        return Cliff;
    }
}