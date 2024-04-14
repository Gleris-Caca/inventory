package com.lufthansatest.inventory.config;

import com.lufthansatest.inventory.service.impl.TokenServiceImpl;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final TokenServiceImpl tokenService;

    public TokenFilter(UserDetailsService userDetailsService, TokenServiceImpl tokenService) {
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }
    @Override
    protected void doFilterInternal( //contains the custom logic for processing the token and performing authentication
                                     HttpServletRequest request,
                                     @NonNull final HttpServletResponse response,
                                     @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization"); //retrieves the token
        final String email; //storing the extracted email and token from the authorization header
        final String token;
        //check if token is null and doesnt start with prefix Bearer so it can go to next filter
        if (authorizationHeader == null ||!authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = authorizationHeader.substring(7);
        email = tokenService.extractEmail(token);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if (tokenService.validateToken(token, userDetails)) { //Validates the token
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);//the user is authenticated
            }
        }
        filterChain.doFilter(request, response); // allows the request to continue to the next filter in the chain
    }
}