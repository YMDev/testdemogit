package mobile.a3tech.com.a3tech.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import eltos.simpledialogfragment.SimpleDialog;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.fragment.A3techHomeAccountFragment;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTest;
import mobile.a3tech.com.a3tech.utils.LetterTileProvider;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;

public class A3techAccountActivity extends AppCompatActivity implements A3techHomeAccountFragment.OnFragmentInteractionListener, SimpleAdapterTest.OnDeconnexion, SimpleDialog.OnDialogResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectedUser = PreferencesValuesUtils.getConnectedUser(A3techAccountActivity.this);
        setContentView(R.layout.a3tech_account_activity);
        setFragment(A3techHomeAccountFragment.newInstance(null, null));
        setupToolbarAccount();
    }

    protected void setFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.frame_account, fragment);
        t.addToBackStack(fragment.getClass().getName());
        t.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void deconnexion() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(A3techAccountActivity.this).edit();
        editor.putString("MyCredentials", "");
        editor.putString("facebookId", "");
        editor.putString("conMode", "");
        editor.putString("ApplicationLanguage", "");
        editor.putString("identifiant", "");

     /*   GCMRegistrar.checkDevice(this.getActivity());
        GCMRegistrar.checkManifest(this.getActivity());
        GCMRegistrar.unregister(this.getActivity());*/
        editor.commit();
        Intent mainIntent = new Intent(A3techAccountActivity.this, A3techLoginActivity.class);
        startActivity(mainIntent);
        finish();
    }

    TextView nomPrenomUser;
    ImageView userAvatar;
    A3techUser connectedUser;
    Toolbar toolAccount;

    private void setupToolbarAccount() {
        // Acount fragment toolbar init
        toolAccount = findViewById(R.id.toolbar_account);
        toolAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String connectedUser = prefs.getString("identifiant", "");
                String password = prefs.getString("password", "");

                mobile.a3tech.com.a3tech.manager.UserManager.getInstance().getProfil(connectedUser, password, new DataLoadCallback() {
                    @Override
                    public void dataLoaded(Object data, int method, int typeOperation) {
                        A3techUser user = (A3techUser) data;
                        Bundle bExtra = new Bundle();
                        bExtra.putString(A3techViewEditProfilActivity.ARG_SRC_ACTION, A3techHomeActivity.ACTION_FROM_A3techHomeActivity);
                        bExtra.putString(A3techViewEditProfilActivity.ARG_USER_OBJECT, new Gson().toJson(user));
                        Intent mainIntent = new Intent(A3techAccountActivity.this, A3techViewEditProfilActivity.class);
                        mainIntent.putExtras(bExtra);
                        startActivity(mainIntent);
                    }

                    @Override
                    public void dataLoadingError(int errorCode) {

                    }
                });

            }
        });

        userAvatar = findViewById(R.id.photo_user);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (connectedUser != null && StringUtils.isNoneBlank(connectedUser.getNom())) {
                    final LetterTileProvider tileProvider = new LetterTileProvider(A3techAccountActivity.this);
                    final Bitmap letterTile = tileProvider.getLetterTile(connectedUser.getNom(), connectedUser.getNom(), 155, 155);
                    userAvatar.setImageBitmap(letterTile);
                }

            }
        });

        String jsonUSer = PreferencesValuesUtils.getPreferenceStringByParam(A3techAccountActivity.this, PreferencesValuesUtils.KEY_CONNECTED_USER_GSON, "");
        if (StringUtils.isNoneBlank(jsonUSer)) {
            A3techUser connectedUSer = new Gson().fromJson(jsonUSer, A3techUser.class);
            if (connectedUSer != null) {
                if (StringUtils.isNoneBlank(connectedUSer.getId_photo_profil())) {
                    //getUSer Avatare
                    //TODO set avatare from user picture
                    //ImageManager.getInstance().getString()
                }
            }
        }
        nomPrenomUser = findViewById(R.id.user_name_pname);
        String nameConnectedUser = "";
        if (connectedUser != null && connectedUser.getNom() != null && connectedUser.getPrenom() != null) {
            nameConnectedUser = connectedUser.getNom() + " " + connectedUser.getPrenom().substring(0, 1) + ".";
        }
        nomPrenomUser.setText(nameConnectedUser);

    }


    @Override
    public boolean onResult(@NonNull String dialogTag, int which, @NonNull Bundle extras) {
        if (which == BUTTON_POSITIVE && "DEC".equals(dialogTag)) {
            deconnexion();
        }
        return false;
    }
}
