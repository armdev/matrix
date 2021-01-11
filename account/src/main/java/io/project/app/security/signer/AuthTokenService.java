package io.project.app.security.signer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.project.app.common.TimeProvider;
import io.project.app.domain.Account;
import io.project.app.api.requests.Credentials;
import io.project.app.api.requests.Device;

import io.project.app.utils.GsonConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import java.security.Key;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

@Service
@Component
@Slf4j
public class AuthTokenService {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expires_in}")
    private int expiresIn;

    @Value("${jwt.mobile_expires_in}")
    private int mobileExpiresIn;

    @Value("${jwt.header}")
    private String authHeader;

    static final String AUDIENCE_UNKNOWN = "unknown";
    static final String AUDIENCE_WEB = "web";
    static final String AUDIENCE_MOBILE = "mobile";
    static final String AUDIENCE_TABLET = "tablet";

    @Autowired
    private TimeProvider timeProvider;

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public String generateToken(Account account, Device device) {

        Credentials credentials = new Credentials(account.getId(), account.getEmail(), account.getName(), account.getEmail());
        String audience = generateAudience(device);
        return Jwts.builder()
                .setIssuer(applicationName)
                .setSubject(GsonConverter.toJson(credentials))
                .setAudience(audience)
                .setIssuedAt(timeProvider.now())
                .setExpiration(generateExpirationDate(device))
                .signWith(key, SIGNATURE_ALGORITHM)
                .compact();
    }

    public String refreshToken(String token, Device device) {

        String refreshedToken;
        Date a = timeProvider.now();
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            claims.setIssuedAt(a);
            refreshedToken = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(generateExpirationDate(device))
                    .signWith(key, SIGNATURE_ALGORITHM)
                    .compact();
        } catch (InvalidKeyException e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Claims getAllClaimsFromToken(String token) {
        Claims claims;

        try {
            claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            claims = null;
        }
        return claims;
    }

    public Boolean validateToken(String token) {
        log.error("Token: " + token);
        final Optional<String> email = this.fetchEmailFromToken(token);

        if (!email.isPresent()) {
            log.error("Could not fetch email from token");
            return false;
        }

        // final Date created = this.getIssuedAtDateFromToken(token);
        final Date exiration = this.getExpirationFromToken(token);

        Date currentDate = Date.from(java.time.ZonedDateTime.now(ZoneOffset.UTC).toInstant());
        if (exiration.getTime() > 0) {
            if (exiration.getTime() < currentDate.getTime()) {
                log.error("CurrentDate.getTime() " + currentDate.getTime());
                log.error("Exiration.getTime() " + exiration.getTime());
                log.error("Token expired");
                return false;
            } else {
                //og.info("You can use token");
                return true;
            }
        }
        return false;
    }

    public Optional<String> fetchEmailFromToken(String token) {
        Credentials credentials;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            credentials = GsonConverter.fromJson(claims.getSubject(), Credentials.class);
            log.info("Credentials found " + credentials.toString());
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.ofNullable(credentials.getEmail());
    }

    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    private Date generateExpirationDate(Device device) {
        long expiresInLocal = device.isTablet() || device.isMobile() ? mobileExpiresIn : this.expiresIn;
        // log.info("Set token exp time " + expiresInLocal);
        return new Date(timeProvider.now().getTime() + expiresInLocal * 1000);
    }

    public int getExpiredIn(Device device) {
        return device.isMobile() || device.isTablet() ? mobileExpiresIn : expiresIn;
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Optional<Credentials> fetchCredentialsFromToken(String token) {
        Credentials credentials;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            credentials = GsonConverter.fromJson(claims.getSubject(), Credentials.class);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.ofNullable(credentials);
    }

    private Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    private Date getExpirationFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getExpiration();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    private String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            audience = claims.getAudience();
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    public String getToken(HttpServletRequest request) {
        String authHeaderLocal = this.getAuthHeaderFromHeader(request);
        if (authHeaderLocal != null && authHeaderLocal.startsWith("Authorization")) {
            return authHeaderLocal.substring(7);
        }
        return null;
    }

    private String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(authHeader);
    }
    // curl http://localhost:2019/api/v2/accounts/login/api/test/test  -H "Authorization: Bearer mytokenn"

}
