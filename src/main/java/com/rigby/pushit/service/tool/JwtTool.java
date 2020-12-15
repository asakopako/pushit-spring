package com.rigby.pushit.service.tool;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTool {
    private static final String SECRET = "PasswordCOMOV";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    public static String createToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }

}
