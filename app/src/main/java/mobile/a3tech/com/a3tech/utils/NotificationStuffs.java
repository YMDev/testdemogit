package mobile.a3tech.com.a3tech.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import mobile.a3tech.com.a3tech.service.MailService;

public class NotificationStuffs {

    public static void sendSmsMsgFnc(String mblNumVar, String smsMsgVar, Activity context)
    {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
        {
            try
            {
                SmsManager smsManager = SmsManager.getDefault();

                if (android.os.Build.VERSION.SDK_INT >= 22){
                    Log.e("Alert","Checking SubscriptionId");
                    try {
                        Log.e("Alert","SubscriptionId is " + smsManager.getSubscriptionId());
                    } catch (Exception e) {
                        Log.e("Alert",e.getMessage());
                        Log.e("Alert","Fixed SubscriptionId to 1");
                        smsManager = SmsManager.getSmsManagerForSubscriptionId(1);

                    }
                    smsManager = SmsManager.getSmsManagerForSubscriptionId(1);
                    smsManager.sendTextMessage(mblNumVar, null, smsMsgVar, null, null);
                }

            }
            catch (Exception ErrVar)
            {
                Toast.makeText(context,ErrVar.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
                ErrVar.printStackTrace();
            }
        }
        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                ActivityCompat.requestPermissions(context,new String[]{Manifest.permission.SEND_SMS}, 10);
            }
        }

    }


    public  static void  sendMail(String mailTO, String html, String subject){
        final MailService mailer = new MailService(Constant.MAIL_3TECH, mailTO, subject, "TextBody", html);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    mailer.sendAuthenticated();
                } catch (Exception e) {
                    Log.e("MAILKKKKKKKKK", "Failed sending email.", e);
                }
            }
        }, 200);
    }
}
