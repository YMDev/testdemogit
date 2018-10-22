package mobile.a3tech.com.a3tech.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.model.A3techEvenementiMission;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTimeLine;

public class A3techTimeLineMissionEventItem extends RelativeLayout {

    private List<A3techEvenementiMission> evenementiMission;
    private A3techMission currentMission;
    private ExpandableRelativeLayout expandableLayout;
    private RecyclerView recyclerViewEvents;
    private TextView statutMission, titreMission, adresseMission;
    private RelativeLayout layoutJeader;

    private ImageView indicatorExpandableLayout;
    private Context pContext;

    private LayoutInflater mInflater;
    public A3techTimeLineMissionEventItem(Context context) {
        super(context);
        initView(context);
    }

    public A3techTimeLineMissionEventItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public A3techTimeLineMissionEventItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public A3techTimeLineMissionEventItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(final Context context){
        pContext = context;
        mInflater = LayoutInflater.from(context);
        RelativeLayout timeLineLayout = (RelativeLayout) mInflater.inflate(R.layout.a3tech_time_line_events_item_parent, null);
        expandableLayout = timeLineLayout.findViewById(R.id.expandableLayoutEvents);
        expandableLayout.collapse();
        expandableLayout.setDuration(230);
        statutMission = timeLineLayout.findViewById(R.id.statut_mission_exp);
        titreMission = timeLineLayout.findViewById(R.id.title_mission);
        adresseMission = timeLineLayout.findViewById(R.id.adresse_alpha);
        recyclerViewEvents = timeLineLayout.findViewById(R.id.recycle_events);
        layoutJeader = timeLineLayout.findViewById(R.id.header_exp_info_mission);
        indicatorExpandableLayout = timeLineLayout.findViewById(R.id.anchor_indicator_exp);
        indicatorExpandableLayout.setImageDrawable(context.getResources().getDrawable(R.drawable.a3tech_exp_expand));
        layoutJeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout.toggle();
                if(!expandableLayout.isExpanded()){
                    indicatorExpandableLayout.setImageDrawable(context.getResources().getDrawable(R.drawable.a3tech_exp_collapse));
                }else{
                    indicatorExpandableLayout.setImageDrawable(context.getResources().getDrawable(R.drawable.a3tech_exp_expand));
                }
            }
        });
        addView(timeLineLayout);
    }

    public void setExpandableLayoutInRecycleView(Boolean value){
        //expandableLayout.setInRecyclerView(value);
    }

    public void initDataMission(A3techMission mission){

        if(mission != null){
            currentMission = mission;
            titreMission.setText(currentMission.getTitre());
            adresseMission.setText(currentMission.getAdresse());
            statutMission.setText(currentMission.getStatut().getDiscreptionEnum());
            evenementiMission = currentMission.getEvenement();
            SimpleAdapterTimeLine adapter = new SimpleAdapterTimeLine(pContext,evenementiMission,(A3techHomeActivity)pContext);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(pContext.getApplicationContext());
            recyclerViewEvents.setLayoutManager(mLayoutManager);
            recyclerViewEvents.setItemAnimator(new DefaultItemAnimator());
            recyclerViewEvents.setAdapter(adapter);
        }

    }

}
