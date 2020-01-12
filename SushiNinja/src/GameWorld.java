import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

/*
    This class will be in charge of creating and organising all other classes.
    It contains the main game loop. (er...it will)
 */

public class GameWorld{
    JFrame gameFrame;
    GameState state;
    final Map MAP;
    ArrayList<Fighter> activeFighters;
    MapFrame frame;

    public GameWorld(final Map MAP_INIT, final ArrayList<Fighter> FIGHTERS_INIT){
        gameFrame = new JFrame();
        state = GameState.PLAYERTURN;
        MAP = MAP_INIT;
        activeFighters = FIGHTERS_INIT;
        frame = new MapFrame(MAP, activeFighters);

        gameFrame.setPreferredSize(new Dimension(960, 672));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void runGame(){
        boolean runGame = true;
        //the main loop of the program
        while(runGame){
            try {
                Thread.sleep(1000);
            }
            catch (Exception e){

            }
            switch (state){
                case SETUP:
                    /*
                case STARTURN:

                        At the start of each round, check to see if one of the teems is dead.

                    boolean friendlyTeamAlive = false;
                    boolean opposingTeamAlive = false;
                    for(Fighter f : aliveFighters){
                        if (f.getTeam() == FighterTeam.ENEMY){
                            opposingTeamAlive = true;
                        }
                        else{
                            friendlyTeamAlive = true;
                        }
                    }


                    if(!friendlyTeamAlive || !opposingTeamAlive){
                        runGame = false;
                    }

                    state = GameState.PLAYERTURN;
                    break;
                    */

                case PLAYERTURN:
                    state = GameState.ALLYTURN;
                    break;
                case ALLYTURN:
                    for(Fighter f : activeFighters){
                        if(f.getTeam() == FighterTeam.ALLIED){
                            CpuInput.doTurn(f, MAP, activeFighters, frame);
                        }
                    }
                    state = GameState.ENEMYTURN;
                    break;
                case ENEMYTURN:
                    for(Fighter f : activeFighters){
                        if(f.getTeam() == FighterTeam.ENEMY){
                            CpuInput.doTurn(f, MAP, Collections.unmodifiableList(activeFighters), frame);
                        }
                    }
                    state = GameState.PLAYERTURN;
                    break;
                default:
                    assert (false);
                    break;
            }

            boolean friendlyTeamAlive = false;
            boolean opposingTeamAlive = false;
            for(Fighter f : activeFighters){
                if (f.getTeam() == FighterTeam.ENEMY){
                    opposingTeamAlive = true;
                }
                else{
                    friendlyTeamAlive = true;
                }
            }

            if(!friendlyTeamAlive || !opposingTeamAlive){
                runGame = false;
            }
        }
    }

    public void drawStuff(){
        String strIn = "XXXXXXXXXX" +
                       "X        X" +
                       "X   V    X" +
                       "X   VV   X" +
                       "X  VVV   X" +
                       "X        X" +
                       "XXXXXXXXXX";

        Map mapIn = new Map(strIn);

        Fighter fighterIn1 = new Fighter(FighterType.SMALLTEST);
        fighterIn1.reset(new Coord(2, 2), mapIn);

        Fighter fighterIn2 = new Fighter(FighterType.FRIENDTEST);
        fighterIn2.reset(new Coord(2, 1), mapIn);

        Fighter fighterIn3 = new Fighter(FighterType.TALLTEST);
        fighterIn3.reset(new Coord(2, 3), mapIn);

        ArrayList<Fighter> listIn = new ArrayList<>();
        listIn.add(fighterIn1);
        listIn.add(fighterIn2);
        listIn.add(fighterIn3);

        MapFrame frameIn = new MapFrame(mapIn, listIn);

        frameIn.drawSelf();
    }
}
