package sudoku.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * @author zzx
 * 2020/6/3 14:14
 */
public class MouseAction extends MouseAdapter implements MouseMotionListener {
    private Point point;
    private JFrame frame;

    /**
     * 构造器
     * @param frame 需要拖动的窗体
     */
    public MouseAction(JFrame frame) {
        this.point = new Point();
        this.frame = frame;
    }

    /**
     * 鼠标按键在组件上按下时调用
     * @param e 鼠标所在点
     */
    @Override
    public void mousePressed(MouseEvent e) {
        point.x = e.getX();
        point.y = e.getY();
    }

    /**
     * 鼠标移动触发事件
     * @param e 鼠标所在的点
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        Point loc = frame.getLocation();
        frame.setLocation(loc.x + e.getX() - point.x, loc.y + e.getY() - point.y);

    }
}
