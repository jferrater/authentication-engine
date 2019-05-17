package com.github.joffryferrater.tokens;

public class KeyConfig {

    private String alias;
    private String password;
    private String keystoreFile;

    public KeyConfig(String alias, String password, String keystoreFile) {
        this.alias = alias;
        this.password = password;
        this.keystoreFile = keystoreFile;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKeystoreFile() {
        return keystoreFile;
    }

    public void setKeystoreFile(String keystoreFile) {
        this.keystoreFile = keystoreFile;
    }
}
