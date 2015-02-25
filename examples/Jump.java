package examples;
import org.pac.games.*;
import org.pac.games.physics.*;
import org.pac.games.gui.*;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class Jump extends BounceGameWindow{
    private static final long serialVersionUID = 93094849478L;
    public static final double GRAVITATIONAL_ACCELERATION = 1600;
    public static final double JUMP_VELOCITY = 800;
    public static final double PLAYER_SPEED = 250;
    public static final double PLAYER_WIDTH = 30;
    public static final double PLAYER_HEIGHT = 50;
    public static final double PLATFORM_HEIGHT = 20;

    Player player;
    Platform ground, platform;

    public Jump(){
        player = new Player(100,100);
        ground = new Platform(0, getDisplayHeight()-PLATFORM_HEIGHT, getDisplayWidth());
        platform = new Platform(50, ground.topY()-PLAYER_HEIGHT*1.5, 150);
        addSprite(player);
        addSprite(ground);
        addSprite(platform);
        addBounceBorder(0,0,getDisplayWidth(),getDisplayHeight());
    }

    @Override
    public void update(double dt){
        player.update(dt);
    }

    @Override
    public void keyPressed(KeyEvent event){
        int c = event.getKeyCode();
        if(c == KeyEvent.VK_UP)
            player.jump();
        else if(c == KeyEvent.VK_LEFT)
            player.setDX(-PLAYER_SPEED);
        else if(c == KeyEvent.VK_RIGHT)
            player.setDX(PLAYER_SPEED);
    }
    @Override
    public void keyReleased(KeyEvent event){
        int c = event.getKeyCode();
        if(c == KeyEvent.VK_LEFT)
            player.setDX(0);
        else if(c == KeyEvent.VK_RIGHT)
            player.setDX(0);
    }
    
    static class Player extends BouncerSprite{
        public Player(double x, double y){
            super(
                new MovingRectangle(x,y,PLAYER_WIDTH,PLAYER_HEIGHT),
                Bouncer.STOP, Bouncer.STOP
            );
            setColor(Color.BLUE);
        }
        public void jump(){
            setDY(-JUMP_VELOCITY);
        }
        public void update(double dt){
            setDY(getDY()+GRAVITATIONAL_ACCELERATION*dt);
        }
    }

    static class Platform extends BouncerSprite{
        public Platform(double x, double y, double width){
            super(
                new MovingRectangle(x,y,width,PLATFORM_HEIGHT),
                Bouncer.DO_NOTHING, Bouncer.DO_NOTHING
            );
        }
    }

    public static void main(String[] args){
        new Jump().start();
    }
}
