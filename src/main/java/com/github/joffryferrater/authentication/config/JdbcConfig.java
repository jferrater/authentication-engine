package com.github.joffryferrater.authentication.config;

public class JdbcConfig implements RealmConfig {

    private String url;
    private String user;
    private String password;
    private String permissionsQuery;
    private String authenticationQuery;
    private String rolesQuery;

    public String getAuthenticationQuery() {
        return authenticationQuery;
    }

    public void setAuthenticationQuery(String authenticationQuery) {
        this.authenticationQuery = authenticationQuery;
    }

    public String getRolesQuery() {
        return rolesQuery;
    }

    public void setRolesQuery(String rolesQuery) {
        this.rolesQuery = rolesQuery;
    }

    public String getPermissionsQuery() {
        return permissionsQuery;
    }

    public void setPermissionsQuery(String permissionsQuery) {
        this.permissionsQuery = permissionsQuery;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
