import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MapFrame extends JPanel {
    private final Map MAP;
    private ArrayList<Fighter> active;
    private String displayText;
    final JFrame FRAME;

    //Tiles
    private BufferedImage wallImage;
    private BufferedImage fieldImage;
    private BufferedImage cliffImage;

    //Fighters
    private BufferedImage smallFighterImage;
    private BufferedImage tallFighterImage;
    private BufferedImage friendFighterImage;

    public MapFrame(final Map INIT_MAP, final ArrayList<Fighter> INIT_FIGHTERS) {
        MAP = INIT_MAP;
        active = INIT_FIGHTERS;

        FRAME = new JFrame();
        FRAME.setPreferredSize(new Dimension(960, 672));
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.add(this);
        FRAME.pack();
        FRAME.setVisible(true);

        try {
            //Tiles
            wallImage = ImageIO.read(new File("data/placeholderWall.png"));
            fieldImage = ImageIO.read(new File("data/placeholderField.png"));
            cliffImage = ImageIO.read(new File("data/placeholderCliff.png"));

            //Fighters
            smallFighterImage = ImageIO.read(new File("data/placeholderFighterSmall.png"));
            tallFighterImage = ImageIO.read(new File("data/placeholderFighterTall.png"));
            friendFighterImage = ImageIO.read(new File("data/placeholderFighterAlly.png"));
        } catch (IOException e) {
            assert(false);
        }
    }

    public void setDisplayText(String newDisplayText){
        displayText = newDisplayText;
    }

    public void drawSelf(){
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
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
        ArrayList<Fighter> drawOrder = new ArrayList<>();
        drawOrder = (ArrayList<Fighter>) active.clone();
        Collections.sort(drawOrder);
        System.out.println("Draw Run");
        for(Fighter f : drawOrder){
            System.out.println(f);
            FighterType crntType = f.getType();
            switch(crntType){
                case SMALLTEST:
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
                default:
                    isTall = false; //these are here as formality for java. A crash should occur here.
                    crntImage = smallFighterImage;
                    assert(false);
                    break;
            }
            crntCoord = f.getXY();
            if(isTall){
                g.drawImage(crntImage, crntCoord.getX() * 96, crntCoord.getY() * 96 - 48, null);
            }
            else {
                g.drawImage(crntImage, crntCoord.getX() * 96, crntCoord.getY() * 96, null);
            }
        }
    }
}
