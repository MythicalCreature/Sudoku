package sudoku.view;

import sudoku.controller.CellAction;
import sudoku.controller.MouseAction;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.SimpleDateFormat;

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
    public static JLabel lb;
    private Container c = getContentPane();

    /**
     *默认构造器
     */
    public GameFrame() {
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
        CellAction panelCell = new CellAction();
        //设置游戏区的标题
        TitledBorder tb = new TitledBorder("游戏区");
        tb.setTitleFont(new Font("楷体",Font.PLAIN,18));
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
        //添加消息区
        addPanelMsg(panelComponent);
        //将组件添加到窗体顶部
        c.add(panelComponent, BorderLayout.NORTH);
    }

    /**
     * 添加时间区
     * @param panelComponent 组件面板
     */
    private void addPanelTime(JPanel panelComponent) {
        //时间组件面板
        JPanel panelTime = new JPanel();
        //背景颜色（由于设置了nimbus风格所以只有白色能显示）
        panelTime.setBackground(Color.white);
        //设置游戏区的标题
        TitledBorder tb = new TitledBorder("时间区");
        tb.setTitleFont(new Font("楷体",Font.PLAIN,18));
        panelTime.setBorder(tb);
        //网格布局两行一列
        panelTime.setLayout(new GridLayout(2,1));
        //系统时间
        JLabel sysTime = new JLabel();
        //用户时间
        JLabel userTime = new JLabel();
        //把系统时间设置在时间面板的北面（即上方）
        panelTime.add(sysTime,BorderLayout.NORTH);
        //把用户时间设置在时间面板的南面（即下方）
        panelTime.add(userTime,BorderLayout.SOUTH);
        //设置系统时间，延迟0.5秒
        Timer sysTimeAction = new Timer(500, e -> {
            //获取的是当前时间与 1970 年 1 月 1 日午夜之间的时间差（以毫秒为单位测量）。
            long timeMillis = System.currentTimeMillis();
            //格式化所获取的毫秒数
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sysTime.setText("系统时间："+df.format(timeMillis));
            sysTime.setFont(new Font("楷体",Font.PLAIN,13));
        });
        //开始计时
        sysTimeAction.start();
        //设置用户时间 延迟1秒
        userTimeAction = new Timer(1000, e -> {
            userTime.setText("您已用时："+(++usedTime)+"sec.");
            userTime.setFont(new Font("楷体",Font.PLAIN,13));
        });
        //开始计时
        userTimeAction.start();
        panelComponent.add(panelTime,BorderLayout.EAST);
    }

    /**
     * 添加消息区
     * @param panelComponent 组件面板
     */
    private void addPanelMsg(JPanel panelComponent) {
        //信息组件面板
        JPanel panelMsg = new JPanel();
        panelMsg.setBackground(Color.white);
        TitledBorder tb = new TitledBorder("消息区");
        tb.setTitleFont(new Font("楷体",Font.PLAIN,18));
        panelMsg.setBorder(tb);
        JLabel lb1 = new JLabel("关卡：第");
        lb1.setFont(new Font("楷体", Font.PLAIN, 18));
        panelMsg.add(lb1);
        //显示关卡数
        lb = new JLabel("" + pass);
        lb.setForeground(Color.RED);
        lb.setFont(new Font("Arial", Font.ITALIC, 40));
        panelMsg.add(lb);
        JLabel lb2 = new JLabel("关/总共10关");
        lb2.setFont(new Font("楷体", Font.PLAIN, 18));
        panelMsg.add(lb2);
        panelComponent.add(panelMsg, BorderLayout.CENTER);

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
        //设置窗体关闭规则
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //设置窗体大小不允许改变
        setResizable(false);
        //按住鼠标左键可随意拖动
        MouseAction ma = new MouseAction(this);
        addMouseListener(ma);
        addMouseMotionListener(ma);
        //刷新窗体
        c.validate();
    }
}
