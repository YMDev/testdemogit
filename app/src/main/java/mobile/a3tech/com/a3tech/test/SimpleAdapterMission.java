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

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techAddMissionActivity;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.activity.A3techViewEditProfilActivity;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.model.User;
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

    public void addMissionb(Mission missionV){
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

       /* holder.technicienName.setText(missionTmp.getTechnicien().getNom() + " " + missionTmp.getTechnicien().getPrenom().substring(0, 1) + ".");
       *//* String adresseFromGpsLocation = StringStuffs.getAdresseFromGpsLocation(Double.valueOf(technicien.getLatitude()),Double.valueOf(technicien.getLongitude()),context);
      */  holder.adresse.setText(missionTmp.getAdresse());
       /* holder.dateMission.setText(missionTmp.getDateCreation());
       */ holder.titreMission.setText(missionTmp.getTitre());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent mainIntent = new Intent(parentActivity, A3techViewEditProfilActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(A3techViewEditProfilActivity.ARG_SRC_ACTION, "MISSION");
                bundle.putString(A3techViewEditProfilActivity.ARG_USER_OBJECT, new Gson().toJson(technicien));
                bundle.putString(A3techViewEditProfilActivity.ARG_MISSION_OBJECT, new Gson().toJson(mission));
                mainIntent.putExtras(bundle);
                parentActivity.startActivityForResult(mainIntent, 545);*/
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
        RelativeLayout container;

        public SimpleItemVH(View itemView) {
            super(itemView);
            layoutContainerInfo = itemView.findViewById(R.id.container_informations_mission);
            titreMission = itemView.findViewById(R.id.title_mission);
            container = itemView.findViewById(R.id.container_item_tech);
            adresse = itemView.findViewById(R.id.adresse_alpha);
        }
    }
}