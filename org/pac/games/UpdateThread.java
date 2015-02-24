package org.pac.games;

public class UpdateThread extends Thread{
    /**60 frames per second**/
    public static final float DEFAULT_FRAMERATE = 60f;
    private GameComponent component;
    private int delay; //millis
    private boolean stopping;

    public UpdateThread(GameComponent component){
        this.component = component;
        setFrameRate(DEFAULT_FRAMERATE);
        setDaemon(true);
    }

    public void setFrameRate(float framerate){
        delay = (int)(1000f/framerate);
    }

    @Override
    public void run(){
        long lastUpdate = System.currentTimeMillis();;
        long remainingDelay = 0;
        while(!stopping){
            try{
                lastUpdate = System.currentTimeMillis();
                component.update(delay/1000.);
                component.render();
                component.repaint();
                remainingDelay = delay-
                    (System.currentTimeMillis()-lastUpdate);
                if(remainingDelay>0)
                    sleep((int)remainingDelay);
            }catch(InterruptedException ex){}
        }
        //break recurrant connections
        this.component = null;
    }

    public void safeStop(){
        stopping = true;
    }
}
