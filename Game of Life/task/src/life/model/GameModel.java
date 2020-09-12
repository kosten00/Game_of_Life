package life.model;

import life.view.GameOfLife;

import java.util.Arrays;
import java.util.Random;

/**
 * Class containing game logics. Changes game view based on that logics.
 * <p>
 * Public methods of this class are called by game controller {@link life.controller.GameController} class.
 * Game model does know nothing about game controller and its methods.
 *
 * @Author Konstantin Vasilev
 * @Version 1.0
 */

public class GameModel {
    private final GameOfLife gameView;
    private Cell[][] cells;
    private final int size;
    private final Random random = new Random();

    /**
     * Constructs game model class.
     *
     * @param gameView interface view class.
     * @param size     size of game "universe" matrix.
     */
    public GameModel(GameOfLife gameView, int size) {
        this.size = size;
        this.gameView = gameView;

        startNewGame();
        gameView.fillCells(size);
        gameView.setVisible(true);
    }

    /**
     * Allocates empty game matrix in memory with passed through constructor size.
     */
    public void startNewGame() {
        cells = new Cell[size][size];
    }

    /**
     * If game is just started and cells matrix is empty (cells[i][j] == null),
     * creates new cells with random boolean status in 2D cycle.
     * <p>
     * If game is in progress, calculates alive status in next generation of
     * cell with i, j coordinates based on neighbor cells alive status in
     * this generation and current generation alive status of this cell.
     */
    public void createGeneration() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cells[i][j] == null) {
                    cells[i][j] = new Cell(random.nextBoolean());
                    continue;
                }
                var cell = cells[i][j];
                cell.setAliveNextGen(willSurviveCurrentGen(getNeighbours(i, j), cell.isAliveCurrentGen()));
            }
        }
    }

    /**
     * Switches alive statuses of cell with coordinates i, j. See {@link Cell} class logics.
     * <p>
     * This method calls game view to draw current game field state.
     *
     * @param cyclesCount value passed to view to show correct number in interface (generation counter)
     */
    public void updateGeneration(int cyclesCount) {
        gameView.setCurrentGenerationLabelText(cyclesCount);

        int aliveCount = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                var cell = cells[i][j];
                boolean aliveStatus = cell.isAliveNextGen();

                cell.setAliveCurrentGen(aliveStatus);
                if (cell.isAliveCurrentGen()) {
                    aliveCount++;
                }
                gameView.setAliveCellsCountLabelText(i, j, aliveStatus, aliveCount);

                cell.setAliveNextGen(false);
            }
        }
    }

    /**
     * Helper method to check if cell will be alive in next generation.
     *
     * @param neighbours        Cells around checked cell.
     * @param isAliveCurrentGen Checked cell current generation alive status.
     * @return Boolean value for alive next generation status for checked cell.
     */
    private boolean willSurviveCurrentGen(Cell[] neighbours, boolean isAliveCurrentGen) {
        long aliveNeighborsCount = Arrays.stream(neighbours).filter(Cell::isAliveCurrentGen).count();

        if (isAliveCurrentGen) {
            return aliveNeighborsCount == 2 || aliveNeighborsCount == 3;
        }
        return aliveNeighborsCount == 3;
    }

    /**
     * Helper method to get checked cell's neighbours to calculate cell's next generation alive status.
     *
     * @param x coordinate x (cells[i][]).
     * @param y coordinate y (Cells[][j]).
     * @return matrix of neighbour cells of checked cell.
     */
    public Cell[] getNeighbours(int x, int y) {
        return new Cell[]{cells[sub(x)][sub(y)], cells[sub(x)][y], cells[sub(x)][add(y)],
                cells[x][sub(y)], cells[x][add(y)],
                cells[add(x)][sub(y)], cells[add(x)][y], cells[add(x)][add(y)]};
    }

    /**
     * Helper method to calculate correct position of neighbour cell within Cells[][] bounds.
     *
     * @param position coordinate in Cells[][] of checked cell.
     * @return position of neighbour cell.
     */
    private int sub(int position) {
        return position > 0 ? position - 1 : size - 1;
    }

    private int add(int position) {
        return position + 1 == size ? 0 : position + 1;
    }
}
