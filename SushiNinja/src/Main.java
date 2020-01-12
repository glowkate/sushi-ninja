/*
    The main class. Starts the program.
 */
//package com.techub.exeute;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        String strIn =
                "XXXXXXXXXX" +
                "X        X" +
                "X   V    X" +
                "X   VV   X" +
                "X  VVV   X" +
                "X        X" +
                "XXXXXXXXXX";

        Map mapIn = new Map(strIn);

        Fighter fighterIn1 = new Fighter(FighterType.SMALLTEST);
        fighterIn1.reset(new Coord(6, 4), mapIn);

        Fighter fighterIn2 = new Fighter(FighterType.FRIENDTEST);
        fighterIn2.reset(new Coord(0, 0), mapIn);

        //Fighter fighterIn3 = new Fighter(FighterType.TALLTEST);
        //fighterIn3.reset(new Coord(2, 3), mapIn);

        ArrayList<Fighter> listIn = new ArrayList<>();
        listIn.add(fighterIn1);
        listIn.add(fighterIn2);
        //listIn.add(fighterIn3);

        GameWorld gw = new GameWorld(new Map(), listIn);
        gw.runGame();
    }
}
