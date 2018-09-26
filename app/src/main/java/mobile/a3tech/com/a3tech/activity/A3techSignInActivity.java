package mobile.a3tech.com.a3tech.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import mobile.a3tech.com.a3tech.R;

public class A3techSignInActivity extends AppCompatActivity {


    Button resetPassword;
    Button facebookLogIn;
    Button emailLogIn;
    TextView signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_sign_in_activity);
        resetPassword = findViewById(R.id.reset_password);
        facebookLogIn = findViewById(R.id.btnfacebookConnection);
        emailLogIn = findViewById(R.id.btn_connect_email);
        signin = findViewById(R.id.signin_action);


    }
}
