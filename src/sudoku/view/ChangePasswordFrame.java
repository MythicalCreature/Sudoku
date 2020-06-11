package sudoku.view;

import sudoku.controller.ChangePasswordAction;
import sudoku.controller.MouseAction;

import javax.swing.*;
import java.awt.*;

/**
 * @author zzx
 * 2020/6/3 14:11
 */
class ChangePasswordFrame {
    private JLabel jl1;
    private JLabel jl2;
    private JTextField jt1;
    private JTextField jt2;
    private JTextField jtfUserName;
    private JButton ensure;
    private JButton ensurePsw;

    /**
     * 默认构造器 包内使用不需要public关键字
     */
    ChangePasswordFrame() {
        JFrame frame = new JFrame("修改密码");
        Container c = frame.getContentPane();
        JLabel mes = new JLabel("请输入您的账号");
        mes.setBounds(40, 15, 250, 20);
        jtfUserName = new JTextField();
        jtfUserName.setBounds(40, 40, 140, 25);
        ensure = new JButton("确认");
        setEnsure(frame);
        ensurePsw = new JButton("确认");
        c.add(mes);
        c.add(jtfUserName);
        c.add(ensure);
        smallFrame(frame);
    }

    /**
     *设置新密码窗体
     */
    private void setPsw() {
        JFrame frame = new JFrame("修改密码");
        Container c = frame.getContentPane();
        jl1 = new JLabel("请设置新密码：");
        jt1 = new JTextField();
        jl2 = new JLabel("重新输入密码：");
        jt2 = new JTextField();
        setPassword(frame);
        c.add(jl1);
        c.add(jl2);
        c.add(jt1);
        c.add(jt2);
        c.add(ensurePsw);
        smallFrame(frame);
    }

    /**
     *     设置窗体相关信息
     * @param frame 传入需要设置信息的窗体
     */
    private void smallFrame(JFrame frame) {
        frame.setLayout(null);
        //初始化窗体大小
        frame.setSize(350, 200);
        //设置窗体居中
        frame.setLocationRelativeTo(null);
        //按住鼠标左键可随意拖动
        MouseAction ma = new MouseAction(frame);
        frame.addMouseListener(ma);
        frame.addMouseMotionListener(ma);
        //设置窗体大小不允许改变
        frame.setResizable(false);
        //设置窗体可见
        frame.setVisible(true);
    }

    /**
     * 设置新密码的监听事件
     * @param frame 当前窗体
     */
    private void setPassword(JFrame frame) {
        jl2.setBounds(15, 100, 150, 25);
        jt2.setBounds(100, 100, 150, 25);
        jl1.setBounds(15, 50, 150, 25);
        jt1.setBounds(100, 50, 150, 25);
        ensurePsw.setBounds(265, 100, 60, 25);
        ensurePsw.addActionListener(e -> {
            String psw = jt1.getText();
            String pswAgain = jt2.getText();
            String userNameText = jtfUserName.getText();
            //更改密码所需事件
            ChangePasswordAction cpa = new ChangePasswordAction(frame);
            cpa.confirmPsw(psw,pswAgain,userNameText);
        });
    }

    /**
     * 确认用户名信息按钮的监听事件
     * @param frame 当前窗体
     */
    private void setEnsure(JFrame frame) {
        ensure.setBounds(200, 40, 60, 25);
        ensure.addActionListener(e -> {
            String userName = jtfUserName.getText();
            ChangePasswordAction cpa = new ChangePasswordAction(frame);
            //判断是否有该用户名
            if(cpa.getUserName(userName)){
                //设置新密码
                setPsw();
            }
        });
    }
}
