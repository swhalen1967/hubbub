package edu.acc.j2ee.hubbub.models;

public class HubbubUser implements java.io.Serializable {
    private String userName;
    private String password;
    private String joinDate;
    
    public HubbubUser(String userName, String password, String joinDate) {
        this.userName = userName;
        this.password = password;
        this.joinDate = joinDate;
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
        return "HubbubUser{" + "userName=" + userName + ", joinDate=" + joinDate + '}';
    }
    
}
