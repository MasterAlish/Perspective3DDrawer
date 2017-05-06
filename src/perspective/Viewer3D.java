package perspective;

import perspective.components.Panel3D;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;

/**
 * Created by master Alish on 5/6/17.
 */
public class Viewer3D extends JFrame {
    Panel3D mainPanel = new Panel3D();
    java.util.Timer timer = new Timer();

    public Viewer3D(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainPanel.setBackground(Color.BLACK);
        setContentPane(mainPanel);
        setSize(600, 480);
    }

    public void open(){
        setVisible(true);
    }

    public static void main(String[] args) {
        Viewer3D viewer = new Viewer3D();
        viewer.open();
        viewer.startTimer();
    }

    private void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mainPanel.tick();
            }
        }, 0, 10);
    }
}
