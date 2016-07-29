
package Game;

import Objects.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.TimerTask;

public class SinglePlayer extends Menu implements BallFeedBack {

    private Ball myBall;
    private Paddle leftPad, rightPad;
    boolean bRightUp = false, bRightDown = false, inPlay = false;
    private int timeLeft = 3;
    private Dimension frame;

    private int leftScore, rightScore;

    public SinglePlayer(Dimension d) {
        super(d);
        frame = d;
        leftPad = new Paddle(frame, false);
        rightPad = new Paddle(frame, true);
        countDown(5);
    }

    public void logic() {
        if (inPlay) {
            myBall.move();
            myBall.bounce(leftPad, rightPad);
            if (myBall.getRect().x < frame.width / 7 && !myBall.getIsRight()) {
                if (myBall.getRect().y + (myBall.getRect().height / 2) < leftPad.getRect().y + (leftPad.getRect().height / 2))
                    leftPad.moveUp();
                if (myBall.getRect().y + (myBall.getRect().height / 2) > leftPad.getRect().y + (leftPad.getRect().height / 2))
                    leftPad.moveDown();
            }
        }

        if (bRightUp) {
            rightPad.moveUp();
        } else if (bRightDown) {
            rightPad.moveDown();
        }
    }

    public void draw(Graphics2D g2) {
        if (inPlay)
            myBall.draw(g2);
        leftPad.draw(g2);
        rightPad.draw(g2);

        g2.setFont(new Font("Calibri", Font.BOLD, 21));
        g2.setColor(Color.white);
        if (!inPlay) {
            g2.drawString("" + timeLeft, (frame.width / 2) - (g2.getFontMetrics().stringWidth("" + timeLeft) / 2), (frame.height / 2) - g2.getFontMetrics().getHeight());
        } else {
            g2.drawString("" + leftScore, (frame.width / 2) - g2.getFontMetrics().stringWidth("" + leftScore), 20 + g2.getFontMetrics().getHeight());
            g2.drawString("" + rightScore, (frame.width / 2) + g2.getFontMetrics().stringWidth("" + rightScore), 20 + g2.getFontMetrics().getHeight());
        }
        drawString("press \"q\" to exit", "#3B3B3B", 12, 11, g2);
    }

    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();

        if ((key == 'k') || (key == 'K')) {
            bRightUp = true;
        } else if ((key == 'm') || (key == 'M')) {
            bRightDown = true;
        }
        if((key == 'q' || key == 'Q'))
            notifyChangeMenu(new MainMenu(frame));
    }

    public void keyReleased(KeyEvent e) {
        char key = e.getKeyChar();

        if ((key == 'k') || (key == 'K')) {
            bRightUp = false;
        } else if ((key == 'm') || (key == 'M')) {
            bRightDown = false;
        }
    }

    private void createBall() {
        myBall = new Ball(frame);
        myBall.notifyFeedBack(this);
    }

    private void countDown(int time) {
        timeLeft = time + 1;
        createBall();
        new java.util.Timer().schedule(new TimerTask() {
            public void run() {
                timeLeft--;
                if (timeLeft == 0) {
                    inPlay = true;
                    this.cancel();
                }
            }
        }, 0, 1000);
    }

    public void hitWall(boolean side) {
        inPlay = false;
        if (!side)
            rightScore++;
        else
            leftScore++;
        countDown(3);
    }
}
