package com.udacity.webdev.services;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.udacity.webdev.entities.EMF;
import com.udacity.webdev.entities.User;
import com.udacity.webdev.utils.HashUtils;

public class UserService {

	public static boolean validateUser(String userName) {

		return userName != null &&  Pattern.matches("^[a-zA-Z0-9_-]{3,20}$", userName);
	}

	public static boolean validatePassword(String pass) {

		return pass != null && Pattern.matches("^.{3,20}$", pass);
	}

	public static boolean validateEmail(String email) {

		return Pattern.matches("^[\\S]+@[\\S]+\\.[\\S]+$", email);
	}

	public static boolean verifyPasswords(String pass1, String pass2) {

		return pass1.equals(pass2);
	}
	
	public static void storeUser(String userName, String pass, String email) {
		
		User user = new User();
		user.setName(userName);
		user.setPasswordHash(HashUtils.createSaltedHash(userName + pass, null));
		if(email != null ) 
			user.setEmail(email);
		
		EntityManager em = EMF.get().createEntityManager();
		em.persist(user);
		em.close();
	}

	public static User userExists(String userName) {
		
		Query query = EMF.get().createEntityManager().createQuery("select u from User u where u.name = :name");
		query.setParameter("name", userName);
		
		List<User> user = query.getResultList();
		if(user.size() > 0)
			return user.get(0);
		
		return null;
	}

	public static boolean checkUserPassword(User user, String givenPass) {
		
		String storedPasswordHash = user.getPasswordHash();
		String[] passParts = storedPasswordHash.split("\\,");
		
		String givenPasswordHash = HashUtils.createSaltedHash(user.getName() + givenPass, passParts[0]);
		
		if(passParts[1].equals(givenPasswordHash.split("\\,")[1]))
			return true;
		
		return false;
	}
	
	public static void setUserCookie(String userName, HttpServletResponse resp) {
		
		String userCookie = userName + "|" + HashUtils.createHashForCookie(userName);
		
		Cookie userIdCookie = new Cookie("user_id", userCookie);
		userIdCookie.setPath("/");
		resp.addCookie(userIdCookie);
		Cookie userNameCookie = new Cookie("user_name", userName);
		userNameCookie.setPath("/");
		resp.addCookie(userNameCookie);
	}
	
	public static void clearUserCookie(HttpServletRequest req, HttpServletResponse resp) {
		
		for(Cookie c: req.getCookies()) {
			
			if(c.getName().equals("user_id") || c.getName().equals("user_name")) {
				c.setMaxAge(0);
				c.setValue(null);
				c.setPath("/");
				resp.addCookie(c);
			}
		}
	}
	
	public static String[] getUserCookie(HttpServletRequest req) {
		
		Cookie userCookie = null;
		
		if(req.getCookies() != null) {
			for(Cookie c: req.getCookies()) {
				
				if(c.getName().equals("user_id")) {
					
					userCookie = c;
				}
			}
		}
		
		if(userCookie != null)
			return userCookie.getValue().split("\\|");
		else
			return null;
	}
}
