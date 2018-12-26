package com.dryox.water_pls_mobile.domain.value;

public class DonatorVO extends ValueObject {
    private UsernameVO username;
    private PasswordVO password;
    private NameVO firstName;
    private NameVO lastName;
    private GeographicLocationVO location;

    public DonatorVO(UsernameVO username, PasswordVO password, NameVO firstName, NameVO lastName, GeographicLocationVO location) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
    }

    public UsernameVO getUsername() {
        return username;
    }

    public PasswordVO getPassword() {
        return password;
    }

    public NameVO getFirstName() {
        return firstName;
    }

    public String getFirstNameValue() {
        return firstName.getName();
    }

    public String getLastNameValue() {
        return getLastName().getName();
    }

    public NameVO getLastName() {
        return lastName;
    }

    public GeographicLocationVO getLocation() {
        return location;
    }
}
