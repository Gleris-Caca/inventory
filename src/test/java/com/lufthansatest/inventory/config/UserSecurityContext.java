package com.lufthansatest.inventory.config;


import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
public class UserSecurityContext implements WithSecurityContextFactory<UseWithMockedUser> {
    @Override
    public SecurityContext createSecurityContext(UseWithMockedUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("test_first", "Test");
        claims.put("test_second", "Test");
        claims.put("scope", Arrays.stream(annotation.roles()).toList());
        List<GrantedAuthority> role = new ArrayList<>();
        Arrays.stream(annotation.roles()).forEach(s -> role.add(new SimpleGrantedAuthority(s)));
        Jwt jwt = new Jwt("faketokenvalue", new Date().toInstant(),
                DateUtils.addHours(new Date(), 1).toInstant(),
                claims,
                claims);
        Authentication auth = new JwtAuthenticationToken(jwt, role, annotation.username());
        context.setAuthentication(auth);
        return context;
    }
}
