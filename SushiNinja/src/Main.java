/*
    The main class. Starts the program.
 */
//package com.techub.exeute;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

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


        String strIn =
                "          " +
                " XVX  XVX " +
                "V        V" +
                "  V    V  " +
                "          " +
                " XXXXXXXX " +
                "          ";

        Map mapIn = new Map(strIn);

        Fighter fighterIn1 = new Fighter(FighterType.SMALLTEST);
        fighterIn1.placeOnMap(new Coord(6, 0), mapIn);

        Fighter fighterIn2 = new Fighter(FighterType.FRIENDTEST);
        fighterIn2.placeOnMap(new Coord(0, 0), mapIn);

        Fighter fighterIn3 = new Fighter(FighterType.TALLTEST);
        fighterIn3.placeOnMap(new Coord(7, 0), mapIn);

        ArrayList<Fighter> listIn = new ArrayList<>();
        listIn.add(fighterIn1);
        listIn.add(fighterIn2);
        listIn.add(fighterIn3);

        GameWorld gw = new GameWorld(mapIn, listIn);
        gw.runGame();
    }
}
