package mobile.a3tech.com.a3tech.test;


import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techDisplayMissionActivity;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.activity.A3techMissionListeActivity;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.A3techMissionStatut;
import mobile.a3tech.com.a3tech.utils.DateStuffs;

/**
 * Created by Suleiman on 03/02/17.
 */

public class SimpleAdapterMission extends RecyclerView.Adapter<SimpleAdapterMission.SimpleItemVH> {

    //  Data
    private List<A3techMission> listeObjects = new ArrayList<>();

    private Context context;
    private A3techMissionListeActivity parentActivity;

    public SimpleAdapterMission(Context context) {
        this.context = context;
    }

    public SimpleAdapterMission(Context context, List objectMenu, A3techMissionListeActivity parent) {
        this.context = context;
        listeObjects = objectMenu;
        parentActivity = parent;
    }

    public void addMissionb(A3techMission missionV) {
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
        final A3techMission missionTmp = listeObjects.get(position);
        if (missionTmp == null) return;


        if (missionTmp.getDateIntervention() != null) {
            Date dateIntervention = missionTmp.getDateIntervention();
            if (dateIntervention != null) {
                String dateInterventionAlphaSimple = DateStuffs.dateToString(DateStuffs.SIMPLE_DATE_FORMAT, dateIntervention);
                String timeIntervention = DateStuffs.dateToString(DateStuffs.HOURS_MINUTES_FORMAT, dateIntervention);
                holder.dateMission.setText(context.getText(R.string.intervention_prevue_le) + " " + dateInterventionAlphaSimple + " " + context.getString(R.string.a_date_time) + " " + timeIntervention);
                holder.dateMission.setVisibility(View.VISIBLE);
            } else {
                holder.dateMission.setVisibility(View.GONE);
            }
        } else
            holder.dateMission.setVisibility(View.GONE);

        if (StringUtils.isNoneBlank(missionTmp.getTitre())) {
            holder.titreMission.setText(missionTmp.getTitre());

        } else
            holder.titreMission.setVisibility(View.GONE);

        if (StringUtils.isNoneBlank(missionTmp.getAdresse())) {
            holder.adresse.setText(missionTmp.getAdresse());

        } else
            holder.adresse.setVisibility(View.GONE);

        if (missionTmp.getStatut() != null) {
            holder.statutMission.setText(missionTmp.getStatut().getDiscreptionEnum());
            if(missionTmp.getStatut().getId() == A3techMissionStatut.VALIDEE.getId()){
                GradientDrawable backStatutMission = (GradientDrawable) holder.containerMissionStatut.getBackground();
                backStatutMission.setColorFilter(context.getResources().getColor(R.color.green_dark), PorterDuff.Mode.SRC_ATOP);
                holder.containerMissionStatut.setBackground(backStatutMission);
            }else if(missionTmp.getStatut().getId() == A3techMissionStatut.ANNULEE.getId()){
                GradientDrawable backStatutMission = (GradientDrawable) holder.containerMissionStatut.getBackground();
                backStatutMission.setColorFilter(context.getResources().getColor(R.color.red_dark), PorterDuff.Mode.SRC_ATOP);
                holder.containerMissionStatut.setBackground(backStatutMission);
            }

        } else
            holder.statutMission.setText(A3techMissionStatut.CREE.getDiscreptionEnum());



        if (missionTmp.getTechnicien() != null) {
            String nameTech = "";
            String nameAbrTech = missionTmp.getTechnicien().getNom();
            String pnameAbrTech = missionTmp.getTechnicien().getPrenom();

            if (pnameAbrTech != null && pnameAbrTech.length() != 0) {
                nameTech = nameAbrTech + " " + pnameAbrTech.substring(0, 1).toUpperCase() + ".";
            }
            holder.technicien.setText(context.getText(R.string.par_tech) + " " + nameTech);
        } else
            holder.technicien.setVisibility(View.GONE);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(parentActivity, A3techDisplayMissionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(A3techDisplayMissionActivity.TAG_MISSION_SELECTED_FROM_HOME_ACTIVITY, new Gson().toJson(missionTmp));
                mainIntent.putExtras(bundle);
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
        TextView statutMission;
        RelativeLayout container, containerMissionStatut;

        public SimpleItemVH(View itemView) {
            super(itemView);
            layoutContainerInfo = itemView.findViewById(R.id.container_informations_mission);
            titreMission = itemView.findViewById(R.id.title_mission);
            container = itemView.findViewById(R.id.container_item_tech);
            adresse = itemView.findViewById(R.id.adresse_alpha);
            dateMission = itemView.findViewById(R.id.date_mission);
            technicien = itemView.findViewById(R.id.technicien_selected);
            statutMission = itemView.findViewById(R.id.statut_mission);
            containerMissionStatut = itemView.findViewById(R.id.container_mission_statut);
        }
    }


}