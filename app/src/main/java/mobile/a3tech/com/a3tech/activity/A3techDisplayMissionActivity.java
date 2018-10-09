package mobile.a3tech.com.a3tech.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.view.ExpandableTextView;

public class A3techDisplayMissionActivity extends AppCompatActivity {


    @BindView(R.id.toolbar_display_mission)
    android.support.v7.widget.Toolbar toolbarDisplayMission;
    @BindView(R.id.title_mission) TextView titreMission;
    @BindView(R.id.dis_statut_mission) TextView statutMission;
    @BindView(R.id.dis_description_mission) ExpandableTextView descriptionMission;
    @BindView(R.id.dis_cout_mission) TextView montantMission;
    @BindView(R.id.dis_temps_mission) TextView tempsMission;
    @BindView(R.id.dis_value_category_mission) TextView categoryMission;
    @BindView(R.id.dis_commentaire_mission) TextView commentaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_display_mission_activity);
        ButterKnife.bind(this);
    }
}
