package class_exercise;

import java.security.InvalidKeyException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Util {
   static EncryptionDecryptionAES aes = new EncryptionDecryptionAES();

   public static String PWD() {
      return aes.decrypt(System.getProperty("PROPERTY_ENCRPTEDPWD"), aes.secretKey);
   }

   public static String ID() {
      return System.getProperty("PROPERTY_ID");
   }

   public static String URL() {
      return System.getProperty("PROPERTY_URL");
   }
}

class EncryptionDecryptionAES {
   Cipher cipher;
   SecretKey secretKey;

   public EncryptionDecryptionAES() {
      try {
         // Cryptographic algorithm -- Advanced Encryption Standard
         KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
         keyGenerator.init(128);
         secretKey = keyGenerator.generateKey();
         cipher = Cipher.getInstance("AES");
         /*
          This javax.crypto.Cipher class provides the functionality of a cryptographic cipher for 
          encryption and decryption. It forms the core of the Java 
          Cryptographic Extension (JCE) framework.
          */
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public String decrypt(String encryptedText, SecretKey secretKey) {
      try {
         Base64.Decoder decoder = Base64.getDecoder();
         byte[] encryptedTextByte = decoder.decode(encryptedText);
         cipher.init(Cipher.DECRYPT_MODE, secretKey);
         byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
         String decryptedText = new String(decryptedByte);
         return decryptedText;
      } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
         e.printStackTrace();
      }
      return null;
   }
}
