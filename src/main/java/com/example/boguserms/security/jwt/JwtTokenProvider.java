package com.example.boguserms.security.jwt;

import com.example.boguserms.feign.LoginServiceFeign;
import com.example.boguserms.security.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtTokenProvider {
    private final CustomUserDetailsService userDetailsService;
    private final LoginServiceFeign loginServiceFeign;

    public JwtTokenProvider(CustomUserDetailsService userDetailsService, LoginServiceFeign loginServiceFeign) {
        this.userDetailsService = userDetailsService;
        this.loginServiceFeign = loginServiceFeign;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginServiceFeign.validateToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }


    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");

        return (!Objects.isNull(bearerToken) && bearerToken.startsWith("Bearer")) ?
                bearerToken.substring(7) : null;
    }

}

