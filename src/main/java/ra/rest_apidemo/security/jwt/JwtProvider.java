package ra.rest_apidemo.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtProvider {
    @Value("${ra.jwt.secret}")
    private String JWT_SECRET;//RikkeiAcademy
    @Value("${ra.jwt.expired.access.token}")
    private long JWT_ACCESS_TOKEN;
    @Value("${ra.jwt.expired.refresh.token}")
    private long JWT_REFRESH_TOKEN;


    public String generateAccessToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())//Nội dung sinh token
                .setIssuedAt(new Date())//Ngày hiệu lực
                .setExpiration(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }


    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJwt(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Failed -> Expired Token Message {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Failed -> Unsupported Token Message {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Failed -> Invalid Format Token Message {}", e.getMessage());
        } catch (SignatureException e) {
            log.error("Failed -> Invalid Signature Token Message {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Failed -> Claims Empty Token Message {}", e.getMessage());
        }
        return false;
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody().getSubject();
    }
//    private Claims extractAllClaims(String token){
//        return Jwts.parser().setSigningKey(JWT_SECRET)
//             .parseClaimsJws(token).getBody();
//    }
//    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//    public String getUserNameFromToken(String token) {
//        return extractClaims(token,Claims::getSubject);
//    }
//
//    public String generateToken(UserDetails userDetails){
//        return generateToken(new HashMap<>(),userDetails);
//    }
//
//    public String generateToken(Map<String,Object> extractClaim,UserDetails userDetails){
//        return Jwts.builder()
//                .setClaims(extractClaim)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis()+JWT_ACCESS_TOKEN))
//                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
//                .compact();
//    }
//    public boolean isTokenValid(String token, UserDetails userDetails){
//        final String userName = getUserNameFromToken(token);
//        return (userName.equals(userDetails.getUsername()))&&!isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaims(token, Claims::getExpiration);
//    }

}
