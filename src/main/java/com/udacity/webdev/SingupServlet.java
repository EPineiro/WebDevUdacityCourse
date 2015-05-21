package com.udacity.webdev;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.udacity.webdev.services.UserService;

public class SingupServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		
		getServletContext().getRequestDispatcher("/singup.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		
		Map<String, String> errors = validateRequest(req);
		
		if(UserService.userExists(req.getParameter("username")) != null) {
			
			errors.put("userAlreadyExists", " That user already exists.");
		}
		
		if(!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			getServletContext().getRequestDispatcher("/singup.jsp").forward(req, resp);
		}
		else {
			UserService.setUserCookie(req.getParameter("username"), resp);
			UserService.storeUser(req.getParameter("username"), req.getParameter("password"), req.getParameter("email"));
			resp.sendRedirect("/blog/welcome");
		}
	}

	private Map<String, String> validateRequest(HttpServletRequest req) {
		
		Map<String, String> errors = new HashMap<String, String>();
		
		String userName = req.getParameter("username");
		String password = req.getParameter("password");
		String veriryPass = req.getParameter("verify");
		String email = req.getParameter("email");
		
		if(!UserService.validateUser(userName))
			errors.put("invalidUser", "That's not a valid username.");
		if(!UserService.validatePassword(password))
			errors.put("invalidPass", "That wasn't a valid password.");
		if(!UserService.verifyPasswords(password, veriryPass))
			errors.put("passNotMatch", "Your passwords didn't match.");
		if(email != null && !email.equals("") && !UserService.validateEmail(email))
			errors.put("invalidEmail", "That's not a valid email.");
		
		return errors;
	}
}
