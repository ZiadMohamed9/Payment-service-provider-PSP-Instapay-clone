package com.psp.cibbank.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 * Utility class for encryption and decryption using AES-GCM algorithm.
 * Provides methods to encrypt and decrypt text and phone numbers securely.
 */
@Component
public class EncryptionUtil {

    @Value("${encryption.key}")
    private String encryptionKey;

    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private final SecureRandom secureRandom = new SecureRandom();

    // Pattern to validate phone numbers (simple pattern for demonstration)
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+?[0-9]{10,15}$");

    /**
     * Encrypts the given text using AES-GCM encryption algorithm.
     * AES-GCM is an authenticated encryption algorithm that provides both confidentiality and integrity.
     *
     * @param text the text to encrypt
     * @return the encrypted text encoded as Base64 string
     */
    public String encrypt(String text) {
        try {
            // Generate a secure random IV (Initialization Vector)
            byte[] iv = new byte[GCM_IV_LENGTH];
            secureRandom.nextBytes(iv);

            // Create GCM parameter specification
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);

            // Get a secure key
            SecretKey secretKey = getSecretKey();

            // Initialize cipher for encryption
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);

            // Encrypt the text
            byte[] encryptedBytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

            // Combine IV and encrypted data
            ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + encryptedBytes.length);
            byteBuffer.put(iv);
            byteBuffer.put(encryptedBytes);

            // Encode as a Base64 string
            return Base64.getEncoder().encodeToString(byteBuffer.array());
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data", e);
        }
    }

    /**
     * Decrypts the given encrypted text using AES-GCM encryption algorithm.
     *
     * @param encryptedText the encrypted text encoded as Base64 string
     * @return the decrypted text
     */
    public String decrypt(String encryptedText) {
        try {
            // Decode the Base64 string
            byte[] encryptedData = Base64.getDecoder().decode(encryptedText);

            // Extract IV and ciphertext
            ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedData);
            byte[] iv = new byte[GCM_IV_LENGTH];
            byteBuffer.get(iv);

            byte[] ciphertext = new byte[byteBuffer.remaining()];
            byteBuffer.get(ciphertext);

            // Create GCM parameter specification
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);

            // Get the secret key
            SecretKey secretKey = getSecretKey();

            // Initialize cipher for decryption
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);

            // Decrypt the data
            byte[] decryptedBytes = cipher.doFinal(ciphertext);

            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting data", e);
        }
    }

    /**
     * Creates a secure key from the encryption key string.
     * Uses SHA-256 to derive a secure key of the appropriate length.
     *
     * @return a SecretKey for AES encryption
     */
    private SecretKey getSecretKey() {
        try {
            // Use SHA-256 to create a secure key from the encryption key string
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = digest.digest(encryptionKey.getBytes(StandardCharsets.UTF_8));
            return new SecretKeySpec(keyBytes, "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error creating secret key", e);
        }
    }

    /**
     * Encrypts a phone number using AES-GCM encryption algorithm.
     * Validates that the input is a valid phone number before encryption.
     *
     * @param phoneNumber the phone number to encrypt
     * @return the encrypted phone number encoded as Base64 string
     * @throws IllegalArgumentException if the phone number format is invalid
     */
    public String encryptPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        return encrypt(phoneNumber);
    }

    /**
     * Decrypts an encrypted phone number.
     * Validates that the decrypted result is a valid phone number.
     *
     * @param encryptedPhoneNumber the encrypted phone number encoded as Base64 string
     * @return the decrypted phone number
     * @throws IllegalArgumentException if the decrypted result is not a valid phone number
     */
    public String decryptPhoneNumber(String encryptedPhoneNumber) {
        String decryptedPhoneNumber = decrypt(encryptedPhoneNumber);
        if (!PHONE_NUMBER_PATTERN.matcher(decryptedPhoneNumber).matches()) {
            throw new IllegalArgumentException("Decrypted value is not a valid phone number");
        }
        return decryptedPhoneNumber;
    }
}
