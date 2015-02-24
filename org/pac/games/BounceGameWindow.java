package org.pac.games;

import org.pac.games.gui.BouncerSprite;
import org.pac.games.physics.BounceEngine;
import org.pac.games.physics.Bouncer;
import org.pac.games.physics.CollisionListener;
import org.pac.games.physics.CollisionInstance;
import org.pac.games.physics.MovingRectangle;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class BounceGameWindow extends SimpleGameWindow
    implements CollisionListener{
    private static final long serialVersionUID = 34587892834L;
    private BounceEngine engine;
    private Color background = Color.WHITE;
    private LinkedList<BouncerSprite> sprites = 
        new LinkedList<BouncerSprite>();
    public BounceGameWindow(BounceEngine engine){
        super();
        this.engine = engine;
        addUpdater(engine);
        engine.addListener(this);
        //put a sprite painter before this painter
        assert(getComponent().removePainter(this));
        getComponent().addPainter(
            new GamePainter(){
                public void paint(Graphics g){
                    g.setColor(background);
                    g.fillRect(0,0,getDisplayWidth(),getDisplayHeight());
                    for(BouncerSprite sprite:sprites)
                        sprite.paint(g);
                }
            }
        );
        getComponent().addPainter(this);
    }
    public BounceGameWindow(){
        this(new BounceEngine());
    }
    public void addSprite(BouncerSprite sprite){
        engine.add(sprite);
        sprites.add(sprite);
    }
    public boolean removeSprite(BouncerSprite sprite){
        engine.remove(sprite);
        return sprites.remove(sprite);
    }
    public void add(MovingRectangle rectangle){
        engine.add(rectangle);
    }
    public void remove(MovingRectangle rectangle){
        engine.remove(rectangle);
    }
    public void clear(){
        sprites.clear();
        engine.clear();
    }
    public BounceEngine getEngine(){
        return engine;
    }
    //bounce engine wrappers
    public void teleport(Bouncer b, MovingRectangle location){
        engine.teleport(b,location);
    }
    public void addBounceBorder(double x, double y, double width, double height){
        engine.addBounceBorder(x,y,width,height);
    }

    //optional overloadables
    public void update(double dt){}
    public void paint(Graphics g){}
    public void handelCollision(CollisionInstance instance){}
}
