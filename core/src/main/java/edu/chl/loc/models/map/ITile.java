package edu.chl.loc.models.map;

import edu.chl.loc.models.utilities.Position2D;

/**
 * @author Alexander Håkansson, Kevin Hoogendijk
 * @version 1.0.0
 * @since 2015-04-05
 */
public interface ITile {
    /**
     * Returns whether it is possible to collide with the tile or not.
     *
     * @return True if the tile is collidable, false otherwise
     */
    public boolean isCollidable();

    /**
     * Sets collision of the tile
     *
     * @param isCollidable
     */
    public void setIsCollidable(boolean isCollidable);

    /**
     * returns if the tile has an item (is an itemTile)
     * necessary since we have to be able to check on every if it has an item
     *
     * @return True if the tile has an item (is an itemTile)
     */
    public boolean hasItem();

    /**
     * Get the position as a Position2D object
     * @return the position
     */
    public Position2D getPosition();

    /**
     * Two tiles are considered equal if they have the same position
     * @param o the other tile
     * @return true if they are equal otherwise false
     */
    public boolean equals(Object o);

}
