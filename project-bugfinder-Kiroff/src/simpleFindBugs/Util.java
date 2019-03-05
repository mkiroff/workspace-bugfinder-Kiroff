package simpleFindBugs;

import java.security.InvalidKeyException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.junit.Assert;
import org.junit.Test;

public class Util {
	static EncryptionDecryptionAES	aes	= new EncryptionDecryptionAES();
	static String							encryptedText;

	@Test
	public void testReadPassword() {
		try {
			String plainText = "AES Symmetric Encryption Decryption";
			Util.savePassword(plainText);
			String decryptedText = Util.readPassword();
			System.out.println("Original Text: " + plainText);
			System.out.println("Encrypted Text After Encryption: " + encryptedText);
			System.out.println("Decrypted Text After Decryption: " + decryptedText);
			Assert.assertTrue("plainText: " + plainText + ", decryptedText: " + decryptedText, plainText.equals(decryptedText));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void savePassword(String plainText) {
		encryptedText = aes.encrypt(plainText, aes.secretKey);
	}

	public static String readPassword() {
		return aes.decrypt(encryptedText, aes.secretKey);
	}
}

class EncryptionDecryptionAES {
	Cipher		cipher;
	SecretKey	secretKey;

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

	public String encrypt(String plainText, SecretKey secretKey) {
		try {
			byte[] plainTextByte = plainText.getBytes();
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encryptedByte = cipher.doFinal(plainTextByte);
			Base64.Encoder encoder = Base64.getEncoder();
			String encryptedText = encoder.encodeToString(encryptedByte);
			return encryptedText;
		} catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		return null;
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