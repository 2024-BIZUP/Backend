package com.likelion.bizup.module.jwt.service;
import com.likelion.bizup.module.jwt.TokenStatusCode;
import com.likelion.bizup.module.jwt.exception.TokenException;
import com.likelion.bizup.module.jwt.entity.UserRefreshToken;
import com.likelion.bizup.module.jwt.repository.UserRefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    @Value("${jwt.refreshExpiration}")
    private long REFRESH_EXPIRATION_TIME;

    @Autowired
    private UserRefreshTokenRepository refreshTokenRepository;
    public String createAccessToken(String userId, String role) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userId)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
    public String createRefreshToken(String userId) {
        Date now = new Date();
        String refreshToken = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        refreshTokenRepository.save(new UserRefreshToken(userId, refreshToken));
        return refreshToken;
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new TokenException(TokenStatusCode.TOKEN_EXPIRED);
        } catch (IllegalArgumentException e) {
            throw new TokenException(TokenStatusCode.TOKEN_EMPTY);
        } catch (Exception e) {
            throw new TokenException(TokenStatusCode.TOKEN_INVALID);
        }
    }
    public String getUserIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public String renewAccessToken(String userId, String refreshToken) {
        Optional<UserRefreshToken> savedToken = refreshTokenRepository.findByUserId(userId);
        if (savedToken.isPresent() && savedToken.get().getRefreshToken().equals(refreshToken) && validateToken(refreshToken)) {
            return createAccessToken(userId, "ROLE_USER");
        }
        throw new SecurityException("Refresh Token이 유효하지 않습니다.");
    }
    public String logout(String userId, String token) {
        if (!refreshTokenRepository.existsByUserId(userId)) {
            return "이미 로그아웃된 상태입니다.";
        }
        if (validateToken(token)) {
            refreshTokenRepository.deleteByUserId(userId);
            return "로그아웃 되었습니다.";
        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }
}
