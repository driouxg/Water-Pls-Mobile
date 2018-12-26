package com.dryox.water_pls_mobile.service;

import org.junit.Test;

import javax.crypto.SecretKey;

public class PasswordEncryptorTest {
    private String ciphertext;
    private String iv;
    private SecretKey key;

    @Test
    public void encryptPassword_hasValidPassword_returnsEncryptedPassword() {
        PasswordEncryptor passwordEncryptor = new PasswordEncryptor("myPassword9");
        System.out.println("Encrypted password: " + passwordEncryptor.encryptPassword());
        System.out.println("derp".getBytes());
        System.out.println("myPassword9".getBytes());

        ciphertext = passwordEncryptor.ciphertext;
        iv = passwordEncryptor.iv;
        key = passwordEncryptor.key;
    }

    @Test
    public void decryptPassword_hasValidParameters_decryptsPassword() {
        PasswordEncryptor passwordEncryptor = new PasswordEncryptor("myPassword9");

        passwordEncryptor.encryptPassword();
        ciphertext = passwordEncryptor.ciphertext;
        iv = passwordEncryptor.iv;
        key = passwordEncryptor.key;


        System.out.println(passwordEncryptor.decryptPassword(iv, key, ciphertext));
    }
}