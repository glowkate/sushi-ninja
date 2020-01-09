import java.util.*;

/*
    Tile Class
    Stores and modifies tile graph information
 */
public class Tile {

    private final Coord XY;

    private final TileType type;

    private boolean hasBeenVisited;
    private boolean isOccupied;
    private LinkedList<Tile> pathToTile;
    private ArrayList<Tile> linked;

    /*
        Class initialisation
     */
    public Tile(final int INIT_X, final int INIT_Y, final TileType INIT_TYPE){
        XY = new Coord(INIT_X, INIT_Y);
        type = INIT_TYPE;
        linked = new ArrayList<>();
        pathToTile = new LinkedList<>();
        hasBeenVisited = false;
        isOccupied = false;
    }

    //For testing only
    public Tile(){
        XY = new Coord(0,0);
        type = TileType.OPENSPACE;
        linked = new ArrayList<>();
        isOccupied = false;
    }

    public void addLink(Tile newTile){
        linked.add(newTile);
    }

    public void setPathAndVisit(LinkedList<Tile> newPath){
        pathToTile = newPath;
        hasBeenVisited = true;
    }

    public void setOccupied(boolean newOccupied){
        isOccupied = newOccupied;
    }

    public boolean getOccupied(){ //sEt GEt
        return isOccupied;
    }

    public void resetPathfindingVars(){
        hasBeenVisited = false;
        pathToTile.clear();
    }

    public boolean checkPassability(Tile compTile){
        boolean areTheyPassable;
        switch(compTile.getType()){
            case GAP:
            case WALL:
                areTheyPassable = false;
                break;
            default:
                areTheyPassable = true;
                break;
        }
        boolean isHeightPassable = !(heightDif(compTile) > 1); //If height diff is 2 or more, it's impassable
        //System.out.println(!hasBeenVisited);
        //System.out.println(!compTile.getOccupied());
        //System.out.println(isHeightPassable);
        //System.out.println(areTheyPassable);
        return (!hasBeenVisited && !compTile.getOccupied() && isHeightPassable && areTheyPassable);
    }

    public int heightDif(Tile compTile){ //gets how much higher one tile is compared to this one
        int ourHeight;
        int theirHeight;
        switch(type){
            case ELEVATED1:
                ourHeight = 2;
                break;
            case ELEVATED2:
                ourHeight = 3;
                break;
            default:
                ourHeight = 1;
                break;
        }
        switch(compTile.getType()){
            case ELEVATED1:
                theirHeight = 2;
                break;
            case ELEVATED2:
                theirHeight = 3;
                break;
            default:
                theirHeight = 1;
                break;
        }
        return(theirHeight - ourHeight);
    }

    public Coord getXY(){
        return(XY);
    }

    public int getX(){
        return(XY.getX());
    }

    public int getY(){
        return(XY.getY());
    }

    public TileType getType(){
        return (type);
    }

    public ArrayList<Tile> getLinked(){
        return(linked);
    }

    public LinkedList<Tile> getPath(){
        return (pathToTile);
    }

    public boolean getHasBeenVisited(){
        return hasBeenVisited;
    }



    @Override
    public String toString(){
        String addString;
        switch(type){
            case WALL:
                addString = "<WALL>";
                break;
            case OPENSPACE:
                addString = "<OPENSPACE>";
                break;
            case GAP:
                addString = "<GAP>";
                break;
            case ELEVATED1:
                addString = "<ELEVATED1>";
                break;
            case ELEVATED2:
                addString = "<ELEVATED2>";
                break;
            default:
                addString = "";
        }
        return(XY.toString() + addString);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null || obj.getClass()!= this.getClass()) {
            return false;
        }
        else {
            Tile compTile = (Tile) obj;
            return (XY.equals(compTile.getXY()) && type == compTile.getType());
        }

    }
}
