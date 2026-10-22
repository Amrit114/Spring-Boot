package com.products.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class KeyClockRoleReader implements Converter<Jwt, Collection<GrantedAuthority>> {

	@Override
	public Collection<GrantedAuthority> convert(Jwt source) {
		// TODO Auto-generated method stub
		Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");
		
//		if(realmAccess != null) {
//			return List.of((GrantedAuthority) () -> "ROLE" +realmAccess.get("roles"));
//		}
		
		if(realmAccess == null || realmAccess.isEmpty()) {
			return List.of();
		}
		
		Collection<GrantedAuthority> returnValue = ((List<String>) realmAccess.get("roles")).stream()
        .map(role -> "ROLE_" + role)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
		
		return returnValue;
		}
	}


