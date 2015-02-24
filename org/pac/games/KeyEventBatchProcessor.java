package org.pac.games;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KeyEventBatchProcessor implements KeyListener, GameUpdater{
    private ConcurrentLinkedQueue<KeyEventWrapper> todo = 
        new ConcurrentLinkedQueue<KeyEventWrapper>();
    private KeyListener toDelay;

    public KeyEventBatchProcessor(KeyListener toDelay){
        this.toDelay = toDelay;
    }

    public void update(double dt){
        while(!todo.isEmpty())
            todo.poll().actOn(toDelay);
    }

    public void keyPressed(KeyEvent e){
        todo.add(new KeyEventWrapper(e,KeyEventWrapper.PRESSED));
    }
    public void keyTyped(KeyEvent e){
        todo.add(new KeyEventWrapper(e,KeyEventWrapper.TYPED));
    }
    public void keyReleased(KeyEvent e){
        todo.add(new KeyEventWrapper(e,KeyEventWrapper.RELEASED));
    }
}

class KeyEventWrapper{
    public static final int PRESSED = 0;
    public static final int TYPED = 1;
    public static final int RELEASED = 2;
    KeyEvent event;
    int type;
    public KeyEventWrapper(KeyEvent event, int type){
        this.event = event;
        this.type = type;
    }
    public void actOn(KeyListener listener){
        if(type==PRESSED)
            listener.keyPressed(event);
        else if(type==TYPED)
            listener.keyTyped(event);
        else if(type==RELEASED)
            listener.keyReleased(event);
    }
}
