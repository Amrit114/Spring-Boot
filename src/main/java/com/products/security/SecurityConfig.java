package com.products.security;




import org.hibernate.cache.spi.support.CollectionReadOnlyAccess;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity.JwtSpec;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.websocket.Session;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {
	
	
//	private UserDetailsService userDetailsService;
	
//	private JwtRequestFilter requestFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf ->csrf.disable())
		    .authorizeHttpRequests(request ->{
		    	request.requestMatchers( "/user/register","/user/login").permitAll();
		    	request.requestMatchers(HttpMethod.GET, "/**").permitAll();
		    	request.anyRequest().authenticated();
		    })
		    .oauth2ResourceServer(oauth2->oauth2.jwt(
		    		JwtSpec->JwtSpec.jwtAuthenticationConverter(jwtAuthenticationConverter())
		    		));
		    
		    
//		    .authenticationProvider(authenticationProvider())
//		    .sessionManagement(Session -> Session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//		    .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
//		    .httpBasic(Customizer.withDefaults());
		return http.build();
	}
	
	private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyClockRoleReader());
		return jwtAuthenticationConverter;
	}
	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails admin = User.builder()
//				.username("admin")
//				.password(passwordEncoder().encode("admin"))
//				.roles("ADMIN")
//				.build();
//		
//		UserDetails seller = User.builder()
//				.username("seller")
//				.password(passwordEncoder().encode("seller"))
//				.roles("SELLER")
//				.build();
//		
//		return new InMemoryUserDetailsManager(admin, seller);
//	}
	
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
//		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
//		return provider;
//	}
	
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
//		return config.getAuthenticationManager();
//	}
//	
}
