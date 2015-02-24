package org.pac.games.physics;

public class Bouncer{
    public static final int DO_NOTHING = 0;
    public static final int BOUNCE = 1;
    public static final int STOP = 2;
    private MovingRectangle bounds;
    private double mass;
    private int onXBounce, onYBounce;
    public Bouncer(
        MovingRectangle bounds, double mass, 
        int onXBounce, int onYBounce
    ){
        this.bounds = bounds;
        this.mass = mass;
        this.onXBounce = onXBounce;
        this.onYBounce = onYBounce;
    }
    public Bouncer(MovingRectangle bounds, double mass){
        this(bounds, mass, BOUNCE, BOUNCE);
    }
    public Bouncer(MovingRectangle bounds){
        this(bounds, 1.0);
    }
    public Bouncer(MovingRectangle bounds, int onXBounce, int onYBounce){
        this(bounds, 1.0, onXBounce, onYBounce);
    }
    public Bouncer(){
        this(new MovingRectangle());
    }

    void setBounds(MovingRectangle newBounds){
        bounds = newBounds;
    }

    public MovingRectangle getBounds(){
        return bounds;
    }
    public double getMass(){
        return mass;
    }
    public int onXBounce(){
        return onXBounce;
    }
    public int onYBounce(){
        return onYBounce;
    }

    //wrappers for bounds
    public double getDX(){
        return bounds.getDX();
    }
    public double getDY(){
        return bounds.getDY();
    }
    public void setDX(double dx){
        bounds.setDX(dx);
    }
    public void setDY(double dy){
        bounds.setDY(dy);
    }
    public double leftX(){
        return bounds.leftX();
    }
    public double rightX(){
        return bounds.rightX();
    }
    public double topY(){
        return bounds.topY();
    }
    public double bottomY(){
        return bounds.bottomY();
    }
    public double width(){
        return bounds.width();
    }
    public double height(){
        return bounds.height();
    }
    public double centerX(){
        return bounds.centerX();
    }
    public double centerY(){
        return bounds.centerY();
    }
    public void nondirectionalAccelerateX(double a){
        bounds.nondirectionalAccelerateX(a);
    }
    public void nondirectionalAccelerateY(double a){
        bounds.nondirectionalAccelerateY(a);
    }
}
