package com.banking.demo.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpMethod;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.banking.demo.filtes.JWTAuthenticationFilter;
import com.banking.demo.filtes.JWTLoginFilter;
import com.banking.demo.models.Role;
import com.banking.demo.models.User;
import com.banking.demo.repositories.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguratio extends WebSecurityConfigurerAdapter {

	private Logger logger = Logger.getLogger(SecurityConfiguratio.class);

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/admin/login").permitAll().antMatchers("/test").permitAll().anyRequest()
//				.authenticated().and().formLogin().loginPage("/admin/login").and().exceptionHandling()
//				.accessDeniedPage("/Access_Denied");
//		http.csrf().disable();
//		http.addFilterBefore(new JWTLoginFilter("/admin/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class) 
//        .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.csrf().disable().authorizeRequests().antMatchers("/").permitAll() // Có nghĩa là request "/" ko cần phải đc
																				// xác thực
				.antMatchers(HttpMethod.POST, "/login").permitAll() // Request dạng POST tới "/login" luôn được phép
																	// truy cập dù là đã authenticated hay chưa
				.anyRequest().authenticated() // Các request còn lại đều cần được authenticated
				.and()
				// Add các filter vào ứng dụng của chúng ta, thứ mà sẽ hứng các request để xử lý
				// trước khi tới các xử lý trong controllers.
				// Về thứ tự của các filter, các bạn tham khảo thêm tại
				// http://docs.spring.io/spring-security/site/docs/3.0.x/reference/security-filter-chain.html
				// mục 7.3 Filter Ordering
				.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}

	@Autowired
	public void configGlobal(AuthenticationManagerBuilder auth, UserRepository userRepository) throws Exception {

		 //auth.inMemoryAuthentication().withUser("admin").password("hungit").roles("ADMIN");
//		auth.userDetailsService((String name) -> {
//			User user = userRepository.findByName(name);
//
//			List<GrantedAuthority> authorities = new ArrayList<>();
//			for (Role role : user.getRole()) {
//				authorities.add(new SimpleGrantedAuthority(role.getName()));
//			}
//
//			logger.info("userdetails called | username = " + " user.getUsername " + user.getName()
//					+ " user.getPassword " + user.getPassword());
//			return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), true,
//					true, true, true, authorities);
//		}).passwordEncoder(new BCryptPasswordEncoder());
		auth.userDetailsService((String name) ->{
			User user = userRepository.findByName(name);
			return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), true,
					true, true, true, Collections.emptyList());
		}).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
