package org.pac.games;

/**
 * An update function which gets invoked several times a second by
 * the GameComponent update thread.
 */
public interface GameUpdater{
    /**
     * Once added to a GameComponent, this update will be invoked several
     * times a second by the GameComponent's update thread. The exact
     * rate at which this method is called depends on the frame rate of
     * the update thread, which is why the 'dt' parameter is passed.
     * @param dt The time (in seconds) since the last update.
     */
    public void update(double dt);
}
