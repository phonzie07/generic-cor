package com.generic.utils;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomUtility {
    public static String randomString() {
        byte[] bytes = new byte[256];
        new SecureRandom().nextBytes(bytes);
        String random = new String(bytes, Charset.forName("UTF-8"));
        
        StringBuffer stringBuffer = new StringBuffer();
        for(int k = 0, n = 16; k < random.length(); k++) {
            char ch = random.charAt(k);
        
            if(((ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) && (n > 0)) {
                stringBuffer.append(ch);
                n--;
            }
        }
        return stringBuffer.toString();
    }

    public static String generateBatchNumber() {
        byte[] bytes = new byte[256];
        new SecureRandom().nextBytes(bytes);
        String random = new String(bytes, Charset.forName("UTF-8"));

        StringBuffer stringBuffer = new StringBuffer();
        for(int k = 0, n = 9; k < random.length(); k++) {
            char ch = random.charAt(k);

            if(((ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) && (n > 0)) {
                stringBuffer.append(ch);
                n--;
            }
        }
        String timeStr = String.valueOf(System.currentTimeMillis()).substring(9);
        return stringBuffer.toString() + timeStr;
    }

    public static String randomStringUpperCase(int number) {
        byte[] bytes = new byte[256];
        new SecureRandom().nextBytes(bytes);
        String random = new String(bytes, Charset.forName("UTF-8"));

        StringBuffer stringBuffer = new StringBuffer();
        for(int k = 0; k < random.length(); k++) {
            char ch = random.charAt(k);

            if((ch >= 'A' && ch <= 'Z') && (number > 0)) {
                stringBuffer.append(ch);
                number--;
            }
        }
        return stringBuffer.toString();
    }

    public static String random(int size) {

        StringBuilder generatedToken = new StringBuilder();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            // Generate 20 integers 0..20
            for (int i = 0; i < size; i++) {
                generatedToken.append(number.nextInt(9));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedToken.toString();
    }

    private String randomCodeFromString(String name) {
        String randomCode = name + randomStringUpperCase(10 - name.length());
        return randomCode;
    }
}
