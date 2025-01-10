package com.example.password_storage.util;

import com.example.password_storage.model.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64; 

public class Encryption {

    private static String secret = "123456789";
    private static String hashingAlgorithm = "MD5";

    public static void hash(User user) {

        user.setPassword(encodeMD5(user.getPassword()));

    }

    public static void hashWithSalt(User user) {
        // Generating random salt
        String salt = BCrypt.gensalt();
        user.setPassword(BCrypt.hashpw(user.getPassword(), salt));
        user.setSalt(salt);
    }
    
    public static void hashWithPepper(User user) {
        try {  
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");  
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");  
            sha256HMAC.init(secretKey);  
            String signature = Base64.getEncoder().encodeToString(sha256HMAC.doFinal(user.getPassword().getBytes()));  
            user.setPassword(encodeMD5(signature));
        } catch (Exception ex) {  

        }
    }

    public static void hashWithSaltPepper(User user) {
        try {  
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");  
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");  
            sha256HMAC.init(secretKey);  
            String signature = Base64.getEncoder().encodeToString(sha256HMAC.doFinal(user.getPassword().getBytes()));  
            System.out.println(signature);
            String salt = BCrypt.gensalt();
            user.setPassword(BCrypt.hashpw(signature,salt));  
            user.setSalt(salt);
        } catch (Exception ex) {  

        }
    }
    public static String encodeMD5(String input) {

        MessageDigest md;
        try {
            md = MessageDigest.getInstance(hashingAlgorithm);
        } catch(NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
        md.update(input.getBytes());

        byte[] messageDigest = md.digest(input.getBytes());

        // Convert byte array into signum representation
        BigInteger no = new BigInteger(1, messageDigest);

        // Convert message digest into hex value
        String hashtext = no.toString(16);

        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }
    //TODO: what else is needed?
}
