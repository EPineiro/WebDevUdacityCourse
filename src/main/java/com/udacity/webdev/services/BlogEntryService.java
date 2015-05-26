package com.udacity.webdev.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.udacity.webdev.entities.BlogEntry;
import com.udacity.webdev.entities.EMF;

public class BlogEntryService {

	private int maxEntries;
	
	public BlogEntryService() {
		
		maxEntries = 10;
	}
	
	public BlogEntry createBlogEntry(HttpServletRequest req) {
		
		BlogEntry entry = new BlogEntry();
		
		entry.setSubject(req.getParameter("subject"));
		entry.setContent(req.getParameter("content"));
		entry.setDate(new Date());
		
		EntityManager em = EMF.get().createEntityManager();
		em.persist(entry);
		em.close();
		
		//we do this to force an update of the cache, so we don't need to access the db in the next request
		getEntries(true);
		
		return entry;
	}

	@SuppressWarnings("unchecked")
	public List<BlogEntry> getEntries(boolean updateCache) {

		String cacheKey = "ENTRIES";
		
		List<BlogEntry> entries = Collections.emptyList();
		
		Query query = EMF.get()
				.createEntityManager()
				.createQuery("select b from BlogEntry b order by b.date desc");
		query.setMaxResults(maxEntries);

		try {
			
			//look first for the entries in the cache
			MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
			entries = (List<BlogEntry>) memcache.get(cacheKey);
			
			//if the entries are not in the cache or we are forcing a cache update we query the database
			if(updateCache || entries == null) {
				System.out.println("DB ACCESS");
				entries = query.getResultList();
				//for some reason the List returned by the JPA query in Google Appengine it's not serializable, 
				//so we need to create a new list with the data that it's serializable
				memcache.put(cacheKey, new ArrayList<BlogEntry>(entries)); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entries;
	}
	
	public BlogEntry getEntry(long id) {
		
		Key key = KeyFactory.createKey(BlogEntry.class.getSimpleName(), id);
		
		//first look in the cache
		String cacheKey = "ENTRY_" + id;
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		BlogEntry entry = (BlogEntry) memcache.get(cacheKey);
		
		if(entry == null) {
			System.out.println("DB ACCESS FOR PERMALINK");
			entry = EMF.get().createEntityManager().find(BlogEntry.class, key);
			memcache.put(cacheKey, entry);
		}
		
		return entry;
	}
	
	public String toJson(BlogEntry entry) throws JsonGenerationException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		mapper.setDateFormat(df);
		return mapper.writeValueAsString(entry);
	}

	public String toJson(List<BlogEntry> entries) throws JsonGenerationException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		mapper.setDateFormat(df);
		return mapper.writeValueAsString(entries);
	}
}
