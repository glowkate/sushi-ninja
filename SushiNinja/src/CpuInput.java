import java.util.ArrayList;
import java.util.LinkedList;

public class CpuInput implements FighterInput {
    public void doTurn(final Fighter FIGHTER, final Map MAP, final ArrayList<Fighter> ACTIVE_FIGHTERS){
        ArrayList<Fighter> opponents = new ArrayList<>();
        final FighterTeam AI_TEAM = FIGHTER.getTeam();
        FighterType F_TYPE = FIGHTER.getType();
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
        Coord ourCoord = FIGHTER.getXY();
        switch (F_TYPE){
            //Ranged types go here.

            //Melee types go here.
            case SMALLTEST:
            case TALLTEST:
                LinkedList<Tile> crntPath;
                LinkedList<Tile> bestPath = null;
                Coord crntCoord;
                int crntPathLen = -1;
                int bestPathLen = -1;
                Fighter bestTarget = null;
                for(Fighter crntTarget : opponents){
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
                            bestPathLen = crntPathLen;
                            bestPath = crntPath;
                            bestTarget = crntTarget;
                        }

                        int xDif = FIGHTER.getXY().getX() - bestTarget.getXY().getX();
                        int yDif = FIGHTER.getXY().getY() - bestTarget.getXY().getY();
                        xDif = Math.abs(xDif);
                        yDif = Math.abs(yDif);
                        if(xDif == 1 ^ yDif == 1){ // ^ is an XOR in java. Prevents diagonal attacking.
                            //insert attack function here
                        }
                    }
                }
                if (bestPath != null && bestPath.size() != 0) {
                    FIGHTER.moveFighter(bestPath, MAP);
                }
                break;
            //if the fighter can't attack they sit on their bum.
            default:
                break;
        }
    }
}
