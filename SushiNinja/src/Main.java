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

            Fighter fighterIn1 = new Fighter(FighterType.SOUL);
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
                            "  X       " +
                            " VXXXX    " +
                            " V        " +
                            "   VVVV  X" +
                            "X        X" +
                            "XX      XX" +
                            "XXXX  XXXX";

            Map mapIn = new Map(strIn);
            Fighter fighterIn1 = new Fighter(FighterType.SOUL);
            fighterIn1.placeOnMap(new Coord(3, 0), mapIn);

            Fighter fighterIn2 = new Fighter(FighterType.SPIRITFLAME);
            fighterIn2.placeOnMap(new Coord(1, 0), mapIn);

            Fighter fighterIn3 = new Fighter(FighterType.FLAME);
            fighterIn3.placeOnMap(new Coord(2, 5), mapIn);

            Fighter fighterIn4 = new Fighter(FighterType.FLAME);
            fighterIn4.placeOnMap(new Coord(7, 5), mapIn);

            Fighter fighterIn5 = new Fighter(FighterType.FLAME);
            fighterIn5.placeOnMap(new Coord(0, 0), mapIn);

            Fighter fighterIn6 = new Fighter(FighterType.UNI);
            fighterIn6.placeOnMap(new Coord(4, 4), mapIn);

            Fighter fighterIn7 = new Fighter(FighterType.DUA);
            fighterIn7.placeOnMap(new Coord(5, 4), mapIn);

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
                            "          " +
                            " V      V " +
                            " VX    XV " +
                            "VVX    XVV" +
                            " VX    XV " +
                            " V      V " +
                            "          ";

            Map mapIn = new Map(strIn);

            Fighter fighterIn1 = new Fighter(FighterType.SOUL);
            fighterIn1.placeOnMap(new Coord(3, 3), mapIn);

            Fighter fighterIn2 = new Fighter(FighterType.SPIRITFLAME);
            fighterIn2.placeOnMap(new Coord(0, 2), mapIn);

            Fighter fighterIn3 = new Fighter(FighterType.SPIRITFLAME);
            fighterIn3.placeOnMap(new Coord(9, 2), mapIn);

            Fighter fighterIn4 = new Fighter(FighterType.SPIRITFLAME);
            fighterIn4.placeOnMap(new Coord(0, 4), mapIn);

            Fighter fighterIn5 = new Fighter(FighterType.SPIRITFLAME);
            fighterIn5.placeOnMap(new Coord(9, 4), mapIn);

            Fighter fighterIn6 = new Fighter(FighterType.UNI);
            fighterIn6.placeOnMap(new Coord(6, 3), mapIn);

            Fighter fighterIn7 = new Fighter(FighterType.DUA);
            fighterIn7.placeOnMap(new Coord(4, 3), mapIn);

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
    }
}
