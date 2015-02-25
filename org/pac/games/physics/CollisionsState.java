package org.pac.games.physics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class CollisionsState{
    private HashMap<MovingRectangle,LinkedList<MovingRectangle>> crossRefs =
        new HashMap<MovingRectangle,LinkedList<MovingRectangle>>();
    private HashSet<CollisionInstance> instances = 
        new HashSet<CollisionInstance>();

    private void addCrossRef(MovingRectangle r, MovingRectangle ref){
        LinkedList<MovingRectangle> refs = crossRefs.get(r);
        if(refs==null){
            refs = new LinkedList<MovingRectangle>();
            refs.add(ref);
            crossRefs.put(r,refs);
        }
        else
            refs.add(ref);
    }
    private void removeCrossRef(MovingRectangle r, MovingRectangle ref){
        LinkedList<MovingRectangle> refs = crossRefs.get(r);
        if(refs==null)
            return;
        refs.remove(ref);
        if(refs.isEmpty())
            crossRefs.remove(r);
    }
    public void add(CollisionInstance instance){
        if(instances.contains(instance))
            return;
        MovingRectangle a = instance.getA();
        MovingRectangle b = instance.getB();
        addCrossRef(a,b);
        addCrossRef(b,a);
        instances.add(instance);
    }
    public void remove(CollisionInstance instance){
        if(!instances.contains(instance))
            return;
        MovingRectangle a = instance.getA();
        MovingRectangle b = instance.getB();
        removeCrossRef(a,b);
        removeCrossRef(b,a);
        instances.remove(instance);
    }
    public boolean contains(CollisionInstance instance){
        return instances.contains(instance);
    }
    public Iterable<MovingRectangle> getIntersecting(MovingRectangle r){
        return crossRefs.get(r);
    }
    public Iterable<CollisionInstance> getInstancesWith(MovingRectangle r){
        LinkedList<CollisionInstance> instancesWith =
            new LinkedList<CollisionInstance>();
        for(MovingRectangle other:getIntersecting(r))
            instancesWith.add(new CollisionInstance(r,other));
        return instancesWith;
    }
    public void removeAllWith(MovingRectangle r){
        if(!crossRefs.containsKey(r))
            return;
        for(MovingRectangle other:crossRefs.get(r)){
            assert(instances.remove(new CollisionInstance(r,other)));
            removeCrossRef(other, r);
        }
        crossRefs.remove(r);
    }
    public void removeAllInactive(){
        LinkedList<CollisionInstance> inactives = new LinkedList<CollisionInstance>();
        for(CollisionInstance instance:instances){
            if(!instance.stillActive())
                inactives.add(instance);
        }
        for(CollisionInstance inactive:inactives)
            remove(inactive);
    }
    public void clear(){
        crossRefs.clear();
        instances.clear();
    }
}
