import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class PlayerInput implements MouseListener {
    public void mouseClicked(MouseEvent event)
    {
        System.out.println("Mouse clicked @ position x = "
                + event.getX() + " y = " + event.getY());
    }

    public void mouseEntered(MouseEvent event)
    {  System.out.println("Mouse entered. x = "
            + event.getX() + " y = " + event.getY());
    }

    public void mouseExited(MouseEvent event)
    {  System.out.println("Mouse exited. x = "
            + event.getX() + " y = " + event.getY());
    }

    public void mousePressed(MouseEvent event)
    {  System.out.println("Mouse pressed. x = "
            + event.getX() + " y = " + event.getY());
    }

    public void mouseReleased(MouseEvent event)
    {  System.out.println("Mouse released. x = "
            + event.getX() + " y = " + event.getY());
    }

    public void doTurn(final Map map, final ArrayList<Fighter> activeFighters, final MapFrame gameFrame)
    {
        ArrayList<Fighter> activeTeam = new ArrayList<>();
        //Gets every active fighter that is on the player's team
        for (Fighter i : activeFighters) {
            if (i.getTeam() == FighterTeam.PLAYER) {
                activeTeam.add(i);
            }
        }
        boolean isTurn = true;

        boolean canAttack;
        boolean canMove;
        boolean canSkip;
        boolean canPass;

        boolean hasAction = true;

        ArrayList<Fighter> nextActiveTeam;
        while(isTurn){
            nextActiveTeam = new ArrayList<>();
            for (Fighter crrntFighter : activeTeam){
                hasAction = true;
                while (hasAction) {
                    //find what actions the fighter can take

                    //ATTACK
                    switch(crrntFighter.getType()) {
                        //Melee
                        case SUSHI:
                        case EGG:
                        default:
                            if (checkMeleeTargets(crrntFighter, activeFighters).size() != 0){
                                canAttack = true;
                            }
                            else{
                                canAttack = false;
                            }
                            break;
                        //Ranged
                        case TEMPURA:
                            if (checkRangedTargets(map, crrntFighter, activeFighters).size() != 0) {
                                canAttack = true;
                            }
                            else{
                                canAttack = false;
                            }
                            break;
                    }

                    //MOVE
                }
            }
        }
    }

    public static ArrayList<Fighter> checkRangedTargets(Map map, Fighter crrntFighter, ArrayList<Fighter> activeFighters) {
        ArrayList<Fighter> LOStargets = new ArrayList<>();
        for(Fighter f : getLOSfighters(map, crrntFighter, activeFighters)){
            if(f.getTeam() == FighterTeam.ENEMY){
                LOStargets.add(f);
            }
        }
        return (LOStargets);
    }

        public static ArrayList<Fighter> checkMeleeTargets(Fighter crrntFighter, ArrayList<Fighter> activeFighters){
        ArrayList<Fighter> adjacentTargets = new ArrayList<>();
        for (Fighter f : getAdjacentFighters(crrntFighter, activeFighters)){
            if (f.getTeam() == FighterTeam.ENEMY){
                adjacentTargets.add(f);
            }
        }
        return (adjacentTargets);
    }

    public static ArrayList<Fighter> getLOSfighters(Map map, Fighter crrntFighter, ArrayList<Fighter> activeFighters){
        ArrayList<Fighter> LOSfighters = new ArrayList<>();
        for(Fighter f : activeFighters){
            if(map.checkLineOfSight(f.getXY(), crrntFighter.getXY()) && f != crrntFighter){
                LOSfighters.add(f);
            }
        }
        return(LOSfighters);
    }

    public static ArrayList<Fighter> getAdjacentFighters (Fighter crrntFighter, ArrayList<Fighter> activeFighters){
        ArrayList<Fighter> adjacentFighters = new ArrayList<>();
        final Coord up = new Coord(crrntFighter.getXY().getX(), crrntFighter.getXY().getY() + 1);
        final Coord down = new Coord(crrntFighter.getXY().getX(), crrntFighter.getXY().getY() - 1);
        final Coord left = new Coord(crrntFighter.getXY().getX() - 1, crrntFighter.getXY().getY());
        final Coord right = new Coord(crrntFighter.getXY().getX() + 1, crrntFighter.getXY().getY());

        for (Fighter f : activeFighters){
            if (f.getXY().equals(up) || f.getXY().equals(down) || f.getXY().equals(left) || f.getXY().equals(right) && f != crrntFighter){
                adjacentFighters.add(f);
            }
        }
        return (adjacentFighters);
    }
}
