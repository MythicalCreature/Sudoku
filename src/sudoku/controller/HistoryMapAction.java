package sudoku.controller;

import sudoku.dao.HistoryMapDao;
import sudoku.model.HistoryMap;
import sudoku.view.GameFrame;

import javax.swing.*;
import java.util.List;

/**
 * @author zzx
 * 2020/6/13 19:58
 */
public class HistoryMapAction {
    private HistoryMapDao dao = new HistoryMapDao();
    private String getGames(String[][] arr) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                s.append(arr[i][j]);
                if (j < 8) {
                    s.append("-");
                }
            }
            if (i < 8) {
                s.append(",");
            }
        }
        return s.toString();
    }

    private String getMaskCell(int[][] arr) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                s.append(arr[i][j]);
                if (j < 8) {
                    s.append("-");
                }
            }
            if (i < 8) {
                s.append(",");
            }
        }
        return s.toString();
    }

    public void saveGame(String userName, int[][] maskCell, String[][] games) {
        String inputContent = JOptionPane.showInputDialog(
                null,
                "文件名称:",
                "默认内容"
        );
        HistoryMap hm = new HistoryMap();
        hm.setFileName(inputContent);
        HistoryMap curHm = dao.queryFileName(hm);
        if (curHm != null) {
            JOptionPane.showMessageDialog(null, "文件名存在,请重新输入");
        } else {
            hm = new HistoryMap(inputContent, userName, GameFrame.pass, GameFrame.usedTime, getMaskCell(maskCell), getGames(games));
            //向数据库中的表增加记录
            int count = dao.save(hm);
            if (count != 0) {
                JOptionPane.showMessageDialog(null, "保存成功！");
            }
        }
    }

    public HistoryMap reloadGame(String userName) {
        HistoryMap hm = new HistoryMap();
        hm.setUserName(userName);
        List<String> curHm = dao.queryUserName(hm);
        if (curHm == null) {
            JOptionPane.showMessageDialog(null, "该用户无历史记录,请就此开始游戏");
            return null;
        } else {
            String[] loads = new String[curHm.size()];
            int i = 0;
            for (String s : curHm
            ) {
                loads[i] = s;
                i++;
            }
            Object inputContent = JOptionPane.showInputDialog(
                    null,
                    "读取一项: ",
                    "读档",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    loads,
                    loads[0]
            );
            hm = new HistoryMap();
            hm.setFileName((String) inputContent);
            HistoryMap resHm = dao.queryAll(hm);
            String[] a = resHm.getMaskCell().split(",");
            int[][] arr = new int[9][9];
            for (int k = 0; k < a.length; k++) {
                String[] s = a[k].split("-");
                for (int d = 0; d < s.length; d++) {
                    arr[k][d] = Integer.parseInt(s[d]);
                }
            }
            hm.setMaskCellArr(arr);
            String[] strings = resHm.getGames().split(",");
            String[][] array = new String[9][9];
            for (int k = 0; k < strings.length; k++) {
                String[] s = strings[k].split("-");
                System.arraycopy(s, 0, array[k], 0, s.length);
            }
            hm.setGamesArr(array);
            hm.setPass(resHm.getPass());
            hm.setTime(resHm.getTime());
        }
        return hm;
    }
}
