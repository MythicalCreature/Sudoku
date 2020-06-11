package sudoku.model;

/**
 * @author zzx
 * 2020/6/3 14:17
 */
public class User {
    private String userName;
    private String password;

    /**
     * 默认构造器
     */
    public User(){
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
