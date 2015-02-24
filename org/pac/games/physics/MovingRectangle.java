package org.pac.games.physics;

public class MovingRectangle extends Rectangle{
    private double dx,dy;

    public MovingRectangle(
        double x, double y, 
        double width, double height,
        double dx, double dy
    ){
        super(x,y,width,height);
        this.dx = dx;
        this.dy = dy;
    }
    public MovingRectangle(
        double x, double y, 
        double width, double height
    ){
        this(x,y,width,height,0,0);
    }
    public MovingRectangle(){
        super();
    }
    public double getDX(){
        return dx;
    }
    public double getDY(){
        return dy;
    }
    public void setDX(double dx){
        this.dx = dx;
    }
    public void setDY(double dy){
        this.dy = dy;
    }
    public void nondirectionalAccelerateX(double a){
        assert(a>=0);
        if(this.dx>=0)
            this.dx+=a;
        else
            this.dx-=a;
    }
    public void nondirectionalAccelerateY(double a){
        assert(a>=0);
        if(this.dy>=0)
            this.dy+=a;
        else
            this.dy-=a;
    }

    public void update(double dt){
        super.translate(dt*dx, dt*dy);
    }
    public Rectangle getMotionBounds(double dt){
        Rectangle next = new Rectangle(
            leftX()+dt*dx, topY()+dt*dy,
            width(), height()
        );
        return Rectangle.getEnclosing(this, next);
    }
    public String toString(){
        return "MovingRectangle("+leftX()+", "+topY()+", "+
            width()+", "+height()+", "+dx+", "+dy+")";
    }
}
