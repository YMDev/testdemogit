package mobile.a3tech.com.a3tech.map;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class getReverseGeoCoding {

	public String getAddress(String language, String latitude, String longitude) {

		try {

			JSONObject jsonObj = parser_Json
					.getJSONfromURL("http://maps.googleapis.com/maps/api/geocode/json?latlng="
							+ latitude
							+ ","
							+ longitude
							+ "&sensor=true&language=fr");
			String Status = jsonObj.getString("status");
			if (Status.equalsIgnoreCase("OK")) {
				JSONArray Results = jsonObj.getJSONArray("results");
				JSONObject zero = Results.getJSONObject(0);
				return zero.getString("formatted_address");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}
	
	
	public  String getAddress(double lat, double lon){
	    StringBuilder stringBuilder = new StringBuilder();
	    String add ="";

	    try {

	        HttpPost httppost = new HttpPost("http://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lon+"&sensor=false");
	        HttpClient client = new DefaultHttpClient();
	        org.apache.http.HttpResponse response;
	        stringBuilder = new StringBuilder();
	        response = client.execute(httppost);
	        HttpEntity entity = response.getEntity();

	        char[] buffer = new char[2048];
	        Reader reader = new InputStreamReader(entity.getContent(), "UTF-8");
	        while (true) {
	            int n = reader.read(buffer);
	            if (n < 0) {
	                break;
	            }
	            stringBuilder.append(buffer, 0, n);
	        }

	    } catch (ClientProtocolException e) {

	    } catch (IOException e) {

	    }

	    JSONObject jsonObject = new JSONObject();
	    try {
	        jsonObject = new JSONObject(stringBuilder.toString());
	    } catch (JSONException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }

	    try {
	        add = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
	        .getString("formatted_address");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return add;
	}

}