package com.udacity.webdev;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * This filter is used to intercept url's of the kind /blog/{id}/ (for example /blog/44934293/)
 * which are a permalink to a blog entry, and redirect them to the correct servlet to find the entry and render the result.
 * This behavior is what is known as URI template.
 * Obviously this could be done more easily with a framework (like Spring MVC) and without an explicit filter (which is horrible in terms of performance)
 * @author epineiro
 *
 */
public class PermalinkFilter implements Filter {

	private Pattern pattern;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		String subPathFilter = filterConfig.getInitParameter("subPathFilter");
		pattern = Pattern.compile(subPathFilter);
	}
	
	@Override
	public void destroy() {	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,	FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getRequestURI();
        
        if(requestURI.endsWith(".json")) {
        	//we mark that we want the json of the permalink and not to view the page
        	//so we discard the json part of the url that we don't need anymore
        	request.setAttribute("json", true);
        	requestURI = requestURI.substring(0, requestURI.lastIndexOf("."));
        }
        else {
        	request.setAttribute("json", false);
        }
        
        if(pattern.matcher(requestURI).matches()) {
        	
        	System.out.println(requestURI);
        	String linkId = requestURI.substring(requestURI.lastIndexOf("/") + 1, requestURI.length());
        	req.setAttribute("linkid", linkId);
        	req.getRequestDispatcher("/blog/permalink").forward(req, resp);
        }
        else {
        	 chain.doFilter(req, resp);
        }
	}

}
