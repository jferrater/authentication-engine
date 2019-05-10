package com.github.joffryferrater.authentication.config;

public class LdapConfig {

    private String organizationUnit;
    private String adminDn;
    private String password;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(String organizationUnit) {
        this.organizationUnit = organizationUnit;
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
