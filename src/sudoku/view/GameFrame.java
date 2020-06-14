package sudoku.view;

import sudoku.controller.CellAction;
import sudoku.controller.HistoryMapAction;
import sudoku.controller.MouseAction;
import sudoku.model.HistoryMap;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zzx
 * 2020/6/3 15:09
 */
public class GameFrame extends JFrame {
    /**
     * pass 关卡
     * usedTime 玩家的用时
     * lb 显示关卡的label
     * c 获得窗体容器
     */
    public static int pass = 1;
    public static long usedTime = 0;
    public static Timer userTimeAction;
    private Container c = getContentPane();
    private CellAction panelCell;
    private HistoryMapAction hma = new HistoryMapAction();
    private JButton pause, save, history;
    private boolean flag = true;
    private String userName;

    /**
     * 默认构造器
     */
    public GameFrame(String userName) {
        this.userName = userName;
        //初始化窗体
        init();
        //添加组件
        addComponent();
        //添加主游戏区
        addMainFrame();
    }

    /**
     * 添加主游戏区
     */
    private void addMainFrame() {
        //游戏区中所需监听事件
        panelCell = new CellAction();
        //设置游戏区的标题
        TitledBorder tb = new TitledBorder("游戏区");
        tb.setTitleFont(new Font("楷体", Font.ITALIC, 18));
        panelCell.setBorder(tb);
        //将组件添加到窗体中部
        c.add(panelCell, BorderLayout.CENTER);
    }

    /**
     * 添加上方组件
     */
    private void addComponent() {
        //添加组件面板
        JPanel panelComponent = new JPanel();
        panelComponent.setLayout(new BorderLayout());
        //添加时间区
        addPanelTime(panelComponent);
        //添加按钮区
        addPanelBut(panelComponent);
        //将组件添加到窗体顶部
        c.add(panelComponent, BorderLayout.NORTH);
    }

    /**
     * 添加时间区
     *
     * @param panelComponent 组件面板
     */
    private void addPanelTime(JPanel panelComponent) {
        //时间组件面板
        JPanel panelTime = new JPanel();
        //背景颜色（由于设置了nimbus风格所以只有白色能显示）
        panelTime.setBackground(Color.white);
        //设置游戏区的标题
        TitledBorder tb = new TitledBorder("时间区");
        tb.setTitleFont(new Font("楷体", Font.PLAIN, 18));
        panelTime.setBorder(tb);
        //网格布局两行一列
        panelTime.setLayout(new GridLayout(2, 1));
        //系统时间
        JLabel sysTime = new JLabel();
        //用户时间
        JLabel userTime = new JLabel();
        //把系统时间设置在时间面板的北面（即上方）
        panelTime.add(sysTime, BorderLayout.NORTH);
        //把用户时间设置在时间面板的南面（即下方）
        panelTime.add(userTime, BorderLayout.SOUTH);
        //设置系统时间，延迟0.5秒
        Timer sysTimeAction = new Timer(500, e -> {
            //获取的是当前时间与 1970 年 1 月 1 日午夜之间的时间差（以毫秒为单位测量）。
            long timeMillis = System.currentTimeMillis();
            //格式化所获取的毫秒数
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sysTime.setText("系统时间：" + df.format(timeMillis));
            sysTime.setFont(new Font("楷体", Font.PLAIN, 13));
        });
        //开始计时
        sysTimeAction.start();
        //设置用户时间 延迟1秒
        userTimeAction = new Timer(1000, e -> {
            userTime.setText("您已用时：" + (++usedTime) + "sec.");
            userTime.setFont(new Font("楷体", Font.PLAIN, 13));
        });
        //开始计时
        userTimeAction.start();
        panelComponent.add(panelTime, BorderLayout.EAST);
    }

    /**
     * 添加按钮区
     *
     * @param panelComponent 组件面板
     */
    private void addPanelBut(JPanel panelComponent) {
        //信息组件面板
        JPanel panelBut = new JPanel();
        panelBut.setBackground(Color.white);
        TitledBorder tb = new TitledBorder("按钮区");
        tb.setTitleFont(new Font("楷体", Font.PLAIN, 18));
        panelBut.setBorder(tb);
        pause = new JButton("暂停计时");
        setPause(panelBut);
        save = new JButton("保存本局");
        setSave(panelBut);
        history = new JButton("历史记录");
        setHistory(panelBut);
        panelComponent.add(panelBut, BorderLayout.CENTER);

    }

    private void setHistory(JPanel panelBut) {
        panelBut.add(history);
        history.addActionListener(e -> {
            HistoryMap hm = hma.reloadGame(userName);
            if (hm != null) {
                panelCell.reLoad(hm);
            }
        });
    }

    private void setSave(JPanel panelBut) {
        panelBut.add(save);
        save.addActionListener(e -> {
            String[][] games = panelCell.getGames();
            int[][] maskCell = panelCell.maps;
            hma.saveGame(userName,maskCell,games);
        }
        );
    }

    private void setPause(JPanel panelBut) {
        panelBut.add(pause);
        pause.addActionListener(e -> {
            if (flag) {
                userTimeAction.stop();
                flag = false;
                pause.setText("恢复计时");
            } else {
                userTimeAction.start();
                flag = true;
                pause.setText("暂停计时");
            }
            panelCell.setCellEnabled(flag);
        });
    }

    /**
     * 初始化
     */
    private void init() {
        //设置窗口标题
        setTitle("数独游戏");
        //初始化窗体大小
        setSize(520, 650);
        //设置窗体居中
        setLocationRelativeTo(null);
        //设置窗体大小不允许改变
        setResizable(false);
        //按住鼠标左键可随意拖动
        MouseAction ma = new MouseAction(this);
        addMouseListener(ma);
        addMouseMotionListener(ma);
        //刷新窗体
        c.validate();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int m = JOptionPane.showConfirmDialog(
                        c, "是否退出?",
                        "提示",
                        JOptionPane.YES_NO_OPTION);
                if (m == JOptionPane.YES_OPTION) {
                    int n = JOptionPane.showConfirmDialog(
                            c, "是否保存本局?",
                            "提示",
                            JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        String[][] games = panelCell.getGames();
                        int[][] maskCell = panelCell.maps;
                        hma.saveGame(userName,maskCell,games);
                        setDefaultCloseOperation(EXIT_ON_CLOSE);
                    } else if (n == JOptionPane.NO_OPTION) {
                        setDefaultCloseOperation(EXIT_ON_CLOSE);
                    }
                }
            }
        });
    }

}