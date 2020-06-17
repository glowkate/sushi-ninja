import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;

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

        boolean[] actions;

        boolean hasAction = true;

        ArrayList<Fighter> nextActiveTeam;
        while(isTurn){
            nextActiveTeam = new ArrayList<>();
            for (Fighter crrntFighter : activeTeam){
                hasAction = true;
                while (hasAction) {
                    //find what actions the fighter can take

                    actions = checkActions(map, crrntFighter, activeFighters, activeTeam);

                    //If the fighter cannot move or attack, it automatically passes regardless if it can skip or not.
                    if(!actions[0] && !actions[1]){
                        hasAction = false;
                    }
                    else{
                        Action playerChoice = Action.PASS;
                        Fighter playerTarget = new Fighter();
                        LinkedList<Tile> playerMove = new LinkedList<>();
                        //stuff getting the player's input. NOT IMPLEMENTED YET!!!


                        //Executing the actions
                        switch (playerChoice){
                            case ATTACK:
                                int damage;
                                switch (crrntFighter.getType()){
                                    case SUSHI:
                                    case EGG:
                                    default:
                                        damage = crrntFighter.calcDamage(RangeType.MELEE);
                                        break;
                                    case TEMPURA:
                                        damage = crrntFighter.calcDamage(RangeType.RANGED);
                                        break;
                                }
                                crrntFighter.attackFighter(damage, playerTarget.calcDefence(), playerTarget, map, gameFrame);
                            case MOVE:
                                crrntFighter.moveFighter(playerMove, map, gameFrame);
                            case SKIP:
                                nextActiveTeam.add(crrntFighter);
                                hasAction = false;
                                break;
                            case PASS:
                            default:
                                hasAction = false;
                                break;
                        }
                    }
                }
            }
        }
    }

    /*
        Checks the possible actions that the current fighter could take
        Can attack if there are any available targets
        Can move if the fighter has movement remaining
        Can skip if there are other fighters in the lineup
        Can always pass
     */
    public static boolean[] checkActions(Map map, Fighter crrntFighter, ArrayList<Fighter> activeFighters, ArrayList<Fighter> activeTeam){
        boolean canAttack, canMove, canSkip;

        //ATTACK
        switch(crrntFighter.getType()) {
            //Melee
            case SUSHI:
            case EGG:
            default:
                canAttack = checkMeleeTargets(crrntFighter, activeFighters).size() != 0;
                break;
            //Ranged
            case TEMPURA:
                canAttack = checkRangedTargets(map, crrntFighter, activeFighters).size() != 0;
                break;
        }

        //MOVE
        canMove = crrntFighter.getCrntMove() > 0;

        //SKIP
        canSkip = activeTeam.size() > 1;

        return (new boolean[]{canAttack, canMove, canSkip});
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
