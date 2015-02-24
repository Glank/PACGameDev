package org.pac.games;
import java.awt.Graphics;

/**
 * An drawing function which gets invoked several times a second by
 * the GameComponent update thread.
 */
public interface GamePainter{
    /**
     * Once added to a GameComponent, this paint function will be invoked 
     * several times a second to render the game's display window. 
     * @param g The graphics of a GameComponent's buffer that will be drawn
     *          to the screen.
     */
    public void paint(Graphics g);
}
