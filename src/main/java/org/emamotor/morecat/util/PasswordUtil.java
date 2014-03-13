package org.emamotor.morecat.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author tanabe
 */
public class PasswordUtil {

    private static final String DEFAULT_ALGORITHM = "SHA-512";

    public static String hasing(String plainPassword, String algorithm) {

        MessageDigest md;
        String hashedPassword = null;

        try {
            md = MessageDigest.getInstance(algorithm);
            md.update(plainPassword.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : md.digest()) {
                String hex = String.format("%02x", b);
                sb.append(hex);
            }
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return hashedPassword;

    }

    public static String hasing(String plainPassword) {
        return hasing(plainPassword, DEFAULT_ALGORITHM);
    }

}
