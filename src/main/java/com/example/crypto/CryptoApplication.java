package com.example.crypto;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CryptoApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException {

		MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
		System.out.println("Lenght : " + md.getAlgorithm().length() + "Algorithm :" + getKeyFactory("ECDSA").toString());
		SpringApplication.run(CryptoApplication.class, args);


	}

	public static KeyFactory getKeyFactory(String algorithm) throws NoSuchAlgorithmException {
		if("ECDSA".equals(algorithm)) {
			// ECDSA is not a listed JavaSE KeyFactory algorithm
			// see https://docs.oracle.com/en/java/javase/11/docs/specs/security/standard-names.html#cipher-algorithm-names
			algorithm = "EC";
		}
		return KeyFactory.getInstance(algorithm);
	}


	public static byte[] computeArtifactBindingIdentifier(String identifierFrom) {
		try {
			MessageDigest sha1Digester = MessageDigest.getInstance("SHA-1");
			return sha1Digester.digest(identifierFrom.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("JVM does not support required cryptography algorithms: SHA-1/SHA1PRNG.", e);
		}
	}

}
