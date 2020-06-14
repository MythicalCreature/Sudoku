package sudoku.dao;

import sudoku.model.HistoryMap;
import sudoku.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzx
 * 2020/6/13 19:51
 */
public class HistoryMapDao {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private HistoryMap resHm;
    /**
     * 查询用户名信息
     * @param hm 传进用户信息
     * @return 结果集用户信息 可能为null
     */
    public List<String> queryUserName(HistoryMap hm) {
        List<String> hmList = new ArrayList<>();
        resHm = null;
        try {
            //获取连接
            conn = JdbcUtils.getConnection();
            //定义sql
            String sql = "select * from map where userName=?";
            //获取执行sql的对象
            ps = conn.prepareStatement(sql);
            //给?占位符赋值
            ps.setString(1, hm.getUserName());
            //执行sql
            rs = ps.executeQuery();
            boolean first = rs.first();
            if(first){
                hmList.add(rs.getString("fileName"));
                while (rs.next()) {
                    hmList.add(rs.getString("fileName"));
                }
            }else {
                hmList = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            JdbcUtils.close(rs, ps, conn);
        }
        return hmList;
    }
    /**
     * 查询文件名信息
     * @param hm 传进文件名信息
     * @return 结果集用户信息 可能为null
     */
    public HistoryMap queryFileName(HistoryMap hm) {
        resHm = null;
        try {
            //获取连接
            conn = JdbcUtils.getConnection();
            //定义sql
            String sql = "select * from map where fileName=?";
            //获取执行sql的对象
            ps = conn.prepareStatement(sql);
            //给?占位符赋值
            ps.setString(1, hm.getFileName());
            //执行sql
            rs = ps.executeQuery();
            if (rs.next()) {
                resHm = new HistoryMap();
                resHm.setFileName(rs.getString("userName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            JdbcUtils.close(rs, ps, conn);
        }
        return resHm;
    }

    /**
     * 查询文件名信息
     * @param hm 传进文件名信息
     * @return 结果集用户信息 可能为null
     */
    public HistoryMap queryAll(HistoryMap hm) {
        resHm = null;
        try {
            //获取连接
            conn = JdbcUtils.getConnection();
            //定义sql
            String sql = "select * from map where fileName=?";
            //获取执行sql的对象
            ps = conn.prepareStatement(sql);
            //给?占位符赋值
            ps.setString(1, hm.getFileName());
            //执行sql
            rs = ps.executeQuery();
            if (rs.next()) {
                resHm = new HistoryMap();
                resHm.setPass(rs.getInt("pass"));
                resHm.setTime(rs.getLong("time"));
                resHm.setMaskCell(rs.getString("maskCell"));
                resHm.setGames(rs.getString("games"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            JdbcUtils.close(rs, ps, conn);
        }
        return resHm;
    }

    /**
     * 保存信息
     * @param hm 传进文件信息
     * @return 修改记录条数
     */
    public int save(HistoryMap hm) {
        int count = 0;
        try {
            //获取连接
            conn = JdbcUtils.getConnection();
            //关闭自动提交事务
            conn.setAutoCommit(false);
            //定义sql
            String sql = "insert into map(fileName,userName,pass,time,maskCell,games) values(?,?,?,?,?,?)";
            //获取执行sql的对象
            ps = conn.prepareStatement(sql);
            //给?占位符赋值
            ps.setString(1, hm.getFileName());
            ps.setString(2, hm.getUserName());
            ps.setInt(3,hm.getPass());
            ps.setLong(4,hm.getTime());
            ps.setString(5,hm.getMaskCell());
            ps.setString(6,hm.getGames());
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
