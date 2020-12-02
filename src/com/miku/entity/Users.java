package com.miku.entity;

public class Users {

    private Integer userId;
    private String userNmae;
    private String passWord;
    private String sex;
    private String email;

    public Users() {
    }

    public Users(Integer userId, String userNmae, String passWord, String sex, String email) {
        this.userId = userId;
        this.userNmae = userNmae;
        this.passWord = passWord;
        this.sex = sex;
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserNmae() {
        return userNmae;
    }

    public void setUserNmae(String userNmae) {
        this.userNmae = userNmae;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
