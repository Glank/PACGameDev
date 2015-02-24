package org.pac.games;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * A window that just contains a GameComponent and can serve
 * a base class for simple games.
 */
public abstract class SimpleGameWindow extends JFrame 
    implements GamePainter, GameUpdater, KeyListener{

    private GameComponent component;

    public SimpleGameWindow(){
        component = new GameComponent();
        component.addPainter(this);
        add(component);
        KeyEventBatchProcessor delay = 
            new KeyEventBatchProcessor(this);
        addKeyListener(delay);
        component.addUpdater(delay);
        component.addUpdater(this);
    }

    /**
     * A wrapper method for {@link GameComponent#addUpdater}.
     */
    public void addUpdater(GameUpdater other){
        component.addUpdater(other);
    }
    /**
     * A wrapper method for {@link GameComponent#removeUpdater}.
     */
    public boolean removeUpdater(GameUpdater other){
        return component.removeUpdater(other);
    }

    /**
     * Finish setting up the window, then open it.
     */
    public void start(){
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Get the underlying GameComponent.
     */
    public GameComponent getComponent(){
        return component;
    }

    /**
     * Get the underlying GameComponent's width.
     */
    public int getDisplayWidth(){
        return component.getWidth();
    }
    /**
     * Get the underlying GameComponent's height.
     */
    public int getDisplayHeight(){
        return component.getHeight();
    }

    /**
     * A method to override - this renders the game's graphics.
     * @see GamePainter
     */
    public abstract void paint(Graphics g);
    /**
     * A method to override - this updates the game's state.
     * @see GameUpdater
     */
    public abstract void update(double dt);

    /**
     * A method to override (optionally) - this get's called whenever
     * a key is pressed.
     * @see KeyListener
     */
    public void keyPressed(KeyEvent e){}
    /**
     * A method to override (optionally) - this get's called whenever
     * a key is released.
     * @see KeyListener
     */
    public void keyReleased(KeyEvent e){}
    /**
     * A method to override (optionally) - this get's called whenever
     * a key is typed.
     * @see KeyListener
     */
    public void keyTyped(KeyEvent e){}
}
