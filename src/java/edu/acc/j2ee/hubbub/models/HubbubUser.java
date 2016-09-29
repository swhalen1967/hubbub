package edu.acc.j2ee.hubbub.models;

public class HubbubUser implements java.io.Serializable {
    private String userName;
    private String password;
    private String joinDate;
    private int userid;
    
    public HubbubUser(String userName, String password, String joinDate, int userId) {
        this.userName = userName;
        this.password = password;
        this.joinDate = joinDate;
        this.userid = userId;
    }
    
    public HubbubUser() {}

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

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        return "HubbubUser{" + "userName=" + userName + ", password=" + password + ", joinDate=" + joinDate + ", userid=" + userid + '}';
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
    
}
