package com.example.olx.security;

import com.example.olx.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;

    private final UserDetailsService userService;

    private final UserContext userContext;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userService, UserContext userContext) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.userContext = userContext;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request,response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt); // todo extract the userEmail from JWTTOKEN

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            User userDetails  = (User) userService.loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken authToken  = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                ); // todo here we are getting authentication token and then updating the context

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                userContext.setEmail(userDetails.getPassword());
                userContext.setId(userDetails.getId());

                SecurityContextHolder.getContext().setAuthentication(authToken);

            }

            filterChain.doFilter(request,response);
        }

    }
}
