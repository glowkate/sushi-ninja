import java.util.LinkedList;
import java.util.Random;

/*
    This class represents one of the fighters that is controllable.
    This class stores a lot of information, mostly representing stats.

    private final FighterType TYPE - The type of the fighter. This dictates what a lot of
        other vars in Fighter are set to upon init.
    private final FighterTeam TEAM - The team that the fighter belongs to, dictated by Type.
        This dictates who controls the fighter and who the fighter can attack.
    private FighterState state - A state that dictates if the fighter is alive or dead. Used
        by the CpuInput to dictate what fighters are alive and dead. Modified by take damage.
    private final String NAME - The name of the fighter. Used by MapFrame, dictated by Type

    private final int MAX_HP - The max hp that the fighter can have. The crnt hp can not go over this.
    private int crntHp - The amount of hp that the fighter currently has. Modified and checked by
        takeDamage.
    private final int DEF - The value used to dictate how many chances at blocking a point of damage
        this fighter has. Used by calcDefence
    private final int MAX_MOVE - The maximum amount of tiles that a fighter can move per turn.
        Used by resetMove
    private int crntMove - The amount of tiles the fighter can currently move. Reset by resetMove and
        modified by moveFighter
    private int hitCount - Keeps track of the amount of times this fighter has been hit
        (even if it didn't deal any damage), this is used by CpuInput as a tiebreaker.

    private Coord xy - the fighter's position on a map. Used by many functions to locate where the
        fighter is and what tile of a map the fighter is on. Inits at 0,0 and can be
        changed with moveFighter or placeOnMap
 */

public class Fighter implements Comparable<Fighter>{

    private final FighterType TYPE;
    private final FighterTeam TEAM;
    private FighterState state;

    private final String NAME;

    private final int MAX_HP;
    private int crntHp;
    private final int DEF;
    private final int ATTK;
    private final int MAX_MOVE;
    private int crntMove;
    private int hitCount;

    private Coord xy;

    //You only need a type of fighter to create a fighter
    public Fighter(final FighterType INIT_TYPE){
        hitCount = 0;
        TYPE = INIT_TYPE;
        state = FighterState.ALIVE;
        switch(TYPE) {
            case FLAME:
                NAME = "Flamekin";
                TEAM = FighterTeam.ENEMY;
                MAX_HP = 4;
                DEF = 0;
                ATTK = 3;
                MAX_MOVE = 2;
                break;
            case SPIRITFLAME:
                NAME = "Spiritfuled Flamekin";
                TEAM = FighterTeam.ENEMY;
                MAX_HP = 3;
                DEF = 0;
                ATTK = 4;
                MAX_MOVE = 3;
                break;
            case SOUL:
                NAME = "Soul";
                TEAM = FighterTeam.ALLIED;
                MAX_HP = 8;
                DEF = 2;
                ATTK = 3;
                MAX_MOVE = 3;
                break;
            case FRIENDTEST:
                NAME = "Melee Ally";
                TEAM = FighterTeam.ALLIED;
                MAX_HP = 6;
                DEF = 0;
                ATTK = 3;
                MAX_MOVE = 2;
                break;
            case TALLTEST:
                NAME = "Ranged Enemy";
                TEAM = FighterTeam.ENEMY;
                MAX_HP = 3;
                DEF = 0;
                ATTK = 1;
                MAX_MOVE = 2;
                break;
            case SMALLTEST:
                NAME = "Melee Enemy";
                TEAM = FighterTeam.ENEMY;
                MAX_HP = 3;
                DEF = 0;
                ATTK = 1;
                MAX_MOVE = 2;
                break;
            case UNI:
                NAME = "Uni";
                TEAM = FighterTeam.ALLIED;
                MAX_HP = 6;
                DEF = 1;
                ATTK = 4;
                MAX_MOVE = 3;
                break;
            case DUA:
                NAME = "Dua";
                TEAM = FighterTeam.ALLIED;
                MAX_HP = 5;
                DEF = 0;
                ATTK = 5;
                MAX_MOVE = 4;
                break;
            case SUSHI:
                NAME = "Sushi";
                TEAM = FighterTeam.PLAYER;
                MAX_HP = 4;
                DEF = 1;
                ATTK = 3;
                MAX_MOVE = 5;
                break;
            case TEMPURA:
                NAME = "Temmie";
                TEAM = FighterTeam.PLAYER;
                MAX_HP = 4;
                DEF = 0;
                ATTK = 5;
                MAX_MOVE = 3;
                break;
            case EGG:
                NAME = "Gregg";
                TEAM = FighterTeam.PLAYER;
                MAX_HP = 6;
                DEF = 2;
                ATTK = 2;
                MAX_MOVE = 3;
                break;
            default:
                NAME = "Enemy";
                TEAM = FighterTeam.ENEMY;
                MAX_HP = 3;
                DEF = 0;
                ATTK = 1;
                MAX_MOVE = 2;
                break;
        }
        crntMove = MAX_MOVE;
        crntHp = MAX_HP;
        xy = new Coord(0,0);
    }

    //FOR TESTING ONLY
    public Fighter(){
        state = FighterState.ALIVE;
        hitCount = 0;
        TEAM = FighterTeam.ENEMY;
        TYPE = FighterType.SMALLTEST;
        NAME = "Melee Enemy";
        MAX_HP = 3;
        DEF = 0;
        ATTK = 1;
        MAX_MOVE = 2;

        crntHp = MAX_HP;
        crntMove = MAX_MOVE;
        xy = new Coord(0,0);
    }

    /*
        Places the fighter on the map. This sets the fighter's xy and flags the
        tile it's standing on as occupied. Takes a Coord for the fighter to start on and
        the map it's being placed on.
     */
    public void placeOnMap(Coord startXY, Map map){
        setXY(startXY);
        map.getTile(startXY).setOccupied(true);
    }

    /*
        Given a linked list of tiles, and a map, move the fighter by the amount of remaining move it has.
        It'll go through each tile one by one, double checking to make sure the tile is passable, and
        if it moves to a tile it'll deduct one off of the crntMove. If it hits something impassable or it
        can't move through one of the tiles, it'll stop. It'll also set the appropriate occupied flags for tiles in map.
        The MapFrame is used to draw the fighter on each step towards it's destination. This is done via updating the
        MapFrame.
     */
    public void moveFighter(final LinkedList<Tile> path, final Map map, final MapFrame frame){
        map.getTile(xy).setOccupied(false);
        xy = path.get(0).getXY();
        Tile crntTile;
        Tile lastTile = path.get(0);
        boolean keepMoving = true;
        for(int i = 1; i < path.size() && crntMove > 0 && keepMoving; i++){ //because getFighterPath includes the start tile, we start at the second tile when moving
            crntTile = path.get(i);
            if (crntTile.checkPassability(lastTile)){
                xy = crntTile.getXY();
                crntMove -= 1;
                frame.drawSelf();
                try {
                    Thread.sleep(200);
                }
                catch (Exception e){
                }
            }
            else{
                keepMoving = false;
            }
        }
        map.getTile(xy).setOccupied(true);
    }

    //FOR TESTING ONLY
    public void moveFighter(final LinkedList<Tile> path, final Map map){
        map.getTile(xy).setOccupied(false);
        xy = path.get(0).getXY();
        Tile crntTile;
        Tile lastTile = path.get(0);
        boolean keepMoving = true;
        for(int i = 1; i < path.size() && crntMove > 0 && keepMoving; i++){ //because getFighterPath includes the start tile, we start at the second tile when moving
            crntTile = path.get(i);
            if (crntTile.checkPassability(lastTile)){
                xy = crntTile.getXY();
                crntMove -= 1;
            }
            else{
                keepMoving = false;
            }
        }
        map.getTile(xy).setOccupied(true);
    }

    /*
        ===THIS FUNCTION CALLS RNG===

        Using this fighter's attack stat, combined with if the attack is a melee or ranged attack,
        this method calls RNG and calculates how many of the fighter's attacks hit. Melee attacks have
        a 1/2 chance at hitting and ranged attacks have a 1/3 chance of hitting.
     */
    public int calcDamage(final RangeType meleeOrRanged){
        final Random RNG = new Random();
        double attcRNG;
        int hitNum = 0;
        //Melee attacks have a 1/2 chance of hitting.
        //Ranged attacks have a 1/3 chance of hitting.
        if (meleeOrRanged == RangeType.MELEE) {
            for (int i = 0; i < ATTK; i++) {
                attcRNG = RNG.nextDouble();
                if(attcRNG <= 0.5){
                    hitNum++;
                }
            }
        }
        else if (meleeOrRanged == RangeType.RANGED){
            for (int i = 0; i < ATTK; i++) {
                attcRNG = RNG.nextDouble();
                if(attcRNG <= 0.333){
                    hitNum++;
                }
            }
        }
        return (hitNum);
    }

    /*
        ===THIS FUNCTION CALLS RNG===

        Using this fighter's defence stat, this method calls RNG and calculates how many attacks the
        fighter blocks. For every point of defence this fighter has a 1/4 chance to block a point of damage.
     */
    public int calcDefence(){
        final Random RNG = new Random();
        double defRNG;
        int blockNum = 0;
        for(int i = 0; i < DEF; i++){
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

    /*
        This function takes the damage, defence, and target. It organises how much damage the target takes.
        A map and mapframe are provided to allow for drawing and for setting appropriate flags if the target
        dies.
     */
    public void attackFighter(final int damage, final int defence, final Fighter target, final Map map, final MapFrame gameFrame){
        int ultdamage = damage - defence;
        if(ultdamage < 0){
            ultdamage = 0; //no negative damage on my watch
        }
        gameFrame.hitFighter(target.getXY(), xy);
        gameFrame.displayText(NAME + " hit " + target.getName() + " " + ultdamage + " times!");
        target.takeDamage(ultdamage, map, gameFrame);
    }

    /*
        Given the amount of damage to be delt, it deals said amount of damage to self.
        Updates graphics if necessary and sets flags if the fighter dies.
     */
    public void takeDamage(final int damage, final Map MAP , final MapFrame gameFrame){
        crntHp = crntHp - damage;
        if(crntHp <= 0){
            gameFrame.displayText(NAME + " has fallen.");
            crntHp = 0;
            state = FighterState.DEAD;
            MAP.getTile(xy).setOccupied(false);
        }
        hitCount += 1;
    }

    //FOR TESTING ONLY
    public void takeDamage(final int damage){
        crntHp =- damage;
        if(crntHp <= 0){
            crntHp = 0;
            state = FighterState.DEAD;
        }
        hitCount += 1;
    }


    /*
        Heals fighters. Health can never go over max hp.
     */
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

    /*
        String is made up of: X,Y<TYPE>
     */
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
            case SOUL:
                addString = "<SOUL>";
                break;
            case UNI:
                addString = "<UNI>";
                break;
            case DUA:
                addString = "<DUA>";
                break;
            case SUSHI:
                addString = "<SUSHI>";
                break;
            case TEMPURA:
                addString = "<TEMPURA>";
                break;
            case EGG:
                addString = "<EGG>";
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