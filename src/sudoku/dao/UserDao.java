package sudoku.dao;

import sudoku.model.User;
import sudoku.utils.JdbcUtils;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zzx
 * 2020/6/3 14:15
 */
public class UserDao {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private User resUser;

    /**
     * 登录验证
     * @param user 传进用户信息
     * @return 结果集用户信息
     */
    public User login(User user) {
        resUser = null;
        try {
            //获取连接
            conn = JdbcUtils.getConnection();
            //定义sql
            String sql = "select * from user where userName=? and password=? ";
            //获取执行sql的对象
            ps = conn.prepareStatement(sql);
            //给?占位符赋值
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            //执行sql
            rs = ps.executeQuery();
            if (rs.next()) {
                resUser = new User();
                resUser.setUserName(rs.getString("userName"));
                resUser.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "数据库连接失败");
        } finally {
            //释放资源
            JdbcUtils.close(rs, ps, conn);
        }
        return resUser;
    }

    /**
     * 注册信息
     * @param user 传进用户信息
     * @return 修改记录条数
     */
    public int register(User user) {
        int count = 0;
        try {
            //获取连接
            conn = JdbcUtils.getConnection();
            //关闭自动提交事务
            conn.setAutoCommit(false);
            //定义sql
            String sql = "insert into user(userName,password) values(?,?)";
            //获取执行sql的对象
            ps = conn.prepareStatement(sql);
            //给?占位符赋值
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            //执行sql
            count = ps.executeUpdate();
            //手动提交事务
            conn.commit();
        } catch (Exception e) {
            //回滚事务
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            //释放资源
            JdbcUtils.close(rs, ps, conn);
        }
        return count;
    }

    /**
     * 查询用户名信息
     * @param user 传进用户信息
     * @return 结果集用户信息 可能为null
     */
    public User queryUserName(User user) {
        resUser = null;
        try {
            //获取连接
            conn = JdbcUtils.getConnection();
            //定义sql
            String sql = "select * from user where userName=?";
            //获取执行sql的对象
            ps = conn.prepareStatement(sql);
            //给?占位符赋值
            ps.setString(1, user.getUserName());
            //执行sql
            rs = ps.executeQuery();
            if (rs.next()) {
                resUser = new User();
                resUser.setUserName(rs.getString("userName"));
                resUser.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            JdbcUtils.close(rs, ps, conn);
        }
        return resUser;
    }

    /**
     * 更新用户信息
     * @param user 传进用户信息
     * @return 修改记录条数
     */
    public int update(User user) {
        int count = 0;
        try {
            //获取连接
            conn = JdbcUtils.getConnection();
            //关闭自动提交事务
            conn.setAutoCommit(false);
            //定义sql
            String sql = "update user set password=? where userName=?";
            //获取执行sql的对象
            ps = conn.prepareStatement(sql);
            //给?占位符赋值
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getUserName());
            //执行sql
            count = ps.executeUpdate();
            //手动提交事务
            conn.commit();
        } catch (Exception e) {
            //回滚事务
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            //释放资源
            JdbcUtils.close(rs, ps, conn);
        }
        return count;
    }
}
