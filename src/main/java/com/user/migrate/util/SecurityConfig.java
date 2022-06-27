package com.user.migrate.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        	.sessionManagement()
            	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
		    .authorizeRequests()
		    .anyRequest()
		    .permitAll().and().csrf().disable();
		  }
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){ //for prod use
	//public PasswordEncoder passwordEncoder(){ //for test use, plaintext passwords
		//return NoOpPasswordEncoder.getInstance(); //for test use, plaintext passwords
		return new BCryptPasswordEncoder(); //for prod use
	}
}
