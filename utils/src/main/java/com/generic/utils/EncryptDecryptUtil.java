package com.generic.utils;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

@Service
public class EncryptDecryptUtil {

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getKey( String filename ) throws IOException {
        // Read key from file
        String strKeyPEM = "";
        BufferedReader br = new BufferedReader( new FileReader( filename ) );
        String line;
        while ( ( line = br.readLine( ) ) != null ) strKeyPEM += line + "\n";
        br.close( );
        return strKeyPEM;
    }


    /**
     * Gets public key.
     *
     * @param filename the filename
     * @return the public key
     * @throws IOException              the io exception
     * @throws GeneralSecurityException the general security exception
     */
    public static RSAPublicKey getPublicKey( String filename ) throws IOException, GeneralSecurityException {
        String publicKeyPEM = getKey( filename );
        return getPublicKeyFromString( publicKeyPEM );
    }

    /**
     * Gets public key from string.
     *
     * @param key the key
     * @return the public key from string
     * @throws IOException              the io exception
     * @throws GeneralSecurityException the general security exception
     */
    public static RSAPublicKey getPublicKeyFromString( String key ) throws IOException, GeneralSecurityException {
        String publicKeyPEM = key;
        publicKeyPEM = publicKeyPEM
                .replace( "-----BEGIN PUBLIC KEY-----\n", "" )
                .replace( "-----BEGIN PUBLIC KEY-----", "" )
                .replace( "-----END PUBLIC KEY-----", "" );
        byte[] encoded = org.apache.tomcat.util.codec.binary.Base64.decodeBase64( publicKeyPEM );
        KeyFactory kf = KeyFactory.getInstance( "RSA" );
        RSAPublicKey pubKey = ( RSAPublicKey ) kf.generatePublic( new X509EncodedKeySpec( encoded ) );
        return pubKey;
    }

    public static String encrypt(String strToEncrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }


    /**
     * Encrypt string.
     *
     * @param rawText   the raw text
     * @param publicKey the public key
     * @return the string
     * @throws IOException              the io exception
     * @throws GeneralSecurityException the general security exception
     */
    public static String encryptUsingRSA( String rawText, PublicKey publicKey ) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance( "RSA" );
        cipher.init( Cipher.ENCRYPT_MODE, publicKey );
        return org.apache.tomcat.util.codec.binary.Base64.encodeBase64String( cipher.doFinal( rawText.getBytes( ) ) );
    }

    /**
     * Decrypt string.
     *
     * @param cipherText the cipher text
     * @param privateKey the private key
     * @return the string
     * @throws IOException              the io exception
     * @throws GeneralSecurityException the general security exception
     */
    public static String decryptUsingRSA( String cipherText,
                                  PrivateKey privateKey ) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance( "RSA" );
        cipher.init( Cipher.DECRYPT_MODE, privateKey );
        return new String( cipher.doFinal( org.apache.tomcat.util.codec.binary.Base64.decodeBase64( cipherText ) ), "UTF-8" );
    }

    public static String getMd5(String input) {
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            for (; hashtext.length() < 32; ) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        } catch (NoSuchAlgorithmException e) { // For specifying wrong message digest algorithms
            throw new RuntimeException(e);
        }
    }

    // https://www.geeksforgeeks.org/java-util-uuid-class-java/
    public static String getUUID(String input) {
        /*String id = "c81d4e2e-bcf2-11e6-869b-7df92533d2db"; // 9c50fe77-3797-3427-83cd-a19d1e95b555
        byte[] b = id.getBytes();*/
        byte[] b = input.getBytes();

        // Creating UUID from array
        UUID uuid = UUID.nameUUIDFromBytes(b);

        return uuid.toString();
    }


}
