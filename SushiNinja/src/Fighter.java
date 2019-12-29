/*
    This class represents the fighters that the player and ai will control.
    They hold and modify specific information based on what kind of fighter they are.
 */

public class Fighter {

    private int maxHp;
    private int crntHp;
    private int def;
    private int attk;
    private int speed;

    private String name;

    private Status fighterStatus;
    private Coord xy;

    private boolean canMeleeAttack;
    private boolean canRangeAttack;
    private boolean canMeleeHeal;
    private boolean canRangeHeal;

    public Fighter(
            final int INIT_HP,
            final int INIT_DEF,
            final int INIT_ATTK,
            final int INIT_SPEED,
            final String INIT_NAME,
            final boolean INIT_FIGHTERCANMELEEATTACK,
            final boolean INIT_FIGHTERCANRANGEATTACK,
            final boolean INIT_FIGHTERCANMELEEHEAL,
            final boolean INIT_FIGHTERCANRANGEHEAL
    ){
        maxHp = INIT_HP;
        def = INIT_DEF;
        attk = INIT_ATTK;
        speed = INIT_SPEED;
        name = INIT_NAME;



        crntHp = maxHp;
        xy = new Coord(0,0);
        fighterStatus = Status.INACTIVE;

        canMeleeAttack = INIT_FIGHTERCANMELEEATTACK;
        canRangeAttack = INIT_FIGHTERCANRANGEATTACK;
        canMeleeHeal = INIT_FIGHTERCANMELEEHEAL;
        canRangeHeal = INIT_FIGHTERCANRANGEHEAL;
    }

    public Fighter(final int INIT_HP, final int INIT_DEF, final int INIT_ATTK, final int INIT_SPEED){
        maxHp = INIT_HP;
        def = INIT_DEF;
        attk = INIT_ATTK;
        speed = INIT_SPEED;
        crntHp = maxHp;

        name = "N/A";

        xy = new Coord(0,0);
        fighterStatus = Status.INACTIVE;

        canMeleeAttack = false;
        canRangeAttack = false;
        canMeleeHeal = false;
        canRangeHeal = false;
    }

    public Fighter(){
        maxHp = 1;
        def = 0;
        attk = 1;
        speed = 2;
        name = "N/A";

        crntHp = maxHp;
        xy = new Coord(0,0);
        fighterStatus = Status.INACTIVE;

        canMeleeAttack = false;
        canRangeAttack = false;
        canMeleeHeal = false;
        canRangeHeal = false;
    }

    public void activate(Coord startXY){
        fighterStatus = Status.ACTIVE_ALIVE;
        crntHp = maxHp;
        setXY(startXY);
    }

    public void takeDamage(int damage){
        crntHp =- damage;
        if(crntHp <= 0){
            crntHp = 0;
            fighterStatus = Status.ACTIVE_DEAD;
        }
    }

    public void takeHealing(int healing){
        crntHp =+ healing;
        if(crntHp > maxHp){
            crntHp = maxHp;
        }
    }

    public void setXY(Coord newCoord){
        xy = newCoord;
    }

    public void setXY(int x, int y){
        Coord newCoord = new Coord(x,y);
        xy = newCoord;
    }

    public int getMaxHp(){
        return maxHp;
    }

    public int getCrntHp(){
        return crntHp;
    }

    public int getAttk(){
        return attk;
    }

    public int getDef(){
        return def;
    }

    public int getSpeed(){
        return speed;
    }

    public boolean getCanMeleeAttack(){
        return canMeleeAttack;
    }

    public boolean getCanRangeAttack(){
        return canRangeAttack;
    }

    public boolean getCanMeleeHeal(){
        return canMeleeHeal;
    }

    public boolean getCanRangeHeal(){
        return canRangeHeal;
    }

    public Status getFighterStatus(){
        return fighterStatus;
    }

    public String getName(){
        return name;
    }

    public Coord getXY(){
        return xy;
    }
}