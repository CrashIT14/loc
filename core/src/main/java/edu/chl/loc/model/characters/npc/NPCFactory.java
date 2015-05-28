package edu.chl.loc.model.characters.npc;

import edu.chl.loc.minigame.IMinigame;
import edu.chl.loc.model.characters.utilities.CharacterUtilities;
import edu.chl.loc.model.characters.utilities.Direction;
import edu.chl.loc.model.characters.utilities.Gender;
import edu.chl.loc.model.items.Inventory;
import edu.chl.loc.model.utilities.Position2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class used for creating NPCs
 * @author Alexander Karlsson
 * @revised by Maxim Goretskyy
 * @version 1.2
 * Revised by Kevin Hoogendijk
 */
public class NPCFactory {
    private static int id = 0;
    private static String name = null;
    private static Gender gender = null;
    private static Direction direction = null;
    private static Inventory npcInventory = null;
    private static Inventory playerInventory = null;
    private static IMinigame minigame = null;
    private static Dialog dialog = null;
    private static List<Integer> setIds = new ArrayList<Integer>();

    //private constructor to prevent from instantiating
    private NPCFactory(){

    }

    /**
     * Sets the id of the NPC currently being built
     * @param id The id of the NPC to create
     */
    public static void setId(int id){
        NPCFactory.id = id;
    }

    /**
     * Sets the name of the NPC currently being built
     * @param name The name of the NPC to create
     */
    public static void setName(String name){
        NPCFactory.name = name;
    }

    /**
     * Sets the gender of the NPC currently being built
     * @param gender The gender of the NPC to create
     */
    public static void setGender(Gender gender){
        NPCFactory.gender = gender;
    }

    /**
     * Sets the direction of the NPC currently being built
     * @param direction The direction of the NPC to create
     */
    public static void setDirection(Direction direction){
        NPCFactory.direction = direction;
    }

    /**
     * Sets the inventory of the NPC being built
     * @param npcInventory The inventory of the NPC being built
     * @param playerInventory The inventory of the player associated with this NPC
     * @throws CannotSetThisValueException If the current build already has a minigame set a inventory cant be set
     */
    public static void setInventory(Inventory npcInventory, Inventory playerInventory) throws CannotSetThisValueException{
        if(NPCFactory.isMinigameSet()){
            throw new CannotSetThisValueException("Cannot set inventory when minigame is set");
        }else{
            NPCFactory.npcInventory = npcInventory;
            NPCFactory.playerInventory = playerInventory;
        }
    }

    /**
     * Sets the minigame of the NPC being built
     * @param minigame The minigame of the NPC being built
     * @throws CannotSetThisValueException If the current build already has an inventory set a minigame cant be set
     */
    public static void setMinigame(IMinigame minigame) throws CannotSetThisValueException{
        if(NPCFactory.isInventorySet()){
            throw new CannotSetThisValueException("Cannot set minigame when inventory is set");
        }else {
            NPCFactory.minigame = minigame;
        }
    }

    /**
     * Sets the dialog of the NPC being built
     * @param dialog The dialog of the NPC to create
     */
    public static void setDialog(Dialog dialog){
        NPCFactory.dialog = dialog;
    }

    /**
     * Checks if the current build has an inventory set
     * @return True if an inventory is set, false otherwise
     */
    public static boolean isInventorySet(){
        return NPCFactory.npcInventory != null;
    }

    /**
     * Checks if the current build has a minigame set
     * @return True if a minigame is set, false otherwise
     */
    public static boolean isMinigameSet(){
        return NPCFactory.minigame != null;
    }

    /**
     * Resets all the values of the current build
     */
    public static void reset(){
        NPCFactory.name = null;
        NPCFactory.gender = null;
        NPCFactory.direction = null;
        NPCFactory.npcInventory = null;
        NPCFactory.playerInventory = null;
        NPCFactory.minigame = null;
        NPCFactory.id = 0;
        NPCFactory.dialog = null;
    }

    /**
     * Builds and returns the current build using the values from the set methods
     * @param position The position the new NPC should have
     * @return An NPC with values from set methods and given position
     */
    public static AbstractNPC build(Position2D position){
        int id;
        Gender gender;
        String name;
        Direction direction;
        Inventory npcInventory;
        Inventory playerInventory;
        IMinigame minigame;
        Dialog dialog;

        if(NPCFactory.id != 0 && !NPCFactory.setIds.contains(NPCFactory.id)){
            id = NPCFactory.id;
        }else{//If the idnumber is taken or unset a free id over 4000 will be chosen
            id = 4000;
            while(NPCFactory.setIds.contains(id)){
                id++;
            }
        }


        if(NPCFactory.gender == null){
            gender = CharacterUtilities.generateGender();
        }else{
            gender = NPCFactory.gender;
        }

        if(NPCFactory.name == null){
            name = CharacterUtilities.generateName(gender);
        }else{
            name = NPCFactory.name;
        }

        if(NPCFactory.direction == null){
            direction = Direction.NORTH;//Standard direction is north
        }else{
            direction = NPCFactory.direction;
        }

        if(NPCFactory.dialog == null){
            dialog = CharacterUtilities.generateDialog();
        }else{
            dialog = NPCFactory.dialog;
        }

        NPCFactory.setIds.add(id);

        if(isInventorySet()){
            npcInventory = NPCFactory.npcInventory;
            playerInventory = NPCFactory.playerInventory;
            NPCFactory.reset();
            return new ItemNPC(position,direction,id,name,gender,npcInventory,dialog,playerInventory);
        }

        if(isMinigameSet()){
            minigame = NPCFactory.minigame;
            NPCFactory.reset();
            return new MinigameNPC(position,direction,id,name,gender,minigame,dialog);
        }

        NPCFactory.reset();
        return new StandardNPC(position,direction,id,name,gender,dialog);
    }
}
