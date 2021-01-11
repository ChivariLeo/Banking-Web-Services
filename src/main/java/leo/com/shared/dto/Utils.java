package leo.com.shared.dto;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import leo.com.security.SecurityConstants;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;


@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();

    public String generateUserId(int length) {
        return generateRandomString(length);
    }

    public String generateAccountId(int length) {
        return generateRandomString(length);
    }

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
            return new String(returnValue);
        }

    public static boolean hasTokenExpired(String token){
        Claims claims= Jwts.parser()
                        .setSigningKey(SecurityConstants.getTokenSecret())  //decrypt token
                        .parseClaimsJws(token).getBody();

        Date tokenExpirationDate=claims.getExpiration();
        Date today=new Date();

        return tokenExpirationDate.before(today);
    }

    public String generateEmailVerificationToken(String userId){
        String token=Jwts.builder()
                    .setSubject(userId)
                    .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512,SecurityConstants.getTokenSecret())
                    .compact();
        return token;
    }
}
