package sudoku.view;

import sudoku.utils.CellUtils;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author zzx
 * 2020/6/3 15:25
 */
public class SelectNumFrame extends JDialog implements MouseListener {
    private CellUtils cell;

    /**
     * 生成构造器
     */
    public SelectNumFrame() {
        //隐藏界面上面的工具栏
        setUndecorated(true);
        setSize(150, 150);
        setLayout(null);
        addNum();
    }

    /**
     * 添加数字1~9
     */
    private void addNum() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton b = new JButton();
                b.setSize(50, 50);
                b.setLocation(i * 50, j * 50);
                b.setText("" + (j * 3 + i + 1));
                b.addMouseListener(this);
                add(b);
            }
        }
    }

    public void setCell(CellUtils cell) {
        this.cell = cell;
    }

    /**
     *     标按键在组件上单击（按下并释放）时调用
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     *     鼠标按键在组件上按下时调用
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
            JButton b = (JButton) e.getSource();
            cell.setText(b.getText());
        }
        dispose();
    }

    /**
     *     鼠标按钮在组件上释放时调用
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     *     鼠标进入到组件上时调用
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     *     鼠标离开组件时调用
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
