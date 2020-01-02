import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
    This class is for holding the tests for other classes to ensure they work properly.
 */

public class GameTest {

    /*
        Coord class tests
     */

    @Test
    public void coordGetXWorks(){
        Coord testCoord = new Coord(12, 8);
        int xTest = testCoord.getX();
        int xGolden = 12;
        assertEquals(xTest, xGolden);
    }

    @Test
    public void coordGetYWorks(){
        Coord testCoord = new Coord(21, 2);
        int yTest = testCoord.getY();
        int yGolden = 2;
        assertEquals(yTest, yGolden);
    }

    @Test
    public void coordToStringWorks(){
        Coord coordTest = new Coord(6,2);
        String strTest = coordTest.toString();
        String strGolden = "6,2";
        assertEquals(strTest, strGolden);
    }

    @Test
    public void coordEqualsWorks(){
        Coord coordTest = new Coord(124, 563);
        Coord coordGolden = new Coord(124, 563);
        assertEquals(coordTest, coordGolden);
    }

    @Test
    public void coordHashCodeWorks(){
        Coord coordTest1 = new Coord(1, 2);
        Coord coordTest2 = new Coord(2, 1);
        int hashTest1 = coordTest1.hashCode();
        int hashTest2 = coordTest2.hashCode();
        int hashGolden1 = 285;
        int hashGolden2 = 567;
        assertEquals(hashTest1, hashGolden1);
        assertEquals(hashTest2, hashGolden2);
    }

    /*
        Fighter class tests
     */

    @Test
    public void fighterActivateWorks(){
        Fighter fighterTest = new Fighter();
        Coord coordIn = new Coord();
        fighterTest.reset(coordIn);
        assertEquals(fighterTest.getXY(), coordIn);
    }

    @Test
    public void fighterTakeDamageWorks(){
        Fighter fighterTest = new Fighter();
        fighterTest.takeDamage(5);
        assertEquals(fighterTest.getCrntHp(), 0);
    }

    @Test
    public void fighterTakeHealingWorks(){
        Fighter fighterTest = new Fighter();
        fighterTest.takeDamage(2);
        fighterTest.takeHealing(10);
        assertEquals(fighterTest.getCrntHp(), 3); //Healing shouldn't go above max HP
    }

    @Test void fighterSetXYWorks(){
        Fighter fighterTest1 = new Fighter();
        Fighter fighterTest2 = new Fighter();
        Coord coordIn = new Coord(5, 6);
        int intInX = 5;
        int intInY = 6;
        Coord coordGolden = new Coord(5, 6);

        fighterTest1.setXY(coordIn);
        fighterTest2.setXY(intInX, intInY);

        assertEquals(fighterTest1.getXY(), coordGolden);
        assertEquals(fighterTest2.getXY(), coordGolden);
    }

    @Test
    public void fighterGetMaxHpWorks(){
        Fighter fighterTest = new Fighter();
        assertEquals(fighterTest.getMaxHp(), 3);
    }

    @Test
    public void fighterGetCrntHpWorks(){
        Fighter fighterTest = new Fighter();
        assertEquals(fighterTest.getCrntHp(), 3);
    }

    /*
        Map class tests
     */

    @Test
    public void mapStrInitWorks(){
        String strIn = "XXXXXXXXXX" +
                       "X        X" +
                       "X        X" +
                       "X   VV   X" +
                       "X  VVVV  X" +
                       "X        X" +
                       "XXXXXXXXXX";
        Map mapTest = new Map(strIn);

        Tile tileGolden1 = new Tile(0,0,TileType.WALL);
        Tile tileGolden2 = new Tile(9,0,TileType.WALL);
        Tile tileGolden3 = new Tile(6,4,TileType.GAP);
        Tile tileGolden4 = new Tile(9,6,TileType.WALL);

        System.out.println(mapTest.getTile(0,0));
        System.out.println(tileGolden1);

        assertEquals(tileGolden1, mapTest.getTile(0,0));
        assertEquals(tileGolden2, mapTest.getTile(9,0));
        assertEquals(tileGolden3, mapTest.getTile(6,4));
        assertEquals(tileGolden4, mapTest.getTile(9,6));
    }

    @Test
    public void mapAddTileWorks(){
        Coord coordIn = new Coord(0, 0);
        Tile tileIn = new Tile();
        Map mapTest = new Map();

        mapTest.addTile(tileIn);
        assertEquals(mapTest.getTile(coordIn), tileIn);
    }

    @Test
    public void mapLinkMapWorks(){
        Map mapTest = new Map(2, 2);

        Coord coordComp1 = new Coord(0,0);
        Coord coordComp2 = new Coord(0,1);
        Coord coordComp3 = new Coord(1,0);

        Tile tileComp1 = mapTest.getTile(coordComp1);
        Tile tileComp2 = mapTest.getTile(coordComp2);
        Tile tileComp3 = mapTest.getTile(coordComp3);

        ArrayList<Tile> linkedComp = tileComp1.getLinked();

        assertEquals(linkedComp.get(1), tileComp2);
        assertEquals(linkedComp.get(0), tileComp3);
    }

    @Test
    public void mapCheckLineOfSightWorks(){
        String strIn =
                    "XXXXXXXXXX" +
                    "     X   X" +
                    "  X   VVV " +
                    "   XXX V X" +
                    " X        " +
                    "    XX X  " +
                    "XXXXXXXXXX";

        Map mapTest = new Map(strIn);
        Coord coordIn1A = new Coord(8, 1);
        Coord coordIn1B = new Coord(6, 4);
        Coord coordIn2A = new Coord(7, 1);
        Coord coordIn2B = new Coord(4, 4);
        assertTrue(mapTest.checkLineOfSight(coordIn1A, coordIn1B));
        assertFalse(mapTest.checkLineOfSight(coordIn2A, coordIn2B));
    }

    @Test
    public void mapGetFighterPathWorks(){
        Map mapTest = new Map(3, 3);
        Coord coordIn1 = new Coord(0,0);
        Coord coordIn2 = new Coord(1,2);
        LinkedList<Tile> pathTest = mapTest.getFighterPath(coordIn1, coordIn2);
        LinkedList<Tile> pathGolden = new LinkedList<>();
        pathGolden.offer(mapTest.getTile(0,0));
        pathGolden.offer(mapTest.getTile(1,0));
        pathGolden.offer(mapTest.getTile(1,1));
        pathGolden.offer(mapTest.getTile(1,2));
        assertEquals(pathTest, pathGolden);
    }

    /*
        Tile class tests
     */

    @Test
    public void tileAddLinkWorks(){
        Tile tileTest = new Tile();
        Tile tileAdd = new Tile();
        tileTest.addLink(tileAdd);

        List<Tile> listGolden = new ArrayList<>();
        listGolden.add(tileAdd);

        assertEquals(tileTest.getLinked(), listGolden);
    }

    @Test
    public void tileSetPathAndVisitWorks(){
        Tile tileTest = new Tile();
        Tile tileIn1 = new Tile();
        Tile tileIn2 = new Tile();

        LinkedList<Tile> pathIn = new LinkedList<>();
        pathIn.offer(tileIn1);
        pathIn.offer(tileIn2);

        tileTest.setPathAndVisit(pathIn);

        assertEquals(tileTest.getPath(), pathIn);
        assertTrue(tileTest.getHasBeenVisited());
    }

    @Test
    public void tileResetPathfindingVarsWorks(){
        Tile tileTest = new Tile();
        Tile tileIn1 = new Tile();
        Tile tileIn2 = new Tile();

        LinkedList<Tile> pathIn = new LinkedList<>();
        pathIn.offer(tileIn1);
        pathIn.offer(tileIn2);

        tileTest.setPathAndVisit(pathIn);
        tileTest.resetPathfindingVars();

        LinkedList<Tile> pathGolden = new LinkedList<>();

        assertEquals(tileTest.getPath(), pathGolden);
        assertFalse(tileTest.getHasBeenVisited());
    }

    @Test
    public void tileCheckPassabilityWorks(){
        Tile tileTest = new Tile();
        Tile tileComp1 = new Tile(0,0,TileType.ELEVATED2);
        Tile tileComp2 = new Tile();
        Tile tileComp3 = new Tile();
        tileComp3.setOccupied(true);
        assertFalse(tileTest.checkPassability(tileComp1));
        assertFalse(tileTest.checkPassability(tileComp3));
        assertTrue(tileTest.checkPassability(tileComp2));
    }

    @Test
    public void tileCheckHeightDiffWorks(){
        Tile tileTest = new Tile();
        Tile tileComp = new Tile(0,0, TileType.ELEVATED2);
        int intTest = tileTest.heightDif(tileComp);
        int intGolden = 2;
        assertEquals(intTest, intGolden);
    }

    @Test
    public void tileGetXYWorks(){
        Tile tileTest = new Tile(34, 12, TileType.WALL);
        Coord coordTest = tileTest.getXY();
        Coord coordGolden = new Coord(34, 12);
        assertEquals(coordTest, coordGolden);
    }

    @Test
    public void tileGetXWorks(){
        Tile tileTest = new Tile(5, 1, TileType.WALL);
        int intTest = tileTest.getX();
        int intGolden = 5;
        assertEquals(intTest, intGolden);
    }

    @Test
    public void tileGetYWorks(){
        Tile tileTest = new Tile(2, 7,TileType.WALL);
        int intTest = tileTest.getY();
        int intGolden = 7;
        assertEquals(intTest, intGolden);
    }

    @Test
    public void tileGetLinkedWorks(){
        Tile tileTest = new Tile();
        List<Tile> listTest = tileTest.getLinked();
        List<Tile> listGolden = new ArrayList<>();
        assertEquals(listTest, listGolden);
    }

}