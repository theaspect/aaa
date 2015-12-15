package com.blzr.service;

import com.blzr.InjectLogger;
import com.blzr.dao.ActivityDao;
import com.blzr.dao.AuthorityDao;
import com.blzr.dao.UserDao;
import com.blzr.domain.Activity;
import com.blzr.domain.Authority;
import com.blzr.domain.Role;
import com.blzr.domain.User;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AaaService {
    @InjectLogger
    private Logger log;
    @Inject
    private UserDao userDao;
    @Inject
    private AuthorityDao authorityDao;
    @Inject
    private ActivityDao activityDao;

    public Long addActivity(Authority authority, String dateStart, String dateEnd, String volume) throws ParseException, NumberFormatException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        activityDao.createActivity(new Activity(authority,
                format.parse(dateStart), format.parse(dateEnd), Integer.valueOf(volume)));
        return activityDao.countActivity();
    }

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
    public String generateHash(String password, String salt) {
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

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return byteArrayToHex(bytes);
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    public boolean isUserExist(String username) {
        return userDao.findByName(username) != null;
    }

    public boolean isRoleExist(String role) {
        return Role.getRole(role) != null;
    }

    public boolean isPasswordCorrect(String username, String password) {
        final User user = userDao.findByName(username);
        return validatePassword(password, user.getHash(), user.getSalt());
    }

    public Authority getAuthority(String username, String role, String site) {
        User user = userDao.findByName(username);
        Role r = Role.getRole(role);
        if (r == null) {
            return null;
        }

        for (Authority a : authorityDao.getAuthoritiesByUserAndRole(user, r)) {
            if (isSubSite(a.getSite(), site)) {
                return a;
            }
        }
        return null;
    }

    public boolean isAuthorized(String username, String role, String site) {
        return getAuthority(username, role, site) != null;
    }

    /**
     * Check if dst subsite of src
     * a.b, a.b.c -> true
     * a.b, a.b   -> true
     * a.b, a     -> false
     * a.b, a.d   -> false
     */
    private boolean isSubSite(String src, String dst) {
        String[] srcList = src.split("\\.");
        String[] dstList = dst.split("\\.");

        if (dstList.length < srcList.length) {
            return false;
        }

        for (int i = 0; i < srcList.length; i++) {
            if (!srcList[i].equals(dstList[i])) {
                return false;
            }
        }
        return true;
    }
}
