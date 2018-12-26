package com.dryox.water_pls_mobile.domain.value;

public class PasswordVO extends ValueObject {
    private String password;

    public PasswordVO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
