package com.example.springboot;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Validation {

//  public static byte[] encrypt(String x) throws Exception {
//    java.security.MessageDigest d = null;
//    d = java.security.MessageDigest.getInstance("SHA-1");
//    d.reset();
//    d.update(x.getBytes());
//    return d.digest();
//  }

  public static String encryptThisString(String input) {
    try {
      // getInstance() method is called with algorithm SHA-1
      MessageDigest md = MessageDigest.getInstance("SHA-1");

      // digest() method is called
      // to calculate message digest of the input string
      // returned as array of byte
      byte[] messageDigest = md.digest(input.getBytes());

      // Convert byte array into signum representation
      BigInteger no = new BigInteger(1, messageDigest);

      // Convert message digest into hex value
      String hashtext = no.toString(16);

      // Add preceding 0s to make it 32 bit
      while (hashtext.length() < 32) {
        hashtext = "0" + hashtext;
      }

      // return the HashText
      return hashtext;
    }

    // For specifying wrong message digest algorithms
    catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }


private static final String key = "aesEncryptionKey";
private static final String initVector = "encryptionIntVec";

public static String encrypt(String value) {
    try {
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        byte[] encrypted = cipher.doFinal(value.getBytes());
        return encrypted.toString();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return null;
}
public static String decrypt(String encrypted) {
  try {
      IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
      SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
      byte[] original = cipher.doFinal(encrypted.getBytes());

      return new String(original);
  } catch (Exception ex) {
      ex.printStackTrace();
  }

  return null;
}
  private String username = "";

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    try {
      this.password = encrypt(password);
      System.out.println("check++"+password);
      System.out.println(decrypt("original"+password));
      // this.password = encrypt(password).toString();
    } catch (Exception e) {
      System.out.println("You have an error");
    }
    return password;
  }


//  public void setPassword(String password) {
//    try {
//      this.password = encrypt(password);
//      // this.password = encrypt(password).toString();
//    } catch (Exception e) {
//      System.out.println("You have an error");
//    }
//  }


  private String password;

  public Validation(String username, String password) {
    this.username = username;
    this.password = password;

  }


}
