package edu.chl.loc.models.characters.npc;

import edu.chl.loc.models.characters.utilities.Direction;
import edu.chl.loc.models.characters.utilities.Gender;
import edu.chl.loc.models.items.Inventory;
import edu.chl.loc.models.utilities.Position2D;

/**
 * Created by maxim on 15-04-24.
 * @author Maxim Goretskyy
 * Revised by Alexander Karlsson
 */
public class ItemNPC extends AbstractNPC{
    private Inventory inventory;


    protected ItemNPC(Position2D pos, Direction dir, String name,  Gender gender, Inventory inventory, Dialog dialog) {
        super(pos, dir, name, gender, dialog);
        this.inventory = inventory.copy();
    }

    @Override
    protected void doAction() {
        //Todo action to give an item
    }

    /**
     *
     * @return Inventory that this NPC holds
     */
    public Inventory getInventory(){
        return this.inventory;
    }
}
