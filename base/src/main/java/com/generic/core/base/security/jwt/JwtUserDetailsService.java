package com.generic.core.base.security.jwt;

import com.generic.utils.EncryptDecryptUtil;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private PasswordEncoder passwordEncoder;
//    @Autowired
//    private UserProfileRepository userProfileRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserProfileEntity entity = userProfileRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Access has not been granted, please contact IT Security Group for access"));
//        return new User(username, passwordEncoder.encode(
//            EncryptDecryptUtil.decrypt(entity.getPassword(), CashboxContants.SECRETKEY)), new ArrayList<>());
//    }
    return null;
  }
}
