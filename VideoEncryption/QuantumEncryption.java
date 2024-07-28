package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;
//creating quantum encryption class
public class QuantumEncryption {

    private Logger log = LoggerFactory.getLogger(this.getClass());
	//using bouncy castle as the provider for encryption and decryption
    static {
        Security.addProvider(Identifiers.BCPROVIDER);
    }
	//generating keys for each user using their username and password
    public void generateKeyStore(String name, String password) throws Exception {
        SecretKey secretKey = generateSecretKey();
        KeyStore keyStore = KeyStore.getInstance(Identifiers.KEYSTORE_FORMAT);
        keyStore.load(null, password.toCharArray());
        keyStore.setEntry(Identifiers.ALIAS_SYM, new KeyStore.SecretKeyEntry(secretKey),
                new KeyStore.PasswordProtection(password.toCharArray()));
        try (FileOutputStream fos = new FileOutputStream("keys/"+name + Identifiers.KEYSTORE_FILE_FORMAT)) {
            keyStore.store(fos, password.toCharArray());
            log.info("Keystore was created successfully with name " + name + Identifiers.KEYSTORE_FILE_FORMAT);
        }
    }

    public SecretKey generateSecretKey() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance(Identifiers.SYMALGORITHM, Identifiers.BCPROVIDER);
        generator.init(Identifiers.KEYSIZE, new SecureRandom());
        return generator.generateKey();
    }

    public SecretKey getSecretKeyFromEncoded(byte [] keyBytes) {
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, Identifiers.SYMALGORITHM);
    }
	//load keys for given user key file and password
    public SecretKey loadSecretKeyFromKeyStore(String filename, String password) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(Identifiers.KEYSTORE_FORMAT);
        keyStore.load(new FileInputStream(filename), password.toCharArray());
        log.info("Symmetric key was loaded successfully from keystore");
        return (SecretKey) keyStore.getKey(Identifiers.ALIAS_SYM, password.toCharArray());
    }
	//function to encrypt video file using secretkey and plain data
    public ArrayList<byte[]> encrypt(SecretKey secretKey, byte [] data) throws Exception {
        byte[] iv = new byte[128];
        new SecureRandom().nextBytes(iv);//generating secure random number to avoid hack from attacker if quantum computers also used
        Cipher cipher = Cipher.getInstance(Identifiers.SYM_CIPHER);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        ArrayList<byte []> result = new ArrayList<>();
        result.add(cipher.doFinal(data));
        result.add(iv);
        return result;
    }
	//function to decrypt video using secret key and secured random number which will known to only genuine users
    public byte[] decrypt(SecretKey secretKey, byte [] encrypted, byte [] secure_random) throws Exception{
        Cipher cipher = Cipher.getInstance(Identifiers.SYM_CIPHER);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(secure_random));
        return cipher.doFinal(encrypted);
    }

}
