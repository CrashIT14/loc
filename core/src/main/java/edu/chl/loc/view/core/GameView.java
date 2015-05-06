package edu.chl.loc.view.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.chl.loc.models.characters.Player;
import edu.chl.loc.models.core.GameModel;
import edu.chl.loc.view.characters.CharacterView;
import edu.chl.loc.view.map.GameMapView;

/**
 * Top level class for the view of loc
 * @author Alexander Karlsson
 * @version 0.6.0
 *
 * Revised by Alexander Håkansson
 */
public class GameView implements Screen{

    public static final int GRID_SIZE = 32; // Size in pixels of each cell in grid
    public static final int RES_X = 1280; // Resolution of game in x-axis
    public static final int RES_Y = 720; // Resolution of game in y-axis

    public static final Texture PLAYER_TEXTURE = new Texture(Gdx.files.internal("player-sheet.png"));

    private static SpriteBatch batch = new SpriteBatch();//Will be used by other views
    private GameModel model;
    private IView playerView;
    private IView gameMapView;

    private Viewport viewport;
    private OrthographicCamera camera;

    private TiledMap tiledMap = new TmxMapLoader().load(Gdx.files.internal("maps/johanneberg.tmx").path());
    private OrthogonalTiledMapRenderer tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

    private final Player player = GameModel.getPlayer();

    private final int[] bottomLayers = {0, 1, 2};
    private final int[] topLayers = {3};

    /**
     * Basic constructor with all necessary values
     * @param model The loc gamemodel
     */
    public GameView(GameModel model){
        this.model = model;
        this.playerView = new CharacterView(GameModel.getPlayer(), PLAYER_TEXTURE);
        this.gameMapView = new GameMapView(this);

        // Setup camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(RES_X, RES_Y, camera);
    }

    /**
     * Gives access to the spritebatch so other view classes render methods
     * can share spritebatch with GameView
     * @return The spritebatch
     */
    public static SpriteBatch getSpriteBatch() {
        return GameView.batch;
    }

    /**
     * Returns the gamemodel this view represents
     * @return The gamemodel
     */
    public GameModel getGameModel() {
        return this.model;
    }

    /**
     * Renders the world represented in the gamemodel this view represents
     * @param deltaTime Time since last rendering
     */
    @Override
    public void render(float deltaTime) {

        camera.position.x = player.getPosition().getX() * GRID_SIZE;
        camera.position.y = player.getPosition().getY() * GRID_SIZE;
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        // Tiled map renderer doesn't use sprite batch
        tiledMapRenderer.setView(camera);

        tiledMapRenderer.render(bottomLayers);
        GameView.batch.begin();
        gameMapView.render(deltaTime);
        playerView.render(deltaTime);
        GameView.batch.end();
        tiledMapRenderer.render(topLayers);
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        //TODO implement this shit
    }

    @Override
    public void hide() {
        //TODO implement this shit
    }

    @Override
    public void pause() {
        //TODO implement this shit
    }

    @Override
    public void resume() {
        //TODO implement this shit
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerView.dispose();
        gameMapView.dispose();
        PLAYER_TEXTURE.dispose();
    }
}
