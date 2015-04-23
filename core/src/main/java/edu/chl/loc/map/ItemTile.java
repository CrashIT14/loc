package edu.chl.loc.map;

import edu.chl.loc.items.AbstractItem;
import edu.chl.loc.utilities.Position2D;

/**
 * Created by maxim on 2015-04-17.
 * @author Maxim Goretskyy
 * Class representing an ItemTile, a Tile with an AbstractItem object on it.
 *
 * Revised by Alexander Håkansson
 */
public class ItemTile implements ITile {

    private AbstractItem item;
    private boolean isCollidable; //default value is false
    private Position2D currentPosition;
    private boolean isItemSet = true;

    /**
     *
     * @param item Item object you want to place on this tile
     * @param position The position you want this tile to have
     */
    public ItemTile(AbstractItem item, Position2D position){
        this.item = item.copy();
        currentPosition = position.copy();

    }

    @Override
    public boolean isCollidable() {
        return this.isCollidable;
    }

    @Override
    public void setIsCollidable(boolean isCollidable) {
        this.isCollidable = isCollidable;

    }

    /**
     * //Todo Discuss what happens when a player picks an item from a tile.
     * //Do we "destroy" the tile, or make an item notify the tile and set hasItem to false?
     * //By "Destroy" - exchange ItemTile to a Tile.
     * @return true if the tile has an item, false if not
     */
    @Override
    public boolean hasItem() {
        return isItemSet;
    }

    @Override
    public Position2D getPosition() {
        return this.currentPosition;
    }

    /**
     *
     * @param pos you want to compare with
     * @return true if the given pos is the same pos as this tile
     */
    public boolean isOverlapping(Position2D pos){
        return currentPosition.equals(pos);
    }

    /**
     * Sets isItemSet, used when removing an item.
     * Temporary solution so far.
     */
    public void unsetItem(){
        isItemSet = false;
    }

    /**
     *
     * @return the copy item that is on this tile
     */
    public AbstractItem getItem(){

        return this.item.copy();
    }

    /**
     *
     * @return the copy of the item that is on this site AND unsetsItem from this tile.
     */
    public AbstractItem takeItem() throws EmptyTileException{
        if(isItemSet) {
            unsetItem();
            return this.item.copy();
        }
        throw new EmptyTileException("No item on this tile"); //Todo throw own exception later on
    }

}
