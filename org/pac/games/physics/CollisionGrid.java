package org.pac.games.physics;
import java.util.Vector;
import java.util.List;
import java.util.LinkedList;

/**
This is a high level grid for figuring out which rectangles *might* collide.
**/
public class CollisionGrid{
    private int xDim, yDim;
    private boolean autoresize;
    private Vector[][] potentialColliders;
    private double minX, minY, maxX, maxY;

    @SuppressWarnings("rawtypes")
    public CollisionGrid(
        int xDivisions, int yDivisions, 
        double minX, double minY, double maxX, double maxY,
        boolean autoresize
   ){
        xDim = xDivisions;
        yDim = yDivisions;
        potentialColliders = new Vector[xDim][yDim];
        for(int x = 0; x < xDim; x++){
            for(int y = 0; y < yDim; y++){
                potentialColliders[x][y] = new Vector<MovingRectangle>();
            }   
        }
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.autoresize = autoresize;
    }
    public CollisionGrid(int xDivisions, int yDivisions){
        this(xDivisions, yDivisions, 0,0,640,480, true);
    }
    public CollisionGrid(){
        this(32,32);
    }
    private void resize(Iterable<MovingRectangle> rectangles, double dt){
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        for(MovingRectangle rectangle:rectangles){
            Rectangle motionBounds = rectangle.getMotionBounds(dt);
            if(motionBounds.leftX()<minX)
                minX = motionBounds.leftX();
            if(motionBounds.rightX()>maxX)
                maxX = motionBounds.rightX();
            if(motionBounds.topY()<minY)
                minY = motionBounds.topY();
            if(motionBounds.bottomY()>maxY)
                maxY = motionBounds.bottomY();
        }
        if(minX < Double.POSITIVE_INFINITY){
            this.minX = minX;
            this.minY = minY;
            this.maxX = maxX;
            this.maxY = maxY;
        }
    }
    private void clearPotentials(){
        for(int x = 0; x < xDim; x++){
            for(int y = 0; y < yDim; y++){
                potentialColliders[x][y].clear();
            }
        }
    }
    @SuppressWarnings("unchecked")
    private void fillPotentials(Iterable<MovingRectangle> rectangles, double dt){
        double cellWidth = (maxX-minX)/xDim;
        double cellHeight = (maxY-minY)/yDim;
        for(MovingRectangle rectangle:rectangles){
            Rectangle motionBounds = rectangle.getMotionBounds(dt);
            int gridStartX = (int)((motionBounds.leftX()-minX)/cellWidth);
            int gridEndX = (int)((motionBounds.rightX()-minX)/cellWidth);
            gridEndX = Math.min(gridEndX,xDim-1);
            int gridStartY = (int)((motionBounds.topY()-minY)/cellHeight);
            int gridEndY = (int)((motionBounds.bottomY()-minY)/cellHeight);
            gridEndY = Math.min(gridEndY,yDim-1);
            for(int x = gridStartX; x<=gridEndX; x++){
                for(int y = gridStartY; y<=gridEndY; y++){
                    potentialColliders[x][y].add(rectangle);
                }
            }
        }
    }
    private void updatePotentials(Iterable<MovingRectangle> rectangles, double dt){
        resize(rectangles, dt);
        clearPotentials();
        fillPotentials(rectangles, dt);
    }

    /** 
    * returns the time of the next collision(s).
    * Fills 'nextCollisions' with the next collisions.
    **/
    private double getNextCollisions(
        List<MovingRectangle> rectangles,  //input
        double dt, //input
        double nextCollisionTime, //input
        LinkedList<CollisionInstance> nextCollisions //output
    ){
        for(int i = 0; i < rectangles.size(); i++){
            MovingRectangle a = rectangles.get(i);
            for(int j = i+1; j<rectangles.size(); j++){
                MovingRectangle b = rectangles.get(j);
                double whenIntersects = a.firstIntersect(
                    b, b.getDX()-a.getDX(), b.getDY()-a.getDY()
                );
                if(whenIntersects!=Double.NaN && whenIntersects>=0 && whenIntersects<dt){
                    if(whenIntersects<nextCollisionTime){
                        nextCollisionTime = whenIntersects;
                        nextCollisions.clear();
                        nextCollisions.add(new CollisionInstance(a,b));
                    }
                    else if(whenIntersects==nextCollisionTime){
                        CollisionInstance collision = new CollisionInstance(a,b);
                        if(!nextCollisions.contains(collision))
                            nextCollisions.add(collision);
                    }
                }
            }
        }
        return nextCollisionTime;
    }

    /** 
    * returns the time of the next collision(s).
    * Fills 'nextCollisions' with the next collisions.
    **/
    @SuppressWarnings("unchecked")
    public double getNextCollisions(
        Iterable<MovingRectangle> rectangles,  //input
        double dt, //input
        LinkedList<CollisionInstance> nextCollisions //output
    ){
        updatePotentials(rectangles, dt);
        double nextCollisionTime = Double.POSITIVE_INFINITY;
        for(int x = 0; x < xDim; x++){
            for(int y = 0; y < yDim; y++){
                nextCollisionTime = getNextCollisions(
                    (Vector<MovingRectangle>)potentialColliders[x][y], 
                    dt, nextCollisionTime,
                    nextCollisions
                );
            }
        }
        return nextCollisionTime;
    }
}
