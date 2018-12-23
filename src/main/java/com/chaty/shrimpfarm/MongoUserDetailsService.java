package com.chaty.shrimpfarm;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.chaty.shrimpfarm.repository.UserRepo;

@Component
public class MongoUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepo repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.chaty.shrimpfarm.model.User user = repository.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));

		return new User(user.getEmail(), user.getToken(), authorities);
	}
}
