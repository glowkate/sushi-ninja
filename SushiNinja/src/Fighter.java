/*
    This class represents the fighters that the player and ai will control.
    They hold and modify specific information based on what kind of fighter they are.
 */

public class Fighter implements Comparable<Fighter>{

    private final FighterType TYPE;

    private final int maxHp;
    private int crntHp;
    private final int def;
    private final int attk;
    private final int speed;

    private Coord xy;

    public Fighter(final FighterType INIT_TYPE){
        TYPE = INIT_TYPE;
        switch(TYPE) {
            default:
                maxHp = 3;
                def = 0;
                attk = 1;
                speed = 2;
                break;
        }

        crntHp = maxHp;
        xy = new Coord(0,0);
    }

    public Fighter(){
        TYPE = FighterType.SMALLTEST;
        maxHp = 3;
        def = 0;
        attk = 1;
        speed = 2;

        crntHp = maxHp;
        xy = new Coord(0,0);
    }

    public void reset(Coord startXY){
        crntHp = maxHp;
        setXY(startXY);
    }

    public void takeDamage(int damage){
        crntHp =- damage;
        if(crntHp <= 0){
            crntHp = 0;
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
        xy = new Coord(x,y);
    }

    public int getMaxHp(){
        return (maxHp);
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

    public int getSpeed(){
        return (speed);
    }

    public Coord getXY(){
        return (xy);
    }

    public FighterType getType(){
        return (TYPE);
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