package examples;
import org.pac.games.*;
import org.pac.games.physics.*;
import org.pac.games.gui.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class BallChase extends BounceGameWindow{
    private static final long serialVersionUID = -29384937582L;
    public static final int PLAYER_SPEED = 200;
    public static final int ENEMY_SPEED = 100;
    BouncerSprite player, ball, enemy;
    int points = 0;

    public BallChase(){
        reset();
        setTitle("Ball Chase");
    }
    public void reset(){
        clear();
        points = 0;
        player = new BouncerSprite(
            new MovingRectangle(    
                100, 100,
                50, 50 
            ),
            Bouncer.STOP, Bouncer.STOP
        );
        ball = new Ball();
        enemy = new Enemy();
        addSprite(player);
        addSprite(ball);
        addSprite(enemy);
        addBounceBorder(0,0,getDisplayWidth(),getDisplayHeight());
    }

    @Override
    public void keyPressed(KeyEvent event){
        int c = event.getKeyCode();
        if(c == KeyEvent.VK_UP)
            player.setDY(-PLAYER_SPEED);
        else if(c == KeyEvent.VK_DOWN)
            player.setDY(PLAYER_SPEED);
        else if(c == KeyEvent.VK_LEFT)
            player.setDX(-PLAYER_SPEED);
        else if(c == KeyEvent.VK_RIGHT)
            player.setDX(PLAYER_SPEED);
    }
    
    @Override
    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.drawString("Points: "+points, 10,30);
    }

    @Override
    public void update(double dt){
        //chase the player
        if(player.centerX()>enemy.centerX())
            enemy.setDX(ENEMY_SPEED);
        else
            enemy.setDX(-ENEMY_SPEED);
        if(player.centerY()>enemy.centerY())
            enemy.setDY(ENEMY_SPEED);
        else
            enemy.setDY(-ENEMY_SPEED);
    }

    @Override
    public void handelCollision(CollisionInstance instance){
        if(instance.contains(ball) && instance.contains(player))
            points++;
        if(instance.contains(enemy) && instance.contains(player))
            endGame();
    }

    public void endGame(){
        JOptionPane.showMessageDialog(
            this, "Game Over! You scored "+points+" points.",
            "Game Over", JOptionPane.INFORMATION_MESSAGE
        );
        reset();
    }

    public static void main(String[] args){
        new BallChase().start();
    }
}
class Ball extends BouncerSprite{
    public Ball(){
        super(new MovingRectangle(250, 250, 25, 25, 187, 294));
    }
    public void paint(Graphics g){
        g.setColor(Color.BLUE);
        g.fillOval((int)leftX(), (int)topY(), (int)width(), (int)height());
    }
}
class Enemy extends BouncerSprite{
    public Enemy(){
        super(
            new MovingRectangle(250, 400, 25, 25, 0, 0),
            Bouncer.DO_NOTHING, Bouncer.DO_NOTHING
        );
        setColor(Color.RED);
    }
}
