import java.io.File;
import java.util.*;

/*
    ~Tile Class~
    Stores and modifies tile graph information
    Uses: Tile, Coord
    Used by: Map

    File documentation https://docs.oracle.com/javase/7/docs/api/java/io/File.html
    List documentation https://docs.oracle.com/javase/8/docs/api/java/util/List.html
 */
public class Tile {

    //private final int X;
    //private final int Y;
    private final Coord XY;
    private int height;
    private File image;

    private boolean fightersCanPass;
    private boolean projectilesCanPass;
    private List<Tile> linked;

    /*
        Class initialisation
     */
    public Tile(final int INIT_X, final int INIT_Y){
        XY = new Coord(INIT_X, INIT_Y);

        //Default vars
        height = 1;
        //IMAGE = INIT_IMAGE;
        fightersCanPass = false;
        projectilesCanPass = false;
        linked = Collections.<Tile>emptyList();
    }

    public Tile(){
        XY = new Coord(0,0);
        height = 1;
        fightersCanPass = false;
        projectilesCanPass = false;
        linked = Collections.<Tile>emptyList();
    }

    /*
        Operation methods
     */
    public void addLink(Tile newTile){
        //System.out.println(newTile);
        //System.out.println(linked.size());


        //linked.add(newTile);
    }

    public void setFightersCanPass(boolean newFightersCanPass){
        fightersCanPass = newFightersCanPass;
    }

    public void setProjectilesCanPass(boolean newProjectilesCanPass){
        projectilesCanPass = newProjectilesCanPass;
    }

    public void setHeight(int h){
        height = h;
    }

    public void setImage(File i){
        //should check file type here
        image = i;
    }

    /*
        Return methods
     */
    public Coord getXY(){
        return(XY);
    }

    public int getX(){
        return(XY.getX());
    }

    public int getY(){
        return(XY.getY());
    }

    public int getHeight(){
        return(height);
    }

    public boolean getFightersCanPass(){
        return(fightersCanPass);
    }

    public boolean getProjectilesCanPass(){
        return(projectilesCanPass);
    }

    public List<Tile> getLinked(){
        return(linked);
    }

    public File giveImage(){
        return(image);
    }
}
