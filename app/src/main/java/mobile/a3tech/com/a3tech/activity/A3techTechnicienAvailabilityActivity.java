package mobile.a3tech.com.a3tech.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import eltos.simpledialogfragment.SimpleDateDialog;
import eltos.simpledialogfragment.SimpleTimeDialog;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.A3techDisponibility;
import mobile.a3tech.com.a3tech.test.SimpleAdapterDisponibility;
import mobile.a3tech.com.a3tech.utils.DateStuffs;
import mobile.a3tech.com.a3tech.view.ClockPieHelper;
import mobile.a3tech.com.a3tech.view.ClockPieView;
import mobile.a3tech.com.a3tech.view.seekbar.ClockView;

public class A3techTechnicienAvailabilityActivity extends AppCompatActivity implements SimpleAdapterDisponibility.OnRangeTimeChangedListener {
    ClockPieView clockPieViewJour, clockPieViewNuit;

    RecyclerView gridRangeMatin;
    Button btnAjouterDispo;

    Switch switchDisableProfile;
    SimpleAdapterDisponibility adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_technicien_availability_activity);
        clockPieViewJour = (ClockPieView)findViewById(R.id.pie_view);
       switchDisableProfile = (Switch) findViewById(R.id.disable_profil_switch);
        gridRangeMatin = findViewById(R.id.grid_range_matin);

        switchDisableProfile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    clockPieViewJour.set
                }
            }
        });
        List<A3techDisponibility> listeDis = new ArrayList<>();
        A3techDisponibility dd = new A3techDisponibility("1", Calendar.getInstance().getTimeInMillis(), Calendar.getInstance().getTimeInMillis());
        listeDis.add(dd);
          adapter = new SimpleAdapterDisponibility(A3techTechnicienAvailabilityActivity.this, listeDis);
        adapter.setOnRangeTimeChangedListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        gridRangeMatin.setLayoutManager(mLayoutManager);
        gridRangeMatin.setAdapter(adapter);
       /* clockPieViewNuit.setNuit(Boolean.TRUE);*/
/*        randomSet(clockPieViewNuit);*/
        randomSet(clockPieViewJour);
        btnAjouterDispo = findViewById(R.id.action_add_dispo);
        btnAjouterDispo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                A3techDisponibility dd = new A3techDisponibility(null, Calendar.getInstance().getTimeInMillis(), Calendar.getInstance().getTimeInMillis());
                if( adapter.getListeObjects() == null){
                    adapter.setListeObjects(new ArrayList<A3techDisponibility>());
                }
                adapter.getListeObjects().add(dd);
                adapter.notifyItemInserted(adapter.getItemCount());
            }
        });
    }

    private void randomSet(ClockPieView clockPieView){
        ArrayList<ClockPieHelper> clockPieHelperArrayList = new ArrayList<ClockPieHelper>();
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

    @Override
    public void rangeChanged(A3techDisponibility dispo) {
        List<A3techDisponibility> listeDispo = adapter.getListeObjects();
        ArrayList<ClockPieHelper> clockPieHelperArrayList = new ArrayList<ClockPieHelper>();
        for (A3techDisponibility dispon: listeDispo
             ) {
            Calendar calendarFrom = Calendar.getInstance();
            calendarFrom.setTimeInMillis(dispon.getTimeFrom());
            Calendar calendarTo = Calendar.getInstance();
            calendarTo.setTimeInMillis(dispon.getTimeTo());
            clockPieHelperArrayList.add(new ClockPieHelper(calendarFrom.get(Calendar.HOUR_OF_DAY),calendarFrom.get(Calendar.MINUTE),calendarTo.get(Calendar.HOUR_OF_DAY),calendarTo.get(Calendar.MINUTE)));
        }
        clockPieViewJour.setDate(clockPieHelperArrayList);
    }
}
