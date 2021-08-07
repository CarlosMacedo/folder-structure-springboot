package projectname.auth.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class Jwt {
    public static boolean validateBearerToken(String jwtSecret, String token) {
        if (jwtSecret == null || token == null) return false;
        
        token = token.replace("Bearer ", "");
        try {
            Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
