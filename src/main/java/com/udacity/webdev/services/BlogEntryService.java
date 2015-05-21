package com.udacity.webdev.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.persistence.Query;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.udacity.webdev.entities.BlogEntry;
import com.udacity.webdev.entities.EMF;

public class BlogEntryService {

	@SuppressWarnings("unchecked")
	public List<BlogEntry> getEntries(int maxEntries) {

		List<BlogEntry> entries = Collections.emptyList();

		try {
			Query query = EMF.get()
					.createEntityManager()
					.createQuery("select b from BlogEntry b order by b.date desc");
			
			query.setMaxResults(maxEntries);
			entries = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entries;
	}
	
	public BlogEntry getEntry(long id) {
		
		Key key = KeyFactory.createKey(BlogEntry.class.getSimpleName(), id);
		return EMF.get().createEntityManager().find(BlogEntry.class, key);
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
