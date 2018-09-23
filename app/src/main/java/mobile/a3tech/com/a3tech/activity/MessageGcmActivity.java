package mobile.a3tech.com.a3tech.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MessageGcmActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onResume() {
        super.onResume();
        Bundle b = getIntent().getExtras();
        String idMissionNotif = "";
        String userId = "";
        String idOffre = "" ;
        String isMessage = "" ;
        if (b != null) {
            idMissionNotif = b.getString("idMissionNot");
            idOffre = b.getString("idOffreNot");
            userId = b.getString("userIdNot");
            isMessage = b.getString("isMessageNot");
        }
        finishAffinity();
//        try{
//        	NavigationMain.instance.finish();
//        }catch(Exception e){
//        	
//        }
       // if (NavigationMain.inBackground.booleanValue()) {
            Intent mainIntent = new Intent(this, SplashActivity.class);
            mainIntent.putExtra("idMissionNot", idMissionNotif);
            mainIntent.putExtra("userIdNot", userId);
            mainIntent.putExtra("idOffreNot", idOffre);
            mainIntent.putExtra("isMessageNot", isMessage);
            startActivity(mainIntent);
        //}
        
         
        finish();
    }
}
