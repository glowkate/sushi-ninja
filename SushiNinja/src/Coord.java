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
    These are needed to prevent garbage.
    If I made x and y final then I would have to make a new Coord
    every time I wished to move a Fighter.
    That's a lot of garbage!
    */
    public void setX(int newX){
        x = newX;
    }

    public void setY(int newY){
        y = newY;
    }

    public void setXY(int newX, int newY){
        x = newX;
        y = newY;
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
            //System.out.println(obj);
            //System.out.println(this);
            Coord compCoord = (Coord) obj;
            return (x == compCoord.getX() && y == compCoord.getY());
        }
    }

    @Override
    public int hashCode(){
        //System.out.println(X * 283 + Y);
        return(x * 283 + y);
        //return (1);
    }
}
