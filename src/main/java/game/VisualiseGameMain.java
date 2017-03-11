package game;

import org.encog.util.Format;
import stochasticOptimiser.MoveSequenceFactory;
import stochasticOptimiser.OptimisationEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Created by fernando on 09-03-2017.
 */
public class VisualiseGameMain extends JFrame implements Runnable, ActionListener{
    private JLabel labelBestScore;
    private Game2048Panel gamePanel;
    private JButton btnLearning;

    private boolean learningUnderway;
    private boolean requestStop = false;

    private static final String PLAYER_NAME = "MISO - MInimal Stochastic Optimiser";
    private OptimisationEngine engine = null;
    private int bestScoreSoFar = 0;
    private final int NUMBER_OF_SEQUENCES = 1000, SEQUENCE_LENGTH = 5;

    private final byte MAX_VALUE = 3;
    private Random random = new Random(1234);

    public VisualiseGameMain() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(PLAYER_NAME);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(340, 475));
        setFocusable(true);

        /*this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                Game2048 game = gamePanel.getGame();
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    game.resetGame();
                }
                if (!game.canMove()) {
                    game.setMyLose(true);
                }

                if (!game.isMyWin() && !game.isMyLose()) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            game.left();
                            break;
                        case KeyEvent.VK_RIGHT:
                            game.right();
                            break;
                        case KeyEvent.VK_DOWN:
                            game.down();
                            break;
                        case KeyEvent.VK_UP:
                            game.up();
                            break;
                    }
                }

                if (!game.isMyWin() && !game.canMove()) {
                    game.setMyLose(true);
                }

                repaint();
                System.out.println("owo");
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });*/
        //setResizable(false);

        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());

        setupButtonAndScore(content);
        setupGamePanel(content);

        this.btnLearning.addActionListener(this);
        this.initialiseLearning();

    }

    private void setupGamePanel(Container content) {
        gamePanel = new Game2048Panel();
        content.add(gamePanel, BorderLayout.NORTH);
    }

    private void setupButtonAndScore(Container content) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(1, 2));
        scorePanel.add(new JLabel("Best score ever: "));
        scorePanel.add(this.labelBestScore = new JLabel("N/A"));
        buttonPanel.add(scorePanel);
        buttonPanel.add(this.btnLearning = new JButton("Play game"));
        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initialiseLearning() {
        MoveSequenceFactory factory = new MoveSequenceFactory(SEQUENCE_LENGTH, MAX_VALUE, random);
        engine = new OptimisationEngine(NUMBER_OF_SEQUENCES, SEQUENCE_LENGTH, random, factory);
    }


    @Override
    public void run() {

        Game2048 game = gamePanel.getGame();

        if (game.isEnded()) {
            game.resetGame();
        }
        this.btnLearning.setEnabled(false);
        initialiseLearning();

        // update the GUI
        this.btnLearning.setText("Stop game");
        this.btnLearning.setEnabled(true);
        this.learningUnderway = true;

        this.requestStop = false;
        while (!this.requestStop && !game.isEnded()) {
            this.engine.setConfiguration(game.getTiles());
            this.engine.executeLearning();

            Direction[] moves = this.engine.getCurrentBestSequence();
            gamePanel.playAll(new Direction[]{moves[0]});

            //update best score ever
            int score = game.getMyScore();
            if (score > bestScoreSoFar) {
                bestScoreSoFar = score;
                this.labelBestScore.setText(Format.formatInteger(bestScoreSoFar));
            }
        }

        this.btnLearning.setText("Play game");
        this.learningUnderway = false;
    }


    private void beginLearning() {
        Thread t = new Thread(this);
        t.start();
    }

    public void handleLearning() {
        if (this.learningUnderway) {
            this.requestStop = true;
        } else {
            beginLearning();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == this.btnLearning) {
            handleLearning();
        }
    }

    public static void main(String[] args) {
        VisualiseGameMain game = new VisualiseGameMain();
        //game.pack();
        game.setVisible(true);
      //  game.requestFocusInWindow();
    }
}
