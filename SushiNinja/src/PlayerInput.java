import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class PlayerInput implements MouseListener {
    static private boolean canInput;
    static private boolean isPressed;
    static private boolean isReleased;
    static private int mousePressX;
    static private int mousePressY;
    static private int mouseReleaseX;
    static private int mouseReleaseY;

    PlayerInput(){
        canInput = false;
        isPressed = false;
        mousePressX = 0;
        mousePressY = 0;
        mouseReleaseX = 0;
        mouseReleaseY = 0;
    }

    public void mouseClicked(MouseEvent event)
    {
    }

    public void mouseEntered(MouseEvent event)
    {
    }

    public void mouseExited(MouseEvent event)
    {
    }

    public void mousePressed(MouseEvent event)
    {
        if(canInput) {
            mousePressX = event.getX();
            mousePressY = event.getY();
            isPressed = true;
        }
    }

    public void mouseReleased(MouseEvent event)
    {
        if(canInput) {
            mouseReleaseX = event.getX();
            mouseReleaseY = event.getY();
            isReleased = true;
        }
    }

    public void doTurn(final Map map, final ArrayList<Fighter> activeFighters, final MapFrame gameFrame)
    {
        ArrayList<Fighter> activeTeam = new ArrayList<>();
        //Gets every active fighter that is on the player's team
        for (Fighter i : activeFighters) {
            if (i.getTeam() == FighterTeam.PLAYER && i.getState() == FighterState.ALIVE) {
                activeTeam.add(i);
            }
        }
        boolean isTurn = true;

        boolean[] actions;

        boolean hasAction;

        ArrayList<Fighter> nextActiveTeam;
        while(isTurn) {
            nextActiveTeam = new ArrayList<>();
            for (Fighter crrntFighter : activeTeam) {
                hasAction = true;
                while (hasAction) {
                    //find what actions the fighter can take
                    actions = checkActions(map, crrntFighter, activeFighters, activeTeam);

                    //If the fighter cannot move or attack, it automatically skips regardless if it can skip or not.
                    Action playerChoice = Action.NONE;
                    Fighter playerTarget = new Fighter();
                    //if (!actions[0] && !actions[1]) {
                    //    playerChoice = Action.PASS;
                    //}
                    //else{
                    gameFrame.displayText(crrntFighter.getName() + "'s turn.");
                    //}
                    LinkedList<Tile> playerMove = new LinkedList<>();
                    //GETTING THE PLAYER'S INPUT VIA MENUS
                    canInput = true;
                    while (playerChoice == Action.NONE) {
                        setButtons(actions, gameFrame);
                        Action chosenAction = Action.NONE;
                        Action actionStorage = Action.NONE;
                        while (chosenAction == Action.NONE) {
                            System.out.print(""); //For some reason, we need this line of code
                            if (isPressed) {
                                Action pressedButton = checkButtonXY(mousePressX, mousePressY);
                                if (pressedButton == Action.ATTACK && actions[0]) {
                                    gameFrame.setAttackButton(ButtonState.PUSHED);
                                    actionStorage = Action.ATTACK;
                                    isPressed = false;
                                } else if (pressedButton == Action.MOVE && actions[1]) {
                                    gameFrame.setMoveButton(ButtonState.PUSHED);
                                    actionStorage = Action.MOVE;
                                    isPressed = false;
                                } else if (pressedButton == Action.SKIP && actions[2]) {
                                    gameFrame.setSkipButton(ButtonState.PUSHED);
                                    actionStorage = Action.SKIP;
                                    isPressed = false;
                                } else if (pressedButton == Action.PASS) {
                                    gameFrame.setPassButton(ButtonState.PUSHED);
                                    actionStorage = Action.PASS;
                                    isPressed = false;
                                }
                            }
                            if (isReleased) {
                                setButtons(actions, gameFrame);
                                if (checkButtonXY(mouseReleaseX, mouseReleaseY) == actionStorage) {
                                    chosenAction = actionStorage;
                                }
                                isReleased = false;
                            }
                        }
                        Coord tileStorage = new Coord(-1, -1);
                        switch (chosenAction) {
                            case ATTACK:
                                gameFrame.setAttackButton(ButtonState.PUSHED);
                                gameFrame.setMoveButton(ButtonState.HIDDEN);
                                gameFrame.setSkipButton(ButtonState.HIDDEN);
                                gameFrame.setPassButton(ButtonState.HIDDEN);
                                gameFrame.setBackButton(ButtonState.ACTIVE);
                                actionStorage = Action.NONE;
                                chosenAction = Action.NONE;
                                while (chosenAction == Action.NONE) {
                                    System.out.print("");
                                    canInput = true;
                                    if (isPressed) {
                                        Action pressedButton = checkButtonXY(mousePressX, mousePressY);
                                        Coord pressedCoord = checkTileXY(mousePressX, mousePressY);
                                        if (pressedButton == Action.BACK) {
                                            gameFrame.setBackButton(ButtonState.PUSHED);
                                            actionStorage = Action.BACK;
                                            isPressed = false;
                                        } else if (!pressedCoord.equals(new Coord(-1, -1))) {
                                            tileStorage = pressedCoord;
                                            isPressed = false;
                                        }
                                    }
                                    if (isReleased) {
                                        Action pressedButton = checkButtonXY(mouseReleaseX, mouseReleaseY);
                                        Coord pressedTile = checkTileXY(mouseReleaseX, mouseReleaseY);
                                        gameFrame.setBackButton(ButtonState.ACTIVE);

                                        if (pressedButton == actionStorage && pressedButton == Action.BACK) {
                                            chosenAction = Action.BACK;
                                        } else if (pressedTile.equals(tileStorage) && !tileStorage.equals(new Coord(-1, -1))) {
                                            for (Fighter f : getTargets(map, crrntFighter, activeFighters)) {
                                                if (f.getXY().equals(pressedTile)) {
                                                    chosenAction = Action.ATTACK;
                                                    playerChoice = Action.ATTACK;
                                                    playerTarget = f;
                                                }
                                            }
                                        }
                                        isReleased = false;
                                    }
                                }
                                break;
                            case MOVE:
                                gameFrame.setAttackButton(ButtonState.HIDDEN);
                                gameFrame.setMoveButton(ButtonState.PUSHED);
                                gameFrame.setSkipButton(ButtonState.HIDDEN);
                                gameFrame.setPassButton(ButtonState.HIDDEN);
                                gameFrame.setBackButton(ButtonState.ACTIVE);
                                actionStorage = Action.NONE;
                                chosenAction = Action.NONE;

                                while (chosenAction == Action.NONE) {
                                    System.out.print("");
                                    canInput = true;
                                    if (isPressed) {
                                        Action pressedButton = checkButtonXY(mousePressX, mousePressY);
                                        Coord pressedCoord = checkTileXY(mousePressX, mousePressY);
                                        if (pressedButton == Action.BACK) {
                                            gameFrame.setBackButton(ButtonState.PUSHED);
                                            actionStorage = Action.BACK;
                                            isPressed = false;
                                        } else if (!pressedCoord.equals(new Coord(-1, -1))) {
                                            tileStorage = pressedCoord;
                                            isPressed = false;
                                        }
                                    }
                                    if (isReleased) {
                                        Action pressedButton = checkButtonXY(mouseReleaseX, mouseReleaseY);
                                        Coord pressedTile = checkTileXY(mouseReleaseX, mouseReleaseY);
                                        gameFrame.setBackButton(ButtonState.ACTIVE);

                                        if (pressedButton == actionStorage && pressedButton == Action.BACK) {
                                            chosenAction = Action.BACK;
                                        } else if (pressedTile.equals(tileStorage) && !tileStorage.equals(new Coord(-1, -1))) {
                                            LinkedList<Tile> potentialPath = map.getFighterPath(crrntFighter.getXY(), pressedTile);
                                            if (potentialPath.size() != 0) {
                                                chosenAction = Action.MOVE;
                                                playerChoice = Action.MOVE;
                                                playerMove = potentialPath;
                                            }
                                        }
                                        isReleased = false;
                                    }
                                }
                                break;
                            case SKIP:
                                playerChoice = Action.SKIP;
                                break;
                            case PASS:
                            default:
                                playerChoice = Action.PASS;
                                break;
                        }
                    }
                    //System.out.println(playerChoice);
                    canInput = false;
                    gameFrame.setAttackButton(ButtonState.HIDDEN);
                    gameFrame.setMoveButton(ButtonState.HIDDEN);
                    gameFrame.setSkipButton(ButtonState.HIDDEN);
                    gameFrame.setPassButton(ButtonState.HIDDEN);
                    gameFrame.setBackButton(ButtonState.HIDDEN);
                    //Executing the actions
                    switch (playerChoice) {
                        case ATTACK:
                            int damage;
                            hasAction = false;
                            System.out.println(crrntFighter);
                            crrntFighter.resetMove();
                            switch (crrntFighter.getType()) {
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
                            break;
                        case MOVE:
                            crrntFighter.moveFighter(playerMove, map, gameFrame);
                            break;
                        case SKIP:
                            nextActiveTeam.add(crrntFighter);
                            hasAction = false;
                            break;
                        case PASS:
                        default:
                            hasAction = false;
                            crrntFighter.resetMove();
                            System.out.println(crrntFighter);
                            break;
                    }
                }
            }

            activeTeam = nextActiveTeam;
            if (activeTeam.size() == 0) {
                isTurn = false;
            }
        }
    }

    public static void setButtons(boolean[] possibleActions, MapFrame gameFrame) {
        if (possibleActions[0]) {
            gameFrame.setAttackButton(ButtonState.ACTIVE);
        } else {
            gameFrame.setAttackButton(ButtonState.INACTIVE);
        }

        if (possibleActions[1]) {
            gameFrame.setMoveButton(ButtonState.ACTIVE);
        } else {
            gameFrame.setMoveButton(ButtonState.INACTIVE);
        }

        if (possibleActions[2]) {
            gameFrame.setSkipButton(ButtonState.ACTIVE);
        } else {
            gameFrame.setSkipButton(ButtonState.INACTIVE);
        }
        gameFrame.setPassButton(ButtonState.ACTIVE);
        gameFrame.setBackButton(ButtonState.HIDDEN);
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
        canAttack = getTargets(map, crrntFighter, activeFighters).size() != 0;

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

    public static ArrayList<Fighter> getTargets(Map map, Fighter crrntFighter, ArrayList<Fighter> activeFighters){
        ArrayList<Fighter> targets;
        switch(crrntFighter.getType()) {
            case SUSHI:
            case EGG:
            default:
                targets = checkMeleeTargets(crrntFighter, activeFighters);
                break;
            //Ranged
            case TEMPURA:
                targets = checkRangedTargets(map, crrntFighter, activeFighters);
                break;
        }
        return (targets);
    }

    public static ArrayList<Fighter> getLOSfighters(Map map, Fighter crrntFighter, ArrayList<Fighter> activeFighters){
        ArrayList<Fighter> LOSfighters = new ArrayList<>();
        for(Fighter f : activeFighters){
            if(map.checkLineOfSight(f.getXY(), crrntFighter.getXY()) && f != crrntFighter && f.getState() == FighterState.ALIVE){
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
            if ((f.getXY().equals(up) || f.getXY().equals(down) || f.getXY().equals(left) || f.getXY().equals(right) && f != crrntFighter) && f.getState() == FighterState.ALIVE){
                adjacentFighters.add(f);
                System.out.println(f.getState());
                System.out.println(f.getState() == FighterState.ALIVE);
            }
        }
        return (adjacentFighters);
    }

    public static Coord checkTileXY(int x, int y){
        int tileX = Math.floorDiv(x, 96);
        int tileY = Math.floorDiv(y - 30, 96);
        Coord tileCoord = new Coord(tileX, tileY);
        if(tileX >= 10 && tileY >= 7) {
            tileCoord = new Coord(-1, -1);
        }
        return (tileCoord);
    }

    public static Action checkButtonXY (int x, int y){
        if(y > 96 * 6.5) {
            if (x < 96) {
                return (Action.ATTACK);
            } else if (x > 96 && x < 96 * 2) {
                return (Action.MOVE);
            } else if (x > 96 * 2 && x < 96 * 3) {
                return (Action.SKIP);
            } else if (x > 96 * 3 && x < 96 * 4) {
                return (Action.PASS);
            } else if (x > 96 * 4 && x < 96 * 5) {
                return (Action.BACK);
            } else {
                return (Action.NONE);
            }
        }
        else{
            return (Action.NONE);
        }
    }
}
