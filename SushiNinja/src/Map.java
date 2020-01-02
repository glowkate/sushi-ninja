import java.io.File;
import java.io.IOException;
import java.util.*;

/*
    Class used for creating, storing, and running operations on a dictionary of tiles.
 */

public class Map {

    private Dictionary tiles;
    private int maxX;
    private int maxY;

    /*
        Initialisation
     */

    public Map(String init_map){
        tiles = new Hashtable();
        makeMap(init_map);
        linkMap();
    }

    //For testing only
    public Map(int x, int y) {
        tiles = new Hashtable();
        makeMap(x, y);
        linkMap();
    }

    //For testing only
    public Map(){
        tiles = new Hashtable();
        makeMap(1, 1);
        linkMap();
    }

    /*
        Private methods
     */

    private void linkTiles(Tile tile1, Tile tile2){
        tile1.addLink(tile2);
        tile2.addLink(tile1);
    }

    public void addTile(Tile newTile){
        tiles.put(newTile.getXY(), newTile);
    }

    public boolean checkLineOfSight(Coord ogCoords, Coord targetCoords){
        final double OG_X = ogCoords.getX() + 0.5;
        final double OG_Y = ogCoords.getY() + 0.5;
        final double TG_X = targetCoords.getX() + 0.5;
        final double TG_Y = targetCoords.getY() + 0.5;
        double crntX;
        double crntY;
        Coord crntCoord;
        Tile crntTile;
        for(double t = 0.0; t <= 1.0; t += 0.01){
            crntX = OG_X + t * (TG_X - OG_X);
            crntY = OG_Y + t * (TG_Y - OG_Y);
            crntCoord = new Coord((int)crntX, (int)crntY);
            crntTile = getTile(crntCoord);
            /*
                If I make any more tiles that projectiles can't pass through, I'll add them.
             */
            switch(crntTile.getType()){
                case WALL:
                    return false;
            }
        }
        return true;
    }

    public LinkedList<Tile> getFighterPath(Coord startCoords, Coord endCoords){
        Tile crntTile = (Tile)tiles.get(startCoords);
        Tile endTile = (Tile)tiles.get(endCoords);
        ArrayList<Tile> crntLinked;
        LinkedList<Tile> crntPath = new LinkedList<>();
        LinkedList<Tile> que = new LinkedList<>();
        crntTile.setPathAndVisit(crntPath);

        boolean queFull = true;

        while(crntTile != endTile && queFull){
            crntLinked = crntTile.getLinked();
            for(Tile t : crntLinked){
                if (t.checkPassability(crntTile)){
                    crntPath = (LinkedList<Tile>) crntTile.getPath().clone();
                    crntPath.offer(crntTile);
                    t.setPathAndVisit(crntPath);
                    que.offer(t);
                }
            }
            if (que.size() == 0){ //if this is true, there is no path to the destination tile.
                queFull = false;
            }
            else {
                crntTile = que.pop();
            }
        }
        if (crntTile == endTile) {
            crntTile.getPath().offer(crntTile);
            return (crntTile.getPath());
        }
        else {
            return (new LinkedList<>());
        }
    }

    private void makeMap(String init_map){
        char crntChar;
        Tile toAddTile;
        int crntX = 0;
        int crntY = 0;
        for(int i = 0; i < init_map.length(); i++) {
            crntChar = init_map.charAt(i);
            switch (crntChar) {
                case (' '):
                    toAddTile = new Tile(crntX, crntY, TileType.OPENSPACE);
                    addTile(toAddTile);
                    break;
                case ('X'):
                    toAddTile = new Tile(crntX, crntY, TileType.WALL);
                    addTile(toAddTile);
                    break;
                case ('V'):
                    toAddTile = new Tile(crntX, crntY, TileType.GAP);
                    addTile(toAddTile);
                    break;
                default:
                    assert(false);
                    break;
            }
            if (crntX == 9) {
                crntX = 0;
                crntY++;
            }
            else{
                crntX++;
            }
        }
        assert(crntX == 0 || crntY == 8);
    }

    //For testing only
    private void makeMap(int xSize, int ySize){
        maxX = xSize;
        maxY = ySize;
        for(int x = 0; x < xSize; x++){
            for(int y = 0; y < ySize; y++){
                Tile newTile = new Tile(x, y, TileType.OPENSPACE);
                addTile(newTile);
            }
        }
    }

    private void linkMap(){
        Coord lastCoord;
        Coord nextCoord;
        Tile tile1;
        Tile tile2;
        for(int y = 0; y < maxY; y++){
            lastCoord = new Coord(0, y);
            for(int x = 1; x < maxX; x++){
                nextCoord = new Coord(x, y);
                tile1 = (Tile)tiles.get(lastCoord);
                tile2 = (Tile)tiles.get(nextCoord);

                linkTiles(tile1, tile2);

                lastCoord = nextCoord;
            }
        }

        // Linking the Ys
        for(int x = 0; x < maxX; x++){
            lastCoord = new Coord(x, 0);
            for(int y = 1; y < maxY; y++){
                nextCoord = new Coord(x, y);
                tile1 = (Tile)tiles.get(lastCoord);
                tile2 = (Tile)tiles.get(nextCoord);

                linkTiles(tile1, tile2);

                lastCoord = nextCoord;
            }
        }
    }

    public Tile getTile(Coord locationCoord){
        return((Tile)tiles.get(locationCoord));
    }

    public Tile getTile(int x, int y){
        Coord location = new Coord(x,y);
        return((Tile)tiles.get(location));
    }
}


/*
        for (Enumeration i = tiles.elements(); i.hasMoreElements();)
        {
            i.nextElement();
        }

        if I want to iterate over the elements in tiles
 */