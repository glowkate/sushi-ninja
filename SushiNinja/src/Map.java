import java.util.*;

public class Map {

    private Dictionary tiles;
    private int maxX;
    private int maxY;

    /*
        Initialisation
     */
    public Map(int x, int y) {
        tiles = new Hashtable();
        makeMap(x, y);
        linkMap();
    }

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

    /*
        Operation methods
     */
    public void addTile(Tile newTile){
        tiles.put(newTile.getXY(), newTile);
    }

    public LinkedList<Tile> getFighterPath(Coord startCoords, Coord endCoords){
        //Start stuff
        Tile crntTile = (Tile)tiles.get(startCoords);
        Tile endTile = (Tile)tiles.get(endCoords);
        ArrayList<Tile> crntLinked;
        LinkedList<Tile> crntPath = new LinkedList<>();
        LinkedList<Tile> que = new LinkedList<>();
        //crntTile.getPath().offer(crntTile);
        crntTile.setPathAndVisit(crntPath);

        while(crntTile != endTile){
            crntLinked = crntTile.getLinked();
            for(Tile t : crntLinked){
                if (t.checkPassability(crntTile)){
                    crntPath = (LinkedList<Tile>) crntTile.getPath().clone();
                    crntPath.offer(crntTile);
                    t.setPathAndVisit(crntPath);
                    que.offer(t);
                }
            }
            crntTile = que.pop();
        }
        crntTile.getPath().offer(crntTile);
        return(crntTile.getPath());
    }

    private void makeMap(int xSize, int ySize){
        maxX = xSize;
        maxY = ySize;
        for(int x = 1; x <= xSize; x++){
            for(int y = 1; y <= ySize; y++){
                Tile newTile = new Tile(x, y);
                addTile(newTile);
            }
        }
    }

    /*
    Although we make and override a bunch of objects this function should
    only be called at the beginning of the game where we can afford time loss
    to garbage collection
    */
    private void linkMap(){
        final Coord CoordTest1 = new Coord(1, 1);
        final Coord CoordTest2 = new Coord(1, 2);

        Coord lastCoord;
        Coord nextCoord;
        Tile tile1;
        Tile tile2;

        // Linking the Xs
        for(int y = 1; y <= maxY; y++){
            lastCoord = new Coord(1, y);
            for(int x = 2; x <= maxX; x++){
                nextCoord = new Coord(x, y);
                tile1 = (Tile)tiles.get(lastCoord);
                tile2 = (Tile)tiles.get(nextCoord);

                linkTiles(tile1, tile2);

                lastCoord = nextCoord;
            }
        }

        // Linking the Ys
        for(int x = 1; x <= maxX; x++){
            lastCoord = new Coord(x, 1);
            for(int y = 2; y <= maxY; y++){
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

/*
    T N N
    N T N
    T N T
 */