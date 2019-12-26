import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
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
        fighterTest.activate(coordIn);
        assertEquals(fighterTest.getFighterStatus(), Status.ACTIVE_ALIVE);
    }

    @Test
    public void fighterTakeDamageWorks(){
        Fighter fighterTest = new Fighter();
        fighterTest.takeDamage(5);
        assertEquals(fighterTest.getCrntHp(), 0);
        assertEquals(fighterTest.getFighterStatus(), Status.ACTIVE_DEAD);
    }

    @Test
    public void fighterTakeHealingWorks(){
        Fighter fighterTest = new Fighter(5,5,5,5);
        fighterTest.takeDamage(4);
        fighterTest.takeHealing(10);
        assertEquals(fighterTest.getCrntHp(), 5); //Healing shouldn't go above max HP
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
        assertEquals(fighterTest.getMaxHp(), 1);
    }

    @Test
    public void fighterGetCrntHpWorks(){
        Fighter fighterTest = new Fighter();
        assertEquals(fighterTest.getCrntHp(), 1);
    }

    @Test
    public void fighterGetCanMeleeAttackWorks(){
        Fighter fighterTest = new Fighter();
        assertFalse(fighterTest.getCanMeleeAttack());
    }

    @Test
    public void fighterGetCanRangeAttackWorks(){
        Fighter fighterTest = new Fighter();
        assertFalse(fighterTest.getCanRangeAttack());
    }

    @Test
    public void fighterGetCanMeleeHealWorks(){
        Fighter fighterTest = new Fighter();
        assertFalse(fighterTest.getCanMeleeHeal());
    }

    @Test
    public void fighterGetCanRangeHealWorks(){
        Fighter fighterTest = new Fighter();
        assertFalse(fighterTest.getCanRangeHeal());
    }

    @Test
    public void fighterGetNameWorks(){
        Fighter fighterTest = new Fighter();
        String stringGolden = "N/A";
        assertEquals(fighterTest.getName(), stringGolden);
    }

    /*
        Map class tests
     */

    @Test
    public void mapAddTileWorks(){
        Coord coordIn = new Coord(3, 3);
        Tile tileIn = new Tile(3, 3);
        Map mapTest = new Map();

        mapTest.addTile(tileIn);
        assertEquals(mapTest.getTile(coordIn), tileIn);
    }

    @Test
    public void mapLinkMapWorks(){
        Map mapTest = new Map(2, 2);
        Coord coordComp1 = new Coord(1,1);
        Coord coordComp2 = new Coord(1,2);
        Coord coordComp3 = new Coord(2,1);

        Tile tileComp1 = mapTest.getTile(coordComp1);
        Tile tileComp2 = mapTest.getTile(coordComp2);
        Tile tileComp3 = mapTest.getTile(coordComp3);

        ArrayList<Tile> linkedComp = tileComp1.getLinked();

        assertEquals(linkedComp.get(1), tileComp2);
        assertEquals(linkedComp.get(0), tileComp3);
    }

    @Test
    public void mapGetFighterPathWorks(){
        Map mapTest = new Map(3, 3);
        Coord coordIn1 = new Coord(1,1);
        Coord coordIn2 = new Coord(2,3);
        LinkedList<Tile> pathTest = mapTest.getFighterPath(coordIn1, coordIn2);
        LinkedList<Tile> pathGolden = new LinkedList<>();
        pathGolden.offer(mapTest.getTile(1,1));
        pathGolden.offer(mapTest.getTile(2,1));
        pathGolden.offer(mapTest.getTile(2,2));
        pathGolden.offer(mapTest.getTile(2,3));
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
    public void tileSetFightersCanPassWorks(){
        Tile tileTest = new Tile();
        tileTest.setFightersCanPass(false);
        boolean boolTest = tileTest.getFightersCanPass();
        assertFalse(boolTest);
    }

    @Test
    public void tileSetProjectilesCanPassWorks(){
        Tile tileTest = new Tile();
        tileTest.setProjectilesCanPass(false);
        boolean boolTest = tileTest.getProjectilesCanPass();
        assertFalse(boolTest);
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
        Tile tileComp1 = new Tile();
        tileComp1.setHeight(3);
        Tile tileComp2 = new Tile();
        assertFalse(tileTest.checkPassability(tileComp1));
        assertTrue(tileTest.checkPassability(tileComp2));
    }

    @Test
    public void tileCheckHeightDiffWorks(){
        Tile tileTest = new Tile();
        Tile tileComp = new Tile();
        tileComp.setHeight(5);
        int intTest = tileTest.heightDif(tileComp);
        int intGolden = 4;
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
        assertTrue(boolTest);
    }

    @Test
    public void tileGetProjectilesCanPassWorks(){
        Tile tileTest = new Tile();
        boolean boolTest = tileTest.getProjectilesCanPass();
        assertTrue(boolTest);
    }

    @Test
    public void tileGetLinkedWorks(){
        Tile tileTest = new Tile();
        List<Tile> listTest = tileTest.getLinked();
        List<Tile> listGolden = new ArrayList<>();
        assertEquals(listTest, listGolden);
    }

}
