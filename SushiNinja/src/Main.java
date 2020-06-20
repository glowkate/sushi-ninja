import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;


/*
    The main class. Organises everything before the game starts such as making the maps and fighters
    used in the game.
 */
public class Main {
    public static void main(String[] args){

        //JFrame f = new JFrame("Mouse Test");

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // Register the CallBack Interface class (below)
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //f.addMouseListener(new PlayerInput() );

        //f.setSize(300, 300);
        //f.setVisible(true);


        Scanner s = new Scanner(System.in);
        String userInput = "";

        System.out.println("<Soul> Hey, hey! This thing working?");
        System.out.println("<Soul> Okay cool, so uh, could you set up the sim?");
        System.out.println("<Soul> There's like, a handful of sims, just pick one.");
        System.out.println("<Soul> Should be 3 scenarios to choose from.");

        while(!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3")) {
            System.out.println("What scenario do you select?");
            System.out.println("[1] [2] [3]");
            userInput = s.nextLine();
            //System.out.println(userInput != "1");
            if(!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3")){
                System.out.println("The computer spits out an error. Please try again.");
            }
        }

        if(userInput.equals("1")) {
            String strIn =
                    "          " +
                            " XVX  XVX " +
                            "V        V" +
                            "  V    V  " +
                            " X      X " +
                            " XXXXXXXX " +
                            "          ";

            Map mapIn = new Map(strIn);

            Fighter fighterIn1 = new Fighter(FighterType.SUSHI);
            fighterIn1.placeOnMap(new Coord(5, 6), mapIn);

            Fighter fighterIn2 = new Fighter(FighterType.SPIRITFLAME);
            fighterIn2.placeOnMap(new Coord(0, 1), mapIn);

            Fighter fighterIn3 = new Fighter(FighterType.SPIRITFLAME);
            fighterIn3.placeOnMap(new Coord(9, 1), mapIn);

            ArrayList<Fighter> listIn = new ArrayList<>();
            listIn.add(fighterIn1);
            listIn.add(fighterIn2);
            listIn.add(fighterIn3);

            GameWorld gw = new GameWorld(mapIn, listIn);
            gw.runGame();
        }
        else if (userInput.equals("2")){
            String strIn =
                            "          " +
                            "XXX    XXX" +
                            "          " +
                            "   XXXX   " +
                            "          " +
                            "VV  VV  VV" +
                            "          ";

            Map mapIn = new Map(strIn);
            Fighter fighterIn1 = new Fighter(FighterType.SPIRITFLAME);
            fighterIn1.placeOnMap(new Coord(0, 6), mapIn);

            Fighter fighterIn2 = new Fighter(FighterType.SPIRITFLAME);
            fighterIn2.placeOnMap(new Coord(9, 6), mapIn);

            Fighter fighterIn3 = new Fighter(FighterType.FLAME);
            fighterIn3.placeOnMap(new Coord(2, 2), mapIn);

            Fighter fighterIn4 = new Fighter(FighterType.FLAME);
            fighterIn4.placeOnMap(new Coord(7, 2), mapIn);

            Fighter fighterIn5 = new Fighter(FighterType.SUSHI);
            fighterIn5.placeOnMap(new Coord(2, 0), mapIn);

            Fighter fighterIn6 = new Fighter(FighterType.EGG);
            fighterIn6.placeOnMap(new Coord(9, 0), mapIn);

            Fighter fighterIn7 = new Fighter(FighterType.TEMPURA);
            fighterIn7.placeOnMap(new Coord(0, 0), mapIn);

            ArrayList<Fighter> listIn = new ArrayList<>();
            listIn.add(fighterIn1);
            listIn.add(fighterIn2);
            listIn.add(fighterIn3);
            listIn.add(fighterIn4);
            listIn.add(fighterIn5);
            listIn.add(fighterIn6);
            listIn.add(fighterIn7);

            GameWorld gw = new GameWorld(mapIn, listIn);
            gw.runGame();

        }
        else if(userInput.equals("3")){
            String strIn =
                            "V       VV" +
                            "  VVVV   V" +
                            "  XXXXXX  " +
                            "    VV    " +
                            "V  XXXX  V" +
                            "X XXXXXX X" +
                            "X   VV   X";

            Map mapIn = new Map(strIn);

            Fighter fighterIn1 = new Fighter(FighterType.UNI);
            fighterIn1.placeOnMap(new Coord(3, 6), mapIn);

            Fighter fighterIn2 = new Fighter(FighterType.DUA);
            fighterIn2.placeOnMap(new Coord(6, 6), mapIn);

            Fighter fighterIn3 = new Fighter(FighterType.SPIRITFLAME);
            fighterIn3.placeOnMap(new Coord(6, 3), mapIn);

            Fighter fighterIn4 = new Fighter(FighterType.SPIRITFLAME);
            fighterIn4.placeOnMap(new Coord(3, 3), mapIn);

            Fighter fighterIn5 = new Fighter(FighterType.SPIRITFLAME);
            fighterIn5.placeOnMap(new Coord(7, 4), mapIn);

            Fighter fighterIn6 = new Fighter(FighterType.SPIRITFLAME);
            fighterIn6.placeOnMap(new Coord(2, 4), mapIn);

            Fighter fighterIn7 = new Fighter(FighterType.EGG);
            fighterIn7.placeOnMap(new Coord(4, 0), mapIn);

            Fighter fighterIn8 = new Fighter(FighterType.TEMPURA);
            fighterIn8.placeOnMap(new Coord(5, 0), mapIn);

            Fighter fighterIn9 = new Fighter(FighterType.FLAME);
            fighterIn9.placeOnMap(new Coord(0, 2), mapIn);

            Fighter fighterIn10 = new Fighter(FighterType.FLAME);
            fighterIn10.placeOnMap(new Coord(9, 2), mapIn);

            Fighter fighterIn11 = new Fighter(FighterType.FLAME);
            fighterIn11.placeOnMap(new Coord(2, 6), mapIn);

            Fighter fighterIn12 = new Fighter(FighterType.FLAME);
            fighterIn12.placeOnMap(new Coord(7, 6), mapIn);

            ArrayList<Fighter> listIn = new ArrayList<>();
            listIn.add(fighterIn1);
            listIn.add(fighterIn2);
            listIn.add(fighterIn3);
            listIn.add(fighterIn4);
            listIn.add(fighterIn5);
            listIn.add(fighterIn6);
            listIn.add(fighterIn7);
            listIn.add(fighterIn8);
            listIn.add(fighterIn9);
            listIn.add(fighterIn10);
            listIn.add(fighterIn11);
            listIn.add(fighterIn12);

            GameWorld gw = new GameWorld(mapIn, listIn);
            gw.runGame();
        }
    }
}
