import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/*
    This class will be in charge of creating and organising all other classes.
    It contains the main game loop and controls the initialisation of the graphics.

    JFrame gameFrame - The object in charge of drawing the image. Takes in a MapFrame
    GameState state - state records what the game is suppose to be doing right now, whether
        setup or player turn or enemy turn.
    final Map MAP - The map that this game is running on. Created at init and given as
        input to a bunch of classes and methods.
    ArrayList<Fighter> activeFighters - A list of every Fighter that's currently
        on the field. Includes both living fighters and dead fighters.
    MapFrame frame - The MapFrame used as input to gameFrame. Passed to other methods in order
        to allow the graphics to be updated.
 */

public class GameWorld{
    JFrame gameFrame;
    GameState state;
    final Map MAP;
    ArrayList<Fighter> activeFighters;
    MapFrame frame;

    public GameWorld(final Map mapInit, final ArrayList<Fighter> fightersInit){
        gameFrame = new JFrame();
        PlayerInput pIn = new PlayerInput();
        gameFrame.addMouseListener(pIn);
        state = GameState.PLAYERTURN;
        MAP = mapInit;
        activeFighters = fightersInit;
        frame = new MapFrame(MAP, activeFighters);

        gameFrame.setPreferredSize(new Dimension(960, 672));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
        Runs the game after setup. Turn order goes as follows
        Pause for a second
        Player's turn
        Check to see if one side has won or not
        Pause for a second
        Ally's turn
        Check to see if one side has won or not
        Pause for a second
        Enemy's turn
        Check to see if one side has won or not
        Repeat

        The turns are represented by states in the switch statement.
        Pause for a second and Checking for a win is done outside of
        the switch statement but still inside the loop.
     */
    public void runGame(){
        boolean runGame = true;
        PlayerInput input = new PlayerInput();
        while(runGame){
            try {
                Thread.sleep(1000);
            }
            catch (Exception e){
            }
            switch (state){
                case PLAYERTURN:
                    input.doTurn(MAP, activeFighters, frame);
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
                            CpuInput.doTurn(f, MAP, activeFighters, frame);
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
                if (f.getState() == FighterState.ALIVE && f.getTeam() == FighterTeam.ENEMY){
                    opposingTeamAlive = true;
                }
                else if (f.getState() == FighterState.ALIVE){
                    friendlyTeamAlive = true;
                }
            }

            if(!friendlyTeamAlive || !opposingTeamAlive){
                runGame = false;
            }
        }
    }
}