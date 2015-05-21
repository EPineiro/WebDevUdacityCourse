package com.udacity.webdev;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.udacity.webdev.entities.User;
import com.udacity.webdev.services.UserService;

public class SinginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		
		getServletContext().getRequestDispatcher("/singin.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		
		Map<String, String> errors = new HashMap<String, String>();
		
		User user = UserService.userExists(req.getParameter("username"));
		
		if(user == null)			
			errors.put("invalidLogin", "Invalid Login.");
		else if(!UserService.checkUserPassword(user, req.getParameter("password")))			
			errors.put("invalidLogin", "Invalid Login.");
		
		if(!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			getServletContext().getRequestDispatcher("/singin.jsp").forward(req, resp);
		}
		else {
			UserService.setUserCookie(req.getParameter("username"), resp);
			resp.sendRedirect("/blog/welcome");
		}
	}
}
