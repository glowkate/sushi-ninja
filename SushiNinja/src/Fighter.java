/*
    This class represents the fighters that the player and ai will control.
    They hold and modify specific information based on what kind of fighter they are.
 */

import java.util.LinkedList;
import java.util.Random;

public class Fighter implements Comparable<Fighter>{

    private final FighterType TYPE;
    private final FighterTeam TEAM;
    private FighterState state;

    private final String NAME;

    private final int MAX_HP;
    private int crntHp;
    private final int def;
    private final int attk;
    private final int MAX_MOVE;
    private int crntMove;
    private int hitCount;

    private Coord xy;

    public Fighter(final FighterType INIT_TYPE){
        hitCount = 0;
        TYPE = INIT_TYPE;
        state = FighterState.ALIVE;
        switch(TYPE) {
            case FRIENDTEST:
                NAME = "Melee Ally";
                TEAM = FighterTeam.ALLIED;
                MAX_HP = 6;
                def = 0;
                attk = 3;
                MAX_MOVE = 2;
                break;
            case TALLTEST:
                NAME = "Ranged Enemy";
                TEAM = FighterTeam.ENEMY;
                MAX_HP = 3;
                def = 0;
                attk = 1;
                MAX_MOVE = 2;
                break;
            case SMALLTEST:
                NAME = "Melee Enemy";
                TEAM = FighterTeam.ENEMY;
                MAX_HP = 3;
                def = 0;
                attk = 1;
                MAX_MOVE = 2;
                break;
            default:
                NAME = "Enemy";
                TEAM = FighterTeam.ENEMY;
                MAX_HP = 3;
                def = 0;
                attk = 1;
                MAX_MOVE = 2;
                break;
        }
        crntMove = MAX_MOVE;
        crntHp = MAX_HP;
        xy = new Coord(0,0);
    }

    public Fighter(){
        state = FighterState.ALIVE;
        hitCount = 0;
        TEAM = FighterTeam.ENEMY;
        TYPE = FighterType.SMALLTEST;
        NAME = "Melee Enemy";
        MAX_HP = 3;
        def = 0;
        attk = 1;
        MAX_MOVE = 2;

        crntHp = MAX_HP;
        crntMove = MAX_MOVE;
        xy = new Coord(0,0);
    }

    public void reset(Coord startXY, Map map){
        hitCount = 0;
        crntHp = MAX_HP;
        crntMove = MAX_MOVE;
        setXY(startXY);
        map.getTile(startXY).setOccupied(true);
    }

    public void moveFighter(final LinkedList<Tile> PATH, final Map MAP, final MapFrame FRAME){
        MAP.getTile(xy).setOccupied(false);
        xy = PATH.get(0).getXY();
        Tile crntTile;
        Tile lastTile = PATH.get(0);
        boolean keepMoving = true;
        for(int i = 1; i < PATH.size() && crntMove > 0 && keepMoving; i++){ //because getFighterPath includes the start tile, we start at the second tile when moving
            crntTile = PATH.get(i);
            if (crntTile.checkPassability(lastTile)){
                xy = crntTile.getXY();
                crntMove -= 1;
                FRAME.drawSelf();
                try {
                    Thread.sleep(250);
                }
                catch (Exception e){
                }
            }
            else{
                keepMoving = false;
            }
        }
        MAP.getTile(xy).setOccupied(true);
    }

    //FOR TESTING ONLY
    public void moveFighter(final LinkedList<Tile> PATH, final Map MAP){
        MAP.getTile(xy).setOccupied(false);
        xy = PATH.get(0).getXY();
        Tile crntTile;
        Tile lastTile = PATH.get(0);
        boolean keepMoving = true;
        for(int i = 1; i < PATH.size() && crntMove > 0 && keepMoving; i++){ //because getFighterPath includes the start tile, we start at the second tile when moving
            crntTile = PATH.get(i);
            if (crntTile.checkPassability(lastTile)){
                xy = crntTile.getXY();
                crntMove -= 1;
            }
            else{
                keepMoving = false;
            }
        }
        MAP.getTile(xy).setOccupied(true);
    }

    public int calcDamage(final RangeType MELEE_OR_RANGED){
        final Random RNG = new Random();
        double attcRNG;
        int hitNum = 0;
        //Melee attacks have a 1/2 chance of hitting.
        //Ranged attacks have a 1/3 chance of hitting.
        if (MELEE_OR_RANGED == RangeType.MELEE) {
            for (int i = 0; i < attk; i++) {
                attcRNG = RNG.nextDouble();
                if(attcRNG <= 0.5){
                    hitNum++;
                }
            }
        }
        else if (MELEE_OR_RANGED == RangeType.RANGED){
            for (int i = 0; i < attk; i++) {
                attcRNG = RNG.nextDouble();
                if(attcRNG <= 0.333){
                    hitNum++;
                }
            }
        }
        return (hitNum);
    }

    //Each fighter has a 1/4 chance of blocking 1 damage per def they have
    public int calcDefence(){
        final Random RNG = new Random();
        double defRNG;
        int blockNum = 0;
        for(int i = 0; i < def; i++){
            defRNG = RNG.nextDouble();
            if(defRNG <= 0.25){
                blockNum++;
            }
        }
        return (blockNum);
    }

    public void resetMove(){
        crntMove = MAX_MOVE;
    }

    //I've divided the RNG portion of attacking from what's ultimately to be called to make it so it can be tested.
    public void attackFighter(final int DAMAGE, final int DEFENCE, final Fighter TARGET, final Map MAP, final MapFrame GAME_FRAME){
        int ULT_DAMAGE = DAMAGE - DEFENCE;
        if(ULT_DAMAGE < 0){
            ULT_DAMAGE = 0; //no negative damage on my watch
        }
        GAME_FRAME.hitFighter(TARGET.getXY(), xy);
        GAME_FRAME.displayText(NAME + " hit " + TARGET.getName() + " " + ULT_DAMAGE + " times!");
        TARGET.takeDamage(ULT_DAMAGE, MAP, GAME_FRAME);
    }

    public void takeDamage(final int DAMAGE, final Map MAP , final MapFrame GAME_FRAME){
        System.out.println(crntHp);
        System.out.println(DAMAGE);
        crntHp = crntHp - DAMAGE;
        if(crntHp <= 0){
            GAME_FRAME.displayText(NAME + " has fallen.");
            crntHp = 0;
            state = FighterState.DEAD;
            MAP.getTile(xy).setOccupied(false);
        }
        hitCount += 1;
    }

    //FOR TESTING ONLY
    public void takeDamage(final int DAMAGE){
        crntHp =- DAMAGE;
        if(crntHp <= 0){
            crntHp = 0;
            state = FighterState.DEAD;
        }
        hitCount += 1;
    }


    public void takeHealing(int healing){
        crntHp =+ healing;
        if(crntHp > MAX_HP){
            crntHp = MAX_HP;
        }
    }

    public void setXY(Coord newCoord){
        xy = newCoord;
    }

    public void setXY(int x, int y){
        xy = new Coord(x,y);
    }

    public String getName(){
        return (NAME);
    }

    public int getMaxHp(){
        return (MAX_HP);
    }

    public int getCrntHp(){
        return (crntHp);
    }

    public int getCrntMove(){
        return (crntMove);
    }

    public Coord getXY(){
        return (xy);
    }

    public FighterType getType(){
        return (TYPE);
    }

    public FighterTeam getTeam(){
        return (TEAM);
    }

    public int getHitCount(){
        return (hitCount);
    }

    public FighterState getState(){
        return (state);
    }

    @Override
    public String toString(){
        String addString;
        switch(TYPE){
            case SMALLTEST:
                addString = "<SMALLTEST>";
                break;
            case TALLTEST:
                addString = "<TALLTEST>";
                break;
            case FRIENDTEST:
                addString = "<FRIENDTEST>";
                break;
            default:
                addString = "<>";
        }
        return(xy.toString() + addString);
    }

    /*
       This function allows us to sort Fighters.
       Fighters shall be sorted by drawing priority.
       The higher the Y value, the higher the priority.
    */
    @Override
    public int compareTo(Fighter fighter) {
        Coord compCoord = fighter.getXY();
        int compY = compCoord.getY();
        int ourY = xy.getY();

        return ourY - compY;
    }
}