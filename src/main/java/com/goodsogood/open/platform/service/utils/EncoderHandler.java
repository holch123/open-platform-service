package com.goodsogood.open.platform.service.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Function;

import static java.util.Arrays.stream;

public abstract class EncoderHandler {

	/**
	 * HEX_DIGITS 16进制字符
	 */
	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	public static String MD5(String input) {
		return hash(input, "MD5", "UTF-8");
	}

	public static String SHA1(String input) {
		return hash(input, "SHA-1", "UTF-8");
	}

	public static String SHA512(String input) {
		return hash(input, "SHA-512", "UTF-8");
	}

	public static String HMAC_SHA1(String key, byte[]... data) {
		if (data == null || data.length == 0) {
			throw new IllegalArgumentException("data is null or empty");
		}
		return hashWithKey(key, "HMACSHA1", "UTF-8", mac -> {
			stream(data).forEach(mac::update);
			return mac.doFinal();
		});
	}

	public static String HMAC_SHA1(String key, Function<Mac, byte[]> function) {
		return hashWithKey(key, "HMACSHA1", "UTF-8", function);
	}

	// 摘要算法加密
	private static String hash(String input, String algorithm, String encoding) {
		notNull(input, "input is null");
		notNull(algorithm, "algorithm is null");
		notNull(encoding, "encoding is null");
		MessageDigest messageDigest;
		byte[] bytes;
		try {
			messageDigest = MessageDigest.getInstance(algorithm);
			bytes = messageDigest.digest(input.getBytes(encoding));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return bytesToHex(bytes);
	}

	private static String hashWithKey(String key, String algorithm, String encoding, Function<Mac, byte[]> function) {
		notNull(key, "key is null");
		notNull(algorithm, "algorithm is null");
		notNull(encoding, "encoding is null");
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(encoding), algorithm);
			Mac mac = Mac.getInstance(algorithm);
			mac.init(signingKey);
			byte[] bytes = function.apply(mac);
			return bytesToHex(bytes);
		} catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	// bytesToHex
	private static String bytesToHex(byte[] bytes) {
		StringBuilder buff = new StringBuilder();
		for (byte bt : bytes) {
			buff.append(HEX_DIGITS[(bt & 0xf0) >> 4]).append("").append(HEX_DIGITS[bt & 0xf]);
		}
		return buff.toString();
	}

	private static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}
}
