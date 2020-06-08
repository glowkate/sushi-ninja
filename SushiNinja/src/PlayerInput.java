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

                            break;

                        //Ranged
                        case TEMPURA:

                            break;
                    }
                }
            }
        }
    }
}
