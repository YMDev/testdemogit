package mobile.a3tech.com.a3tech.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import mobile.a3tech.com.a3tech.exception.EducationException;

public class AbstractService {

	public final static String CONNECTION_ERROR = "connection_error";

	protected <T> T getResult(String url, TypeReference<T> typeReference)
			throws EducationException {
		T result = callWebService(url, typeReference);
		return result;
	}

	protected <T> T getResult(String url, Map<String, String> map,
			TypeReference<T> typeReference) throws EducationException {
		T result = callWebService(url, map, typeReference);
		return result;
	}

	private <T> T callWebService(String url, TypeReference<T> typeReference)
			throws EducationException {
		T result = callWebService(url, null, typeReference);
		return result;

	}

	private <T> T callWebService(String url, Map<String, String> map,
			TypeReference<T> typeReference) throws EducationException {
		ConnexionService connexionService = ConnexionService
				.getConnexionService();
		HttpClient httpClient = connexionService.getHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			if (map != null && map.isEmpty() == false) {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						map.size());
				String k, v;
				Iterator<String> itKeys = map.keySet().iterator();
				while (itKeys.hasNext()) {
					k = itKeys.next();
					v = map.get(k);
					nameValuePairs.add(new BasicNameValuePair(k, v));
				}
				//httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
			}
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream inputStream = httpEntity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				
				sb.append(new String(line.getBytes("UTF-8"),"UTF-8"));
				//sb.append(line);
			}
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			T result = null ;
			if(sb!= null && !sb.toString().equals(""))
				result = (T) mapper.readValue(sb.toString(), typeReference);
			
			

			inputStream.close();

			return result;
		
		} catch (UnsupportedEncodingException e) {
			throw new EducationException("Cannot read params.", e);
		} catch (SocketTimeoutException e) {
			throw new EducationException(CONNECTION_ERROR, e);
		} catch (ClientProtocolException e) {
			throw new EducationException(CONNECTION_ERROR, e);
		} catch (ConnectTimeoutException e) {
			throw new EducationException(CONNECTION_ERROR, e);
		} catch (UnknownHostException e) {
			throw new EducationException(CONNECTION_ERROR, e);
		} catch (IOException e) {
			throw new EducationException("Cannot read JSON stream.", e);
		} catch (Exception e) {
			throw new EducationException("Cannot read JSON stream.", e);
		}
	}
	

	private <T> T callWebServiceConvert(String url, Map<String, String> map,
			TypeReference<T> typeReference) throws EducationException {
		ConnexionService connexionService = ConnexionService
				.getConnexionService();
		HttpClient httpClient = connexionService.getHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			if (map != null && map.isEmpty() == false) {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						map.size());
				String k, v;
				Iterator<String> itKeys = map.keySet().iterator();
				while (itKeys.hasNext()) {
					k = itKeys.next();
					v = map.get(k);
					nameValuePairs.add(new BasicNameValuePair(k, v));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			}
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream inputStream = httpEntity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				
				sb.append(new String(line.getBytes("ISO-8859-1"),"UTF-8"));
				//sb.append(line);
			}
			ObjectMapper mapper = new ObjectMapper();
			T result = null ;
			if(sb!= null && !sb.toString().equals(""))
				result = (T) mapper.readValue(sb.toString(), typeReference);
			
			

			inputStream.close();

			return result;
		} catch (UnsupportedEncodingException e) {
			throw new EducationException("Cannot read params.", e);
		} catch (SocketTimeoutException e) {
			throw new EducationException(CONNECTION_ERROR, e);
		} catch (ClientProtocolException e) {
			throw new EducationException(CONNECTION_ERROR, e);
		} catch (ConnectTimeoutException e) {
			throw new EducationException(CONNECTION_ERROR, e);
		} catch (UnknownHostException e) {
			throw new EducationException(CONNECTION_ERROR, e);
		} catch (IOException e) {
			throw new EducationException("Cannot read JSON stream.", e);
		}
	}

}
