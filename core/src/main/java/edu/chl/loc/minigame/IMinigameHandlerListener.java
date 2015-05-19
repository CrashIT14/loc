package edu.chl.loc.minigame;

/**
 * @author Alexander Håkansson
 * @version 1.0.0
 * @since 2015-05-11
 */
public interface IMinigameHandlerListener {
    public void minigameFinished(IMinigame minigame);
    public void startMinigame(IMinigame minigame);
}
