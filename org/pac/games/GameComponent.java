package org.pac.games;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JComponent;

public class GameComponent extends JComponent implements HierarchyListener{
    private static final long serialVersionUID = 209348939209L;
    private BufferedImage displayCanvas, workCanvas;
    private LinkedList<GamePainter> painters =
        new LinkedList<GamePainter>();
    private LinkedList<GameUpdater> updaters =
        new LinkedList<GameUpdater>();
    private UpdateThread updateThread;
    private int width, height;

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
    private BufferedImage createCanvas(int width, int height){
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }
    public GameComponent(){
        this(640,480);
    }

    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public void addPainter(GamePainter painter){
        synchronized(painters){
            painters.add(painter);
        }
    }
    public boolean removePainter(GamePainter painter){
        synchronized(painters){
            return painters.remove(painter);
        }
    }
    public void addUpdater(GameUpdater updater){
        synchronized(updaters){
            updaters.add(updater);
        }
    }
    public boolean removeUpdater(GameUpdater updater){
        synchronized(updaters){
            return updaters.remove(updater);
        }
    }

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

    public void paint(Graphics g){
        synchronized(displayCanvas){
            g.drawImage(displayCanvas, 0, 0, null);
        }
    }

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
