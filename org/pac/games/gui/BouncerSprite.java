package org.pac.games.gui;

import org.pac.games.physics.Bouncer;
import org.pac.games.physics.MovingRectangle;
import org.pac.games.GamePainter;
import java.awt.Color;
import java.awt.Graphics;

public class BouncerSprite extends Bouncer{
    private Color color = Color.BLACK;
    public BouncerSprite(
        MovingRectangle bounds, double mass, 
        int onXBounce, int onYBounce
    ){
        super(bounds, mass, onXBounce, onYBounce);
    }
    public BouncerSprite(MovingRectangle bounds, double mass){
        super(bounds, mass);
    }
    public BouncerSprite(MovingRectangle bounds){
        super(bounds);
    }
    public BouncerSprite(MovingRectangle bounds, int onXBounce, int onYBounce){
        super(bounds, onXBounce, onYBounce);
    }
    public BouncerSprite(){
        super();
    }
    public void setColor(Color color){
        this.color = color;
    }
    public Color getColor(){
        return color;
    }
    public void paint(Graphics g){
        g.setColor(color);
        DrawUtils.fillRectangle(g, this);
    }
}
