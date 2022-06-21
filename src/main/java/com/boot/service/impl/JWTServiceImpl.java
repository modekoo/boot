package com.boot.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import com.boot.dto.UserDto;
import com.boot.service.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service("JWTService")
public class JWTServiceImpl implements JWTService {

    private static final String privateKey =  "demo";
    
    public String createToken(UserDto userDto){
        String jwt = Jwts.builder()
                         .setHeaderParam("typ", "JWT")
                         .setHeaderParam("regDate", System.currentTimeMillis())
                         .claim("userId", userDto.getUserId())
                         .signWith(SignatureAlgorithm.HS256, this.generateKey())
                         .compact();
        return jwt;
    }
 
    public byte[] generateKey(){
        byte[] key = null;
        try {
            key = privateKey.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        }
         
        return key;
    }
    
    public boolean verify(String jwt) {
    	
    	if(jwt == null || jwt.equals(""))
    		return false;
    	
        try{
            Jws<Claims> claims = Jwts.parser()
                      .setSigningKey(this.generateKey())
                      .parseClaimsJws(jwt);
            
            return claims != null ? true : false;
             
        }catch (Exception e) {
        	e.printStackTrace();
        	return false;
        }
    }
    
    public String getUserId(String jwt) throws io.jsonwebtoken.JwtException{
    	
    	jwt = jwt.replace("Bearer ", "").trim();
    	
    	if(!verify(jwt))
    		throw new io.jsonwebtoken.JwtException("not valid Token");
    	
        Jws<Claims> claims = Jwts.parser()
        			.setSigningKey(this.generateKey())
                    .parseClaimsJws(jwt);
        
        String id = (String) claims.getBody().get("userId");
        
        return id;
             
    }
    
    
    public String sha256(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());
        
        StringBuilder sb = new StringBuilder();
        for(byte b : md.digest()) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
