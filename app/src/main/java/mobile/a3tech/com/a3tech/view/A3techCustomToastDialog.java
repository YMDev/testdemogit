package mobile.a3tech.com.a3tech.view;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techTechnicienAvailabilityActivity;

public class A3techCustomToastDialog {

    public static final int TOAST_ERROR = 1;
    public static final int TOAST_INFO = 2;
    public static final int TOAST_WARNING = 3;
    public static final int TOAST_SUCESS = 4;

    public static Toast createToastDialog(Context mContext, String text, int duration, int type) {
       /* LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View layout = inflater.inflate(R.layout.a3tech_toast_v2,
                null);

        ImageView infoIcon = (ImageView) layout.findViewById(R.id.icon_toast_info);
        ImageView warningIcon = (ImageView) layout.findViewById(R.id.icon_toast_warn);
        ImageView errorIcon = (ImageView) layout.findViewById(R.id.icon_toast_alert);
        ImageView successIcon = (ImageView) layout.findViewById(R.id.icon_toast_success);
        TextView contentToast = (TextView) layout.findViewById(R.id.toast_content);
        RelativeLayout container = (RelativeLayout) layout.findViewById(R.id.container_toast);

        switch (type) {
            case TOAST_ERROR:
                errorIcon.setVisibility(View.VISIBLE);
                infoIcon.setVisibility(View.GONE);
                warningIcon.setVisibility(View.GONE);
                successIcon.setVisibility(View.GONE);
                container.setBackground(mContext.getResources().getDrawable(R.drawable.a3tech_toast_background_layout_error));
                break;
            case TOAST_INFO:
                errorIcon.setVisibility(View.GONE);
                infoIcon.setVisibility(View.VISIBLE);
                warningIcon.setVisibility(View.GONE);
                successIcon.setVisibility(View.GONE);
                container.setBackground(mContext.getResources().getDrawable(R.drawable.a3tech_toast_background_layout_info));
                break;
            case TOAST_WARNING:
                errorIcon.setVisibility(View.GONE);
                infoIcon.setVisibility(View.GONE);
                warningIcon.setVisibility(View.VISIBLE);
                successIcon.setVisibility(View.GONE);
                container.setBackground(mContext.getResources().getDrawable(R.drawable.a3tech_toast_background_layout_warning));
                break;
            case TOAST_SUCESS:
                errorIcon.setVisibility(View.GONE);
                infoIcon.setVisibility(View.GONE);
                warningIcon.setVisibility(View.GONE);
                successIcon.setVisibility(View.VISIBLE);
                container.setBackground(mContext.getResources().getDrawable(R.drawable.a3tech_toast_background_layout_sucess));
                break;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.activity_bottom_out);
        //use this to make it longer:  animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                //view.setVisibility(View.GONE);
            }
        });

        layout.startAnimation(animation);  contentToast.setText(text);
        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 230);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();*/
        createSnackBar(mContext, text, duration, type);
        return null;
    }


    public static Snackbar createSnackBar(Context mContext, String text, int duration, int type) {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) ((Activity) mContext).findViewById(android.R.id.content)).getChildAt(0);
        Snackbar snack = Snackbar
                .make(viewGroup, text,
                        Snackbar.LENGTH_LONG);
        switch (type) {
            case TOAST_ERROR:
                snack.getView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.error_toast_color));
                break;
            case TOAST_INFO:
                snack.getView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.info_toast_color));
                break;
            case TOAST_WARNING:
                snack.getView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.warning_toat_color));
                break;
            case TOAST_SUCESS:
                snack.getView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.sucess_toast_color));
                break;
        }

        snack.show();
        return snack;
    }

}
