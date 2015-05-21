package com.udacity.webdev.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

public class HashUtils {

	//TODO: this should be in a config file
	private static String secretKey = "qnscAdgRlkIhAUPY44oiexBKtQbGY0orf7OV1I50";
	
	/**
	 * Uses HMAC(md5) to create a hash for a cookie
	 * @param cookieValue
	 * @return
	 * @throws InvalidKeyException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static String createHashForCookie(String cookieValue) {
		
		SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "HmacMD5");

        Mac mac;
        byte[] rawHmac = null;
		try {
			mac = Mac.getInstance("HmacMD5");
			mac.init(keySpec);
		    rawHmac = mac.doFinal(cookieValue.getBytes());
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			
			e.printStackTrace();
		}

        return Hex.encodeHexString(rawHmac);
	}
	
	/**
	 * It uses a ramdom salt of 16 bits and SHA256 for hashing
	 * @return (%salt,%hash)
	 */
	public static String createSaltedHash(String param, String salt) {
		
		if(salt == null)
			salt = getRandomSalt();
		
		String hash = DigestUtils.sha256Hex(param + salt);
		
		return salt + "," + hash;
	}
	
	private static String getRandomSalt() {
		
        return RandomStringUtils.randomAlphabetic(5);
	}
}
