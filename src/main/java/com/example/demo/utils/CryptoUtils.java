package com.example.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * @author Kisong
 */
public class CryptoUtils {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    private static final byte[] KEY = "demoappserverkey".getBytes();

    private static final Logger logger = LoggerFactory.getLogger(CryptoUtils.class);

    /**
     * Gets the algorithm
     *
     * @return algorithm
     */
    public static String algorithm() {
        return ALGORITHM;
    }

    /**
     * Gets the default key
     *
     * @return default key
     */
    public static Key key() {
        return new SecretKeySpec(KEY, "AES");
    }

    /**
     * encrypt content with default key
     *
     * @param contents content about to encrypt
     * @return AES-256 encrypted content in byte array
     */
    public static byte[] encrypt(byte[] contents) {
        try {
            Key key = new SecretKeySpec(KEY, "AES");
            return encrypt(contents, key);
        } catch (Exception e) {
            logger.error("[encrypt] Error ", e);
            return null;
        }
    }

    /**
     * Encrypt AES-256 encrypted contents with given randomKey
     *
     * @param contents content about to encrypt
     * @param randomKey random key
     * @return encrypted content in byte array
     */
    public static byte[] encrypt(byte[] contents, String randomKey) {
        try {
            Key key = new SecretKeySpec(randomKey.getBytes(), "AES");
            return encrypt(contents, key);
        } catch (Exception e) {
            logger.error("[encrypt] Error ", e);
            return null;
        }
    }

    /**
     * Decrypt AES-256 encrypted contents with static key
     *
     * @param contents AES-256 encrypted content
     * @return decrypted content in byte array
     */
    public static byte[] decrypt(byte[] contents) {
        try {
            Key key = new SecretKeySpec(KEY, "AES");
            return decrypt(contents, key);
        } catch (Exception e) {
            logger.error("[decrypt] Error ", e);
            return null;
        }
    }

    /**
     * Decrypt AES-256 encrypted contents with given randomKey
     *
     * @param contents AES-256 encrypted content
     * @param randomKey random key
     * @return decrypted content in byte array
     */
    public static byte[] decrypt(byte[] contents, String randomKey) {
        try {
            Key key = new SecretKeySpec(randomKey.getBytes(), "AES");
            return decrypt(contents, key);
        } catch (Exception e) {
            logger.error("[decrypt] Error ", e);
            return null;
        }
    }

    private static byte[] encrypt(byte[] contents, Key key) {
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            return c.doFinal(contents);
        } catch (Exception e) {
            logger.error("[encrypt] Error ", e);
            return null;
        }
    }

    private static byte[] decrypt(byte[] contents, Key key) {
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            return c.doFinal(contents);
        } catch (Exception e) {
            logger.error("[decrypt] Error ", e);
            return null;
        }
    }

}
