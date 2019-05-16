package com.github.joffryferrater.authentication;

public class UserInfo {

    private String username;
    private boolean isAuthenticated;

    public UserInfo(String username, boolean isAuthenticated) {
        this.username = username;
        this.isAuthenticated = isAuthenticated;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }
}
