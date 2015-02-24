package org.pac.games.physics;
import org.pac.games.GameUpdater;
import java.util.Vector;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CollisionEngine implements GameUpdater{

    private Vector<MovingRectangle> rectangles = new Vector<MovingRectangle>();
    private LinkedList<CollisionListener> listeners =
        new LinkedList<CollisionListener>();
    private CollisionGrid collisionGrid = new CollisionGrid();

    public void addListener(CollisionListener listener){
        listeners.add(listener);
    }
    public boolean removeListener(CollisionListener listener){
        return listeners.remove(listener);
    }

    public void add(MovingRectangle rectangle){
        LinkedList<CollisionInstance> collisions = new LinkedList<CollisionInstance>();
        for(MovingRectangle other:rectangles){
            if(rectangle.intersects(other))
                collisions.add(new CollisionInstance(rectangle, other));
        }
        rectangles.add(rectangle);
        for(CollisionInstance collision:collisions)
            handelCollision(collision);
    }
    public boolean remove(MovingRectangle rectangle){
        return rectangles.remove(rectangle);
    }
    public void clear(){
        rectangles.clear();
    }
    private void updateWithoutCollision(double dt){
        assert(dt>=0);
        for(MovingRectangle rectangle:rectangles)
            rectangle.update(dt);
    }
    private void handelCollision(CollisionInstance collision){
        for(CollisionListener listener:listeners)
            listener.handelCollision(collision);
    }


    /** returns the remaining amount of time to update */
    private double partialUpdate(double dt){
        LinkedList<CollisionInstance> nextCollisions =
            new LinkedList<CollisionInstance>();
        double nextCollisionTime = collisionGrid.getNextCollisions(
            rectangles, dt, nextCollisions
        );
        if(nextCollisionTime<=dt){
            updateWithoutCollision(nextCollisionTime);
            for(CollisionInstance collision:nextCollisions){
                handelCollision(collision);
            }
            return dt-nextCollisionTime;
        }
        else{
            updateWithoutCollision(dt);
            return 0;
        }
    }

    public void update(double dt){
        while(dt>0)
            dt = partialUpdate(dt);
    }
}
