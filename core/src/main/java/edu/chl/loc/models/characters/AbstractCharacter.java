package edu.chl.loc.models.characters;

import edu.chl.loc.models.characters.utilities.Direction;
import edu.chl.loc.models.characters.utilities.Gender;
import edu.chl.loc.models.items.Inventory;
import edu.chl.loc.models.utilities.Position2D;

/**
 * General representation of player-objects
 *
 * Created by Maxim on 15-04-05.
 * Version 2.0.1
 * @author Maxim
 * Revised by Alexander Karlsson
 */

public abstract class AbstractCharacter {
    private Direction currentDirection;
    private Position2D currentPosition;
    private String characterName;
    private final String DEFAULT_NAME = "Emil";
    private Gender gender = Gender.MALE;

    /**
     * Creates an abstractCharacter on a given position, with North as default direction, and with an empty inventory
     * @param pos The position you want AbstractCharacter to have
     */
    public AbstractCharacter(Position2D pos) {
        currentPosition = pos;
        currentDirection = Direction.NORTH; //default direction is North.
        characterName = DEFAULT_NAME;

    }

    /**
     * Creates an abstractCharacter on a given position, with a given direction and with an empty inventory
     * @param pos The position you want AbstractCharacter to have
     * @param direction The direction you want AbstractCharacter to have
     */
    public AbstractCharacter(Position2D pos, Direction direction) {
        currentDirection = direction;
        currentPosition = pos;
        characterName = DEFAULT_NAME;
    }

    /**
     * Creates an abstractCharacter on a given position, with North as default direction and with a given inventory
     * @param pos   The position you want AbstractCharacter to have
     * @param name  The name you want AbstractCharacter to have
     * @param inventory The inventory you want AbstractCharacter to have
     */
    public AbstractCharacter(Position2D pos, String name, Inventory inventory){
        currentPosition = pos;
        currentDirection = Direction.NORTH; //default direction is North.
        this.characterName = name;
    }


    /**
     * Creates an abstractCharacter with a given position, direction, name and inventory
     * @param pos The position to use
     * @param direction The direction to use
     * @param name The name to use
     */
    public AbstractCharacter(Position2D pos, Direction direction, String name, Gender gender){
        this.currentPosition = pos;
        this.currentDirection = direction;
        this.characterName = name;
        this.gender = gender;

    }
    /**
     *
     * Creates an abstractCharacter on a given position and a given name, with North as default direction, and with an empty inventory
     * @param pos   The position you want AbstractCharacter to have
     * @param name  The name you want AbstractCharacter to have
     */
    public AbstractCharacter(Position2D pos, String name){
        currentPosition = pos;
        currentDirection = Direction.NORTH; //default direction is North.
        this.characterName = name;
    }
    /**
     Move character 1 step in a given direction

     */
    public void move(Direction direction){
        setDirection(direction);
        Position2D tempPos = getPosition().add(getDirection().getDelta());
        setPosition(tempPos);

    }

    /**
     Move character 1 step in the position he is currently facing
     */
    public void move(){
        Direction tempDir = getDirection();
        Position2D tempPos = getPosition().add(tempDir.getDelta());
        setPosition(tempPos);

    }

    /**
     Move character in a given change(delta) in X and Y-coordinates
     */
    public void move(int deltaX, int deltaY){
        Position2D tempPos = getPosition().add(deltaX, deltaY);
        setPosition(tempPos);
    }



    /**
     @return current position in Position2D
     */
    public  Position2D getPosition(){
        return currentPosition;
    }


    /**
     * @param position the new position you want to set for the character
     * Updates current position
     */
    public void setPosition(Position2D position){
        currentPosition = position;
    }


    /**
     * @param x X-coordinate you want to set in the new position
     * @param y Y-coordinate you want to set in the new position
    More natural to use setPlayerPosition(Position2D pos)
     */
    public void setPosition(int x, int y){
        currentPosition = new Position2D(x,y);
    }

    /**
     * Get the next position in the current direction of the character
     * without actually moving it.
     *
     * @return The next position in the character direction
     */
    public Position2D getNextPosition() {
        return new Position2D(currentPosition.add(currentDirection.getDelta()));
    }

    /**
     @param direction direction you want to set
     Set the current direction to a given one
     */
    public void setDirection(Direction direction){
        currentDirection = direction;
    }

    /**
     @return current direction
     */
    public Direction getDirection(){
        return currentDirection;
    }

    public void setName(String name){
        this.characterName = name;
    }

    public String getName(){
        return characterName;
    }


    /**
     *
     * @param o the object you want to compare with
     * @return true if characters have same position, false otherwise
     */
    public boolean equals(Object o){
        if(this==o){
            return true;
        }else if(o==null || (!this.getClass().equals(o.getClass()))){
            return false;
        }else{
            AbstractCharacter other = (AbstractCharacter)o;
            return this.getPosition().equals(other.getPosition());
        }
    }

    /**
     *
     * @return Gender of the character
     */
    public Gender getGender(){
        return this.gender;
    }

    /**
     *
     * @param gender the gender you want to set for this character
     */
    public void setGender(Gender gender){
        this.gender = gender;
    }
}
