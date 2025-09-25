package com.phamtra.identity_service.util;

import com.nimbusds.jose.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Service
public class SecurityUtil {

    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    @Value("${phamtra.jwt.base64-secret}")
    private String jwtKey;

    @Value("${phamtra.jwt.token-validity-in-seconds}")
    private String jwtKeyExpiration;

    public void createToken(Authentication authentication) {

    }
}
