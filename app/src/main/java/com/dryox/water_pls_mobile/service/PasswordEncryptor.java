package com.dryox.water_pls_mobile.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
//import java.util.Base64;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * https://developer.android.com/guide/topics/security/cryptography
 */
public class PasswordEncryptor {
    private String password;

    public String ciphertext;
    public String iv;
    public SecretKey key;

    public PasswordEncryptor(String password) {
        this.password = password;
    }

    /*private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public static String getSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public static String generateSecurePassword(String password, String salt) {
        String returnValue = null;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

        returnValue = Base64.getEncoder().encodeToString(securePassword);

        return returnValue;
    }

    public static boolean verifyUserPassword(String providedPassword,
                                             String securedPassword, String salt) {
        boolean returnValue = false;

        // Generate New secure password with the same salt
        String newSecurePassword = generateSecurePassword(providedPassword, salt);

        // Check if two passwords are equal
        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);

        return returnValue;
    }*/

    /*public void verifyDigitalSignature() {
        byte[] message = password.getBytes();
        byte[] signature = ...;
        PublicKey key = ...;
        Signature s = Signature.getInstance("SHA256withECDSA");
        s.initVerify(key);
        s.update(message);
        boolean valid = s.verify(signature);
    }*/

    public byte[] generateDigitalSignature() {
        byte[] message = password.getBytes();
        PrivateKey key = new PrivateKey() {
            @Override
            public String getAlgorithm() {
                return "DSA";
            }

            @Override
            public String getFormat() {
                return "PKCS#8";
            }

            @Override
            public byte[] getEncoded() {
                //return new byte[0];
                return null;
            }
        };
        try {
            Signature s = Signature.getInstance("SHA256withECDSA");
            s.initSign(key);
            s.update(message);
            byte[] signature = s.sign();
            return signature;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return "derp".getBytes();
    }

    public byte[] encryptPassword() {
        byte[] plaintext = password.getBytes();

        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(256);
            SecretKey key = keygen.generateKey();
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] ciphertext = cipher.doFinal(plaintext);
            System.out.println(ciphertext);
            System.out.println(new String(ciphertext));
            byte[] iv = cipher.getIV();

            this.ciphertext = new String(ciphertext);
            this.iv = new String(iv);
            this.key = key;

            return ciphertext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "derp".getBytes();
    }

    public byte[] decryptPassword(String initVector, SecretKey key, String encrypted) {
        Cipher cipher = null;
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            //SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            return original;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "derp".getBytes();
    }
}

