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


/**
 * A window that can serve as a base class for games which
 * use the BounceEngine (a simple physics engine).
 */
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

    /**
     * Add a BouncerSprite to the physics engine and the screen.
     */
    public void addSprite(BouncerSprite sprite){
        engine.add(sprite);
        sprites.add(sprite);
    }
    /**
     * Remove a BouncerSprite from the physics engine and the screen.
     */
    public boolean removeSprite(BouncerSprite sprite){
        engine.remove(sprite);
        return sprites.remove(sprite);
    }
    /**
     * Add a MovingRectangle to the collision detection part of the
     * physics engine.
     */
    public void add(MovingRectangle rectangle){
        engine.add(rectangle);
    }
    /**
     * Remove a MovingRectangle from the collision detection part of the
     * physics engine.
     */
    public void remove(MovingRectangle rectangle){
        engine.remove(rectangle);
    }
    /**
     * Remove all sprites and rectangles from the physics engine and the
     * screen.
     */
    public void clear(){
        sprites.clear();
        engine.clear();
    }
    /**
     * Get the underlying BounceEngine (physics engine).
     */
    public BounceEngine getEngine(){
        return engine;
    }
    //bounce engine wrappers
    /**
     * Tell the physics engine to telleport 'b' to 'location'.
     */
    public void teleport(Bouncer b, MovingRectangle location){
        engine.teleport(b,location);
    }
    /**
     * Add an invisible 1pxl border to the physics engine.
     */
    public void addBounceBorder(double x, double y, double width, double height){
        engine.addBounceBorder(x,y,width,height);
    }

    //optional overloadables
    /**
     * A method to override (optionally) - this can update the game's state every
     * couple of seconds.
     * @see GameUpdater
     */
    public void update(double dt){}
    /**
     * A method to override (optionally) - this renders additional graphics on top of
     * the background and sprites (which are drawn automatically).
     * @see GamePainter
     */
    public void paint(Graphics g){}
    /**
     * A method to override (optionally) - this method is called whenever the
     * physics engine detects a collision. 
     * @see CollisionListener
     */
    public void handelCollision(CollisionInstance instance){}
}
