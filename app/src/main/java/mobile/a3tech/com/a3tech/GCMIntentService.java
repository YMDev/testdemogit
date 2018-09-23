package mobile.a3tech.com.a3tech;

import java.util.Random;
import java.util.StringTokenizer;

import mobile.a3tech.com.a3tech.activity.MessageGcmActivity;
import mobile.a3tech.com.a3tech.utils.Constant;

import org.codehaus.jackson.util.MinimalPrettyPrinter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gms.drive.DriveFile;

public class GCMIntentService extends GCMBaseIntentService {
    private static final String TAG = "GCMIntentService";

    public GCMIntentService() {
        super(Constant.SENDER_ID);
    }

    protected void onRegistered(Context context, String registrationId) {
        Intent intent = new Intent(Constant.ACTION_ON_REGISTERED);
        intent.putExtra(Constant.FIELD_REGISTRATION_ID, registrationId);
        context.sendBroadcast(intent);
    }

    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
    }

    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        generateNotification(context, intent.getStringExtra(Constant.EXTRA_MESSAGE));
    }

    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        generateNotification(context, "Test");
    }

    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
    }

    protected boolean onRecoverableError(Context context, String errorId) {
        return super.onRecoverableError(context, errorId);
    }

    private static void generateNotification(Context context, String message) {
        StringTokenizer st = new StringTokenizer(message, "||");
        String idMission = (String) st.nextElement();
        String type = (String) st.nextElement();
        String pseudo = (String) st.nextElement();
        String msg = (String) st.nextElement();
        String userId = (String) st.nextElement();
        String idOffre = (String) st.nextElement();
        if(idOffre!=null && idOffre.equals(" "))
        	idOffre="0" ;
        String isMessage = "0";
        try{
        	isMessage = (String) st.nextElement();
        }catch(Exception e){
        	
        }
        long when = System.currentTimeMillis();
        String title = "";
        String text = "";
        switch (Integer.parseInt(type)) {
            case 0 /*0*/:
                title = context.getString(R.string.txtGcmNotification_titleNouveauMessage);
                text = new StringBuilder(String.valueOf(pseudo)).append(" : ").append(msg).toString();
                break;
            case 1 /*1*/:
                title = context.getString(R.string.txtGcmNotification_titleNouvelleOffre);
                text = new StringBuilder(String.valueOf(pseudo)).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(context.getString(R.string.txtGcmNotification_offreApropose)).toString();
                break;
            case 9 /*1*/:
                title = context.getString(R.string.txtGcmNotification_titleAnnulationSonOffre);
                text = new StringBuilder(String.valueOf(pseudo)).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(context.getString(R.string.txtGcmNotification_offreAAnnuleSon)).toString();
                break;   
            case 2 /*2*/:
                title = context.getString(R.string.txtGcmNotification_titleAnnulationOffre);
                text = new StringBuilder(String.valueOf(pseudo)).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(context.getString(R.string.txtGcmNotification_offreAAnnuleOffre)).toString();
                break;
            case 3 /*3*/:
                title = context.getString(R.string.txtGcmNotification_titleAcceptationOffre);
                text = new StringBuilder(String.valueOf(pseudo)).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(context.getString(R.string.txtGcmNotification_offreAAccepte)).toString();
                break;
            case 8 /*3*/:
                title = context.getString(R.string.txtGcmNotification_titleRejetOffre);
                text = new StringBuilder(String.valueOf(pseudo)).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(context.getString(R.string.txtGcmNotification_offreARejete)).toString();
                break;
                
            case 15 :
    			try {
    				SmsManager smsManager = SmsManager.getDefault();
    				smsManager.sendTextMessage(userId, null, "code de validation est:"+idOffre,null, null);
		        
			     } catch (Exception e) {
			         e.printStackTrace();
			     }
            	break;
            default:
                title = context.getString(R.string.txtGcmNotification_titleNouvelleMission);
                text = new StringBuilder(String.valueOf(pseudo)).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(context.getString(R.string.txtGcmNotification_missionNouvelle)).toString();
                break;
        }
        if(Integer.parseInt(type)!=15){
        	sendNotification(context, title, text, idMission, idOffre, userId, isMessage);
//	        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
//	        Notification notification = new Notification(R.drawable.ic_launcher, text, when);
//	        Intent notificationIntent = new Intent(context, MessageGcmActivity.class);
//	        Bundle bundle = new Bundle();
//	        bundle.putString("idMissionNot", idMission);
//	        bundle.putString("idOffreNot", idOffre);
//	        bundle.putString("userIdNot", userId);
//	        bundle.putString("isMessageNot", isMessage);
//	        notificationIntent.putExtras(bundle);
//	        Random random = new Random();
//	        int m = random.nextInt(9999 - 1000) + 1000;
//	        notificationIntent.setFlags(m);
//	        notification.setLatestEventInfo(context, title, text, PendingIntent.getActivity(context, 0, notificationIntent,m));
//	        notification.flags |= 16;
//	        notification.defaults |= 1;
//	        notification.defaults |= 2;
//	      
//	        notificationManager.notify(m, notification);
        }
    }
    
    
    private static void sendNotification(Context context, String title,String message,String idMission,
    String idOffre,String  userId,String  isMessage) {
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationCompat.Builder nBuilder;
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        nBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setLights(Color.RED, 500, 500).setContentText(message)
                .setAutoCancel(true).setTicker(title)
                .setWhen(when)
                .setVibrate(new long[] { 100, 250, 100, 250, 100, 250 })
                .setSound(alarmSound);
       
        Intent resultIntent = new Intent(context, MessageGcmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("idMissionNot", idMission);
        bundle.putString("idOffreNot", idOffre);
        bundle.putString("userIdNot", userId);
        bundle.putString("isMessageNot", isMessage);
        resultIntent.putExtras(bundle);
       
        int m = (new Random()).nextInt(9999 - 1000) + 1000;
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,m, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
      
        nBuilder.setContentIntent(resultPendingIntent);
        NotificationManager nNotifyMgr = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        nNotifyMgr.notify(m + 1, nBuilder.build());
    }
    
    
    
}
