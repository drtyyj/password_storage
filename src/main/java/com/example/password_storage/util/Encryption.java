package com.example.password_storage.util;

import com.example.password_storage.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Hex;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

    private static String hashingAlgorithm = "MD5";

    public static void hash(User user) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(hashingAlgorithm);
        } catch(NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
        md.update(user.getPassword().getBytes());

        byte[] messageDigest = md.digest(user.getPassword().getBytes());

        // Convert byte array into signum representation
        BigInteger no = new BigInteger(1, messageDigest);

        // Convert message digest into hex value
        String hashtext = no.toString(16);

        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        
        System.out.println(hashtext);
        user.setPassword(hashtext);

    }

    public static void hashWithSalt(User user) {
        //TODO: hashing with salt should retrieve salt and inject into user object
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        user.setPassword(bcrypt.encode(user.getPassword()));
    }

    public static void hashWithSaltPepper(User user) {
        //TODO: whole thing
    }

    //TODO: what else is needed?
}
