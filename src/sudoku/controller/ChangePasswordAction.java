package sudoku.controller;

import sudoku.dao.UserDao;
import sudoku.model.User;
import sudoku.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * @author zzx
 * 2020/6/3 14:48
 */
public class ChangePasswordAction {
    private JFrame frame;
    private Container c;
    private UserDao dao = new UserDao();

    /**
     * 构造器
     * @param frame 当前窗体
     */
    public ChangePasswordAction(JFrame frame) {
        this.frame = frame;
        this.c = frame.getContentPane();
    }

    /**
     * 判断是否有该用户名
     * @param userName 用户名信息
     * @return 是否有该用户名
     */
    public boolean getUserName(String userName){
        User user = new User();
        user.setUserName(userName);
        if (StringUtils.isEmpty(userName)) {
            JOptionPane.showMessageDialog(frame, "用户名不能为空");
        } else {
            User curUser = dao.queryUserName(user);
            if (curUser != null) {
                JOptionPane.showMessageDialog(frame, "请设置新密码");
                frame.dispose();
                return true;
            } else {
                JOptionPane.showMessageDialog(frame, "用户名不存在");
            }
        }
        return false;
    }

    /**
     * 确认修改密码
     * @param psw 第一次输入的密码
     * @param pswAgain 再次确认输入的密码
     * @param userName 用户名
     */
    public void confirmPsw(String psw,String pswAgain,String userName){
        if (StringUtils.isEmpty(psw) || StringUtils.isEmpty(pswAgain)) {
            JOptionPane.showMessageDialog(c, "密码不能为空");
        } else if (!(psw.equals(pswAgain))) {
            JOptionPane.showMessageDialog(c, "两次输入的密码不一致，请重新输入");
        } else {
            User user = new User(userName, psw);
            //查找之前的密码看是否一致
            User curUser = dao.login(user);
            if (curUser != null) {
                JOptionPane.showMessageDialog(c, "密码与原密码一致");
            } else {
                //更新用户信息
                int count = dao.update(user);
                if (count > 0) {
                    JOptionPane.showMessageDialog(c, "密码修改成功");
                    //修改文件中的用户信息
                    File fileName = new File("密码本.txt");
                    try {
                        FileReader fileReader = new FileReader(fileName);
                        BufferedReader reader = new BufferedReader(fileReader);
                        String s;
                        ArrayList<String> list = new ArrayList<>();
                        while ((s = reader.readLine()) != null) {
                            if (s.contains(userName)) {
                                s = "userName:" + userName + "&&" + "Password:" + psw;
                            }
                            list.add(s);
                        }
                        FileWriter fileWriter = new FileWriter(fileName,false);
                        BufferedWriter writer = new BufferedWriter(fileWriter);
                        for(String w:list){
                            writer.write(w);
                            writer.newLine();
                        }
                        writer.close();
                        fileWriter.close();
                        reader.close();
                        fileReader.close();
                        frame.dispose();
                    } catch (IOException ignored) {
                    }
                }
            }
        }
    }
}
