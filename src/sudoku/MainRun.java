package sudoku;

import sudoku.view.LoginFrame;

import javax.swing.*;

/**
 * @author zzx
 * 2020/6/3 14:06
 */
public class MainRun{
        public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            new LoginFrame();
        }

}
