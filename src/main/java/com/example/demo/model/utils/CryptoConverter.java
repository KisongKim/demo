package com.example.demo.model.utils;

import com.example.demo.ServiceError;
import com.example.demo.ServiceException;
import com.example.demo.utils.CryptoUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.persistence.AttributeConverter;
import java.util.Base64;

/**
 * @author Kisong
 */
public class CryptoConverter implements AttributeConverter<String, String> {

    private static final Logger logger = LoggerFactory.getLogger(CryptoConverter.class);

    /**
     * Encrypt the entity attribute with AES-256
     *
     * @param attribute entity attribute about to encrypt
     * @return encrypted entity attribute
     */
    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            if (StringUtils.isEmpty(attribute)) {
                return null;
            }
            Cipher c = Cipher.getInstance(CryptoUtils.algorithm());
            c.init(Cipher.ENCRYPT_MODE, CryptoUtils.key());
            return Base64.getEncoder().encodeToString(c.doFinal(attribute.getBytes()));
        } catch (Exception e) {
            logger.error("[convertToDatabaseColumn] Error ", e);
            throw new ServiceException(ServiceError.INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Decrypt the entity attribute that encrypted in advance.
     *
     * @param dbData entity attribute about to decrypt
     * @return decrypted entity attribute
     */
    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            if (StringUtils.isEmpty(dbData)) {
                return null;
            }
            Cipher c = Cipher.getInstance(CryptoUtils.algorithm());
            c.init(Cipher.DECRYPT_MODE, CryptoUtils.key());
            return new String(c.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (Exception e) {
            logger.error("[convertToEntityAttribute] Error ", e);
            throw new ServiceException(ServiceError.INTERNAL_SERVER_ERROR, e);
        }
    }

}
