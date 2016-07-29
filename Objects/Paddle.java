package Objects;

import java.awt.*;

public class Paddle {
    private int speed;

    private Dimension frame;
    private Rectangle rect;

    public Paddle(Dimension fr, boolean opp) {
        frame = fr;
        speed = 3;
        int width = 5, height = 36;
        rect = new Rectangle(!opp ? 5 : frame.width - (5 + width), ((frame.height / 2) - height), width, height);
    }

    public void moveUp() {
        if (rect.y > 0)
            rect.setLocation(rect.x, rect.y - speed);
    }

    public void moveDown() {
        if (rect.y + rect.height < frame.height)
            rect.setLocation(rect.x, rect.y + speed);
    }

    public void draw(Graphics g) {
        g.setColor(Color.decode("#EE5A2A"));
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }

    public Rectangle getRect()
    {
        return rect;
    }

    public boolean collision(Rectangle col) {
        return ((col.width + col.x < col.x || col.width + col.x > rect.x) &&
                (col.height + col.y < col.y || col.height + col.y > rect.y) &&
                (rect.width + rect.x < rect.x || rect.width + rect.x > col.x) &&
                (rect.height + rect.y < rect.y || rect.height + rect.y > col.y));
    }
}
