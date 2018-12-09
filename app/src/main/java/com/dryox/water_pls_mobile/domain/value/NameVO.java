package com.dryox.water_pls_mobile.domain.value;

public class NameVO extends ValueObject {
    private String name;

    public NameVO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
