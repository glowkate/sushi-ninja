import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/*
    This class will be in charge of creating and organising all other classes.
    It contains the main game loop. (er...it will)
 */

public class GameWorld{
    JFrame gameFrame;

    public GameWorld(){
        gameFrame = new JFrame();

        gameFrame.setPreferredSize(new Dimension(960, 672));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void drawStuff(){
        String strIn = "XXXXXXXXXX" +
                       "X        X" +
                       "X   V    X" +
                       "X   VV   X" +
                       "X  VVV   X" +
                       "X        X" +
                       "XXXXXXXXXX";
        Fighter fighterIn1 = new Fighter(FighterType.SMALLTEST);
        fighterIn1.reset(new Coord(2, 2));

        Fighter fighterIn2 = new Fighter(FighterType.FRIENDTEST);
        fighterIn2.reset(new Coord(2, 1));

        Fighter fighterIn3 = new Fighter(FighterType.TALLTEST);
        fighterIn3.reset(new Coord(2, 3));

        ArrayList<Fighter> listIn = new ArrayList<>();
        listIn.add(fighterIn1);
        listIn.add(fighterIn2);
        listIn.add(fighterIn3);

        Map mapIn = new Map(strIn);

        MapFrame frameIn = new MapFrame(mapIn);
        frameIn.setActive(listIn);

        gameFrame.add(frameIn);
        gameFrame.pack();
        gameFrame.setVisible(true);
    }
}
