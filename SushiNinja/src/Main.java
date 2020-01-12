/*
    The main class. Starts the program.
 */
//package com.techub.exeute;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        String strIn =
                "     X    " +
                "     X    " +
                "   X X    " +
                "XX X XXX X" +
                "  X  X   X" +
                "         X" +
                "XXXXXXXXXX";

        Map mapIn = new Map(strIn);

        Fighter fighterIn1 = new Fighter(FighterType.SMALLTEST);
        fighterIn1.reset(new Coord(6, 0), mapIn);

        Fighter fighterIn2 = new Fighter(FighterType.FRIENDTEST);
        fighterIn2.reset(new Coord(0, 0), mapIn);

        Fighter fighterIn3 = new Fighter(FighterType.TALLTEST);
        fighterIn3.reset(new Coord(7, 0), mapIn);

        ArrayList<Fighter> listIn = new ArrayList<>();
        listIn.add(fighterIn1);
        listIn.add(fighterIn2);
        listIn.add(fighterIn3);

        GameWorld gw = new GameWorld(mapIn, listIn);
        gw.runGame();
    }
}
