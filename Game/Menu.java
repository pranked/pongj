package Game;

import Objects.Clickable;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Dominic on 12/5/2014.
 */
public class Menu implements KeyListener, MouseListener {
    public Dimension frame;

    private MenuFeedBack feedBack;

    public Menu(Dimension d)
    {
        frame = d;
    }

    private ArrayList<Clickable> clickables = new ArrayList<Clickable>();

    public void logic() {

    }

    public void draw(Graphics2D g2) {

    }

    public void drawClckables(Graphics2D g2) {
        for(Clickable click : clickables) {
            click.draw(g2);
        }
    }

    public void addClickable(Clickable cl) {
        clickables.add(cl);
    }

    public void removeClickable(Clickable cl) {
        clickables.remove(cl);
    }

    public void addMenuFeedBack(MenuFeedBack m)
    {
        feedBack = m;
    }

    public void notifyChangeMenu(Menu m) {
        if(feedBack != null)
            feedBack.changeMenu(m);
    }

    public void drawString(String text, String color, int x, int y, int size, Graphics2D g2) {
        g2.setFont(new Font("Calibri", Font.BOLD, size));
        g2.setColor(Color.decode(color));
        g2.drawString(text, x, y);
    }

    public void drawString(String text, String color, int y, int size, Graphics2D g2) {
        g2.setFont(new Font("Calibri", Font.BOLD, size));
        drawString(text, color, (frame.width / 2) - (g2.getFontMetrics().stringWidth(text) / 2), y, size, g2);
    }

    public void dispose() {
        feedBack = null;
        //clickables.clear();
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
        for(Clickable click : clickables) {
            click.wasClicked(e.getPoint());
        }
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
