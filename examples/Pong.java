package examples;
import org.pac.games.*;
import org.pac.games.physics.*;
import org.pac.games.gui.*;
import java.awt.event.KeyEvent;
public class Pong extends BounceGameWindow{
    private static final long serialVersionUID = -39847928349L;
    public static final double PADDLE_WIDTH = 10;
    public static final double PADDLE_HEIGHT = 80;
    public static final double PADDLE_OFFSET = 50;
    public static final double PADDLE_SPEED = 400;
    public static final double BALL_WIDTH = 10;
    public static final double BALL_ACCELERATION = 10; //pxls per second per second
    BouncerSprite left,right,ball;
    MovingRectangle leftCatcher, rightCatcher;
    public Pong(){
        left = new Paddle(
            PADDLE_OFFSET,(getDisplayHeight()-PADDLE_HEIGHT)/2
        );
        right = new Paddle(
            getDisplayWidth()-(PADDLE_OFFSET+PADDLE_WIDTH),
            (getDisplayHeight()-PADDLE_HEIGHT)/2
        );
        ball = new BouncerSprite(
            getBallStart(), Bouncer.BOUNCE, Bouncer.BOUNCE
        );
        addSprite(left);
        addSprite(right);
        addSprite(ball);
        leftCatcher = new MovingRectangle(0,0,1,getDisplayHeight());
        rightCatcher = new MovingRectangle(getDisplayWidth(),0,1,getDisplayHeight());
        add(leftCatcher);
        add(rightCatcher);
        addBounceBorder(0,0,getDisplayWidth(),getDisplayHeight());
    }
    @Override
    public void update(double dt){
        ball.nondirectionalAccelerateX(BALL_ACCELERATION*dt);
        ball.nondirectionalAccelerateY(BALL_ACCELERATION*dt);
    }
    @Override
    public void keyPressed(KeyEvent event){
        int c = event.getKeyCode();
        if(c == KeyEvent.VK_W)
            left.setDY(-PADDLE_SPEED);
        else if(c == KeyEvent.VK_S)
            left.setDY(PADDLE_SPEED);
        else if(c == KeyEvent.VK_UP)
            right.setDY(-PADDLE_SPEED);
        else if(c == KeyEvent.VK_DOWN)
            right.setDY(PADDLE_SPEED);
    }
    @Override
    public void keyReleased(KeyEvent event){
        int c = event.getKeyCode();
        if(c == KeyEvent.VK_W)
            left.setDY(0);
        else if(c == KeyEvent.VK_S)
            left.setDY(0);
        else if(c == KeyEvent.VK_UP)
            right.setDY(0);
        else if(c == KeyEvent.VK_DOWN)
            right.setDY(0);
    }
    @Override
    public void handelCollision(CollisionInstance instance){
        if(instance.contains(ball)){
            if(instance.contains(leftCatcher)){
                teleport(ball, getBallStart());
            }
            else if(instance.contains(rightCatcher)){
                teleport(ball, getBallStart());
            }
        }
    }
    public MovingRectangle getBallStart(){
        double dx;
        if(Math.random()>.5)
            dx = 100;
        else
            dx = -100;
        double dy = Math.random()*300-150;
        return new MovingRectangle(
            (640-BALL_WIDTH)/2, (480-BALL_WIDTH)/2,
            BALL_WIDTH, BALL_WIDTH, dx, dy
        );
    }
    static class Paddle extends BouncerSprite{
        public Paddle(double x, double y){
            super(
                new MovingRectangle(x,y,PADDLE_WIDTH,PADDLE_HEIGHT),
                Bouncer.DO_NOTHING, Bouncer.STOP
            );
        }
    }
    public static void main(String[] args){
        new Pong().start();
    }
}
