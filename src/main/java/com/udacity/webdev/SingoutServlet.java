package com.udacity.webdev;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.udacity.webdev.services.UserService;

public class SingoutServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		
		UserService.clearUserCookie(req, resp);
		getServletContext().getRequestDispatcher("/blog/singup").forward(req, resp);
	}
}
