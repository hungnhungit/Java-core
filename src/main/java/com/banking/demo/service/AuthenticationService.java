package com.banking.demo.service;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.banking.demo.config.Constants;
import com.banking.demo.models.User;
import com.banking.demo.repositories.UserRepository;

@Service
public class AuthenticationService {

	private UserRepository userRepository;
	private Logger logger = Logger.getLogger(AuthenticationService.class);
	AuthenticationManager authManager;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.authManager = authenticationManager;
	}

	public String doLogin(String name, String password, RedirectAttributes redirectAttributes, HttpSession session) {

		User user;
		
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)
				|| (user = userRepository.findByName(name)) == null) {
			redirectAttributes.addFlashAttribute("errors", "Email/Password is Empty");
			return "redirect:/admin/login";
		}
		if (doAuth(name, password, session)) {
			redirectAttributes.addFlashAttribute("success", "Auth Manager");
			return "redirect:/admin/dashboard";
		}else {
			redirectAttributes.addFlashAttribute("errors", "Admin Login Fails");
			return "redirect:/admin/login";
		}
	}

	private boolean doAuth(String name, String password, HttpSession session) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(name, password);
		if (authManager == null) {
			return false;
		}
		
		try {

			authToken = (UsernamePasswordAuthenticationToken) authManager.authenticate(authToken);
			if (authToken.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(authToken);
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				session.setAttribute(Constants.LOGGED_IN_USER,
				userRepository.findByName(MySecurityService.findLoggedInUsername()));
				session.setAttribute("constant", new Constants());
				logger.info("User " + ((UserDetails) auth.getPrincipal()).getAuthorities() + " just logged in!");
				for(GrantedAuthority role : auth.getAuthorities()) {
					if(role.getAuthority() == "Admin") {
						return true;
					}
				}
				System.out.println(authToken);
				
				return true;
			}

			return true;

		} catch (BadCredentialsException e) {
			logger.info("bad credentials exception");
			e.printStackTrace();
			return false;
		} catch (Exception ex) {
			logger.info("Other exceptions occurred in doAuth " + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

}
