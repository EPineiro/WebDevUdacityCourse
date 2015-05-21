package com.udacity.webdev;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.udacity.webdev.entities.BlogEntry;
import com.udacity.webdev.entities.EMF;

public class NewPostServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		
		getServletContext().getRequestDispatcher("/newpost.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		
		Map<String, String> errors = validateRequest(req);
	
		if(!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			getServletContext().getRequestDispatcher("/newpost.jsp").forward(req, resp);
		}
		else {
			
			EntityManager em = EMF.get().createEntityManager();
			BlogEntry entry = createBlogEntry(req);
			
			em.persist(entry);
			em.close();
			
			resp.sendRedirect("/blog/" + entry.getId());
		}
	}

	private BlogEntry createBlogEntry(HttpServletRequest req) {
		
		BlogEntry entry = new BlogEntry();
		
		entry.setSubject(req.getParameter("subject"));
		entry.setContent(req.getParameter("content"));
		entry.setDate(new Date());
		
		return entry;
	}

	private Map<String, String> validateRequest(HttpServletRequest req) {
		
		Map<String, String> errors = new HashMap<String, String>();
		
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		
		if(subject == null || subject.equals("") || content == null || content.equals("")) {
			errors.put("invalidContent", "subject and content, please!");
		}
		
		return errors;
	}
}
