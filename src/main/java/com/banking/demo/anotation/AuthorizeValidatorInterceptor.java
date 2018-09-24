package com.banking.demo.anotation;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.http.HTTPException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.banking.demo.service.MySecurityService;
import com.banking.demo.utils.Constant;
import com.banking.demo.utils.DateUtil;

@Aspect
@Component
public class AuthorizeValidatorInterceptor {

	@Before(value = "@annotation(com.banking.demo.anotation.AuthorizeValidator)  && @annotation(roles)")
	public void before(JoinPoint caller, AuthorizeValidator roles) {

		// validate role

		// Capture access token from current request
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String token = request.getHeader("token");
		
//		System.out.println(Constant.USER_ROLE.SYS_ADMIN.getRoleName());
//		// equals role
//		for (GrantedAuthority item : MySecurityService.currentRole()) {
//			if (validateRole(item.getAuthority(), roles)) {
//				System.out.println("Yes Admin");
//			}else {
//				System.out.println("No Admim");
//			}
//				
//		
//		}

	}

	private boolean validateRole(String currentRole, AuthorizeValidator roles) {
		for (String role : roles.value()) {
			if (currentRole.equals(role))
				return true;
		}
		return false;
	}
}
