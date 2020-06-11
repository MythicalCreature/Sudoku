package sudoku.view;

import sudoku.controller.LoginAction;
import sudoku.controller.MouseAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * @author zzx
 * 2020/6/3 14:02
 */
public class LoginFrame {
    /**
     * 加载图片路径
     */
    private URL url = LoginFrame.class.getResource("/image/2.JPEG");
    private ImageIcon icon = new ImageIcon(url);
    private JLabel label = new JLabel(icon);

    private JLabel labUserName = new JLabel("账号:");
    private JTextField jtfUserName = new JTextField();
    private Font lab = new Font("楷体", Font.BOLD, 20);
    private JLabel labPassword = new JLabel("密码:");
    private JPasswordField jtfPassword = new JPasswordField();
    private JButton btnRegister = new JButton("注册");
    private JButton btnLogin = new JButton("登录");
    private JButton btnFindPassword = new JButton("找回密码");
    private JFrame frame = new JFrame();
    private Container c = frame.getContentPane();
    private LoginAction la = new LoginAction(frame);

    /**
     * 默认构造器
     */
    public LoginFrame() {
        init();
        setRegister();
        setLogin();
        setFindPassword();
    }

    /**
     * 设置找回密码按钮监听事件
     */
    private void setFindPassword() {
        btnFindPassword.addActionListener(
                //lambda表达式
                e -> new ChangePasswordFrame()
        );
    }

    /**
     * 设置登录按钮监听事件
     */
    private void setLogin() {
        btnLogin.addActionListener(e -> {
            String userName = jtfUserName.getText();
            String password = new String(jtfPassword.getPassword());
            //设置登录所需事件
            la.getLogin(userName,password);
        });
    }

    /**
     * 设置注册按钮监听事件
     */
    private void setRegister() {
        btnRegister.addActionListener(e -> {
            String userName = jtfUserName.getText();
            String password = new String(jtfPassword.getPassword());
            //设置注册所需事件
            la.getRegister(userName,password);
            //清空输入框
            jtfUserName.setText("");
            jtfPassword.setText("");
        });
    }

    /**
     * 初始化面板
     */
    private void init() {
        JPanel panel = new JPanel();
        //将所有的组件添加到panel面板中
        panel.add(labUserName);
        panel.add(labPassword);
        panel.add(jtfUserName);
        panel.add(jtfPassword);
        panel.add(btnRegister);
        panel.add(btnLogin);
        panel.add(btnFindPassword);
        panel.add(label);
        panel.setLayout(null);
        //将面板添加到容器中
        c.add(panel);
        //设置位置及大小
        labUserName.setBounds(50, 150, 200, 100);
        //设置字体样式
        labUserName.setFont(lab);
        labPassword.setBounds(50, 200, 200, 100);
        labPassword.setFont(lab);
        jtfUserName.setBounds(160, 185, 190, 30);
        jtfPassword.setBounds(160, 233, 190, 30);
        btnRegister.setFont(lab);
        btnRegister.setBounds(20, 300, 130, 40);
        btnLogin.setFont(lab);
        btnLogin.setBounds(160, 300, 120, 40);
        btnFindPassword.setFont(lab);
        btnFindPassword.setBounds(300, 300, 140, 40);
        label.setBounds(0, 0, 500, 150);
        //设置界面大小
        frame.setSize(470, 440);
        //设置窗体关闭规则
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置界面居中对齐
        frame.setLocationRelativeTo(null);
        //设置标题
        frame.setTitle("数独登录");
        //设置界面大小不可改变
        frame.setResizable(false);
        //按住鼠标左键可随意拖动
        MouseAction ma = new MouseAction(frame);
        frame.addMouseListener(ma);
        frame.addMouseMotionListener(ma);
        //设置可见
        frame.setVisible(true);
    }
}
