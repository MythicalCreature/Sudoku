package sudoku.controller;

import sudoku.model.HistoryMap;
import sudoku.model.InitMap;
import sudoku.utils.CellUtils;
import sudoku.utils.StringUtils;
import sudoku.view.GameFrame;
import sudoku.view.SelectNumFrame;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author zzx
 * 2020/6/3 15:17
 */
public class CellAction extends JPanel implements MouseListener {
    private CellUtils[][] cells = new CellUtils[9][9];
    public int[][] maps;
    private SelectNumFrame selectNum;
    private static final int ALL_PAST = 10;
    private static final int SIZE = 9;
    private InitMap map = new InitMap();
    /**
     * 生成构造器
     */
    public CellAction() {
        GameFrame.usedTime = 0;
        maps = map.generate(GameFrame.pass);
        //加载数独区
        setLayout(null);
        for (int i = 0; i <SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                //创建单元格
                cells[i][j] = new CellUtils();
            }
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                //创建单元格
                cells[i][j] = new CellUtils();
                //设置位置
                cells[i][j].setLocation(20 + (j * 50) + (j / 3) * 10, 30 + (i * 50) + (i / 3) * 10);
                if (maps[i][j] > 0) {
                    cells[i][j].setText("" + maps[i][j]);
                    //该空有数据不能编辑
                    cells[i][j].setEnabled(false);
                } else {
                    cells[i][j].setText("");
                    //添加鼠标监听
                    cells[i][j].addMouseListener(this);
                }
                add(cells[i][j]);
            }
            //检查是否完成
            checkFinish();
        }
    }


    /**
     * 检查是否完成
     */
    private void checkFinish() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!check(i, j)) {
                    return;
                }
            }
        }
        //停止用户用时计时器
        GameFrame.userTimeAction.stop();
        //清除所有cells监听
        clearAllListener();
        if (GameFrame.pass == ALL_PAST) {
            int o = JOptionPane.showConfirmDialog(this, "您已经通关了，是否重头开始？", "闯关成功", JOptionPane.YES_NO_CANCEL_OPTION);
            if (o == 1) {
                System.exit(0);
            } else {
                GameFrame.pass = 1;
            }
        } else {
            int o = JOptionPane.showConfirmDialog(this, "恭喜您通过本关！用时：" + GameFrame.usedTime + "秒\n是否进入下一关", "闯关成功", JOptionPane.YES_NO_CANCEL_OPTION);
            if (o == 0) {
                //闯关数加1
                GameFrame.pass += 1;
                reLoad();
                GameFrame.userTimeAction.start();
            } else {
                System.exit(0);
            }
        }
    }

    /**
     * 重新加载进入下一关
     */
    private void reLoad() {
        GameFrame.usedTime = 0;
        maps = map.generate(GameFrame.pass);
        for (int i = 0; i <SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                remove(cells[i][j]);
                //创建单元格
                cells[i][j] = new CellUtils();
            }
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                //设置位置
                cells[i][j].setLocation(20 + (j * 50) + (j / 3) * 10, 30 + (i * 50) + (i / 3) * 10);
                if (maps[i][j] > 0) {
                    cells[i][j].setText("" + maps[i][j]);
                    cells[i][j].setEnabled(false);
                } else {
                    cells[i][j].setText("");
                    //添加鼠标监听
                    cells[i][j].addMouseListener(this);
                }
                add(cells[i][j]);
            }
            repaint();
            checkFinish();
        }

    }

    /**
     * 重新加载进入下一关
     */
    public void reLoad(HistoryMap hm) {
        GameFrame.usedTime = hm.getTime();
        GameFrame.pass = hm.getPass();
        maps = hm.getMaskCellArr();
        for (int i = 0; i <SIZE ; i++) {
            for (int j = 0; j < SIZE; j++) {
                remove(cells[i][j]);
                //创建单元格
                cells[i][j] = new CellUtils();
            }
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                //设置位置
                cells[i][j].setLocation(20 + (j * 50) + (j / 3) * 10, 30 + (i * 50) + (i / 3) * 10);
                if (maps[i][j] > 0) {
                    cells[i][j].setText("" + maps[i][j]);
                    cells[i][j].setEnabled(false);
                } else {
                    if (hm.getMaskCellArr()[i][j] == 0 && !("0").equals(hm.getGamesArr()[i][j])) {
                        cells[i][j].setText("" + hm.getGamesArr()[i][j]);
                    } else {
                        cells[i][j].setText("");
                    }
                    //添加鼠标监听
                    cells[i][j].addMouseListener(this);
                }
                add(cells[i][j]);
            }
            repaint();
            checkFinish();
        }

    }

    /**
     * 清空所有的监听事件
     */
    private void clearAllListener() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j].removeMouseListener(this);
            }
        }
    }

    /**
     * 检查
     *
     * @param i 数独区域的第i行
     * @param j 数独区域的第j列
     * @return 是否有重复数字
     */
    private boolean check(int i, int j) {

        try {
            if (cells[i][j].getText().isEmpty()) {
                return false;
            }
            for (int k = 0; k < SIZE; k++) {
                if (cells[i][j].getText().trim().equals(cells[i][k].getText().trim()) && j != k) {
                    return false;
                }
                if (cells[i][j].getText().trim().equals(cells[k][j].getText().trim()) && i != k) {
                    return false;
                }
                int ii = (i / 3) * 3 + k / 3;
                int jj = (j / 3) * 3 + k % 3;
                String text = cells[ii][jj].getText();
                String text1 = cells[i][j].getText();
                boolean flag = text1.equals(text) && !(i == ii && j == jj);
                if (flag) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 标按键在组件上单击（按下并释放）时调用
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * 鼠标按键在组件上按下时调用
     */
    @Override
    public void mousePressed(MouseEvent e) {
        //点击鼠标右键
        if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0) {
            ((CellUtils) e.getSource()).setText("");
            //点击鼠标左键
        } else if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
            //如果选择数字窗口存在则销毁
            if (selectNum != null) {
                selectNum.dispose();
            }
            //新建一个选择窗口
            selectNum = new SelectNumFrame();
            //设置成模态窗口
            selectNum.setModal(true);
            //设置选择窗口在显示器上的位置
            selectNum.setLocation(e.getLocationOnScreen().x, e.getLocationOnScreen().y - 100);
            //将点击的单元格传递给数字选择窗口
            selectNum.setCell((CellUtils) e.getSource());
            //显示数字选择窗口
            selectNum.setVisible(true);
        }
        //检查是否完成
        checkFinish();
    }

    /**
     * 鼠标按钮在组件上释放时调用
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * 鼠标进入到组件上时调用
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * 鼠标离开组件时调用
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void setCellEnabled(boolean flag) {
        if (flag) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (maps[i][j] <= 0) {
                        cells[i][j].setEnabled(true);
                    }
                }
            }
        } else {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    cells[i][j].setEnabled(false);
                }
            }
        }

    }

    public String[][] getGames() {
        String[][] strings = new String[9][9];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                try {
                    if (StringUtils.isEmpty(cells[i][j].getText())) {
                        strings[i][j] = cells[i][j].getText();
                    } else {
                        strings[i][j] = "0";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return strings;
    }
}
