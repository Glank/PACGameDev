package org.pac.games.physics;

/**
 * A rectangle. Moving the rectangle should be handled by the
 * physics engine.
 */
public class Rectangle{
    private double x,y,width,height;
    public Rectangle(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public Rectangle(){
        this(0,0,10,10);
    }
    protected void translate(double deltaX, double deltaY){
        this.x+=deltaX;
        this.y+=deltaY;
    }

    public double leftX(){
        return x;
    }
    public double rightX(){
        return x+width;
    }
    public double topY(){
        return y;
    }
    public double bottomY(){
        return y+height;
    }
    public double width(){
        return width;
    }
    public double height(){
        return height;
    }
    public double centerX(){
        return x+width/2.;
    }
    public double centerY(){
        return y+height/2.;
    }

    /**
     * Returns the smallest possible rectangle that contains 'a' and 'b'.
     */ 
    public static Rectangle getEnclosing(Rectangle a, Rectangle b){
        double minX = a.leftX();
        double maxX = a.rightX();
        double minY = a.topY();
        double maxY = a.bottomY();
        minX = minX<b.leftX()?minX:b.leftX();
        maxX = maxX>b.rightX()?maxX:b.rightX();
        minY = minY<b.topY()?minY:b.topY();
        maxY = maxY>b.bottomY()?maxY:b.bottomY();
        return new Rectangle(minX, minY, maxX-minX, maxY-minY);
    }

    /**
     * @return True if 'other' is touching or overlapping this rectangle, false otherwise.
     */
    public boolean intersects(Rectangle other){
        return (
            rightX() >= other.leftX() && 
            leftX() <= other.rightX() &&
            bottomY() >= other.topY() &&
            topY() <= other.bottomY()
        );
    }

    /**
     * @return True if the specified point is touching or overlapping this rectangle, 
     *         false otherwise.
     */
    public boolean intersects(double x, double y){
        return leftX()<=x && rightX()>=x && topY()<=y && bottomY()>=y;
    }

    /**
     * Assuming that this rectangle is stationary, returns the earliest time
     * when the specified moving point will intersect (or has intersected)
     * this rectangle. If the point will never intersect this rectangle,
     * Double.NaN is returned. If the point has always intersected this
     * rectangle, Double.NEGATIVE_INFINITY is returned. 
     */
    public double firstIntersect(double x, double y, double dx, double dy){
        //find when intersects x
        double startIntersectX, endIntersectX;
        if(dx==0){
            if(leftX()<=x && rightX()>=x){
                //has always and will always intersect x
                startIntersectX = Double.NEGATIVE_INFINITY;
                endIntersectX = Double.POSITIVE_INFINITY;
            }
            else{
                //does not intersect X and never will
                return Double.NaN;
            }
        }
        else if(dx>0){
            //it will intersect with leftX first, then rightX
            startIntersectX = (leftX()-x)/dx;
            endIntersectX = (rightX()-x)/dx;
        }
        else{
            //it will intersect with rightX first, then leftX
            startIntersectX = (rightX()-x)/dx;
            endIntersectX = (leftX()-x)/dx;
        }

        //find when intersects y
        double startIntersectY, endIntersectY;
        if(dy==0){
            if(topY()<=y && bottomY()>=y){
                //has always and will always intersect y
                startIntersectY = Double.NEGATIVE_INFINITY;
                endIntersectY = Double.POSITIVE_INFINITY;
            }
            else{
                //does not intersect Y and never will
                return Double.NaN;
            }
        }
        else if(dy>0){
            //it will intersect with topY first, then bottomY
            startIntersectY = (topY()-y)/dy;
            endIntersectY = (bottomY()-y)/dy;
        }
        else{
            //it will intersect with bottomY first, then topY
            startIntersectY = (bottomY()-y)/dy;
            endIntersectY = (topY()-y)/dy;
        }

        //checks
        assert(startIntersectX<endIntersectX);
        assert(startIntersectY<endIntersectY);
        
        //if the times when it was intersection X and Y do not ever coincide
        if(startIntersectY>endIntersectX || startIntersectX>endIntersectY){
            return Double.NaN;
        }
        else if(startIntersectX<startIntersectY)
            return startIntersectY;
        else
            return startIntersectX;
    }

    /**
     * Assuming that this rectangle is stationary, returns the earliest time
     * when the specified moving rectangle will intersect (or has intersected)
     * this rectangle. If the point will never intersect this rectangle,
     * Double.NaN is returned. If the point has always intersected this
     * rectangle, Double.NEGATIVE_INFINITY is returned. 
     */
    public double firstIntersect(Rectangle other, double dx, double dy){
        //find when intersects x
        double startIntersectX, endIntersectX;
        if(dx==0){
            if(leftX()<=other.rightX() && rightX()>=other.leftX()){
                //has always and will always intersect x
                startIntersectX = Double.NEGATIVE_INFINITY;
                endIntersectX = Double.POSITIVE_INFINITY;
            }
            else{
                //does not intersect X and never will
                return Double.NaN;
            }
        }
        else if(dx>0){
            //it will intersect with leftX first, then rightX
            startIntersectX = (leftX()-other.rightX())/dx;
            endIntersectX = (rightX()-other.leftX())/dx;
        }
        else{
            //it will intersect with rightX first, then leftX
            startIntersectX = (rightX()-other.leftX())/dx;
            endIntersectX = (leftX()-other.rightX())/dx;
        }

        //find when intersects y
        double startIntersectY, endIntersectY;
        if(dy==0){
            if(topY()<=other.bottomY() && bottomY()>=other.topY()){
                //has always and will always intersect y
                startIntersectY = Double.NEGATIVE_INFINITY;
                endIntersectY = Double.POSITIVE_INFINITY;
            }
            else{
                //does not intersect Y and never will
                return Double.NaN;
            }
        }
        else if(dy>0){
            //it will intersect with topY first, then bottomY
            startIntersectY = (topY()-other.bottomY())/dy;
            endIntersectY = (bottomY()-other.topY())/dy;
        }
        else{
            //it will intersect with bottomY first, then topY
            startIntersectY = (bottomY()-other.topY())/dy;
            endIntersectY = (topY()-other.bottomY())/dy;
        }

        //checks
        assert(startIntersectX<endIntersectX);
        assert(startIntersectY<endIntersectY);
        
        //if the times when it was intersection X and Y do not ever coincide
        if(startIntersectY>endIntersectX || startIntersectX>endIntersectY){
            return Double.NaN;
        }
        else if(startIntersectX<startIntersectY)
            return startIntersectY;
        else
            return startIntersectX;
    }

    /**
     * Returns the length of the X intersection between this rectangle and 'other',
     * assuming that they do intersect at all.
     */
    public double getXOverlap(Rectangle other){
        if(leftX()<other.leftX()){
            if(rightX()<other.rightX()){
                // either they're overlapping, or other is to the right of this
                return rightX()-other.leftX();
            }
            else{
                //this encloses other
                return other.width();
            }
        }
        else{
            if(rightX()>other.rightX()){
                // either they're overlapping, or other is to the left of this
                return other.rightX()-leftX();
            }
            else{
                //other encloses this
                return width();
            }
        }
    }
    /**
     * Returns the length of the Y intersection between this rectangle and 'other',
     * assuming that they do intersect at all.
     */
    public double getYOverlap(Rectangle other){
        if(topY()<other.topY()){
            if(bottomY()<other.bottomY()){
                // either they're overlapping, or other is to the right of this
                return bottomY()-other.topY();
            }
            else{
                //this encloses other
                return other.height();
            }
        }
        else{
            if(bottomY()>other.bottomY()){
                // either they're overlapping, or other is to the left of this
                return other.bottomY()-topY();
            }
            else{
                //other encloses this
                return height();
            }
        }
    }
}
