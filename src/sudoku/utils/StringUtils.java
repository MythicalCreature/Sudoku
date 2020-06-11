package sudoku.utils;

/**
 * @author zzx
 * 2020/6/3 14:12
 */
public class StringUtils {
    /**
     * 字符串是否为空
     * @param s 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String s) {
        return ("".equals(s) || s == null);
    }
}
