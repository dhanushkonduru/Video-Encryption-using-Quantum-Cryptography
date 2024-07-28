package com;
import javax.crypto.SecretKey;
import java.util.ArrayList;
public class test {
public static void main(String args[]) throws Exception{
	 QuantumEncryption qc = new  QuantumEncryption();

	 //SecretKey key = qc.generateSecretKey();

	 //qc.generateKeyStore("aaa", "aaa");
	 SecretKey key = qc.loadSecretKeyFromKeyStore("aaa.ubr", "aaa");
	 ArrayList<byte[]> encrypt = qc.encrypt(key, "Hello world".getBytes());
	 byte decrypt[] = qc.decrypt(key, encrypt.get(0), encrypt.get(1));
	 System.out.println(encrypt.get(0));
	 System.out.println(new String(decrypt));
}
}