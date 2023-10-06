package com.generic.core.base.controller.jwt.signin;

import com.generic.core.base.exceptions.RestExceptionError;
import com.generic.core.base.response.EndpointResult;
import com.generic.core.base.security.jwt.JwtTokenUtility;
import com.generic.core.base.security.jwt.JwtUserDetailsService;
import com.generic.utils.EncryptDecryptUtil;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserProfileService {
    @Autowired
    private UserProfileRepository repo;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    public ResponseEntity<?> signIn(UserProfileEntity model) throws RestExceptionError {
        boolean exists = repo.existsByUsernameAndPassword(model.getUsername(), EncryptDecryptUtil
            .encrypt(model.getPassword(), "SECRETKEY"));
        if(!exists)
            throw new RestExceptionError("Incorrect username or password");
        authenticate(model.getUsername(), model.getPassword());
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(model.getUsername());
        String jws = JwtTokenUtility.generateToken(userDetails);
        
        UserProfileEntity entity = repo.findByUsername(model.getUsername()).orElseThrow(() -> new RestExceptionError("User not found"));
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("data", entity);
        response.put("token", jws);
        response.put("message", "User has successfully logged in");
        return ResponseEntity.ok(response);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
