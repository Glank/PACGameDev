package org.pac.games;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JComponent;

/**
 * This lightweight component is the display window for a game. It handles
 * buffering the graphics and starting/stopping the update thread.
 **/
public class GameComponent extends JComponent implements HierarchyListener{
    private static final long serialVersionUID = 209348939209L;
    private BufferedImage displayCanvas, workCanvas;
    private LinkedList<GamePainter> painters =
        new LinkedList<GamePainter>();
    private LinkedList<GameUpdater> updaters =
        new LinkedList<GameUpdater>();
    private UpdateThread updateThread;
    private int width, height;

    /**
     * Create a GameComponent that is 'width' by 'height'. The size of
     * the component should not be changed after creation.
     **/
    public GameComponent(int width, int height){
        //set size
        this.width = width;
        this.height = height;
        Dimension size = new Dimension(width, height);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
        //create canvases
        displayCanvas = createCanvas(width, height);
        workCanvas = createCanvas(width, height);
        addHierarchyListener(this);
    }
    /**
     * Creates a GameComponent with the default width and height of
     * 640x480.
     **/
    public GameComponent(){
        this(640,480);
    }
    private BufferedImage createCanvas(int width, int height){
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    /**
     * Add a GamePainter to the painter queue which gets invoked every update.
     **/
    public void addPainter(GamePainter painter){
        synchronized(painters){
            painters.add(painter);
        }
    }
    /**
     * Remove a GamePainter from the painter queue.
     **/
    public boolean removePainter(GamePainter painter){
        synchronized(painters){
            return painters.remove(painter);
        }
    }
    /**
     * Add a GameUpdater to the update queue which gets invoked every update.
     **/
    public void addUpdater(GameUpdater updater){
        synchronized(updaters){
            updaters.add(updater);
        }
    }
    /**
     * Remove a GameUpdater from the update queue.
     **/
    public boolean removeUpdater(GameUpdater updater){
        synchronized(updaters){
            return updaters.remove(updater);
        }
    }

    /**
     * Re-Prepare the graphics buffer to be drawn to the screen.
     * This is done by invoking each painter in the order in which
     * they were added to the painter queue.
     **/
    public void render(){
        synchronized(workCanvas){
            synchronized(painters){
                for(GamePainter painter:painters)
                    painter.paint(workCanvas.getGraphics());
            }
        }
        //swap work canvas for display canvas
        BufferedImage tmp = displayCanvas;
        displayCanvas = workCanvas;
        workCanvas = tmp;
    }

    @Override
    public void paint(Graphics g){
        synchronized(displayCanvas){
            g.drawImage(displayCanvas, 0, 0, null);
        }
    }

    /**
     * Invoked by the update thread several times a second and
     * then calls all of the GameUpdaters in turn.
     * @param dt The number of seconds since the last update.
     **/
    public void update(double dt){
        synchronized(updaters){
            for(GameUpdater updater:updaters)
                updater.update(dt);
        }
    }

    private void ensureUpdates(){
        if(updateThread==null){
            updateThread = new UpdateThread(this);
            updateThread.start();
        }
    }

    private void stopUpdates(){
        if(updateThread!=null){
            updateThread.safeStop();
            updateThread = null;
        }
    }

    public void hierarchyChanged(HierarchyEvent e) {
        if((e.getChangeFlags()&HierarchyEvent.DISPLAYABILITY_CHANGED)>0){
            if(this.isDisplayable())                         
                ensureUpdates();
            else
                stopUpdates();
        }
    }
}
