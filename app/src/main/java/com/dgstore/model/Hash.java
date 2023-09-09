package com.dgstore.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Hash {

    public String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());

            Formatter formatter = new Formatter();
            for(byte b : hashBytes) {
                formatter.format("%02x", b);
            }
            String hash = formatter.toString();
            formatter.close();

            return hash;
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void main(String[] args) {
        String originalString = "Hashlenecek";
        String hashedString = sha256(originalString);

        System.out.println("Orijinal Metin: " + originalString);
        System.out.println("Hash Değeri: " + hashedString);
    }
}
