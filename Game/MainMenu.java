package Game;

import Objects.Ball;
import Objects.Clickable;
import Objects.ClickableFeedBack;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Dominic on 12/5/2014.
 */
public class MainMenu extends Menu {
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    private Timer timer;

    public MainMenu(Dimension d) {
        super(d);
        int size = 250;
        this.addClickable(new Clickable("Single Player", new Rectangle((frame.width / 2) - size / 2, 120, size, 30), new ClickableFeedBack() {
            public void run() {
                timer.cancel();
                timer.purge();
                notifyChangeMenu(new SinglePlayer(frame));
            }
        }));

        this.addClickable(new Clickable("Multi Player", new Rectangle((frame.width / 2) - size / 2, 160, size, 30), new ClickableFeedBack() {
            public void run() {
                timer.cancel();
                timer.purge();
                notifyChangeMenu(new MultiPlayer(frame));
            }
        }));

        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (balls.size() < 50)
                    balls.add(new Ball(frame));
                else
                    this.cancel();
            }
        }, 0, 1500);
    }

    public void draw(Graphics2D g2) {
        drawString("PongJ", "#ffffff", 90, 35, g2);
        drawString("written by dominic", "#3B3B3B", 110, 11, g2);
        for(Ball b : balls) {
            b.draw(g2);
        }
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'a')
            balls.add(new Ball(frame));
    }

    public void logic() {
        for(Ball b : balls) {
            b.move();
            b.bounce();
        }
    }
}
