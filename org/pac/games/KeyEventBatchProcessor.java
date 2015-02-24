package org.pac.games;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * <p>
 * A wrapper for a KeyListener so that it can safely work with the game update
 * thread. All updates to the game must be done through the game update thread,
 * so handling user input (which is on the swing thread I believe) is somewhat
 * of a problem. To fix this, you can use this class to catch all of the events
 * then handel them as a GameUpdater.
 * </p>
 * So rather than doing something simple like this:
 * <pre>
 * {@code
 * aComponent.addKeyListener(myListener);
 * }
 * </pre>
 * You will need to create the wrapper, add the listener to the wrapper, then 
 * add the wrapper to the key-listening component and the game component:
 * <pre>
 * {@code
 * KeyEventBatchProcessor keyBatchProcessor = 
 *     new KeyEventBatchProcessor(myListener);
 * aComponent.addKeyListener(keyBatchProcessor);
 * gameComponent.addUpdater(keyBatchProcessor);
 * }
 * </pre>
 */
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
