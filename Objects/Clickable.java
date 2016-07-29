package Objects;

import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by Dominic on 12/5/2014.
 */
public class Clickable {

    private Rectangle rect;
    private String label;
    private ClickableFeedBack clickableFeedBack;

    public Clickable(String text, Rectangle r, @Nullable ClickableFeedBack feedBack) {
        label = text;
        rect = r;
        clickableFeedBack = feedBack;
    }

    public Clickable(String text, Point p, @Nullable ClickableFeedBack feedBack) {
        label = text;
        rect = new Rectangle(p);
        clickableFeedBack = feedBack;
    }

    public void draw(Graphics2D g2) {
        g2.setFont(new Font("Calibri", Font.BOLD, 15));
        if (rect.width <= 0) {
            rect.width = g2.getFontMetrics().stringWidth(label) + 20;
            rect.height = g2.getFontMetrics().getHeight() + 4;
        }
        g2.setColor(Color.decode("#DB5225"));
        g2.fill(new RoundRectangle2D.Double(rect.x, rect.y, rect.width, rect.height + 3, 5, 5));
        g2.setColor(Color.decode("#EE5A2A"));
        g2.fill(new RoundRectangle2D.Double(rect.x, rect.y, rect.width, rect.height, 5, 5));
        g2.setColor(Color.white);
        g2.drawString(label, (rect.x + (rect.width / 2)) - (g2.getFontMetrics().stringWidth(label) / 2), (rect.y + (rect.height / 2) + g2.getFontMetrics().getAscent() / 2));
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String la) {
        label = la;
    }

    public void onClick(ClickableFeedBack cfb) {
        clickableFeedBack = cfb;
    }

    private void notifyFeedBack() {
        if(clickableFeedBack != null)
            clickableFeedBack.run();
    }

    public void wasClicked(Point p) {
        if (rect.contains(p))
            notifyFeedBack();
    }

    public boolean equals(Object obj) {
        return obj instanceof Clickable && (this == obj || (this.label.equals(((Clickable) obj).label) && this.rect.equals(((Clickable) obj).rect)));
    }
}
