package edu.chl.loc.models.core;

import edu.chl.loc.models.characters.Player;
import edu.chl.loc.models.characters.utilities.Direction;
import edu.chl.loc.models.characters.utilities.Gender;
import edu.chl.loc.models.items.AbstractItem;
import edu.chl.loc.models.map.*;
import edu.chl.loc.models.utilities.Position2D;
import edu.chl.loc.models.utilities.Stats;

/**
 * @author Alexander Håkansson
 * @version 1.0.0
 * @since 2015-04-12
 * revised by Maxim Goretskyy
 */
public class GameModel {

    // The starting position of the player
    public static final Position2D STARTING_POS = new Position2D(0, 0);


    private static Player player = new Player(STARTING_POS, Direction.NORTH, "Emil", Gender.MALE);
    private GameMap gameMap;

    //TODO: come up with a better idea to save score
    private Stats stats;

    public GameModel() {
        gameMap = new GameMap();
        stats = new Stats();
    }

    /**
     * Get the player of the game.
     *
     * @return The player
     */
    public static Player getPlayer() {
        return player;
    }

    /**
     * Get the game map containing all the tiles and NPCs.
     *
     * @return The game map
     */
    public GameMap getGameMap() {
        return this.gameMap;
    }

    public void addPlayerStat(String key, double value){
        stats.addPlayerStat(key, value);
    }

    public double getPlayerStat(String key){
        return stats.getPlayerStat(key);
    }

    public void addHec(double amount){
        stats.addHec(amount);
    }

    public double getHec(){
        return stats.getHec();
    }

    /**
     * Moves character to the given position,
     * performs checks whether it's okay to walk there, and if player is standing on
     * a item/minigame tile, if so then it will perform appropriate actions
     *
     * @param nextPos Position you want the character to move to
     */
    public void moveCharacter(Position2D nextPos) {
                                    //todo fix the layer
        if(!gameMap.isCollidable(new Layer("NonexistableLayerJustFillingOut"), nextPos)){
            player.move();
        }

        //If player stands on an item, do item's action
        ITile tempTile = getGameMap().getTile(new Layer("NonexistableLayerJustFillingOut"),player.getPosition());
        if(tempTile.hasItem()){ //todo discuss to use instanceof later or getClass, will need when we have minigameTile
            ItemTile itemTile = (ItemTile)tempTile; //safe to convert because only itemTile have items
            doItemAction(itemTile);

        }//if tile doesn't have an item do something else, check for minigame
    }

    /**
     * Picks up an item from this itemTile and performs an appropriate action of the item
     * @param itemTile you want to pick an item from
     */

    private void doItemAction(ItemTile itemTile){
        switch(itemTile.getItem().getType()){
            case USE:
                try {
                    itemTile.takeItem().use(this);
                } catch (EmptyTileException e) {
                    e.printStackTrace(); //for debugging
                }
                break;
            case COLLECT:
                //do something else when you have collectable item
                break;
            case QUEST:
                //do something else with quest item
                break;
        }
    }
}
