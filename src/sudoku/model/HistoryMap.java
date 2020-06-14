package sudoku.model;

import java.io.Serializable;

/**
 * @author zzx
 * 2020/6/13 19:47
 */
public class HistoryMap implements Serializable {
    private String fileName;
    private String userName;
    private int pass;
    private long time;
    private String maskCell;
    private String games;
    private int[][] maskCellArr;
    private String[][] gamesArr;
    public HistoryMap() {
    }

    public HistoryMap(String fileName, String userName, int pass, long time, String maskCell, String games) {
        this.fileName = fileName;
        this.userName = userName;
        this.pass = pass;
        this.time = time;
        this.maskCell = maskCell;
        this.games = games;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMaskCell() {
        return maskCell;
    }

    public void setMaskCell(String maskCell) {
        this.maskCell = maskCell;
    }

    public String getGames() {
        return games;
    }

    public void setGames(String games) {
        this.games = games;
    }

    public void setMaskCellArr(int[][] maskCellArr) {
        this.maskCellArr = maskCellArr;
    }
    public int[][] getMaskCellArr() {
        return this.maskCellArr;
    }
    public String[][] getGamesArr() {
        return this.gamesArr;
    }
    public void setGamesArr(String[][] gamesArr) {
        this.gamesArr = gamesArr;
    }


}
