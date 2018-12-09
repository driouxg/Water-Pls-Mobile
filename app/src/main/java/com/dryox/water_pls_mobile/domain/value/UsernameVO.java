package com.dryox.water_pls_mobile.domain.value;

import com.dryox.water_pls_mobile.service.validator.NameValidator;

public class UsernameVO extends ValueObject {
    private String username;
    private NameValidator nameValidator;

    public UsernameVO(String username, NameValidator nameValidator) {
        this.username = username;
        this.nameValidator = nameValidator;
    }

    public String getUsername() {
        return username;
    }

    public void validate() {
        nameValidator.hasCharacters();
        nameValidator.hasNoWhitespace();
        nameValidator.isAlphaNumeric();
        nameValidator.isLessThan25Characters();
    }
}
