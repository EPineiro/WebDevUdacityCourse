package com.udacity.webdev;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.udacity.webdev.entities.BlogEntry;
import com.udacity.webdev.services.BlogEntryService;

public class BlogContentJsonServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		
		BlogEntryService service = new BlogEntryService();
		
		List<BlogEntry> entries = service.getEntries(10);
		
		resp.setContentType("application/json");
		resp.getWriter().println(service.toJson(entries));
	}
}
