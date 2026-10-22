//package com.products.controller;
//
//import java.util.List;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.products.dto.UserDTO;
//import com.products.entity.User;
//import com.products.security.JwtUtil;
//import com.products.service.MyUserDetailsService;
//
//import lombok.AllArgsConstructor;
//
//@RestController
//@RequestMapping("/user")
//@AllArgsConstructor
//public class UserController {
//	
//	private JwtUtil jwtUtil;
//	
//	private AuthenticationManager authenticationManager;
//	
//	private MyUserDetailsService myUserDetailsService;
//
//	@PostMapping("/register")
//	public User register(@RequestBody User user) {
//		return myUserDetailsService.createUser(user);
//	}
//	
//	@PostMapping("/login")
//	public String login(@RequestBody UserDTO userDTO) {
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		
//		List<String> roles = authentication.getAuthorities().stream()
//				.map(GrantedAuthority::getAuthority).toList();
//		
//		UserDetails userDetails = myUserDetailsService.loadUserByUsername(userDTO.getUsername());
//		
//		return jwtUtil.generateToken(userDetails.getUsername(), roles);
//		
//	}
//}
