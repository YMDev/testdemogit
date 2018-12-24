package mobile.a3tech.com.a3tech.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.codehaus.jackson.util.MinimalPrettyPrinter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.fragment.A3techHomeAccountFragment;
import mobile.a3tech.com.a3tech.fragment.A3techHomeBrowseTechFragment;
import mobile.a3tech.com.a3tech.fragment.A3techMissionsHomeFragment;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;
import mobile.a3tech.com.a3tech.view.A3techCustomToastDialog;
import mobile.a3tech.com.a3tech.view.A3techWaitingDialog;

public class A3techSplashActivity extends BaseActivity implements DataLoadCallback {
    ImageView animationLoginImage;
    String conMode;
    String connectedUser;
    String password;
    String idMissionNotif;
    String idOffreNotif;
    AnimationDrawable rocketAnimation;
    String userIdNotif;
    String isMessageNotif;
    String versionname;
    A3techUser usern;
    ProgressDialog waitingDialg;
    RelativeLayout networkOn, networkDown;

    static int requExce = 34232;

    public A3techSplashActivity() {
        this.idMissionNotif = "";
        this.idOffreNotif = "";
        this.userIdNotif = "";
        this.isMessageNotif = "";
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.a3tech_splash_activity);
        networkDown = findViewById(R.id.network_down);
        networkOn = findViewById(R.id.network_on);

        networkDown.setVisibility(View.GONE);
        networkOn.setVisibility(View.VISIBLE);


        //android O fix bug orientation
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        try {
            PackageInfo info = getPackageManager().getPackageInfo("mobile.a3tech.com.a3tech", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash======>:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NameNotFoundException e) {
            Log.e("KeyHash:", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash:", e.toString());
        }
        setVersionInfo();
        installListener();
        //initAuthParam();
    }


    private void initAuthParam() {

        this.idMissionNotif = getIntent().getStringExtra("idMissionNot");
        this.userIdNotif = getIntent().getStringExtra("userIdNot");
        this.idOffreNotif = getIntent().getStringExtra("idOffreNot");
        this.isMessageNotif = getIntent().getStringExtra("isMessageNot");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.conMode = prefs.getString("conMode", "");
        this.connectedUser = prefs.getString("identifiant", "");
        this.password = prefs.getString("password", "");
    /*    if (!isConnected()) {
           // startActivity(new Intent(this, A3techWelcomPageActivity.class));
            A3techCustomToastDialog.createToastDialog(A3techSplashActivity.this, getApplicationContext().getString(R.string.txtSplash_messageCheckConnexion), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_INFO);
        } else*/
        if (this.conMode.equals("facebook")) {
            Intent mainIntent = new Intent(this, FacebookActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constant.RESULT_ACTION_CODE_FACEBOOK_SRC, Constant.RESULT_ACTION_CODE_FACEBOOK_SRC_SPLASH);
            mainIntent.putExtras(bundle);
            startActivityForResult(mainIntent, requExce);
        } else if (this.conMode.length() == 0) {
            /*  startActivity(new Intent(this, A3techTechnicienAvailabilityActivity.class));*/
            startActivity(new Intent(this, A3techLoginActivity.class));
            finish();
        } else {
            //Todo display dialogue
            // waitingDialg =  A3techWaitingDialog.createProgressDialog(A3techSplashActivity.this, "");
            UserManager.getInstance().getProfil(this.connectedUser, password, this);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != Constant.SPLASH_FACEBOOK_REQUEST_CODE_SPLASH || resultCode != -1) {
            return;
        }
        if (data.getStringExtra("resultCode").equals("OK")) {
            String nom = data.getStringExtra("nom");
            String prenom = data.getStringExtra("prenom");
            String email = data.getStringExtra("email");
            password = data.getStringExtra("facebookIdentifiant");
            UserManager.getInstance().loginfb(nom, prenom, data.getStringExtra("facebookIdentifiant"), email, this);
            return;
        }
        new Builder(this).setTitle("Error").setMessage(data.getStringExtra("info")).setPositiveButton("OK", null).show();
    }

    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Locale locale2 = new Locale(prefs.getString("ApplicationLanguage", Constant.LANGUAGE_FRENSH));
        Locale.setDefault(locale2);
        Configuration config2 = new Configuration();
        config2.locale = locale2;
        getBaseContext().getResources().updateConfiguration(config2, getBaseContext().getResources().getDisplayMetrics());
    }

    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnected()) {
            return false;
        }
        return true;
    }

    public void dataLoaded(Object data, int method, int typeOperation) {
        switch (method) {
            case Constant.KEY_USER_MANAGER_LOGIN_FB:
            case Constant.KEY_USER_MANAGER_GET_PROFIL:
                usern = (A3techUser) data;
                UserManager.getInstance().getVersion(A3techSplashActivity.this);
                break;
            case Constant.KEY_USER_GET_VERSION:
                String version = (String) data;
                /*Intent mainIntent = new Intent(this, NavigationMain.class);*/
                Intent mainIntent = new Intent(this, A3techWelcomPageActivity.class);
                if (versionname.equals(version)) {
                    mainIntent.putExtra("nomPrenom", usern.getPrenom() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + usern.getNom());
                    mainIntent.putExtra("nbr", usern.getNbrMission());
                    mainIntent.putExtra("idMissionNotif", this.idMissionNotif);
                    mainIntent.putExtra("userIdNotif", this.userIdNotif);
                    mainIntent.putExtra("idOffreNotif", this.idOffreNotif);
                    mainIntent.putExtra("isMessageNotif", this.isMessageNotif);

                    Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                    editor.putString(PreferencesValuesUtils.KEY_CONNECTED_USER_NAME, usern.getPrenom() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + usern.getNom());
                    editor.putString("MyCredentials", usern.getEmail());
                    editor.putString("pseudo", usern.getPseudo());
                    editor.putString("identifiant", usern.getEmail() + "");
                    editor.putString("password", password);
                    editor.putString("facebookId", usern.getFacebookId());
                    editor.putString(PreferencesValuesUtils.KEY_CONNECTED_USER_GSON, new Gson().toJson(usern));
                    editor.putString("checkphone", usern.getTelephone());
                    editor.commit();
                    startActivity(mainIntent);
                    if (waitingDialg != null)
                        waitingDialg.dismiss();
                    finish();
                } else {
                    Toast.makeText(A3techSplashActivity.this, "Veuillez proceder � la mise � jour de votre application", Toast.LENGTH_LONG).show();
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=ma.khodara")));
                    finish();
                }

        }

    }

    private void setVersionInfo() {
        // int versionCode = -1;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionname = packageInfo.versionName;
            // versionCode = packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void dismissDialog(AlertDialog dialog){
        if(dialog != null) dialog.dismiss();
    }
    AlertDialog alertDialog = null;
    public void dataLoadingError(int errorCode) {

        if (errorCode == Constant.ERROR_USER_DISABLED) {

            Builder builder = new Builder(A3techSplashActivity.this);
            View content = A3techSplashActivity.this.getLayoutInflater()
                    .inflate(R.layout.a3tech_compte_disabled, null);

            ImageView closeImg = (ImageView) content
                    .findViewById(R.id.close_dialog);
            closeImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissDialog(alertDialog);
                    deconnexion();
                }
            });
            builder.setView(content);
            alertDialog = builder.create();
            alertDialog.setOnDismissListener(new AlertDialog.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    dismissDialog(alertDialog);
                    deconnexion();
                }
            });
            alertDialog.show();
        } else if (errorCode == Constant.ERROR_USER_NOT_FOUND) {
            Builder builder = new Builder(A3techSplashActivity.this);
            View content = A3techSplashActivity.this.getLayoutInflater()
                    .inflate(R.layout.a3tech_compte_disabled, null);

            TextView label = (TextView) content
                    .findViewById(R.id.header_label);
            label.setText(getResources().getString(R.string.compte_not_found));
            TextView labelsub = (TextView) content
                    .findViewById(R.id.sub_header_label);
            labelsub.setText(getResources().getString(R.string.error_user_not_found));
            ImageView closeImg = (ImageView) content
                    .findViewById(R.id.close_dialog);
            closeImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissDialog(alertDialog);
                    deconnexion();

                }
            });
            builder.setView(content);
            alertDialog = builder.create();
            alertDialog.setOnDismissListener(new AlertDialog.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    dismissDialog(alertDialog);
                    deconnexion();
                }
            });
            alertDialog.show();
        } else {
            final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) A3techSplashActivity.this
                    .findViewById(android.R.id.content)).getChildAt(0);
            Snackbar
                    .make(viewGroup, getString(R.string.error_connexion_server),
                            Snackbar.LENGTH_LONG)
                    .show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1000);
        }

    }
    public void deconnexion() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(A3techSplashActivity.this).edit();
        editor.putString("MyCredentials", "");
        editor.putString("facebookId", "");
        editor.putString("conMode", "");
        editor.putString("ApplicationLanguage", "");
        editor.putString("identifiant", "");

     /*   GCMRegistrar.checkDevice(this.getActivity());
        GCMRegistrar.checkManifest(this.getActivity());
        GCMRegistrar.unregister(this.getActivity());*/
        editor.commit();
        Intent mainIntent = new Intent(A3techSplashActivity.this, A3techLoginActivity.class);
        startActivity(mainIntent);
        finish();
    }

    public void dataLoadingError(String error) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }


    BroadcastReceiver broadcastReceiver;

    private void installListener() {

        if (broadcastReceiver == null) {

            broadcastReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {

                    Bundle extras = intent.getExtras();

                    NetworkInfo info = (NetworkInfo) extras
                            .getParcelable("networkInfo");

                    NetworkInfo.State state = info.getState();
                    Log.d("KKKKKKKK NET", info.toString() + " "
                            + state.toString());

                    if (state == NetworkInfo.State.CONNECTED) {
                        onNetworkUp();
                        /* A3techCustomToastDialog.createToastDialog(A3techHomeActivity.this, getString(R.string.connexion_retablie), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_INFO);*/

                    } else {
                        A3techCustomToastDialog.createSnackBar(A3techSplashActivity.this, getString(R.string.network_error_text), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);
                        onNetworkDown();
                    }

                }
            };

            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(broadcastReceiver, intentFilter);
        }
    }


    private void onNetworkDown() {
        networkDown.setVisibility(View.VISIBLE);
        networkOn.setVisibility(View.GONE);

    }

    private void onNetworkUp() {
        networkDown.setVisibility(View.GONE);
        networkOn.setVisibility(View.VISIBLE);
        initAuthParam();
    }

}
