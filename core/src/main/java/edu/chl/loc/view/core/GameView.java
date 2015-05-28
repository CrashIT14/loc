package edu.chl.loc.view.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.chl.loc.models.characters.Player;
import edu.chl.loc.models.characters.npc.Dialog;
import edu.chl.loc.models.core.GameModel;
import edu.chl.loc.models.core.IGameModel;
import edu.chl.loc.models.map.ITile;
import edu.chl.loc.models.map.Layer;
import edu.chl.loc.view.characters.CharacterView;
import edu.chl.loc.view.characters.DialogView;
import edu.chl.loc.view.map.GameMapView;
import edu.chl.loc.view.menu.GameMenuView;
import edu.chl.loc.view.music.Playlist;

/**
 * Top level class for the view of loc
 * @author Alexander Karlsson
 * @version 1.0.0
 *
 * Revised by Alexander Håkansson
 * Revised by Kevin Hoogendijk
 */
public class GameView implements Screen{

    public static final int GRID_SIZE = 32; // Size in pixels of each cell in grid
    public static final int RES_X = 1024; // Resolution of game in x-axis
    public static final int RES_Y = 576; // Resolution of game in y-axis

    public static final Texture PLAYER_TEXTURE = new Texture(Gdx.files.internal("player-sheet.png"));

    private static SpriteBatch batch = new SpriteBatch();//Will be used by other views
    private IGameModel model;
    private IView playerView;
    private IView gameMapView;
    private DialogView dialogView;
    private GameMenuView gameMenuView;
    private StatsView statsView;

    private static OrthographicCamera camera = new OrthographicCamera();
    private static Viewport viewport = new FitViewport(RES_X, RES_Y, camera);

    private TiledMap tiledMap = new TmxMapLoader().load(Gdx.files.internal("maps/johanneberg.tmx").path());
    private OrthogonalTiledMapRenderer tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

    private final Player player;

    private final BitmapFont font = new BitmapFont();

    // Music tracks
    private static final Music musicNyan = Gdx.audio.newMusic(Gdx.files.internal("music/nyan.mp3"));
    private static final Music musicRickroll = Gdx.audio.newMusic(Gdx.files.internal("music/rickroll.mp3"));
    private static final Music musicSax = Gdx.audio.newMusic(Gdx.files.internal("music/sax.mp3"));
    private static final Music musicTrololo = Gdx.audio.newMusic(Gdx.files.internal("music/trololo.mp3"));
    private static final Music marioLevels = Gdx.audio.newMusic(Gdx.files.internal("music/marioLevels.mp3"));

    private Playlist gameMusic;

    // ground, groundDetail and building layer
    private final int[] bottomLayers = {0, 1, 2};
    // buildingRoof layer
    private final int[] topLayers = {3};

    /**
     * Basic constructor with all necessary values
     * @param model The loc gamemodel
     */
    public GameView(IGameModel model){
        this.model = model;
        this.player  = model.getPlayer();
        this.playerView = new CharacterView(model.getPlayer(), PLAYER_TEXTURE);
        this.gameMapView = new GameMapView(this);
        this.dialogView = new DialogView();
        this.gameMenuView = new GameMenuView(model.getGameMenu());
        this.statsView = new StatsView(model.getStatsWindow());

        // Setup camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(RES_X, RES_Y, camera);

        this.gameMusic = new Playlist(true, true, musicNyan, musicRickroll, musicSax, musicTrololo, marioLevels);
    }

    /**
     * Gives access to the spritebatch so other view classes render methods
     * can share spritebatch with GameView
     * @return The spritebatch
     */
    public static SpriteBatch getSpriteBatch() {
        return GameView.batch;
    }

    public static Viewport getViewport(){
        return GameView.viewport;
    }

    public static Camera getCamera(){
        return GameView.camera;
    }

    /**
     * Returns the gamemodel this view represents
     * @return The gamemodel
     */
    public IGameModel getGameModel() {
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

        Vector2 viewportOrigo = new Vector2(camera.position.x - viewport.getWorldWidth()/2,
                camera.position.y + viewport.getWorldHeight()/2);

        batch.setProjectionMatrix(camera.combined);

        // Tiled map renderer doesn't use sprite batch
        tiledMapRenderer.setView(camera);

        tiledMapRenderer.render(bottomLayers);
        GameView.batch.begin();
        gameMapView.render(deltaTime, GameView.batch);
        playerView.render(deltaTime, GameView.batch);

        font.draw(batch, model.getHec() + " HP", viewportOrigo.x + 16, viewportOrigo.y - 16);
        GameView.batch.end();

        TiledMapTileLayer rooflayer = (TiledMapTileLayer) tiledMap.getLayers().get("buildingRoof");
        if (isPlayerUnderRoof()) {
            rooflayer.setOpacity(0.2f);
        } else {
            rooflayer.setOpacity(1.0f);
        }

        tiledMapRenderer.render(topLayers);
        if(model.isDialogActive()){
            dialogView.setDialog(model.getActiveDialog(), model.getActiveSpeakerName());
            dialogView.render(deltaTime, GameView.batch);
        }

        if (model.getGameMenu().isMenuOpen()) {
            gameMenuView.render(deltaTime, batch);
        }

        if(model.isStatsActive()){
            statsView.render(deltaTime, batch);
        }

    }

    private boolean isPlayerUnderRoof() {
        for (ITile tile : getGameModel().getGameMap().getTilesFromLayer(new Layer("buildingRoof"))) {
            if (tile.getPosition().equals(player.getPosition())) {
                return true;
            }
        }

        return false;
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        gameMusic.play();
    }

    @Override
    public void hide() {
        gameMusic.stop();
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
        dialogView.dispose();
        statsView.dispose();
    }
}
