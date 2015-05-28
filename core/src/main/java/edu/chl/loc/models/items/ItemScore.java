package edu.chl.loc.models.items;

import edu.chl.loc.models.core.GameModel;
import edu.chl.loc.models.core.IGameModel;
import edu.chl.loc.models.core.Stats;

/**
 * Created by Maxim on 15-04-14.
 * @author Maxim Goretskyy
 *
 * Revised by Alexander Håkansson
 */
public class ItemScore extends AbstractItem{
    
    private double hec;
    public ItemScore(String beverageName, double hec){
        super(ItemType.USE, beverageName);
        this.hec = hec;
    }

    public ItemScore(ItemType itemType, String beverageName, double hec){
        super(itemType, beverageName);
        this.hec = hec;
    }

    public ItemScore(ItemScore copyBeer){
        super(copyBeer.getType(), copyBeer.getItemName());
        this.hec = copyBeer.hec;
    }

    public double getHec(){
        return this.hec;
    }

    @Override
    public AbstractItem copy() {
        return new ItemScore(this);
    }

    @Override
    public void use(IGameModel state) {
        state.addHec(hec);
        System.out.println("You drank a " + getItemName());
    }

}