package life;

import life.controller.GameController;
import life.model.GameModel;
import life.treads.GameThread;
import life.view.GameOfLife;

import java.util.Scanner;

/**
 * Conway's Game of Life realization.
 * <p>
 * Application consists of model ({@link GameModel}), view ({@link GameOfLife}),
 * controller ({@link GameController}) and game thread ({@link GameThread}) classes.
 * <p>
 * On launch it gets from standard input size (int) of the game field.
 * <p>
 * Thread starts controller, controller switches model states, model contains game logics
 * and share logics with game view, drawn with {@linkplain javax.swing} library.
 * <p>
 * Interface buttons call methods of game thread to control game state (pause, restart).
 *
 * @Author Konstantin Vasilev
 * @Version 1.0
 */

public class Main {
    public static void main(String[] args) {
        final var scanner = new Scanner(System.in);

        final int size = scanner.nextInt();
        final int cyclesCount = 1000;

        scanner.close();

        final var view = new GameOfLife();
        final var model = new GameModel(view, size);
        final var controller = new GameController(cyclesCount, model);

        final var gameThread = new GameThread(controller);

        view.setGameThread(gameThread);
        gameThread.start();
    }
}

