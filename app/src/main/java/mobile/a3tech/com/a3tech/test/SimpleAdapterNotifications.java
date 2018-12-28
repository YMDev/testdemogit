package mobile.a3tech.com.a3tech.test;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.manager.NotificationsManager;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.A3techNotification;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;

/**
 * Created by Suleiman on 03/02/17.
 */

public class SimpleAdapterNotifications extends RecyclerView.Adapter<SimpleAdapterNotifications.SimpleItemVH> {

    //  Data
    private   List<A3techMission>  listeObjects;

    private Context context;
    private Activity parentActivity;
    private List<A3techNotification> evenementiMission;
    private A3techMission currentMission;



    public SimpleAdapterNotifications(Context context) {
        this.context = context;
    }

    public SimpleAdapterNotifications(Context context,  List<A3techMission> listeMissions, Activity parent) {
        this.context = context;
        listeObjects = listeMissions;
        parentActivity = parent;
    }




    @Override
    public SimpleItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a3tech_item_notificiation, parent, false);

        return new SimpleItemVH(v);
    }

    @Override
    public void onBindViewHolder(final SimpleItemVH holder, int position) {
        final A3techMission missionTmp = listeObjects.get(position);
        if (missionTmp == null) return;
        holder.setIsRecyclable(false);
        holder.indicatorExpandableLayout.setImageDrawable(context.getResources().getDrawable(R.drawable.a3tech_exp_expand));
        holder.layoutJeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.expandableLayout.toggle();
                if(!holder.expandableLayout.isExpanded()){
                    holder.indicatorExpandableLayout.setImageDrawable(context.getResources().getDrawable(R.drawable.a3tech_exp_collapse));
                }else{
                    holder.recyclerViewEvents.setVisibility(View.GONE);
                    holder.waitingBar.setVisibility(View.VISIBLE);
                    NotificationsManager.getInstance().filtreNotificationByMission(missionTmp, "0", "0", new DataLoadCallback() {
                        @Override
                        public void dataLoaded(Object data, int method, int typeOperation) {
                            List<A3techNotification> listeNotifs =  (List<A3techNotification>) data;
                            evenementiMission = listeNotifs;
                            SimpleAdapterTimeLine adapter = new SimpleAdapterTimeLine(context,evenementiMission,(Activity)context);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());
                            holder.recyclerViewEvents.setLayoutManager(mLayoutManager);
                            holder.recyclerViewEvents.setItemAnimator(new DefaultItemAnimator());
                            holder.recyclerViewEvents.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            holder.recyclerViewEvents.setVisibility(View.VISIBLE);
                            holder.waitingBar.setVisibility(View.GONE);
                        }
                        @Override
                        public void dataLoadingError(int errorCode) {
                            holder.recyclerViewEvents.setVisibility(View.VISIBLE);
                            holder.waitingBar.setVisibility(View.GONE);
                        }
                    });
                    holder.indicatorExpandableLayout.setImageDrawable(context.getResources().getDrawable(R.drawable.a3tech_exp_expand));
                }
            }
        });
        currentMission = missionTmp;
        holder.titreMission.setText(currentMission.getTitre());
        holder.adresseMission.setText(currentMission.getAdresse());
        holder.statutMission.setText(currentMission.getStatut().getDiscreptionEnum());



    }


    @Override
    public int getItemCount() {
        return listeObjects != null ? listeObjects.size() : 0;
    }


    protected static class SimpleItemVH extends RecyclerView.ViewHolder {
        private ExpandableLayout expandableLayout;
        private RecyclerView recyclerViewEvents;
        private TextView statutMission, titreMission, adresseMission;
        private RelativeLayout layoutJeader;
        private ImageView indicatorExpandableLayout;
        private ProgressBar waitingBar;
        public SimpleItemVH(View itemView) {
            super(itemView);
            expandableLayout = itemView.findViewById(R.id.expandableLayoutEvents);
            expandableLayout.collapse();
            expandableLayout.setDuration(230);
            statutMission = itemView.findViewById(R.id.statut_mission_exp);
            titreMission = itemView.findViewById(R.id.title_mission);
            adresseMission = itemView.findViewById(R.id.adresse_alpha);
            recyclerViewEvents = itemView.findViewById(R.id.recycle_events);
            layoutJeader = itemView.findViewById(R.id.header_exp_info_mission);
            indicatorExpandableLayout = itemView.findViewById(R.id.anchor_indicator_exp);
            waitingBar = itemView.findViewById(R.id.waiting_for_notifs);
        }
    }


}