package com.TraceCircle.RolesManagementService.ServiceIMPL;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.TraceCircle.RolesManagementService.Entity.SignUpEntity;
import com.TraceCircle.RolesManagementService.Repository.SignUpRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final SignUpRepository signUpRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       
    	SignUpEntity user = signUpRepository.findByEmailId(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        log.debug("Loaded user: {}", email);

        return new org.springframework.security.core.userdetails.User(
                user.getEmailId(),
                user.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
    }
}
