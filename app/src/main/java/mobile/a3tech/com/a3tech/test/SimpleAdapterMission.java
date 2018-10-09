package mobile.a3tech.com.a3tech.test;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techAddMissionActivity;
import mobile.a3tech.com.a3tech.activity.A3techDisplayMissionActivity;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.activity.A3techViewEditProfilActivity;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.utils.DateStuffs;
import mobile.a3tech.com.a3tech.utils.SphericalUtil;

/**
 * Created by Suleiman on 03/02/17.
 */

public class SimpleAdapterMission extends RecyclerView.Adapter<SimpleAdapterMission.SimpleItemVH> {

    //  Data
    private List<Mission> listeObjects = new ArrayList<>();

    private Context context;
    private A3techHomeActivity parentActivity;

    public SimpleAdapterMission(Context context) {
        this.context = context;
    }

    public SimpleAdapterMission(Context context, List objectMenu, A3techHomeActivity parent) {
        this.context = context;
        listeObjects = objectMenu;
        parentActivity = parent;
    }

    public void addMissionb(Mission missionV) {
        this.listeObjects.add(missionV);
        this.notifyDataSetChanged();
    }


    @Override
    public SimpleItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a3tech_item_mission, parent, false);

        return new SimpleItemVH(v);
    }

    @Override
    public void onBindViewHolder(SimpleItemVH holder, int position) {
        final Mission missionTmp = listeObjects.get(position);
        if (missionTmp == null) return;


        Date dateIntervention = DateStuffs.stringToDate(missionTmp.getDateCreation(), DateStuffs.SIMPLE_DATE_FORMAT);
        if (dateIntervention != null) {
            String dateInterventionAlphaSimple = DateStuffs.dateToString(DateStuffs.SIMPLE_DATE_FORMAT, dateIntervention);
            String timeIntervention = DateStuffs.dateToString(DateStuffs.HOURS_MINUTES_FORMAT, dateIntervention);
            holder.dateMission.setText(context.getText(R.string.intervention_prevue_le) + " " + dateInterventionAlphaSimple + " " + context.getString(R.string.a_date_time) + " " + timeIntervention);

        }
        else
            holder.dateMission.setVisibility(View.GONE);

        if(missionTmp.getTitre() != null){
            holder.titreMission.setText(missionTmp.getTitre());

        }else
            holder.titreMission.setVisibility(View.GONE);

        if(missionTmp.getAdresse() != null){
            holder.adresse.setText(missionTmp.getAdresse());

        }else
            holder.adresse.setVisibility(View.GONE);

        if(missionTmp.getTechnicien() != null){
            String nameTech = "";
            String nameAbrTech = missionTmp.getTechnicien().getNom();
            String pnameAbrTech = missionTmp.getTechnicien().getPrenom();

            if(pnameAbrTech != null && pnameAbrTech.length() != 0){
                nameTech = nameAbrTech+" "+pnameAbrTech.substring(0,1).toUpperCase()+".";
            }
            holder.technicien.setText(context.getText(R.string.par_tech)+" "+ nameTech);
        }else
            holder.technicien.setVisibility(View.GONE);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent mainIntent = new Intent(parentActivity, A3techDisplayMissionActivity.class);
  /*              Bundle bundle = new Bundle();
                bundle.putString(A3techViewEditProfilActivity.ARG_SRC_ACTION, "MISSION");
                bundle.putString(A3techViewEditProfilActivity.ARG_USER_OBJECT, new Gson().toJson(technicien));
                bundle.putString(A3techViewEditProfilActivity.ARG_MISSION_OBJECT, new Gson().toJson(mission));
                mainIntent.putExtras(bundle);*/
                parentActivity.startActivityForResult(mainIntent, 545);
            }

        });

    }

    @Override
    public int getItemCount() {
        return listeObjects != null ? listeObjects.size() : 0;
    }


    protected static class SimpleItemVH extends RecyclerView.ViewHolder {
        LinearLayout layoutContainerInfo;
        TextView titreMission;
        TextView adresse;
        TextView dateMission;
        TextView technicien;
        RelativeLayout container;

        public SimpleItemVH(View itemView) {
            super(itemView);
            layoutContainerInfo = itemView.findViewById(R.id.container_informations_mission);
            titreMission = itemView.findViewById(R.id.title_mission);
            container = itemView.findViewById(R.id.container_item_tech);
            adresse = itemView.findViewById(R.id.adresse_alpha);
            dateMission = itemView.findViewById(R.id.date_mission);
            technicien = itemView.findViewById(R.id.technicien_selected);
        }
    }


}