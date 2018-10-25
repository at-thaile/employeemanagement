package com.example.demo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.example.demo.entity.BlockUser;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.LoginErrorMessage;
import com.example.demo.util.VerificationUtil;


@Component
public class FailureLoginHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String messageError = LoginErrorMessage.loginErrorMessage(exception);
		request.getSession().setAttribute("error", messageError);
		getRedirectStrategy().sendRedirect(request, response, "/login?error");
	}

}
