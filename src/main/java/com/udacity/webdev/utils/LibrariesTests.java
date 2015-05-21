package com.udacity.webdev.utils;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public class LibrariesTests {

	public static void main(String[] args) throws ClientProtocolException,	IOException {

		//testApacheHttpClient();
		testJacksonJsonApi();
	}

	private static void testApacheHttpClient() throws ClientProtocolException, IOException {

		ResponseHandler<String> handler = new ResponseHandler<String>() {
			
			@Override
			public String handleResponse(HttpResponse resp) throws IOException {
				
				System.out.println(resp.getFirstHeader("Server"));
				
				//TODO the entity should be accesed by an InputStream (entity.getContent()) and parsed accordingly (like json, html, etc)
				HttpEntity entity = resp.getEntity();
				return EntityUtils.toString(entity);
			}
		};
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://www.example.com/");
		String result = httpclient.execute(httpget, handler);
		
		System.out.println(result);
	}
	
	private static void testJacksonJsonApi() throws JsonGenerationException, JsonMappingException, IOException {
		
		TestPojo pojo = new TestPojo();
		pojo.setId(100);
		pojo.setDesc("dfjifhruyheru");
		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(pojo));
	}
	
	static class TestPojo {
		
		int id;
		String desc;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
}
