package mobile.a3tech.com.a3tech.update;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


public class ASyncCheck extends AsyncTask<String, Integer, Integer> {
    private static final String PLAY_STORE_ROOT_WEB = "https://play.google.com/store/apps/details?id=";
    private static final String PLAY_STORE_HTML_TAGS_TO_GET_RIGHT_POSITION = "itemprop=\"softwareVersion\"> ";
    private static final String PLAY_STORE_HTML_TAGS_TO_REMOVE_USELESS_CONTENT = "  </div> </div>";
    private static final String PLAY_STORE_PACKAGE_NOT_PUBLISHED_IDENTIFIER = "We're sorry, the requested URL was not found on this server.";

  

    private static final int VERSION_DOWNLOADABLE_FOUND = 0;
    private static final int MULTIPLE_APKS_PUBLISHED = 1;
    private static final int NETWORK_ERROR = 2;
    private static final int PACKAGE_NOT_PUBLISHED = 3;
    private static final int STORE_ERROR = 4;

    
    Context mContext;
  
    String mVersionDownloadable;

    ASyncCheck( Context activity) {
        this.mContext = activity;
    }

    @Override
    protected Integer doInBackground(String... notused) {
        if (Network.isAvailable(mContext)) {
            try {
                HttpParams params = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(params, 4000);
                HttpConnectionParams.setSoTimeout(params, 5000);
                HttpClient client = new DefaultHttpClient(params);
               
                HttpGet request = new HttpGet(PLAY_STORE_ROOT_WEB + mContext.getPackageName()); // Set the right Play Store page by getting package name.
                HttpResponse response = client.execute(request);
                InputStream is = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains(PLAY_STORE_HTML_TAGS_TO_GET_RIGHT_POSITION)) { // Obtain HTML line contaning version available in Play Store
                        String containingVersion = line.substring(line.lastIndexOf(PLAY_STORE_HTML_TAGS_TO_GET_RIGHT_POSITION) + 28);  // Get the String starting with version available + Other HTML tags
                        String[] removingUnusefulTags = containingVersion.split(PLAY_STORE_HTML_TAGS_TO_REMOVE_USELESS_CONTENT); // Remove useless HTML tags
                        mVersionDownloadable = removingUnusefulTags[0]; // Obtain version available
                    } else if (line.contains(PLAY_STORE_PACKAGE_NOT_PUBLISHED_IDENTIFIER)) { // This packages has not been found in Play Store
                        return PACKAGE_NOT_PUBLISHED;
                    }
                }
                if (mVersionDownloadable == null) {
                    return STORE_ERROR;
                } else if (containsNumber(mVersionDownloadable)) {
                    return VERSION_DOWNLOADABLE_FOUND;
                } else {
                    return MULTIPLE_APKS_PUBLISHED;
                }
                
            } catch (IOException connectionError) {
                Network.logConnectionError();
                return NETWORK_ERROR;
            }
        } else {
            return NETWORK_ERROR;
        }
        
    }

    /**
     * Return to UpdateChecker class to work with the versionDownloadable if the library found it.
     *
     * @param result
     */
    @Override
    protected void onPostExecute(Integer result) {
        if (result == VERSION_DOWNLOADABLE_FOUND) {
        	if(Comparator.isVersionDownloadableNewer((Activity)mContext, mVersionDownloadable)){
        		Toast.makeText(mContext, "Nouvelle mise � jour disponible", Toast.LENGTH_LONG);
        	}  
        } 
    }

    
    
  
    /**
     * Since the library check from the Desktop Web Page of the app the "Current Version" field, if there are different apks for the app,
     * the Play Store will shown "Varies depending on the device", so the library can't compare it to versionName installed.
     *
     * @see <a href="https://github.com/rampo/UpdateChecker/issues/1">Issue #1</a>
     */
    public final boolean containsNumber(String string) {
        return string.matches(".*[0-9].*");
    }
}
