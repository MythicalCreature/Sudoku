package sudoku.model;

import java.util.Random;

/**
 * @author zzx
 * 2020/6/3 15:27
 */
public class InitMap {
    private static final int SIZE = 9;
    private static final int LEVEL_MAX = 10;
    private int[][] maps = new int[SIZE][SIZE];
    private int[] ranNum = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    /**
     * 根据通过的关卡数随机生成数独
     * @param level 通过的关卡数
     * @return 返回一个二维数组即生成的有解数独
     */
    public int[][] generate(int level) {
        int time;
        for (int i = 0; i < SIZE; i++) {
            time = 0;
            for (int j = 0; j < SIZE; j++) {
                maps[i][j] = getNum(time);
                //如果返回值为0，则表示卡住，退回处理
                //退回处理原则是：如果不是第一列，则先倒退到前一列，否则倒回前一行的最后一列
                if (maps[i][j] == 0) {
                    //不是第一列则倒退一列
                    if (j > 0) {
                        j -= 2;
                        continue;
                    } else {
                        //是第一列，则推到上一行的最后一列
                        i--;
                        j = 8;
                        continue;
                    }
                }
                //填充成功
                if (checkCol(j) && checkRow(i) && checkBox(i, j)) {
                    //初始化time，为下次填充做准备
                    time = 0;
                } else {//继续填充
                    //次数增加1
                    time++;
                    //继续填充当前格
                    j--;
                }
            }
        }
        int[][] games = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(maps[i], 0, games[i], 0, SIZE);
        }

        System.out.println("生成数独");
        printMaps();
        maskCells(level,maps);
        return maps;
    }

    /**
     * 产生1-9之间的随机数字
     * 规则：生成的随机数字放置在数组8-time下标的位置，随着time的增加，已经尝试过的数字将不会再取到
     *说明：即第一次是从所有数字中随机，第二次是从前八个数字中随机，依次类推，这样既保证随机，也不会再重复取已经不符合要求的数字
     * 提高程序的效率。这个规则是本算法的核心
     *
     * @param time 填充的次数，0表示第一次填充
     * @return 返回随机数
     */
    private int getNum(int time) {
        Random r = new Random();
        //第一次尝试时，初始化随机数字源数组
        if (time == 0) {
            for (int i = 0; i < SIZE; i++) {
                ranNum[i] = i + 1;
            }
        }
        //第10次填充，表示该位置已经卡住，则返回0，由主程序处理退回
        if (time == SIZE) {
            return 0;
        }
        //不是第一次填充
        //生成随机数字，该数字是数组的下标，取数组num中该下标对应的数字为随机数字
        int num = r.nextInt(9 - time);
        //把数字放置在数组倒数第time个位置
        int temp = ranNum[8 - time];
        ranNum[8 - time] = ranNum[num];
        ranNum[num] = temp;
        //返回数字
        return ranNum[8 - time];
    }

    /**
     * 打印输出所生成的数独
     */
    private void printMaps() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(maps[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * 根据关卡数随机挖空
     * @param level 通过的关卡数
     */
    private void maskCells(int level,int[][] games) {
        int min, max;
        level %= LEVEL_MAX;
        if (level == 0) {
            level = LEVEL_MAX;
        }
        if (level < 4) {
            min = 20;
            max = 15;
        } else if (level < 7) {
            min = 40;
            max = 10;
        } else if (level < 10) {
            min = 50;
            max = 10;
        } else {
            min = 60;
            max = 5;
        }
        Random random = new Random();
        int count = random.nextInt(max) + min;
        for (int i = 0; i < count; i++) {
            do {
                int n = random.nextInt(SIZE);
                int m = random.nextInt(SIZE);
                if (games[n][m] > 0) {
                    games[n][m] = 0;
                    break;
                }
            } while (true);
        }
    }

    /**
     * 检查行是否符合要求
     * @param row 检查的行号
     * @return true 代表符合要求
     */
    private boolean checkRow( int row) {
        for (int i = 0; i < SIZE - 1; i++) {
            if (maps[row][i] == 0) {
                continue;
            }

            for (int n = i + 1; n < SIZE; n++) {
                if (maps[row][i] == maps[row][n]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检查列是否符合要求
     * @param col 检查的列号
     * @return true 代表符合要求
     */
    private boolean checkCol(int col) {
        for (int i = 0; i < SIZE - 1; i++) {

            if (maps[i][col] == 0) {
                continue;
            }
            for (int n = i + 1; n < SIZE; n++) {
                if (maps[i][col] == maps[n][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检查3×3区域是否符合要求
     * @param row 检查的行号
     * @param col 检查的列号
     * @return true 代表符合要求
     */
    private boolean checkBox(int row, int col) {
        int x = row / 3 * 3;
        int y = col / 3 * 3;
        //循环比较
        for (int i = 0; i < SIZE - 1; i++) {
            if (maps[x + i / 3][y + i % 3] == 0) {
                continue;
            }
            for (int n = i + 1; n < SIZE; n++) {
                if (maps[x + i / 3][y + i % 3] == maps[x + n / 3][y + n % 3]) {
                    return false;
                }
            }
        }
        return true;
    }
}
