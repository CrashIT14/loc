package edu.chl.loc.models.characters.npc;

import edu.chl.loc.models.characters.AbstractCharacter;
import edu.chl.loc.models.characters.utilities.Direction;
import edu.chl.loc.models.characters.utilities.Gender;
import edu.chl.loc.models.utilities.Position2D;

/**
 * Class for non-playable characters
 * @author Alexander Karlsson
 * @author Maxim Goretskyy
 * @version 1.1.3
 */
public abstract class AbstractNPC extends AbstractCharacter {
    private Dialog dialog;
    private int ID;

    /**
     * Creates an AbstractNPC with a given inventory, direction, name and inventory
     * @param pos The AbstractNPC's position
     * @param dir The direction the AbstractNPC should face
     * @param name The name for the AbstractNPC
     * @param gender The NPCs gender
     */
    protected AbstractNPC(Position2D pos, Direction dir, String name, Gender gender, Dialog dialog){
        super(pos, dir, name, gender);
        this.dialog = dialog;
    }


    protected abstract void doAction();

    public int getID() {
        return ID;
    }

    public Dialog getDialog() {
        return dialog;
    }
}
