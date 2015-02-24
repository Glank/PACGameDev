package org.pac.games.gui;
import org.pac.games.physics.Rectangle;
import org.pac.games.physics.Bouncer;
import java.awt.Graphics;

public class DrawUtils{
    public static void fillRectangle(Graphics g, Rectangle r){
        g.fillRect(
            (int)r.leftX(), (int)r.topY(),
            (int)r.width()+1, (int)r.height()+1
        );
    }
    public static void fillRectangle(Graphics g, Bouncer b){
        fillRectangle(g,b.getBounds());
    }
}
