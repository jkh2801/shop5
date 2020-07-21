package util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtil {
	private final static byte [] iv = new byte [] {
			(byte) 0x8E, 0x12, 0x39, (byte) 0x9C,
				0x07, 0x72, 0x6F, (byte) 0x5A,
				(byte) 0x8E, 0x12, 0x39, (byte) 0x9C,
				0x07, 0x72, 0x6F, (byte) 0x5A}; 
	static Cipher cipher;
	static { // static 초기화 블럭 : static 변수의 초기화
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String makehash(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] plain = password.getBytes();
		byte[] hash = md.digest(plain); // 암호 알고리즘을 실행하여 생성한 hash로 생성
		return byteToHex(hash);
	}

	private static String byteToHex(byte[] hash) {
		if(hash == null) return null;
		String str = "";
		for(byte b : hash) str += String.format("%02X", b);
		return str;
	}
	
	public static String encrypt(String plain, String key) {
		byte [] cipherMsg = new byte[1024];
		try {
			Key genkey = new SecretKeySpec(makeKey(key), "AES"); 
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv); // 초기화 벡터 설정
			cipher.init(Cipher.ENCRYPT_MODE, genkey, paramSpec); // Ciper.ENCRYPT_MODE : 암호화모드
			cipherMsg = cipher.doFinal(plain.getBytes()); // 암호화 실행
		} catch (Exception e) {
			e.printStackTrace();
		}
		return byteToHex(cipherMsg);
	}

	public static String decrypt(String ciphermsg, String key) {
		byte [] plainMsg = new byte[1024];
		try {
			Key genkey = new SecretKeySpec(key.getBytes(), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv); // 초기화 벡터 : CBC 모드에서 필요
			cipher.init(Cipher.DECRYPT_MODE, genkey, paramSpec); // 복호화 모드 설정
			plainMsg = cipher.doFinal(hexToByte(ciphermsg.trim()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(plainMsg).trim();
	}
	
	public static byte[] hexToByte(String str) {
		if (str == null || str.length() < 2) return null;
		int len = str.length() / 2;
		byte [] buf = new byte [len];
		for (int i = 0; i < len; i++) {
			buf[i] = (byte) Integer.parseInt(str.substring(i*2,i*2+2), 16);
		}
		return buf;
	}
	
	// 128bit = 16byte
		private static byte[] makeKey(String key) {
			int len = key.length();
			char ch = 'A';
			for(int i= len; i < 16; i++) key += ch++;
			return key.substring(0, 16).getBytes();
		}
}
