import java.util.*;

/*
    This class creates, stores, and runs operations on a dictionary of tiles. Used by most classes to
    move fighters, check where they are, and provide data on what they can do. In charge of caluating
    paths for the fighters to move on and checking if a fighter can shoot another fighter.

    private Dictionary tiles - where all the tiles are stored. Each tile's key is the Coord where they're
        located. This is why Coord is hashable. Used by most methods in Map.
    private int maxX - The X size of the map. For most maps will be 10. Used by linkMap.
    private int maxY - The Y size of the map. For most maps will be 7. Used by linkMap.
 */

public class Map {

    private Dictionary tiles;
    private int maxX;
    private int maxY;


    public Map(String initMap){
        tiles = new Hashtable();
        makeMap(initMap);
        maxX = 10;
        maxY = 7;
        linkMap();
    }

    //For testing only
    public Map(){
        tiles = new Hashtable();
        makeMap(10, 8);
        linkMap();
    }

    /*
        Used on init by linkMap to link up 2 tiles to one another.
     */

    private void linkTiles(Tile tile1, Tile tile2){
        tile1.addLink(tile2);
        tile2.addLink(tile1);
    }

    /*
        Used on init by makeMap. Adds a tile to the map.
     */

    public void addTile(Tile newTile){
        tiles.put(newTile.getXY(), newTile);
    }

    /*
        Given two Coords, finds out if they have line of sight on one another.
        Stats at origin and calculates the line linking the two. Goes through
        the line bit by bit, checking what tile it's standing on. If it finds
        a tile that blocks LoS, returns false. If it gets to the destination without
        resistance, returns true.
     */
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
            if (crntTile.getType() == TileType.WALL) {
                return false;
            }
        }
        return true;
    }

    /*
        Finds the closest path from the startCoord to the endCoord using
        breadth first search. Stores tiles to check out using a que and stores
        paths to tiles by giving them the path take to them. Returns an
        empty list if it can't find it's way to the tile.
     */
    public LinkedList<Tile> getFighterPath(Coord startCoords, Coord endCoords){
        Tile crntTile = (Tile)tiles.get(startCoords);
        Tile endTile = (Tile)tiles.get(endCoords);
        ArrayList<Tile> crntLinked;
        LinkedList<Tile> crntPath = new LinkedList<>();
        LinkedList<Tile> que = new LinkedList<>();
        crntTile.setPathAndVisit(crntPath);

        boolean queFull = true;

        switch(((Tile) tiles.get(endCoords)).getType()){ //If the end tile can't be stood on then eff it.
            case WALL:
            case GAP:
                return (new LinkedList<>());
        }

        while(crntTile != endTile && queFull){
            crntLinked = crntTile.getLinked();
            for(Tile t : crntLinked){
                if (t.checkPassability(crntTile) || t == endTile){
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

        LinkedList<Tile> returnPath;
        if (crntTile == endTile) {
            crntTile.getPath().offer(crntTile);
            returnPath = (LinkedList<Tile>) crntTile.getPath().clone();
        }
        else {
            returnPath = new LinkedList<>();
        }

        //once path is found, reset vars.
        Tile resetTile;
        Coord resetCoord;
        for(int x = 0; x < maxX; x++){
            for(int y = 0; y < maxY; y++){
                resetCoord = new Coord(x, y);
                resetTile = getTile(resetCoord);
                resetTile.resetPathfindingVars();
            }
        }
        return (returnPath);
    }

    /*
        Called on init. Makes the map via a string. Each char in the string is a different tileType.
        This way we can make a map quickly and edit it easily.
     */
    private void makeMap(String initMap){
        char crntChar;
        Tile toAddTile;
        int crntX = 0;
        int crntY = 0;
        for(int i = 0; i < initMap.length(); i++) {
            crntChar = initMap.charAt(i);
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

    /*
        Goes through the rows and columns of the map and links them together.
     */
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

    /*
        Gets a tile from the map. Used by other classes to manipulate tiles.
     */
    public Tile getTile(Coord locationCoord){
        return((Tile)tiles.get(locationCoord));
    }

    public Tile getTile(int x, int y){
        Coord location = new Coord(x,y);
        return((Tile)tiles.get(location));
    }

}