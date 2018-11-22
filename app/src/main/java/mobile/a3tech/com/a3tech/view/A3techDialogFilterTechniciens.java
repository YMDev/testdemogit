package mobile.a3tech.com.a3tech.view;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.A3techReviewMission;

public class A3techDialogFilterTechniciens {
    static Integer distance;

    public static String SRC_FROM_DIALOGUE_DISPLAY_TECH = "SRC_FROM_DIALOGUE_DISPLAY_TECH";
    public static int REQUEST_DISPLAY_TECH_FROM_DIALOGUE = 3221;

    public static Dialog displayFilterDialogue(final OnAppliquerPerimetre param, Integer perimetre) {
        final Dialog progressDialog = new Dialog(param.getContext());
        progressDialog.show();
        LayoutInflater inflater = ((Activity) param.getContext()).getLayoutInflater();
        View content = inflater.inflate(R.layout.a3tech_dialogue_filter_technicien_perimetre, null);
        final SeekArc seekArc = (SeekArc) content.findViewById(R.id.seekArc);
        final TextView permietreDisplayText = (TextView) content.findViewById(R.id.seekArcProgress);
        final Button appliquer = (Button) content.findViewById(R.id.appliquer_filter);
        if (perimetre == null) perimetre = 0;
        distance = perimetre;
        seekArc.setProgress(distance);
        permietreDisplayText.setText(perimetre + " KM");
        seekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekArc seekArc, int progress,
                                          boolean fromUser) {
                permietreDisplayText.setText(String.valueOf(progress) + " KM");
                distance = progress;

            }
        });
        appliquer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (param != null) {
                    param.appliquerPerimetre(distance);
                }
                progressDialog.dismiss();
            }
        });
        progressDialog.setContentView(content);
        progressDialog.setCancelable(true);
        Window window = progressDialog.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        return progressDialog;
    }

    public interface OnAppliquerPerimetre {
        void appliquerPerimetre(Integer perim);

        Context getContext();
    }
}
