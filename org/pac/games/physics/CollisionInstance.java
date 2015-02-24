package org.pac.games.physics;

public class CollisionInstance{
    private MovingRectangle a, b;
    public CollisionInstance(MovingRectangle a, MovingRectangle b){
        this.a = a;
        this.b = b;
    }
    public MovingRectangle getA(){
        return a;
    }
    public MovingRectangle getB(){
        return b;
    }
    public boolean contains(MovingRectangle r){
        return r==a || r==b;
    }
    public boolean contains(Bouncer b){
        return contains(b.getBounds());
    }
    @Override
    public int hashCode(){
        return a.hashCode()^b.hashCode();
    }
    @Override
    public boolean equals(Object o){
        if(!(o instanceof CollisionInstance))
            return false;
        CollisionInstance other = (CollisionInstance)o;
        return (a==other.getA() && b==other.getB()) ||
            (a==other.getB() && b==other.getA());
    }
}
