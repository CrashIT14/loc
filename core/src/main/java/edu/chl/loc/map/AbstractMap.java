package edu.chl.loc.map;

import java.util.*;

/**
 * A general representation of maps.
 *
 * @author Alexander Håkansson
 * @version 2.1.0
 * @since 2015-04-05
 */
public abstract class AbstractMap {

    private final Map<ILayer, List<ITile>> mapTiles = new HashMap<ILayer, List<ITile>>();

    /**
     * Checks if the tile on the specified layer and coordinates is collidable
     * or not. If the tile can't be found, an exception is thrown.
     *
     * @param layer The layer on which to check
     * @param x The x coordinate of the tile to check
     * @param y THe y coordinate of the tile to check
     * @throws java.lang.IllegalArgumentException If the specified tile and layer doesn't exist
     * @return Returns true if the specified tile is collidable, false otherwise
     */
    public boolean isCollidable(ILayer layer, int x, int y) {
        if (!mapTiles.containsKey(layer)) {
            throw new IllegalArgumentException("The layer specified does not exist");
        }

        for (ITile tile : mapTiles.get(layer)) {
            if (tile.getX() == x && tile.getY() == y) {
                return tile.isCollidable();
            }
        }

        throw new IllegalArgumentException("The tile specified at x=" + x +" and y=" + y + ", does not exist");
    }

    /**
     * Gets the tile on the specified layer and coordinates.
     * If the tile can't be found, an exception is thrown.
     *
     * @param layer The layer on which to get
     * @param x The x coordinate of the tile to get
     * @param y THe y coordinate of the tile to get
     * @throws java.lang.IllegalArgumentException If the specified tile and layer doesn't exist
     * @return Returns the specified tile
     */
    public ITile getTile(ILayer layer, int x, int y) {
        if (!mapTiles.containsKey(layer)) {
            throw new IllegalArgumentException("The layer \"" + layer + "\" does not exist");
        }

        for (ITile tile : mapTiles.get(layer)) {
            if (tile.getX() == x && tile.getY() == y) {
                return tile;
            }
        }

        throw new IllegalArgumentException("The tile specified at x=" + x +" and y=" + y + ", does not exist");
    }

    /**
     * Add a tile to the map on the specified layer
     *
     * @param layer The layer on which to place the tile
     * @param tile The tile to add to the map
     */
    public void addTile(ILayer layer, ITile tile) {
        if (!mapTiles.containsKey(layer)) {
            mapTiles.put(layer, new ArrayList<ITile>());
            mapTiles.get(layer).add(tile);
        } else {
            mapTiles.get(layer).add(tile);
        }
    }

    /**
     * Get all the tiles on a specified layer
     *
     * @param layer The layer to get the tiles from
     * @return A list of all tiles on the specified layer
     */
    public List<ITile> getTilesFromLayer(ILayer layer) {
        if (!mapTiles.containsKey(layer)) {
            throw new IllegalArgumentException("The specified layer does not exist");
        }

        return mapTiles.get(layer);
    }

    /**
     * Adds a new layer with no tiles to the map.
     *
     * @param layer The layer to add to the map
     */
    public void addLayer(ILayer layer) {
        if (!mapTiles.containsKey(layer)) {
            mapTiles.put(layer, new ArrayList<ITile>());
        }
    }

    /**
     * Checks if the specified layer exists in the map.
     *
     * @param layer The layer to check
     * @return Returns true if the layer exists, false otherwise
     */
    public boolean layerExists(ILayer layer) {
        return mapTiles.containsKey(layer);
    }

    /**
     * Get a set of all the layers on the map.
     *
     * @return A set with all layers on the map
     */
    public Set<ILayer> getLayers() {
        return mapTiles.keySet();
    }

    /**
     * The number of layers in the map
     *
     * @return Number of layers in the map
     */
    public int getNumberOfLayers() {
            return mapTiles.keySet().size();
    }

    public String toString() {
        return "A map with " + mapTiles.keySet().size() + " layers.";
    }
}