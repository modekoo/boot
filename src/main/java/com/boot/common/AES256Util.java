package com.boot.common;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

import com.boot.entity.User;

public class AES256Util {
	
	private String iv;
    private Key keySpec;
    private static AES256Util instance = new AES256Util("helloworld123456789101");
    
    private AES256Util() {}
    
    public static AES256Util getInstance() {
    	return instance;
    }
    
    /**
     * 16자리의 키값을 입력하여 객체를 생성한다.
     * @param key 암/복호화를 위한 키값
     * @throws UnsupportedEncodingException 키값의 길이가 16이하일 경우 발생
     */
    private AES256Util(String key){
        this.iv = key.substring(0, 16);
        byte[] keyBytes = new byte[16];
        byte[] b = null;
		try {
			b = key.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int len = b.length;
        if(len > keyBytes.length){
            len = keyBytes.length;
        }
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        this.keySpec = keySpec;
    }
    
    /**
     * AES256 으로 암호화 한다.
     * @param str 암호화할 문자열
     * @return
     * @throws NoSuchAlgorithmException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public String encrypt(String str) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException{
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
        String enStr = new String(Base64.encodeBase64(encrypted));
        return enStr;
    }
    
    /**
     * AES256으로 암호화된 txt 를 복호화한다.
     * @param str 복호화할 문자열
     * @return
     * @throws NoSuchAlgorithmException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public String decrypt(String str) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] byteStr = Base64.decodeBase64(str.getBytes());
        return new String(c.doFinal(byteStr), "UTF-8");
    }
    
    public User encryptUser(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException, GeneralSecurityException {
    	User resultUser = User.builder()
    						.userId(user.getUserId())
    						.password(encrypt(user.getPassword()))
    						.name(user.getName())
    						.regNo(encrypt(user.getRegNo()))
    						.build();
    	
    	return resultUser;
    }
    
    public User decryptUser(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException, GeneralSecurityException {
    	User resultUser = User.builder()
    						.userId(user.getUserId())
    						.password(decrypt(user.getPassword()))
    						.name(user.getName())
    						.regNo(decrypt(user.getRegNo()))
    						.build();
    	
    	return resultUser;
    }

}
