import java.util.*;

/*
    Stores and modifies tile graph information. Used by Map and accessed by a fair amount of classes and methods.

    private final Coord XY - The location of the tile. Used to access the tile in map.
    private final TileType type - The type of tile. Dictates if fighters can walk through it and
        if projectiles can pass through it.

    private boolean hasBeenVisited - Pathfinding var used by Map. Blocks pathfinding algorithm from checking
        where it's already been.
    private boolean isOccupied - Used to store if a fighter is standing on the tile or not. Used by checkPassability
    private LinkedList<Tile> pathToTile - Used by Map to store a path for pathfinding.
    private ArrayList<Tile> linked - Stores the tiles adjacent to this one. Used for movement.
 */

public class Tile {

    private final Coord XY;

    private final TileType type;

    private boolean hasBeenVisited;
    private boolean isOccupied;
    private LinkedList<Tile> pathToTile;
    private ArrayList<Tile> linked;


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

    /*
        Given a tile, returns if that tile could move into this one.
     */
    public boolean checkPassability(Tile source){
        boolean areTheyPassable;
        switch(type){
            case GAP:
            case WALL:
                areTheyPassable = false;
                break;
            default:
                areTheyPassable = true;
                break;
        }
        boolean isHeightPassable = !(heightDif(source) > 1); //If height diff is 2 or more, it's impassable
        return (!hasBeenVisited && !isOccupied && isHeightPassable && areTheyPassable);
    }

    /*
        Gets the difference in height between two tiles
     */
    public int heightDif(Tile compTile){
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
