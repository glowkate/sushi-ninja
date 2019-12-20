import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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


    /*public void coordSetXWorks(){
        Coord coordTest = new Coord();
        Coord coordGolden = new Coord(8, 0);
        coordTest.setX(8);
        assertEquals(coordTest, coordGolden);
    }

    void coordSetYWorks(){
        Coord coordTest = new Coord();
        Coord coordGolden = new Coord(0, 2);
        coordTest.setY(2);
        assertEquals(coordTest, coordGolden);
    }


    public void coordSetXYWorks(){
        Coord coordTest = new Coord();
        Coord coordGolden = new Coord(3, 7);
        coordTest.setXY(3, 7);
        assertEquals(coordTest, coordGolden);
    }
    */
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
    public void tileSetFightersCanPassWorks(){
        Tile tileTest = new Tile();
        tileTest.setFightersCanPass(true);
        boolean boolTest = tileTest.getFightersCanPass();
        assertTrue(boolTest);
    }

    @Test
    public void tileSetProjectilesCanPassWorks(){
        Tile tileTest = new Tile();
        tileTest.setProjectilesCanPass(true);
        boolean boolTest = tileTest.getProjectilesCanPass();
        assertTrue(boolTest);
    }

    @Test
    public void tileSetHeightWorks(){
        Tile tileTest = new Tile();
        tileTest.setHeight(5);
        int intTest = tileTest.getHeight();
        int intGolden = 5;
        assertEquals(intTest, intGolden);
    }

    @Test
    public void tileGetXYWorks(){
        Tile tileTest = new Tile(34, 12);
        Coord coordTest = tileTest.getXY();
        Coord coordGolden = new Coord(34, 12);
        assertEquals(coordTest, coordGolden);
    }

    @Test
    public void tileGetXWorks(){
        Tile tileTest = new Tile(5, 1);
        int intTest = tileTest.getX();
        int intGolden = 5;
        assertEquals(intTest, intGolden);
    }

    @Test
    public void tileGetYWorks(){
        Tile tileTest = new Tile(2, 7);
        int intTest = tileTest.getY();
        int intGolden = 7;
        assertEquals(intTest, intGolden);
    }

    @Test
    public void tileGetHeightWorks(){
        Tile tileTest = new Tile();
        int intTest = tileTest.getHeight();
        int intGolden = 1; //Tiles init at height 1
        assertEquals(intTest, intGolden);
    }

    @Test
    public void tileGetFighterCanPassWorks(){
        Tile tileTest = new Tile();
        boolean boolTest = tileTest.getFightersCanPass();
        assertFalse(boolTest);
    }

    @Test
    public void tileGetProjectilesCanPassWorks(){
        Tile tileTest = new Tile();
        boolean boolTest = tileTest.getProjectilesCanPass();
        assertFalse(boolTest);
    }

    @Test
    public void tileGetLinkedWorks(){
        Tile tileTest = new Tile();
        List<Tile> listTest = tileTest.getLinked();
        List<Tile> listGolden = new ArrayList<>();
        assertEquals(listTest, listGolden);
    }

}
