package com.banking.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.banking.demo.models.Role;
import com.banking.demo.models.User;
import com.banking.demo.repositories.RoleRepository;
import com.banking.demo.repositories.UserRepository;
import com.banking.demo.service.AuthenticationService;
import com.banking.demo.service.MySecurityService;

@Controller
public class LoginController {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	AuthenticationService authService;

	@Autowired
	UserRepository userRepository;

	@GetMapping("/admin/login")
	public String index() {
		if (MySecurityService.findLoggedInUsername() != null) {
			return "redirect:/admin/dashboard";
		}
		return "index";
	}

	@GetMapping("/test")
	@ResponseBody
	public User test() {
		return userRepository.findByName("hungit");
	}

	@RequestMapping(value = { "/test" }, method = RequestMethod.POST)
	public String login(@RequestParam("email") String email, @RequestParam("password") String password,
			RedirectAttributes redirectAttributes, HttpSession session) {

		return authService.doLogin(email, password, redirectAttributes, session);
	}

	@RequestMapping(value = "/admin/logout", method = {RequestMethod.GET })
	public String logout(HttpServletRequest req, HttpServletResponse res, ModelMap model) {
		MySecurityService.doLogout(req, res);
		return "redirect:/admin/login";
	}

}
