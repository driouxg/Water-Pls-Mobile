package com.dryox.water_pls_mobile.domain.value;

import com.dryox.water_pls_mobile.domain.exception.NameException;
import com.dryox.water_pls_mobile.service.validator.NameValidator;

public class UsernameVO extends ValueObject {
    private String username;

    public UsernameVO(String username) {
        if (isValid(username))
            this.username = username;
        else
            throw new NameException("Username " + username + " is invalid");
    }

    public String getUsername() {
        return username;
    }

    private boolean isValid(String username) {
        NameValidator validator = new NameValidator(username);

        return !username.isEmpty()
                && validator.hasNoWhitespace()
                && validator.isLessThan25Characters()
                && validator.isAlphaNumeric();
    }
}
