package mobile.a3tech.com.a3tech.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.view.ClockPieHelper;
import mobile.a3tech.com.a3tech.view.ClockPieView;

public class A3techTechnicienAvailabilityActivity extends AppCompatActivity {
    ClockPieView clockPieViewJour, clockPieViewNuit;
    RelativeLayout lundi,mardi,mercredi,jeudi,vendredi,samedi,dimanche;
    TextView txtLundi, txtMardi, txtMercredi, txtJeudi, txtVendredi, txtSamedi, txtDimanche;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_technicien_availability_activity);
        clockPieViewJour = (ClockPieView)findViewById(R.id.pie_view);
        clockPieViewNuit = (ClockPieView)findViewById(R.id.pie_view2);
        randomSet(clockPieViewNuit);
        randomSet(clockPieViewJour);
    }

    private void randomSet(ClockPieView clockPieView){
        ArrayList<ClockPieHelper> clockPieHelperArrayList = new ArrayList<ClockPieHelper>();
      /*  for(int i=0; i<20; i++){
            int startHour = (int)(24*Math.random());
            int startMin = (int)(60*Math.random());
            int duration = (int)(50*Math.random());
            clockPieHelperArrayList.add(new ClockPieHelper(startHour,startMin,0,startHour,startMin+duration,0));
        }*/
        clockPieHelperArrayList.add(new ClockPieHelper(8,0,0,12,0,0));
        clockPieHelperArrayList.add(new ClockPieHelper(14,5,0,18,0,0));
        clockPieView.setDate(clockPieHelperArrayList);
    }

    private void set(ClockPieView clockPieView){
        ArrayList<ClockPieHelper> clockPieHelperArrayList = new ArrayList<ClockPieHelper>();
        clockPieHelperArrayList.add(new ClockPieHelper(1,50,2,30));
        clockPieHelperArrayList.add(new ClockPieHelper(6,50,8,30));
        clockPieView.setDate(clockPieHelperArrayList);
    }

}
