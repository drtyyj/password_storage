package com.example.password_storage.util;

import com.example.password_storage.model.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {

    private static String secret = "123456";
    private static String hashingAlgorithm = "MD5";

    public static void hash(User user) {
        user.setPassword(encodeMD5(user.getPassword()));
    }

    public static String verifyHash(String password) {
        return encodeMD5(password);
    }

    public static void hashWithSalt(User user) {
        // Generating random salt
        String salt = BCrypt.gensalt();
        user.setPassword(BCrypt.hashpw(user.getPassword(), salt));
        user.setSalt(salt);
    }

    public static String verifyWithSalt(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }
    
    public static void hashWithPepper(User user) {
        try {  
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");  
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");  
            sha256HMAC.init(secretKey);  
            BigInteger number = new BigInteger(1, sha256HMAC.doFinal(user.getPassword().getBytes()));  
            String signature = number.toString(16);
            user.setPassword(signature);
        } catch (Exception ex) {  

        }
    }

    public static String verifyWithPepper(String password) {
        try {
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256HMAC.init(secretKey);
            BigInteger number = new BigInteger(1, sha256HMAC.doFinal(password.getBytes()));  
            String signature = number.toString(16);
            return signature;
        } catch (Exception ex) {
            return null;
        }
    }

    public static void hashWithSaltPepper(User user) {
        try {
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256HMAC.init(secretKey);
            BigInteger number = new BigInteger(1, sha256HMAC.doFinal(user.getPassword().getBytes()));  
            String signature = number.toString(16);
            String salt = BCrypt.gensalt();
            user.setPassword(BCrypt.hashpw(signature,salt));
            user.setSalt(salt);
        } catch (Exception ex) {

        }
    }

    public static String verifyWithSaltPepper(String password, String salt) {
        try {
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256HMAC.init(secretKey);
            BigInteger number = new BigInteger(1, sha256HMAC.doFinal(password.getBytes()));  
            String signature = number.toString(16);
            return BCrypt.hashpw(signature,salt);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String encodeMD5(String input) {

        MessageDigest md;
        try {
            md = MessageDigest.getInstance(hashingAlgorithm);

            byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
            //md.update(bytes);

            byte[] messageDigest = md.digest(bytes);

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        } catch(NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    //TODO: what else is needed?
}
