package com.dat.proj.backend.security;

import java.util.ArrayList;

import com.dat.proj.backend.entity.User;
import com.dat.proj.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findUserByUserName(username);
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getAuthentication(),
				new ArrayList<>());
	}

}
