/*
    The main class. Starts the program.
 */
//package com.techub.exeute;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/*
class MyPanel extends JPanel {

    private BufferedImage i;

    public MyPanel() {
        try {
            i = ImageIO.read(new File("data/placeholderWall.png"));
        } catch (IOException e) {
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(i, 0, 0, null);
        g.drawImage(i, 96, 0, null);
    }
};
*/
public class Main {
    public static void main(String[] args){
        /*
        JFrame frame = new JFrame();

        frame.setPreferredSize(new Dimension(960, 672));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new MyPanel());
        frame.pack();
        frame.setVisible(true);
        */


        GameWorld gw = new GameWorld();
        gw.drawStuff();
    }
}
