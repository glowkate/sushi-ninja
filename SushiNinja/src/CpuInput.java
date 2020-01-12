import java.util.ArrayList;
import java.util.LinkedList;

public class CpuInput implements FighterInput {
    public void doTurn(final Fighter FIGHTER, final Map MAP, final ArrayList<Fighter> ACTIVE_FIGHTERS){
        ArrayList<Fighter> opponents = getOpponents(ACTIVE_FIGHTERS, FIGHTER);
        final FighterType F_TYPE = FIGHTER.getType();
        switch (F_TYPE){
            //Ranged types go here.
            case TALLTEST:
                while(FIGHTER.getCrntMove() > 0) { //while the fighter can move...

                    //check to see who the best target is.
                    Fighter bestTarget = getClosestLOS(FIGHTER, opponents, MAP);

                    if(bestTarget == null){ //if no target was found...
                        LinkedList<Tile> bestPath = getClosestPath(FIGHTER, opponents, MAP);

                        if (bestPath != null && bestPath.size() != 0) {
                            //move 1 step towards it.
                            LinkedList<Tile> ultPath = new LinkedList<>();
                            ultPath.offer(bestPath.get(0));
                            ultPath.offer(bestPath.get(1));
                            FIGHTER.moveFighter(ultPath, MAP);
                        }
                    }
                    else{ //if a target was found, attack it.
                        int fighterAttc = FIGHTER.calcDamage(RangeType.RANGED);
                        int targetDef = bestTarget.calcDefence();
                        FIGHTER.attackFighter(fighterAttc, targetDef, bestTarget);
                        break;
                    }
                }
                break; //if you run out of movement or you hit a target, stop.

            //Melee types go here.
            case FRIENDTEST:
            case SMALLTEST:

                //figure out the closest path to a target. In case of a tie, attack the one that's been attacked less
                LinkedList<Tile> bestPath = getClosestPath(FIGHTER, opponents, MAP);

                Fighter bestTarget = null;
                //figure out what target we selected
                for(Fighter f : opponents){
                    if(MAP.getTile(f.getXY()) == bestPath.getLast()){
                        bestTarget = f;
                        break;
                    }
                }

                //move the dude.
                if (bestPath != null && bestPath.size() != 0) {
                    FIGHTER.moveFighter(bestPath, MAP);
                }

                if(bestTarget != null) {
                    //if next to best target after move, attack it
                    int xDif = FIGHTER.getXY().getX() - bestTarget.getXY().getX();
                    int yDif = FIGHTER.getXY().getY() - bestTarget.getXY().getY();
                    xDif = Math.abs(xDif);
                    yDif = Math.abs(yDif);
                    if (xDif == 1 ^ yDif == 1) { // ^ is an XOR in java. Prevents diagonal attacking.
                        int fighterDamage = FIGHTER.calcDamage(RangeType.MELEE);
                        int targetDef = bestTarget.calcDefence();
                        FIGHTER.attackFighter(fighterDamage, targetDef, bestTarget);
                    }
                }
                break;
            //if the fighter can't attack they sit on their bum.
            default:
                break;
        }
        FIGHTER.resetMove();
    }

    public ArrayList<Fighter> getOpponents(final ArrayList<Fighter> ACTIVE_FIGHTERS, final Fighter FIGHTER){
        ArrayList<Fighter> opponents = new ArrayList<>();
        final FighterTeam AI_TEAM = FIGHTER.getTeam();
        FighterTeam crntFighterTeam;
        //This loops figures out what Fighters are the AI's opponents.
        for(Fighter f : ACTIVE_FIGHTERS){
            crntFighterTeam = f.getTeam();
            if (AI_TEAM == FighterTeam.ENEMY) {
                if (crntFighterTeam == FighterTeam.PLAYER || crntFighterTeam == FighterTeam.ALLIED) {
                    opponents.add(f);
                }
            }
            else {
                if (crntFighterTeam == FighterTeam.ENEMY) {
                    opponents.add(f);
                }
            }
        }
        return(opponents);
    }

    public Fighter getClosestLOS(final Fighter FIGHTER, final ArrayList<Fighter> OPPONENTS, final Map MAP){
        Fighter bestTarget = null;
        for(Fighter f : OPPONENTS) {
            if (MAP.checkLineOfSight(FIGHTER.getXY(), f.getXY())) {
                if(bestTarget == null) {
                    bestTarget = f;
                }
                else if (bestTarget.getHitCount() > f.getHitCount()){ //if the new target has been hit less, it becomes the best target
                    bestTarget = f;
                }
            }
        }
        return (bestTarget);
    }

    public LinkedList<Tile> getClosestPath(final Fighter FIGHTER, final ArrayList<Fighter> OPPONENTS, final Map MAP){
        Coord ourCoord = FIGHTER.getXY();
        LinkedList<Tile> crntPath;
        LinkedList<Tile> bestPath = null;
        Coord crntCoord;
        int crntPathLen;
        int bestPathLen = -1;
        Fighter bestTarget = null;
        //figure out the closest (best) target. In case of a tie, attack the one that's been attacked less
        for(Fighter crntTarget : OPPONENTS){
            crntCoord = crntTarget.getXY();
            crntPath = MAP.getFighterPath(ourCoord, crntCoord);
            crntPathLen = crntPath.size();
            if(crntPathLen != 0) {
                if (bestPathLen == -1) {
                    bestPathLen = crntPathLen;
                    bestPath = crntPath;
                    bestTarget = crntTarget;
                } else if (crntPathLen < bestPathLen) {
                    bestPathLen = crntPathLen;
                    bestPath = crntPath;
                    bestTarget = crntTarget;
                } else if (crntPathLen == bestPathLen && crntTarget.getHitCount() < bestTarget.getHitCount()) {
                    bestPath = crntPath;
                    bestTarget = crntTarget;
                }
            }
        }
        return (bestPath);
    }
}
