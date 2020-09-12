package life.treads;

import life.controller.GameController;

/**
 * GameThread class realization on {@link Thread}.
 * <p>
 * Starts controller {@link GameController} and change controller's state.
 * <p>
 * Public methods of this class are called by game view ({@link life.view.GameOfLife}).
 * Game thread does know nothing about game view and its methods.
 *
 * @Author Konstantin Vasilev
 * @Version 1.0
 */

public class GameThread extends Thread {
    GameController gameController;

    public GameThread(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public synchronized void start() {
        super.start();
        try {
            gameController.run();
        } catch (InterruptedException ignored) {
        }
    }

    public void playPauseGame() {
        gameController.togglePlayPause();
    }

    public void resetGame() {
        gameController.reset();
    }
}