public class Fighter {

    private int maxHp;
    private int crntHp;
    private int def;
    private int attk;
    private int speed;

    private String name;

    private Coord xy;

    public Fighter(final int INIT_HP, final int INIT_DEF, final int INIT_ATTK, final int INIT_SPEED, final String INIT_NAME){
        maxHp = INIT_HP;
        def = INIT_DEF;
        attk = INIT_ATTK;
        speed = INIT_SPEED;
        name = INIT_NAME;

        xy = new Coord(0,0);
    }

    public void setXY(Coord newCoord){
        xy = newCoord;
    }
}
