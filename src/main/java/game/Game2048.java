package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by fernando on 09-03-2017.
 */
public class Game2048 {

    private Tile[] myTiles;
    boolean myWin = false;
    boolean myLose = false;
    int myScore = 0;


    //start a game from a given configuration
    public Game2048(Tile[] configuration) {
        this.myTiles = new Tile[configuration.length];
        for (int i = 0; i < myTiles.length; i++)
            myTiles[i] = new Tile(configuration[i].getValue());

        myScore = 0;
        myWin = false;
        myLose = false;
    }

    //start a game from scratch
    public Game2048() {}

    public void play(Direction direction) {
        if (!canMove()) {
            setMyLose(true);
        }

        if (!isMyWin() && !isMyLose()) {
            switch (direction) {
                case LEFT:
                    left();
                    break;
                case RIGHT:
                    right();
                    break;
                case DOWN:
                    down();
                    break;
                case UP:
                    up();
                    break;
            }
        }

        if (!isMyWin() && !canMove()) {
            setMyLose(true);
        }
    }


    public void resetGame() {
        myScore = 0;
        myWin = false;
        myLose = false;
        myTiles = new Tile[4 * 4];
        for (int i = 0; i < myTiles.length; i++) {
            myTiles[i] = new Tile();
        }
        addTile();
        addTile();
    }

    public void left() {
        boolean needAddTile = false;
        for (int i = 0; i < 4; i++) {
            Tile[] line = getLine(i);
            Tile[] merged = mergeLine(moveLine(line));
            setLine(i, merged);
            if (!needAddTile && !compare(line, merged)) {
                needAddTile = true;
            }
        }

        if (needAddTile) {
            addTile();
        }
    }

    private void setLine(int index, Tile[] re) {
        System.arraycopy(re, 0, getTiles(), index * 4, 4);
    }

    public void right() {
        myTiles = rotate(180);
        left();
        myTiles = rotate(180);
    }

    public void up() {
        myTiles = rotate(270);
        left();
        myTiles = rotate(90);
    }

    public void down() {
        myTiles = rotate(90);
        left();
        myTiles = rotate(270);
    }

    private Tile tileAt(int x, int y) {
        return myTiles[x + y * 4];
    }

    private void addTile() {
        List<Tile> list = availableSpace();
        if (!availableSpace().isEmpty()) {
            int index = (int) (Math.random() * list.size()) % list.size();
            Tile emptyTime = list.get(index);
            emptyTime.setValue(Math.random() < 0.9 ? 2 : 4);
        }
    }

    private List<Tile> availableSpace() {
        final List<Tile> list = new ArrayList<Tile>(16);
        for (Tile t : myTiles) {
            if (t.isEmpty()) {
                list.add(t);
            }
        }
        return list;
    }

    private boolean isFull() {
        return availableSpace().size() == 0;
    }

    boolean canMove() {
        if (!isFull()) {
            return true;
        }
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Tile t = tileAt(x, y);
                if ((x < 3 && t.getValue() == tileAt(x + 1, y).getValue())
                        || ((y < 3) && t.getValue() == tileAt(x, y + 1).getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean compare(Tile[] line1, Tile[] line2) {
        if (line1 == line2) {
            return true;
        } else if (line1.length != line2.length) {
            return false;
        }

        for (int i = 0; i < line1.length; i++) {
            if (line1[i].getValue() != line2[i].getValue()) {
                return false;
            }
        }
        return true;
    }

    private Tile[] rotate(int angle) {
        Tile[] newTiles = new Tile[4 * 4];
        int offsetX = 3, offsetY = 3;
        if (angle == 90) {
            offsetY = 0;
        } else if (angle == 270) {
            offsetX = 0;
        }

        double rad = Math.toRadians(angle);
        int cos = (int) Math.cos(rad);
        int sin = (int) Math.sin(rad);
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                int newX = (x * cos) - (y * sin) + offsetX;
                int newY = (x * sin) + (y * cos) + offsetY;
                newTiles[(newX) + (newY) * 4] = tileAt(x, y);
            }
        }
        return newTiles;
    }

    private Tile[] moveLine(Tile[] oldLine) {
        LinkedList<Tile> l = new LinkedList<Tile>();
        for (int i = 0; i < 4; i++) {
            if (!oldLine[i].isEmpty())
                l.addLast(oldLine[i]);
        }
        if (l.size() == 0) {
            return oldLine;
        } else {
            Tile[] newLine = new Tile[4];
            ensureSize(l, 4);
            for (int i = 0; i < 4; i++) {
                newLine[i] = l.removeFirst();
            }
            return newLine;
        }
    }

    private Tile[] mergeLine(Tile[] oldLine) {
        LinkedList<Tile> list = new LinkedList<Tile>();
        for (int i = 0; i < 4 && !oldLine[i].isEmpty(); i++) {
            int num = oldLine[i].getValue();
            if (i < 3 && oldLine[i].getValue() == oldLine[i + 1].getValue()) {
                num *= 2;
                myScore += num;
                /*int ourTarget = 2048;
                if (num == ourTarget) {
                    myWin = true;
                }*/
                i++;
            }
            list.add(new Tile(num));
        }
        if (list.size() == 0) {
            return oldLine;
        } else {
            ensureSize(list, 4);
            return list.toArray(new Tile[4]);
        }
    }

    private static void ensureSize(java.util.List<Tile> l, int s) {
        while (l.size() != s) {
            l.add(new Tile());
        }
    }

    private Tile[] getLine(int index) {
        Tile[] result = new Tile[4];
        for (int i = 0; i < 4; i++) {
            result[i] = tileAt(i, index);
        }
        return result;
    }

    public Tile[] getTiles() {
        return myTiles;
    }

    public boolean isMyWin() {
        return myWin;
    }

    public boolean isMyLose() {
        return myLose;
    }

    public int getMyScore() {
        return myScore;
    }

    public void setMyLose(boolean myLose) {
        this.myLose = myLose;
    }

    public boolean isEnded() {
        return !canMove();
    }


}
