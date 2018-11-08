package mobile.a3tech.com.a3tech.service;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class ConnexionService {

	private static ConnexionService SINGLETON = new ConnexionService();
	
	private HttpParams httpParameters;
	
	private ClientConnectionManager connexionManager;
	
	public static ConnexionService getConnexionService() {
		return SINGLETON;
	}
	
	private ConnexionService() {
		this.httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = 10000;
		HttpConnectionParams.setConnectionTimeout(this.httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 10000;
		HttpConnectionParams.setSoTimeout(this.httpParameters, timeoutSocket);
//		HttpProtocolParams.setContentCharset(this.httpParameters, "UTF-8");
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

		this.connexionManager = new ThreadSafeClientConnManager(httpParameters, registry);
	}
	
	public HttpClient getHttpClient() {
		return new DefaultHttpClient(this.connexionManager, this.httpParameters);
	}
	
	
	
}
