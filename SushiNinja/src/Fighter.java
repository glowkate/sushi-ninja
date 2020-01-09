/*
    This class represents the fighters that the player and ai will control.
    They hold and modify specific information based on what kind of fighter they are.
 */

import java.util.LinkedList;
import java.util.Random;

public class Fighter implements Comparable<Fighter>{

    private final FighterType TYPE;
    private final FighterTeam TEAM;

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
        switch(TYPE) {
            default:
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
        hitCount = 0;
        TEAM = FighterTeam.ENEMY;
        TYPE = FighterType.SMALLTEST;
        MAX_HP = 3;
        def = 0;
        attk = 1;
        MAX_MOVE = 2;

        crntHp = MAX_HP;
        crntMove = MAX_MOVE;
        xy = new Coord(0,0);
    }

    public void reset(Coord startXY){
        hitCount = 0;
        crntHp = MAX_HP;
        crntMove = MAX_MOVE;
        setXY(startXY);
    }

    //moveFighter and attackFighter will handle any graphics
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
    public void attackFighter(final int DAMAGE, final int DEFENCE, final Fighter TARGET){
        int ULT_DAMAGE = DAMAGE - DEFENCE;
        if(ULT_DAMAGE < 0){
            ULT_DAMAGE = 0; //no negative damage on my watch
        }
        TARGET.takeDamage(ULT_DAMAGE);
    }

    public void takeDamage(final int DAMAGE){
        crntHp =- DAMAGE;
        if(crntHp <= 0){
            crntHp = 0; //GameWorld will figure out what fighters die
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

    public int getMaxHp(){
        return (MAX_HP);
    }

    public int getCrntHp(){
        return (crntHp);
    }

    public int getAttk(){
        return (attk);
    }

    public int getDef(){
        return (def);
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