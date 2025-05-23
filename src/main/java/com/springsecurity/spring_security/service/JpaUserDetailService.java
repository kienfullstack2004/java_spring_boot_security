package com.springsecurity.spring_security.service;

import com.springsecurity.spring_security.models.SecurityUser;
import com.springsecurity.spring_security.respository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    public JpaUserDetailService(UserRepository userRepository) {
         this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepository
                .findByUsername(username)
               .map(SecurityUser::new)
                .orElseThrow(()->new UsernameNotFoundException("Username not found" + username));
    }
}
