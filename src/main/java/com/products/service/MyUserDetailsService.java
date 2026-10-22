//package com.products.service;
//
//
//import java.util.Optional;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.products.entity.User;
//import com.products.repository.UserRepository;
//import com.products.security.UserPrincipal;
//
//import lombok.AllArgsConstructor;
//
//@Service
//@AllArgsConstructor
//public class MyUserDetailsService implements UserDetailsService {
//
//	
//	private UserRepository userRepository;
//	
//	public User createUser(User user) {
//		user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
//		return userRepository.save(user);
//	}
//	
//	@Override
//	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
//		Optional<User> user = userRepository.findByUsername(username); 
//		if(user.isEmpty()) throw new UsernameNotFoundException("User Not found!");
//		return new UserPrincipal(user.get());
//	}
//}
