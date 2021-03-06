package com.udacity.webdev;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.udacity.webdev.entities.BlogEntry;
import com.udacity.webdev.services.BlogEntryService;

public class PermalinkServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		
		BlogEntryService service = new BlogEntryService();
		List<BlogEntry> entries = new ArrayList<BlogEntry>(); 
		
		BlogEntry entry = service.getEntry(Long.parseLong((String) req.getAttribute("linkid")));
		
		boolean json = (boolean) req.getAttribute("json");
		
		if(json) {
			
			resp.setContentType("application/json");
			resp.getWriter().println(service.toJson(entry));
		}
		else {
			entries.add(entry);
			req.setAttribute("entries", entries);
			getServletContext().getRequestDispatcher("/frontpage.jsp").forward(req, resp);
		}
	}
}
