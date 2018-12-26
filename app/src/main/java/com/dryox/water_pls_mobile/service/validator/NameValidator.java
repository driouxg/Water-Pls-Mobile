package com.dryox.water_pls_mobile.service.validator;

public class NameValidator {
    private String name;

    public NameValidator(String name) {
        this.name = name;
    }

    public boolean isAlphaNumeric() {
        return name.matches("[a-zA-Z0-9]+");
    }

    public boolean isLessThan25Characters() {
        return name.length() < 25;
    }

    public boolean hasNoWhitespace() {
        return !name.contains(" ");
    }
}
