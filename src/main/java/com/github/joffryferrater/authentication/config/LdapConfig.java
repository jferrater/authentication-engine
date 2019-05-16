package com.github.joffryferrater.authentication.config;

public class LdapConfig implements RealmConfig {

    private String userDnTemplate;
    private String adminDn;
    private String password;
    private String url;

    public String getUserDnTemplate() {
        return userDnTemplate;
    }

    public void setUserDnTemplate(String userDnTemplate) {
        this.userDnTemplate = userDnTemplate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdminDn() {
        return adminDn;
    }

    public void setAdminDn(String adminDn) {
        this.adminDn = adminDn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
