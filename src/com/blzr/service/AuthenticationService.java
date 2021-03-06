package com.blzr.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class AuthenticationService {
    private static final Logger log = LogManager.getLogger(AuthenticationService.class);

    public boolean validatePassword(String password, String hash, String salt) {
        try {
            return MessageDigest.isEqual(generateHash(password, salt).getBytes("UTF-8"), hash.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("Cannot validate password", e);
            throw new RuntimeException("Suppress exception", e);
        }
    }

    /**
     * sha(sha(pass)+salt)
     */
    public static String generateHash(String password, String salt) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] shaFirst = password.getBytes("UTF-8");
            sha.reset();
            sha.update(shaFirst);
            sha.update(sha.digest());
            sha.update(salt.getBytes("UTF-8"));
            return byteArrayToHex(sha.digest());
        } catch (Exception e) {
            log.error("Cannot generate hash", e);
            throw new RuntimeException("Suppress exception", e);
        }
    }

    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return byteArrayToHex(bytes);
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }
}
