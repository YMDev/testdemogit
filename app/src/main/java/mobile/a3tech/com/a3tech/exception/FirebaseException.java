package mobile.a3tech.com.a3tech.exception;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuthException;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.BaseActivity;

public class FirebaseException {

    public static String getExceptionMessage(BaseActivity activity, FirebaseAuthException task) {
        String errorCode = task.getErrorCode();
        String errorMsg = "";
        switch (errorCode) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                errorMsg = activity.getResources().getString(R.string.error_authentication_firebase_token);
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                errorMsg = activity.getResources().getString(R.string.error_authentication_firebase_token);
                break;

            case "ERROR_INVALID_CREDENTIAL":
                errorMsg = activity.getResources().getString(R.string.error_authentication_firebase_cridential);
                break;

            case "ERROR_INVALID_EMAIL":
                errorMsg = activity.getResources().getString(R.string.error_authentication_firebase_mail);
                break;

            case "ERROR_WRONG_PASSWORD":
                errorMsg = activity.getResources().getString(R.string.error_authentication_firebase_pass);
                break;

            case "ERROR_USER_MISMATCH":
                errorMsg = activity.getResources().getString(R.string.error_authentication_firebase_user_mismatch);
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                errorMsg = activity.getResources().getString(R.string.error_authentication_firebase_sensitive_data);
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                errorMsg = activity.getResources().getString(R.string.error_authentication_firebase_user_exist_diff_cri);
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                errorMsg = activity.getResources().getString(R.string.error_authentication_firebase_mail_used);
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                errorMsg = activity.getResources().getString(R.string.error_authentication_firebase_crd_used);
                break;

            case "ERROR_USER_DISABLED":
                errorMsg = activity.getResources().getString(R.string.error_authentication_firebase_user_disabled);
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                errorMsg = activity.getResources().getString(R.string.error_authentication_firebase_reconnect);
                break;

            case "ERROR_USER_NOT_FOUND":
                errorMsg = activity.getResources().getString(R.string.error_authentication_firebase_no_user);
                break;

            case "ERROR_INVALID_USER_TOKEN":
                errorMsg = activity.getResources().getString(R.string.error_authentication_firebase_reconnect);
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                errorMsg = activity.getResources().getString(R.string.error_authentication_firebase_token);
                break;

            case "ERROR_WEAK_PASSWORD":
                errorMsg = activity.getResources().getString(R.string.error_authentication_firebase_weak_pass);
                break;
        }
        return errorMsg;
    }
}
