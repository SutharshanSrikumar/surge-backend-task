package com.surge.studentmanagement.security;

import com.surge.studentmanagement.entity.User;
import com.surge.studentmanagement.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user != null) {
            return CustomUserDetails.build(user);
        } else {
            throw new UsernameNotFoundException("User Name not found");
        }

    }
}
