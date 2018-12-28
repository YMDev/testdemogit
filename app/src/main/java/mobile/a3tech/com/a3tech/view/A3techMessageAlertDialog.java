package mobile.a3tech.com.a3tech.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techLoginActivity;
import mobile.a3tech.com.a3tech.activity.BaseActivity;

public class A3techMessageAlertDialog extends AlertDialog {
  AlertDialog okMsgDialog;
    String label, subMsg, btnLabel;
    protected A3techMessageAlertDialog(@NonNull Context context) {
        super(context);
    }
    protected A3techMessageAlertDialog(@NonNull Context context, String label, String subMsg, String btnActionLanevl) {
        super(context);
        this.label = label;
        this.subMsg = subMsg;
        this.btnLabel = btnActionLanevl;
        init(context);
    }
    protected A3techMessageAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected A3techMessageAlertDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void init(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View content = ((BaseActivity) context).getLayoutInflater()
                .inflate(R.layout.a3tech_msg_dialog_info, null);
        TextView btnActionTxt = content.findViewById(R.id.ok_close);
        TextView labelTxt = content.findViewById(R.id.header_label);
        TextView subMsgTxt = content.findViewById(R.id.sub_header_label);
        btnActionTxt.setText(btnLabel);
        labelTxt.setText(label);
        subMsgTxt.setText(subMsg);
        btnActionTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (okMsgDialog != null) okMsgDialog.dismiss();
               /*  FirebaseAuth.getInstance().signOut();
                ((BaseActivity) context).startActivity(new Intent( ((BaseActivity)context), A3techLoginActivity.class));
                ((BaseActivity) context).finish();*/
            }
        });
        builder.setView(content);
        okMsgDialog = builder.create();
        okMsgDialog.setCancelable(false);
        okMsgDialog.show();
    }
}
