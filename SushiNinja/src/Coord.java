public class Coord {
    private int x;
    private int y;

    public Coord(int initX, int initY){
        x = initX;
        y = initY;
    }

    public Coord(){
        x = 0;
        y = 0;
    }

    public int getX(){
        return(x);
    }

    public int getY(){
        return(y);
    }

    /*
        Methods to make Coord a valid hashtable key

        Override is in place to catch errors in the hashtable functions
        If it's not a valid hashtable function then the program will crash.
     */
    @Override
    public String toString(){
        return (x + "," + y);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null || obj.getClass()!= this.getClass()) {
            return false;
        }
        else {
            Coord compCoord = (Coord) obj;
            return (x == compCoord.getX() && y == compCoord.getY());
        }
    }

    @Override
    public int hashCode(){
        return(x * 283 + y);
    }
}
