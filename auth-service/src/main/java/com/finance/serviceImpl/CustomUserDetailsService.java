package com.finance.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.finance.model.User;
import com.finance.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		        User user = userRepository.findByEmail(email)
		                .orElseThrow(() ->
		                        new UsernameNotFoundException("User not found with email: " + email));

		        return new CustomUserDetails(user);
		    }

}
