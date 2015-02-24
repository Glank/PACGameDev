package examples;
import org.pac.games.*;
import org.pac.games.physics.*;
import org.pac.games.gui.*;
import javax.swing.*;
import java.util.*;
import java.awt.Graphics;
import java.awt.Color;

public class BounceEngineExample extends SimpleGameWindow{
    private static final long serialVersionUID = -39847928349L;
    public static final double BULLDOZER_SPEED = 25;
    public static final double BOUNCER_SPEED = 50;
    public static final double BOUNCER_SIZE = 3.5;
    public static final double BOUNCERS = 1000;

    BounceEngine engine;
    LinkedList<Rectangle> bouncers;
    Bouncer centerBlock;
    Bouncer bulldozer;
    int bulldozerDirection = 0;

    public BounceEngineExample(){
        super();
        engine = new BounceEngine();
        engine.addBounceBorder(0,0,640,480);
        //init bouncers
        bouncers = new LinkedList<Rectangle>();
        for(int i = 0; i < BOUNCERS; i++){
            double x = Math.random()*630+5;
            double y = Math.random()*470+5;
            double theta = Math.random()*2*Math.PI;
            double dx = Math.sin(theta)*BOUNCER_SPEED;
            double dy = Math.cos(theta)*BOUNCER_SPEED;
            double s = Math.random()*Math.random()+.75;
            s*=BOUNCER_SIZE;
            Bouncer bouncer = new Bouncer(
                new MovingRectangle(x,y,s,s,dx,dy), s*s*s
            );
            bouncers.add(bouncer.getBounds());
            engine.add(bouncer);
        }
        //init center block
        centerBlock = new Bouncer(
            new MovingRectangle(300,220,40,40,0,0),
            Bouncer.DO_NOTHING, Bouncer.DO_NOTHING
        );
        engine.add(centerBlock);
        //init bulldozer
        bulldozer = new Bouncer(
            new MovingRectangle(100,100,20,20,BULLDOZER_SPEED,0),
            Bouncer.DO_NOTHING, Bouncer.DO_NOTHING
        );
        engine.add(bulldozer);
    }

    public void paint(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getDisplayWidth(), getDisplayHeight());
        g.setColor(Color.RED);
        for(Rectangle r:bouncers)
            DrawUtils.fillRectangle(g,r);
        g.setColor(Color.BLUE);
        DrawUtils.fillRectangle(g,centerBlock.getBounds());
        g.setColor(Color.GREEN);
        DrawUtils.fillRectangle(g,bulldozer.getBounds());
    }

    public void update(double dt){
        engine.update(dt);
        if(bulldozerDirection==0 && bulldozer.getBounds().rightX()>540){
            bulldozerDirection = 1;
            bulldozer.getBounds().setDX(0);
            bulldozer.getBounds().setDY(BULLDOZER_SPEED);
        }
        else if(bulldozerDirection==1 && bulldozer.getBounds().bottomY()>380){
            bulldozerDirection = 2;
            bulldozer.getBounds().setDX(-BULLDOZER_SPEED);
            bulldozer.getBounds().setDY(0);
        }
        else if(bulldozerDirection==2 && bulldozer.getBounds().leftX()<100){
            bulldozerDirection = 3;
            bulldozer.getBounds().setDX(0);
            bulldozer.getBounds().setDY(-BULLDOZER_SPEED);
        }
        else if(bulldozerDirection==3 && bulldozer.getBounds().topY()<100){
            bulldozerDirection = 0;
            bulldozer.getBounds().setDX(BULLDOZER_SPEED);
            bulldozer.getBounds().setDY(0);
        }
    }

    public static void main(String[] args){
        new BounceEngineExample().start();
    }
}
