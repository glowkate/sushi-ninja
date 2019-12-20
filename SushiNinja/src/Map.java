import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

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


    /*
        Private methods
     */
    private String toString(final int X, final int Y) {
        return (X + "," + Y);
    }

    private int heightDiff(Tile tile1, Tile tile2){
        return(tile2.getHeight() - tile1.getHeight());
    }

    private void linkTiles(Tile tile1, Tile tile2){
        //System.out.println(tile1);
        //System.out.println(tile2);
        //System.out.println("-Going into addLink with tile2-");
        tile1.addLink(tile2);
        //System.out.println("-Going into addLink with tile1-");
        tile2.addLink(tile1);
    }

    /*
        Operation methods
     */
    public void addTile(Tile newTile){
        //System.out.println(newTile);
        //System.out.println(newTile.getXY());
        tiles.put(newTile.getXY(), newTile);
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
        //System.out.println("-=MAP INITIALISATION COMPLETE=-");
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
                //System.out.println(x + "," + y);
                nextCoord = new Coord(x, y);
                tile1 = (Tile)tiles.get(lastCoord);
                tile2 = (Tile)tiles.get(nextCoord);
                //System.out.println(tile1.getXY());
                //System.out.println(tile2.getXY());
                //System.out.println("--------");
                //System.out.println("=Going into linkTiles=");

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

        //final Coord test = new Coord(1, 1);
        //final Coord test2 = new Coord(1,2);
        /*Coord crntCoord = new Coord(2, 1);
        Coord lastCoord = new Coord(1, 1);
        for(int x = 3; tiles.get(crntCoord) != null; x++){
            for(int y = 1; tiles.get(crntCoord) != null; y++){
                Tile crntTile = (Tile) tiles.get(crntCoord);
                Tile lastTile = (Tile) tiles.get(lastCoord);
                linkTiles(lastTile, crntTile);
                lastCoord = crntCoord;
                crntCoord = new Coord(x, y);
            }
        }

        crntCoord = new Coord(1, 2);
        lastCoord = new Coord(1, 1);
        for(int y = 3; tiles.get(crntCoord) != null; y++){
            for(int x = 1; tiles.get(crntCoord) != null; x++){
                Tile crntTile = (Tile) tiles.get(crntCoord);
                Tile lastTile = (Tile) tiles.get(lastCoord);
                linkTiles(lastTile, crntTile);
                lastCoord = crntCoord;
                crntCoord = new Coord(x, y);
            }
        }*/
        //System.out.println( tiles.get(CoordTest1));
        //System.out.println( tiles.get(CoordTest2));
        //System.out.println( tiles.get("This key does not exist"));
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