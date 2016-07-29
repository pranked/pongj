/**
 * @(#)Pong.java
 *
 * Sample Applet application
 *
 * @author
 * @version 1.00 06/11/02
 */

import Game.MainMenu;
import Game.Menu;
import Game.MenuFeedBack;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;


public class Pong extends Applet implements Runnable {
    private Thread animation;  //declares a thread called animation
    private static final int REFRESH_RATE = 10;  //Constant for “delay”
    private Image finalize;
    private Menu currentMenu;

    //declare other class data members

    public void init() {
        //initialize class data members
        currentMenu = new MainMenu(this.getSize());
        setupMenu();
        setBackground(Color.decode("#1E1E1E"));
        this.requestFocus();
    }

    private void setupMenu()
    {
        addKeyListener(currentMenu);
        addMouseListener(currentMenu);
        currentMenu.addMenuFeedBack(new MenuFeedBack() {
            @Override
            public void changeMenu(Menu newMenu) {
                if(currentMenu != null) {
                    removeKeyListener(currentMenu);
                    removeMouseListener(currentMenu);
                    currentMenu.dispose();
                }
                currentMenu = newMenu;
                setupMenu();
            }
        });
    }

    public void paint(Graphics g) {
        //painting
        g.drawImage(finalize, 0, 0, this);
    }

    public void update(Graphics g) {
        Image frameImage = createImage(this.getWidth(), this.getHeight());
        Graphics2D g2d = (Graphics2D)frameImage.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        currentMenu.draw(g2d);
        currentMenu.drawClckables(g2d);

        finalize = createImage(this.getWidth(), this.getHeight());
        Graphics bG = finalize.getGraphics();
        bG.drawImage(frameImage, 0,0, this.getWidth(), this.getHeight(), this);
        bG.dispose();
        paint(g);
    }

    public void start() {
        animation = new Thread(this);
        animation.start();
    }

    public void run() {
        repaint();
        while (true) {
            currentMenu.logic();
            repaint();
            try {
                Thread.sleep(REFRESH_RATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        animation = null;
    }
}
