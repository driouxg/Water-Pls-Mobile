package com.dryox.water_pls_mobile.domain.value;

public class DonaterVO extends ValueObject {
    private NameVO firstName;
    private NameVO lastName;
    private GeographicLocationVO location;

    public DonaterVO(NameVO firstName, NameVO lastName,
                     GeographicLocationVO location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
    }

    public NameVO getFirstName() {
        return firstName;
    }

    public NameVO getLastName() {
        return lastName;
    }

    public GeographicLocationVO getLocation() {
        return location;
    }
}
