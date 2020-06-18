import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.*;

/*
    Class responsible for the GUI of the program. Tells a JFrame what it wants it to draw. Given the
    active fighters and a map.

    private final Map MAP - The map being drawn by this MapFrame. Used by paintComponent.
    private ArrayList<Fighter> active - The fighters being drawn by this MapFrame. Used by paintComponent.
    private String displayText - The text currently being displayed. Used by paintComponent.
        Changed by displayText().
    final JFrame FRAME - The JFrame drawing the MapFrame. Used on init.

    private Coord - targetCoords - Used by hitFighter and paintComponent to draw a line indicating an attack.
    private Coord - ogCoords - Used by hitFighter and paintComponent to draw a line indicating an attack.

    private BufferedImage - Vars used to store images to be used by paintComponent. Represent a tile or fighter.
 */

public class MapFrame extends JPanel {
    private final Map MAP;
    private ArrayList<Fighter> active;
    private String displayText;
    final JFrame FRAME;

    private ButtonState attackButtonState;
    private ButtonState moveButtonState;
    private ButtonState skipButtonState;
    private ButtonState passButtonState;
    private ButtonState backButtonState;

    private Coord targetCoords;
    private Coord ogCoords;

    //Buttons
    private BufferedImage attackButtonActive;
    private BufferedImage moveButtonActive;
    private BufferedImage skipButtonActive;
    private BufferedImage passButtonActive;
    private BufferedImage backButtonActive;

    private BufferedImage attackButtonPushed;
    private BufferedImage moveButtonPushed;
    private BufferedImage skipButtonPushed;
    private BufferedImage passButtonPushed;
    private BufferedImage backButtonPushed;

    private BufferedImage attackButtonInactive;
    private BufferedImage moveButtonInactive;
    private BufferedImage passButtonInactive;

    //Tiles
    private BufferedImage wallImage;
    private BufferedImage fieldImage;
    private BufferedImage cliffImage;

    //Fighters
    private BufferedImage smallFighterImage;
    private BufferedImage tallFighterImage;
    private BufferedImage friendFighterImage;
    private BufferedImage soulFighterImage;
    private BufferedImage uniFighterImage;
    private BufferedImage duaFighterImage;
    private BufferedImage flameFighterImage;
    private BufferedImage spiritFlameFighterImage;


    public MapFrame(final Map INIT_MAP, final ArrayList<Fighter> INIT_FIGHTERS) {
        MAP = INIT_MAP;
        active = INIT_FIGHTERS;

        displayText = "";

        attackButtonState = ButtonState.HIDDEN;
        moveButtonState = ButtonState.HIDDEN;
        skipButtonState = ButtonState.HIDDEN;
        passButtonState = ButtonState.HIDDEN;
        backButtonState = ButtonState.HIDDEN;

        targetCoords = null;
        ogCoords = null;

        FRAME = new JFrame();
        FRAME.setPreferredSize(new Dimension(960, 672 + 96));
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.add(this);
        FRAME.pack();
        FRAME.setVisible(true);
        FRAME.addMouseListener(new PlayerInput());

        try {
            //Tiles
            wallImage = ImageIO.read(new File("data/wallTileArt.png"));
            fieldImage = ImageIO.read(new File("data/openspaceTileArt.png"));
            cliffImage = ImageIO.read(new File("data/gapTileArt.png"));

            //Fighters
            smallFighterImage = ImageIO.read(new File("data/placeholderFighterSmall.png"));
            tallFighterImage = ImageIO.read(new File("data/placeholderFighterTall.png"));
            friendFighterImage = ImageIO.read(new File("data/placeholderFighterAlly.png"));
            soulFighterImage = ImageIO.read(new File("data/soulFighterArt.png"));
            uniFighterImage = ImageIO.read(new File("data/uniFighterArt.png"));
            duaFighterImage = ImageIO.read(new File("data/duaFighterArt.png"));
            spiritFlameFighterImage = ImageIO.read(new File("data/spiritFlameFighterArt.png"));
            flameFighterImage = ImageIO.read(new File("data/flameFighterArt.png"));
        } catch (IOException e) {
            assert(false);
        }
    }

    /*
        Sets hit line vars and updates the screen.
     */
    public void hitFighter(Coord target, Coord origin){
        ogCoords = origin;
        targetCoords = target;
        drawSelf();
    }

    /*
        Displays text on screen for around 3 seconds. Resets any hit lines afterwards.
     */
    public void displayText(String newDisplayText){
        displayText = newDisplayText;
        drawSelf();
        try {
            Thread.sleep(3000);
        }
        catch (Exception e){
        }
        displayText = "";
        drawSelf();
        ogCoords = null;
        targetCoords = null;
    }

    /*
        Used to update the image on screen.
     */
    public void drawSelf(){
        repaint();
    }

    /*
        Called by JFrame. Draws everything.
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(255, 0, 0));
        //Drawing tiles
        super.paintComponent(g);
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 7; y++){
                Tile crntTile = MAP.getTile(x, y);
                TileType crntType = crntTile.getType();
                switch(crntType){
                    case OPENSPACE:
                        g.drawImage(fieldImage, x * 96, y * 96, null);
                        break;
                    case WALL:
                        g.drawImage(wallImage, x * 96, y * 96, null);
                        break;
                    case GAP:
                        g.drawImage(cliffImage, x * 96, y * 96, null);
                        break;
                }
            }
        }
        //Drawing fighters
        BufferedImage crntImage;
        Coord crntCoord;
        boolean isTall;
        ArrayList<Fighter> drawOrder;
        drawOrder = (ArrayList<Fighter>) active.clone();
        Collections.sort(drawOrder);
        for(Fighter f : drawOrder){
            if (f.getState() == FighterState.ALIVE) {
                FighterType crntType = f.getType();
                switch (crntType) {
                    case SMALLTEST:
                    default:
                        isTall = false;
                        crntImage = smallFighterImage;
                        break;
                    case TALLTEST:
                        isTall = true;
                        crntImage = tallFighterImage;
                        break;
                    case FRIENDTEST:
                        isTall = false;
                        crntImage = friendFighterImage;
                        break;
                    case SOUL:
                        isTall = true;
                        crntImage = soulFighterImage;
                        break;
                    case UNI:
                        isTall = true;
                        crntImage = uniFighterImage;
                        break;
                    case DUA:
                        isTall = true;
                        crntImage = duaFighterImage;
                        break;
                    case FLAME:
                        isTall = false;
                        crntImage = flameFighterImage;
                        break;
                    case SPIRITFLAME:
                        isTall = false;
                        crntImage = spiritFlameFighterImage;
                        break;
                }

                crntCoord = f.getXY();
                if (isTall) {
                    g.drawImage(crntImage, crntCoord.getX() * 96, crntCoord.getY() * 96 - 48, null);
                } else {
                    g.drawImage(crntImage, crntCoord.getX() * 96, crntCoord.getY() * 96, null);
                }
            }
            g.drawString(displayText, (int)(4.5 * 96.0), (int)(7.25 * 96));

            if(ogCoords != null && targetCoords != null){
                g.drawLine(ogCoords.getX() * 96 + 48, ogCoords.getY() * 96 + 48, targetCoords.getX() * 96 + 48, targetCoords.getY() * 96 + 48);
                g.drawOval((int)((targetCoords.getX() + 0.25) * 96.0), (int)((targetCoords.getY() + 0.25) * 96.0), 48, 48);
            }
        }
    }
}
