package server;

import java.awt.*;
import javax.swing.*;
import server.utils.Utils;

public class FrontEnd {

    public static int lightSet;
    public static int tests;
                
    public static void main(String[] args) throws InterruptedException, Exception {

        JFrame frame = initializeFrame();
        Light l1 = new Light();
        frame.add(l1);
        tests = Utils.adjustments();

        while (true) {
            l1.incrementValue();
            Thread.sleep(500);
            l1.swapEm();
        }

    }

    private static JFrame initializeFrame() {
        JFrame frame = new JFrame();
        frame.setTitle("Traffic Light");
        frame.setSize(250, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return frame;
    }


static class Light extends JPanel {

    private static final long serialVersionUID = 1L;
    private int iValue = 0;
    private int iDistance=90;
    private int topLightHeight=10;
    private Color iRed = Color.red;
    private Color iYellow = Color.black;
    private Color iGreen = Color.black;

    Light() {
    }

    public void paintComponent(Graphics g) {
        defineLight(g);
    }

    public int getValue() {
        return this.iValue;
    }

    public void setLighDistance(int distance) {
        this.iDistance = distance;
    }

    public void incrementValue() {
        if(tests == 10){
            this.iRed = Color.black;
            this.iGreen = Color.GREEN;
            this.iValue = 15;
            tests = 0;
        }else{
        if (this.iValue == 35) {
            this.iValue = -1;
        }
        }
        this.iValue = this.iValue + 1;
    }

    public void swapEm() {
        if (this.iValue == 0) {
            this.iYellow = Color.black;
            this.iRed = Color.red;
        }
        if (this.iValue == 15) {
            this.iRed = Color.black;
            this.iYellow = Color.black;
            this.iGreen = Color.green;
        }
        if (this.iValue == 30) {
            this.iGreen = Color.black;
            this.iYellow = Color.yellow;
        }
        repaint();
    }

    
    
    public void defineLight(Graphics g) {
        g.setColor(this.iRed);
        g.fillRoundRect(80, this.topLightHeight, 80, 80, 70, 70);
        g.setColor(this.iYellow);
        g.fillRoundRect(80, this.topLightHeight+this.iDistance, 80, 80, 70, 70);
        g.setColor(this.iGreen);
        g.fillRoundRect(80, this.topLightHeight+2*this.iDistance, 80, 80, 70, 70);
    }
    }
}