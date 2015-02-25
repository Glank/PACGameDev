package org.pac.games.physics;
import org.pac.games.GameUpdater;
import java.util.HashMap;

public class BounceEngine implements CollisionListener, GameUpdater{
    public static final double SHORT_INSTANT = .000001;
    private HashMap<MovingRectangle,Bouncer> boundsIndex = 
        new HashMap<MovingRectangle,Bouncer>();
    private CollisionEngine collisionEngine;

    public BounceEngine(CollisionEngine collisionEngine){
        this.collisionEngine = collisionEngine;
        collisionEngine.addListener(this);
    }
    public BounceEngine(){
        this(new CollisionEngine());
    }

    public void addListener(CollisionListener listener){
        collisionEngine.addListener(listener);
    }
    public boolean removeListener(CollisionListener listener){
        return collisionEngine.removeListener(listener);
    }

    public void add(Bouncer bouncer){
        boundsIndex.put(bouncer.getBounds(), bouncer);
        collisionEngine.add(bouncer.getBounds());
    }
    public void remove(Bouncer bouncer){
        boundsIndex.remove(bouncer.getBounds());
        collisionEngine.remove(bouncer.getBounds());
    }
    public void add(MovingRectangle rect){
        collisionEngine.add(rect);
    }
    public void remove(MovingRectangle rect){
        collisionEngine.remove(rect);
    }
    public void clear(){
        collisionEngine.clear();
        boundsIndex.clear();
    }

    public void teleport(Bouncer bouncer, MovingRectangle to){
        remove(bouncer);
        bouncer.setBounds(to);
        add(bouncer);
    }

    //Adds a 1pxl wide invisible border around the specified area
    public void addBounceBorder(double x, double y, double width, double height){
        add(new Bouncer(
            new MovingRectangle(x-1,y-1,width+2,1,0,0), 
            Bouncer.DO_NOTHING, Bouncer.DO_NOTHING
        ));
        add(new Bouncer(
            new MovingRectangle(x-1,y-1,1,height+2,0,0), 
            Bouncer.DO_NOTHING, Bouncer.DO_NOTHING
        ));
        add(new Bouncer(
            new MovingRectangle(width,y-1,1,height+2,0,0), 
            Bouncer.DO_NOTHING, Bouncer.DO_NOTHING
        ));
        add(new Bouncer(
            new MovingRectangle(x-1,height,width+2,1,0,0), 
            Bouncer.DO_NOTHING, Bouncer.DO_NOTHING
        ));
    }

    public void update(double dt){
        collisionEngine.update(dt);
    }
    
    static int count = 0;
    public void handelCollision(CollisionInstance instance){
        Bouncer a = boundsIndex.get(instance.getA());
        Bouncer b = boundsIndex.get(instance.getB());
        if(a==null || b==null)
            return;

        double xOverlap = a.getBounds().getXOverlap(b.getBounds());
        double yOverlap = a.getBounds().getYOverlap(b.getBounds());
        assert(xOverlap>=0);
        assert(yOverlap>=0);
        if(xOverlap<yOverlap){
            //Handle X bounce
            if(a.onXBounce()==Bouncer.BOUNCE && b.onXBounce()==Bouncer.BOUNCE)
                elasticBounceX(a,b);
            else{
                if(a.onXBounce()==Bouncer.BOUNCE)
                    bulldozeBounceX(a,b);
                else if(a.onXBounce()==Bouncer.STOP)
                    stopAgainstX(a,b);
                else
                    a.getBounds().update(SHORT_INSTANT);
                if(b.onXBounce()==Bouncer.BOUNCE)
                    bulldozeBounceX(b,a);
                else if(b.onXBounce()==Bouncer.STOP)
                    stopAgainstX(b,a);
                else
                    b.getBounds().update(SHORT_INSTANT);
            }
        }
        else{
            //Handel Y bounce
            if(a.onYBounce()==Bouncer.BOUNCE && b.onYBounce()==Bouncer.BOUNCE)
                elasticBounceY(a,b);
            else{
                if(a.onYBounce()==Bouncer.BOUNCE)
                    bulldozeBounceY(a,b);
                else if(a.onYBounce()==Bouncer.STOP)
                    stopAgainstY(a,b);
                else
                    a.getBounds().update(SHORT_INSTANT);
                if(b.onYBounce()==Bouncer.BOUNCE)
                    bulldozeBounceY(b,a);
                else if(b.onYBounce()==Bouncer.STOP)
                    stopAgainstY(b,a);
                else
                    b.getBounds().update(SHORT_INSTANT);
            }
        }
    }

    /** see: http://en.wikipedia.org/wiki/Elastic_collision */
    public static void elasticBounceX(Bouncer a, Bouncer b){
        MovingRectangle aBounds = a.getBounds();
        MovingRectangle bBounds = b.getBounds();
        double massA = a.getMass();
        double massB = b.getMass();
        double av1 = aBounds.getDX();
        double bv1 = bBounds.getDX();
        double av2 = (av1*(massA-massB)+2*massB*bv1)/(massA+massB);
        double bv2 = (bv1*(massB-massA)+2*massA*av1)/(massA+massB);
        aBounds.setDX(av2);
        bBounds.setDX(bv2);
    }
    public static void elasticBounceY(Bouncer a, Bouncer b){
        MovingRectangle aBounds = a.getBounds();
        MovingRectangle bBounds = b.getBounds();
        double massA = a.getMass();
        double massB = b.getMass();
        double av1 = aBounds.getDY();
        double bv1 = bBounds.getDY();
        double av2 = (av1*(massA-massB)+2*massB*bv1)/(massA+massB);
        double bv2 = (bv1*(massB-massA)+2*massA*av1)/(massA+massB);
        aBounds.setDY(av2);
        bBounds.setDY(bv2);
    }
    public static void bulldozeBounceX(Bouncer apple, Bouncer bulldozer){
        MovingRectangle aBounds = apple.getBounds();
        MovingRectangle bBounds = bulldozer.getBounds();
        double av1 = aBounds.getDX();
        double bv1 = bBounds.getDX();
        double av2 = 2*bv1-av1;
        aBounds.setDX(av2);
    }
    public static void bulldozeBounceY(Bouncer apple, Bouncer bulldozer){
        MovingRectangle aBounds = apple.getBounds();
        MovingRectangle bBounds = bulldozer.getBounds();
        double av1 = aBounds.getDY();
        double bv1 = bBounds.getDY();
        double av2 = 2*bv1-av1;
        aBounds.setDY(av2);
    }
    public static void wallBounceX(MovingRectangle rect){
        rect.setDX(-rect.getDX());
    }
    public static void wallBounceY(MovingRectangle rect){
        rect.setDY(-rect.getDY());
    }
    public static void stopAgainstX(Bouncer ball, Bouncer wall){
        bulldozeBounceX(ball, wall);
        ball.setDX(ball.getBounds().getDX()>0?1:-1);
        double dy = ball.getDY();
        ball.setDY(0);
        double delta = SHORT_INSTANT;
        while(ball.getBounds().intersects(wall.getBounds())){
            ball.getBounds().update(delta);
        }
        assert(!ball.getBounds().intersects(wall.getBounds()));
        ball.setDX(0);
        ball.setDY(dy);
    }
    public static void stopAgainstY(Bouncer ball, Bouncer wall){
        bulldozeBounceY(ball, wall);
        ball.setDY(ball.getBounds().getDY()>0?1:-1);
        double dx = ball.getDX();
        ball.setDX(0);
        double delta = SHORT_INSTANT;
        while(ball.getBounds().intersects(wall.getBounds())){
            ball.getBounds().update(delta);
        }
        assert(!ball.getBounds().intersects(wall.getBounds()));
        ball.setDY(0);
        ball.setDX(dx);
    }
}
