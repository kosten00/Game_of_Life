package life.controller;

import life.model.GameModel;

/**
 * Game controller class.
 * <p>
 * Manipulates game model, by changing it iteration (generation).
 * <p>
 * Public methods of this class are called by game thread {@link life.treads.GameThread} class.
 * Game controller does know nothing about game thread and its methods.
 *
 * @Author Konstantin Vasilev
 * @Version 1.0
 */

public class GameController {
    private final GameModel gameModel;
    private final int generationsCount;

    private int currentGenerationNumber;
    private boolean isPaused;

    /**
     * Constructs game controller.
     *
     * @param generationsCount initial count of generations.
     * @param gameModel        game model with game logics.
     */
    public GameController(int generationsCount, GameModel gameModel) {
        this.generationsCount = generationsCount;
        this.gameModel = gameModel;

        isPaused = false;
    }

    /**
     * Switches game model state (generations) in cycle with 200 milliseconds
     * delay until init generations count is over.
     * <p>
     * Watches if isPaused boolean is set to false to make a change in game model.
     *
     * @throws InterruptedException when thread is interrupted.
     */
    public void run() throws InterruptedException {
        currentGenerationNumber = generationsCount;

        while (currentGenerationNumber != 0) {
            Thread.sleep(200L);
            if (!isPaused) {
                gameModel.createGeneration();
                gameModel.updateGeneration(currentGenerationNumber);
                currentGenerationNumber--;
            }
        }
    }

    /**
     * Resets game model by returning to init (first generation) state.
     * Reset generation number to init number of generation to prolong run() method cycle.
     */
    public void reset() {
        gameModel.startNewGame();
        currentGenerationNumber = generationsCount;
    }

    public void togglePlayPause() {
        isPaused = !isPaused;
    }
}
