package org.pac.games;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public abstract class SimpleGameWindow extends JFrame 
    implements GamePainter, GameUpdater, KeyListener{

    private GameComponent component;

    public SimpleGameWindow(){
        super();
        component = new GameComponent();
        component.addPainter(this);
        add(component);
        KeyEventBatchProcessor delay = 
            new KeyEventBatchProcessor(this);
        addKeyListener(delay);
        component.addUpdater(delay);
        component.addUpdater(this);
    }

    public void addUpdater(GameUpdater other){
        component.addUpdater(other);
    }
    public boolean removeUpdater(GameUpdater other){
        return component.removeUpdater(other);
    }

    public void start(){
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public GameComponent getComponent(){
        return component;
    }

    public int getDisplayWidth(){
        return component.getWidth();
    }

    public int getDisplayHeight(){
        return component.getHeight();
    }

    public abstract void paint(Graphics g);
    public abstract void update(double dt);

    public void keyPressed(KeyEvent e){}
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
}
