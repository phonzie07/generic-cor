package com.generic.core.base.security.jwt;

import java.io.IOException;
import java.io.NotActiveException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jws = request.getHeader("Authorization");
        String username = null;
        if(jws != null) {
            if(jws.contains("Basic")){
                String[] basic = jws.split(" ");
                filterChain.doFilter(request, response);
                return;
            }
            jws = jws.replace("Bearer ", "");
            if(JwtTokenUtility.isTokenExpired(jws)) {
                throw new NotActiveException("Jason Web Token has expired");
            }
            username = JwtTokenUtility.getUsernameFromToken(jws);
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
            if(JwtTokenUtility.validateToken(jws, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
