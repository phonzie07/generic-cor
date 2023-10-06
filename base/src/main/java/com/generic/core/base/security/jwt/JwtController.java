package com.generic.core.base.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JwtController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthentication(@RequestBody JwtRequest model) {
        authenticate(model.getUsername(), model.getPassword());
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(model.getUsername());
        String jws = JwtTokenUtility.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(jws, "Token has successfully generated"));
    }
    
    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
