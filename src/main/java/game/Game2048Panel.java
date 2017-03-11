package game;/*
 * Copyright 1998-2014 Konstantin Bulenkov http://bulenkov.com/about
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import javax.swing.*;
import java.awt.*;

/**
 * @author Konstantin Bulenkov
 * @modified by Fernando Silva
 */
public class Game2048Panel extends JPanel {
    private static final Color BG_COLOR = new Color(0xbbada0);
    private static final String FONT_NAME = "Arial";
    private static final int TILE_SIZE = 64;
    private static final int TILES_MARGIN = 16;

    private Game2048 game;


    public Game2048Panel() {
        game = new Game2048();
        setPreferredSize(new Dimension(340, 400));
        setFocusable(true);
       /* addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
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
            }
        });*/
        game.resetGame();
        //  this.startPlayer();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        Tile[] myTiles = game.getTiles();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                drawTile(g, myTiles[x + y * 4], x, y);
            }
        }
    }

    private void drawTile(Graphics g2, Tile tile, int x, int y) {
        Graphics2D g = ((Graphics2D) g2);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        int value = tile.getValue();
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y);
        g.setColor(tile.getBackground());
        g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, 14, 14);
        g.setColor(tile.getForeground());
        final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
        final Font font = new Font(FONT_NAME, Font.BOLD, size);
        g.setFont(font);

        String s = String.valueOf(value);
        final FontMetrics fm = getFontMetrics(font);

        final int w = fm.stringWidth(s);
        final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

        if (value != 0)
            g.drawString(s, xOffset + (TILE_SIZE - w) / 2, yOffset + TILE_SIZE - (TILE_SIZE - h) / 2 - 2);

        if (game.isMyWin() || game.isMyLose()) {
            g.setColor(new Color(255, 255, 255, 30));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(new Color(78, 139, 202));
            g.setFont(new Font(FONT_NAME, Font.BOLD, 48));
            if (game.isMyWin()) {
                g.drawString("You won!", 68, 150);
            }
            if (game.isMyLose()) {
                g.drawString("Game over!", 50, 130);
                //   g.drawString("You lose!", 64, 200);
            }
            if (game.isMyWin() || game.isMyLose()) {
                g.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
                g.setColor(new Color(128, 128, 128, 128));
                //  g.drawString("Press ESC to play again", 80, getHeight() - 40);
            }
        }
        g.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
        g.drawString("Score: " + game.getMyScore(), 200, 365);

    }

    private static int offsetCoors(int arg) {
        return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN;
    }

    public void playAll(Direction[] moves) {
        for (Direction d : moves) {
            this.game.play(d);
            repaint();
        }
    }

    public Game2048 getGame() {
        return game;
    }
}