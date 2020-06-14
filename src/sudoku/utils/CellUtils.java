package sudoku.utils;

import javax.swing.*;
import java.awt.*;

/**
 * @author zzx
 * 2020/6/3 15:22
 */
public class CellUtils extends JButton {
    /**
     * 构造器
     */
    public CellUtils() {
        //设置单元格大小
        setSize(50, 50);
        //设置单元格字体
        setFont(new Font("楷体", Font.BOLD, 24));
        //设置单元格背景色
        setBackground(Color.white);
        setText("-1");
    }
}
