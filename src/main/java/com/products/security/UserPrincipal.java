//package com.products.security;
//
//import java.util.List;
//import java.util.Collection;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.products.entity.User;
//
//import lombok.AllArgsConstructor;
//
//
//@AllArgsConstructor
//public class UserPrincipal implements UserDetails{
//	
//	private User user;
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return List.of(new SimpleGrantedAuthority("ROLE_SELLER"));
//	}
//	
//	@Override
//	public String getPassword() {
//		return user.getPassword();
//	}
//	
//	@Override
//	public String getUsername() {
//		return user.getUsername();
//	}
//}
