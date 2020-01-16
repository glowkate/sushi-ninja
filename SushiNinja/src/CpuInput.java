import java.util.ArrayList;
import java.util.LinkedList;

/*
    This is a static class in charge of holding the functions that make a fighter
    do it's turn. The doTurn function uses 3 subfunctions.

    A fighter's turn works like so.
    -Find out who my foes are.
    If I'm a ranged fighter...
        POINT A
        - Who is the least attacked target that I can hit?
        If I can't hit anyone...
            - Find the closest fighter
            If I can't reach any fighters...
                 -Do nothing
            Otherwise
                -Take ONE step towards it. Go back to point A.
        If I can hit someone...
            -Attack them and stop.
    If I'm a melee fighter...
        -Who is the closest target?
        If I can't reach any fighters...
            -Do nothing
        Otherwise
            -Move towards target
            If I'm adjacent to them...
                -Attack them
 */

/*
    This is the only function that should be called from other classes (testing excluded), every
    other function in this class should only be used by this function. This function takes a
    fighter and calculates plus preform it's turn.

    final Fighter FIGHTER - This is the fighter we are currently controlling. We accsess the
        information stored within, along with an RNG call for attacking
    final Map MAP - This is the map that we are playing on. Used to store what spaced are occupied,
        if one tile has line of sight to another, and how to get from one tile to another.
    final ArrayList<Fighter> ACTIVE_FIGHTERS - These are the fighters currently in play.
        Includes both dead and alive fighters. Used for finding the FIGHTER's opponents.
    final MapFrame GAME_FRAME - The screen we are drawing on. We don't use it but we give it to
        functions in fighter to allow them to update the screen.
 */

public class CpuInput{
    public static void doTurn(final Fighter fighter, final Map map, final ArrayList<Fighter> activeFighters, final MapFrame gameFrame){
        if (fighter.getState() == FighterState.ALIVE){

            //Gets the fighter's opponents out of the ACTIVE FIGHTERS.
            //Active fighters is no longer used.
            ArrayList<Fighter> opponents = getOpponents(activeFighters, fighter);

            //Preforms Melee or Ranged AI based on the fighter's type.
            final FighterType F_TYPE = fighter.getType();
            switch (F_TYPE) {

                //RANGED FIGHTER TYPES
                case TALLTEST:
                case SPIRITFLAME:
                case UNI:
                    while (fighter.getCrntMove() > 0) {

                        //Gets the least hit target that the fighter can see.
                        Fighter bestTarget = getClosestLOS(fighter, opponents, map);

                        if (bestTarget == null) { //It there's no viable targets...
                            LinkedList<Tile> bestPath = getClosestPath(fighter, opponents, map);

                            if (bestPath != null && bestPath.size() != 0) {
                                //move 1 step towards it.
                                LinkedList<Tile> ultPath = new LinkedList<>();
                                ultPath.offer(bestPath.get(0));
                                ultPath.offer(bestPath.get(1));
                                fighter.moveFighter(ultPath, map, gameFrame);
                            } else {
                                break; //if you can't reach the target, break
                            }
                        } else { //if a target was found, attack it.
                            int fighterAttc = fighter.calcDamage(RangeType.RANGED);
                            int targetDef = bestTarget.calcDefence();
                            fighter.attackFighter(fighterAttc, targetDef, bestTarget, map, gameFrame);
                            break;
                        }
                    }
                    break; //if you run out of movement or you hit a target, stop.

                //Melee types go here.
                case FLAME:
                case SOUL:
                case DUA:
                case FRIENDTEST:
                case SMALLTEST:

                    //figure out the closest path to a target. In case of a tie, attack the one that's been attacked less
                    LinkedList<Tile> bestPath = getClosestPath(fighter, opponents, map);

                    Fighter bestTarget = null;
                    if(bestPath != null) {
                        //figure out what target we selected
                        for (Fighter f : opponents) {
                            if (map.getTile(f.getXY()) == bestPath.getLast()) {
                                bestTarget = f;
                                break;
                            }
                        }
                    }

                    //move the dude.
                    if (bestPath != null && bestPath.size() != 0) {
                        fighter.moveFighter(bestPath, map, gameFrame);
                    }

                    if (bestTarget != null) {
                        //if next to best target after move, attack it
                        Tile fighterTile = map.getTile(fighter.getXY());
                        Tile targetTile = map.getTile(bestTarget.getXY());
                        if (fighterTile.getLinked().contains(targetTile)) {
                            int fighterDamage = fighter.calcDamage(RangeType.MELEE);
                            int targetDef = bestTarget.calcDefence();
                            fighter.attackFighter(fighterDamage, targetDef, bestTarget, map, gameFrame);
                        }
                    }
                    break;
                //if the fighter can't attack they sit on their bum.
                default:
                    break;
            }
            fighter.resetMove();
        }
    }

    /*
        This is a function to be used by doTurn. Given a list of fighters and one single "pov" fighter
        the function will return an ArrayList<Fighter> of who the pov fighter can attack.

        final ArrayList<Fighter> ACTIVE_FIGHTERS - The fighters currently on the board. Iterated through
            to get all the potential foes.
        final Fighter FIGHTER - The fighter who the program is getting the
     */

    public static ArrayList<Fighter> getOpponents(final ArrayList<Fighter> activeFighters, final Fighter fighter){
        ArrayList<Fighter> opponents = new ArrayList<>();
        final FighterTeam AI_TEAM = fighter.getTeam();
        FighterTeam crntFighterTeam;
        //This loops figures out what Fighters are the AI's opponents.
        for(Fighter f : activeFighters){
            if (f.getState() == FighterState.ALIVE) {
                crntFighterTeam = f.getTeam();
                if (AI_TEAM == FighterTeam.ENEMY) {
                    if (crntFighterTeam == FighterTeam.PLAYER || crntFighterTeam == FighterTeam.ALLIED) {
                        opponents.add(f);
                    }
                } else {
                    if (crntFighterTeam == FighterTeam.ENEMY) {
                        opponents.add(f);
                    }
                }
            }
        }
        return(opponents);
    }

    /*
        Given a fighter, a list of opponents, and the map, this function find out
        what the least hit opponent that the fighter has line of sight on is.

        final Fighter FIGHTER - The fighter, who's coords are used as the point of
            origin in the LoS method.
        final ArrayList<Fighter> OPPONENTS - The list of potential targets. Iterated
            through to see who is closest.
        final Map MAP - The map that the game is happening on. Used to check if two
            co-ordinates have line of sigh on each other.
     */

    public static Fighter getClosestLOS(final Fighter fighter, final ArrayList<Fighter> opponents, final Map map){
        Fighter bestTarget = null;
        for(Fighter f : opponents) {
            if (map.checkLineOfSight(fighter.getXY(), f.getXY())) {
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

    /*
        Given a fighter, a list of opponents, and the map, this function find out
        what the closest opponent that the fighter can move to is. If two fighters
        are tied in terms of how close they are, it targets the one that's been
        attacked less.

        final Fighter FIGHTER - The fighter, who's coords are used as the start
            coord in the pathfinding method.
        final ArrayList<Fighter> OPPONENTS - The list of potential targets. Iterated
            through and used at the end coord in the pathfinding method.
        final Map MAP - The map that the game is happening on. Used to produce a
            path between two co-ordinates.
     */

    public static LinkedList<Tile> getClosestPath(final Fighter fighter, final ArrayList<Fighter> opponents, final Map map){
        Coord ourCoord = fighter.getXY();
        LinkedList<Tile> crntPath;
        LinkedList<Tile> bestPath = null;
        Coord crntCoord;
        int crntPathLen;
        int bestPathLen = -1;
        Fighter bestTarget = null;
        //figure out the closest (best) target. In case of a tie, attack the one that's been attacked less
        for(Fighter crntTarget : opponents){
            crntCoord = crntTarget.getXY();
            crntPath = map.getFighterPath(ourCoord, crntCoord);
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
