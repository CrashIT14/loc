package edu.chl.loc.models.map;

import edu.chl.loc.models.items.ItemScore;
import edu.chl.loc.models.utilities.Position2D;
import org.junit.Test;

/**
 * @author Alexander Håkansson
 * @version 1.0.0
 * @since 2015-04-30
 */
public class ItemTileTest {

    @Test (expected = EmptyTileException.class)
    public void testTakeItemFromEmptyTile() throws EmptyTileException {
        ItemTile itemTile = new ItemTile(new ItemScore("Test", 3), new Position2D());
        itemTile.unsetItem();
        itemTile.takeItem();
    }
}
