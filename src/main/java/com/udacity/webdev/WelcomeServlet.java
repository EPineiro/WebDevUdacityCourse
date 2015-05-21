package com.udacity.webdev;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.udacity.webdev.services.UserService;
import com.udacity.webdev.utils.HashUtils;

public class WelcomeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		
		String[] userCookie = UserService.getUserCookie(req);
		
		//validates the user name it's equal to the hash received
		if(userCookie != null && HashUtils.createHashForCookie(userCookie[0]).equals(userCookie[1])) {
			
			req.setAttribute("username", userCookie[0]);
			getServletContext().getRequestDispatcher("/welcome.jsp").forward(req, resp);
		}
		else {
			//request singup again
			getServletContext().getRequestDispatcher("/singup.jsp").forward(req, resp);
		}
	}
}
