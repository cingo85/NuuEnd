package com.leadtek.nuu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig 
//extends WebSecurityConfigurerAdapter 
{
	
	
//	@Autowired
//    private UserDetailsService userDetailsService;
		
//	protected void configure(final HttpSecurity http) throws Exception {
	    
//		http.authorizeRequests().anyRequest().permitAll().and().exceptionHandling().accessDeniedPage("/403");
//		http.formLogin().loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/home", true) 
//        	.usernameParameter("j_username").passwordParameter("j_password") 
//            .and().logout().logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true).permitAll().and().csrf().disable(); 
//
//        http.headers().frameOptions().sameOrigin();//X-Frame-Options: DENY
//        //http.requiresChannel().antMatchers("/**").requiresSecure();
//	}  

	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
//    }  
	
}
