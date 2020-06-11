package sudoku.controller;

import sudoku.dao.UserDao;
import sudoku.model.User;
import sudoku.utils.StringUtils;
import sudoku.view.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author zzx
 * 2020/6/3 14:35
 */
public class LoginAction {
    private JFrame frame;
    private Container c;
    private UserDao dao = new UserDao();

    /**
     * 构造器
     * @param frame 处理登录事务的具体窗口
     */
    public LoginAction(JFrame frame) {
        this.frame = frame;
        this.c = frame.getContentPane();
    }

    /**
     * 通过数据库验证登录信息
     * @param userName 用户名
     * @param password 密码
     */
    public void getLogin(String userName,String password){
        if (StringUtils.isEmpty(userName)) {
            JOptionPane.showMessageDialog(c, "用户名不能为空");
        } else if (StringUtils.isEmpty(password)) {
            JOptionPane.showMessageDialog(c, "密码不能为空");
        } else {
            User user = new User(userName, password);
            //查询当前数据库中的表是否含有此用户信息
            User curUser = dao.login(user);
            if (curUser != null) {
                JOptionPane.showMessageDialog(c, "登录成功");
                frame.dispose();
                new GameFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(c, "用户名或密码错误");
            }
        }
    }

    /**
     * 注册账号信息并把信息写入文件和数据库表中
     * @param userName 用户名
     * @param password 密码
     */
    public void getRegister(String userName,String password){
        if (StringUtils.isEmpty(userName)) {
            JOptionPane.showMessageDialog(c, "用户名不能为空");
        } else if (StringUtils.isEmpty(password)) {
            JOptionPane.showMessageDialog(c, "密码不能为空");
        } else {
            User user = new User();
            user.setUserName(userName);
            //查询当前数据库中的表是否含有此用户名
            User curUser = dao.queryUserName(user);
            if (curUser != null) {
                JOptionPane.showMessageDialog(c, "用户名存在,请重新注册");
            } else {
                user = new User(userName, password);
                //向数据库中的表增加记录
                int count = dao.register(user);
                if (count != 0) {
                    //尝试写入文件
                    String fileName = "密码本.txt";
                    try {
                        FileWriter writer = new FileWriter(fileName, true);
                        BufferedWriter outWriter = new BufferedWriter(writer);
                        outWriter.write("userName:" + userName + "&&" + "Password:" + password);
                        outWriter.newLine();
                        //注意关闭io流的顺序
                        outWriter.close();
                        writer.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(c, "注册成功！ 您的用户名是：" + userName + "密码是：" + password);
                }
            }
        }
    }
}
