package Objects;

import java.awt.*;
import java.util.Random;

public class Ball {
    private boolean isUp, isRight, outOfBounds;
    private int speedVert, speedLat;

    private Rectangle rect;
    private Dimension frame;
    private BallFeedBack feedBack;

    public Ball(Dimension fr) {
        frame = fr;
        speedVert = 1;
        speedLat = 1;
        isUp = randomBoolean();
        isRight = randomBoolean();
        rect = new Rectangle(frame.width / 2, 100 + new Random().nextInt(frame.height - 100), 10, 10);
    }
    public void move() {
        if (outOfBounds)
            return;
        rect.setLocation(rect.x + (isRight ? speedLat : -1 * speedLat), rect.y + (isUp ? -1 * speedVert : speedVert));
    }

    public void bounce() {
        if (outOfBounds)
            return;
        if (rect.y <= 0)
            isUp = false;
        if (rect.y + rect.height >= frame.height)
            isUp = true;
        if (rect.x <= 0)
            randomize();
        if (rect.x + rect.width >= frame.width)
            randomize();
    }

    public void bounce(Paddle lp, Paddle rp) {
        if (outOfBounds)
            return;
        if (rect.y <= 0)
            isUp = false;
        if (rect.y + rect.height >= frame.height)
            isUp = true;
        if (rect.x <= 0)
            notifyFeedBack();
        if (rect.x + rect.width >= frame.width)
            notifyFeedBack();
        Rectangle predictive = new Rectangle(rect.x + (isRight ? speedLat : -1 * speedLat), rect.y + (isUp ? -1 * speedVert : speedVert), rect.width, rect.height);
        if (lp.collision(rect) || rp.collision(rect) || lp.collision(predictive)) {
            randomize();
        }
    }

    private void randomize() {
        isRight = !isRight;
        isUp = randomBoolean();
        int ran = new Random().nextInt(100);
        if (ran > 95) {
            speedVert = 3;
            speedLat = 3;
        } else if (ran > 70) {
            speedVert = 2;
            speedLat = 2;
        } else if (ran > 60) {
            speedVert = 1;
            speedLat = 2;
        } else if (ran > 30) {
            speedVert = 1;
            speedLat = 2;
        } else {
            speedLat = 1;
            speedVert = 1;
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g2.setColor(Color.white);
        g2.fillOval(rect.x, rect.y, rect.width, rect.height);
    }

    public void notifyFeedBack(BallFeedBack b) {
        feedBack = b;
    }

    private void notifyFeedBack() {
        outOfBounds = true;
        if (feedBack != null)
            feedBack.hitWall(isRight);
    }

    private boolean randomBoolean() {
        return new Random().nextInt(10) < 5;
    }

    public Rectangle getRect() {
        return rect;
    }

    public boolean getIsRight(){
        return isRight;
    }
}
