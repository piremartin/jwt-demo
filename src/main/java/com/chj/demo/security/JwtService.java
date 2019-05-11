package com.chj.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author chehaojie
 * @date 2019/05/11 16:50
 */
@Component
public class JwtService {

    private static final Long expireSeconds = 60L;
    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(String payload) {
        String compact = Jwts.builder()
                .setSubject(payload)
                .setExpiration(new Date(System.currentTimeMillis() + expireSeconds * 1000))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
        return "Bearer " + compact;
    }


    /**
     * JJWT并没有提供判断JWT是否合法的方法，但是在解码非法JWT时会抛出异常
     *
     * @param jwt jwt
     * @return boolean
     */
    public boolean isTokenValid(String jwt) {
        try {
            //todo
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);
//            .getBody().getSubject();
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}
