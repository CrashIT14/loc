package edu.chl.loc.models.core;

import edu.chl.loc.models.characters.Player;
import edu.chl.loc.models.characters.npc.Dialog;
import edu.chl.loc.models.map.GameMap;
import edu.chl.loc.models.menu.GameMenu;
import edu.chl.loc.models.utilities.Position2D;

/**
 * Created by maxim on 15-05-27.
 */
public interface IGameModel {
    public Player getPlayer();
    public GameMap getGameMap();
    public void addPlayerStat(String key, double value);
    GameMenu getGameMenu();
    boolean isDialogActive();
    boolean isStatsActive();
    Dialog getActiveDialog();
    void setIsDialogActive(boolean isDialogActive);
    StatsWindow getStatsWindow();
    void setIsStatsActive(boolean isStatsActive);
    void moveCharacter(Position2D nextPos);
    void setActiveDialog(Dialog dialog);
    void setActiveSpeakerName(String activeSpeakerName);
    Stats getStats();
    double getHec();
    String getActiveSpeakerName();


}
