package life.view;

import life.treads.GameThread;

import javax.swing.*;
import java.awt.*;

/**
 * Realization of {@link JFrame} class.
 * Class that draws game frame (window) and game board.
 * <p>
 * Interface buttons actions call methods of thread {@link GameThread} class to pause or restart game.
 * <p>
 * Public methods of this class are called by game model {@link life.model.GameModel} class.
 * Game view does know nothing about game model and its methods.
 */
public class GameOfLife extends JFrame {
    private JPanel[][] cells;

    private GameThread gameThread;

    private final JPanel menu;
    private JPanel board;

    private final JLabel generationLabel;
    private final JLabel aliveLabel;

    private final JButton resetButton;
    private final JToggleButton playToggleButton;

    public GameOfLife() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        generationLabel = new JLabel();
        generationLabel.setName("GenerationLabel");
        generationLabel.setText("Generation #" + 0);

        aliveLabel = new JLabel();
        aliveLabel.setName("AliveLabel");
        aliveLabel.setText("Alive: " + 0);

        resetButton = new JButton();
        resetButton.setName("ResetButton");
        resetButton.setText("Reset");

        playToggleButton = new JToggleButton();
        playToggleButton.setName("PlayToggleButton");
        playToggleButton.setText("Pause");

        menu = new JPanel();
        menu.add(generationLabel);
        menu.add(aliveLabel);
        menu.add(playToggleButton);
        menu.add(resetButton);

        add(menu);
    }

    public void setGameThread(GameThread gameThread) {
        this.gameThread = gameThread;
        initActionListeners();
    }

    private void initActionListeners() {
        resetButton.addActionListener(e -> {
            gameThread.resetGame();
        });
        playToggleButton.addActionListener(e -> {
            if ("Pause".equals(playToggleButton.getText())) {
                playToggleButton.setText("Play");
                gameThread.playPauseGame();
                return;
            }
            playToggleButton.setText("Pause");
            gameThread.playPauseGame();
        });
    }

    /**
     * Init game board on start and restart and adds it to main frame (this).
     * <p>
     * If game was restarted, removes all components from main frame and than adds them again.
     *
     * @param size size of game "universe".
     */
    public void fillCells(int size) {
        if (board != null) {
            removeAll();
            add(menu);
        }
        board = new JPanel();

        board.setLayout(new GridLayout(size, size, 1, 2));
        cells = new JPanel[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                var cell = new JPanel();

                cells[i][j] = cell;
                cell.setBackground(Color.GRAY);
                board.add(cell);
            }
        }
        add(board);
    }

    public void setCurrentGenerationLabelText(int generationNumber) {
        generationLabel.setText("Generation #" + generationNumber);
    }

    public void setAliveCellsCountLabelText(int x, int y, boolean isAlive, int aliveCount) {
        cells[x][y].setBackground(isAlive ? Color.BLACK : Color.GRAY);
        aliveLabel.setText("Alive: " + aliveCount);
    }
}
