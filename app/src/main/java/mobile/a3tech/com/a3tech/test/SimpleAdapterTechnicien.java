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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techAddMissionActivity;
import mobile.a3tech.com.a3tech.activity.A3techViewEditProfilActivity;
import mobile.a3tech.com.a3tech.model.User;

/**
 * Created by Suleiman on 03/02/17.
 */

public class SimpleAdapterTechnicien extends RecyclerView.Adapter<SimpleAdapterTechnicien.SimpleItemVH> {

    //  Data
    private List<User> listeObjects = new ArrayList<>();

    private Context context;
    private A3techAddMissionActivity parentActivity;

    public SimpleAdapterTechnicien(Context context) {
        this.context = context;
    }

    public SimpleAdapterTechnicien(Context context, List objectMenu,A3techAddMissionActivity parent) {
        this.context = context;
        listeObjects = objectMenu;
        parentActivity = parent;
    }


    @Override
    public SimpleItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a3tech_item_technicien, parent, false);

        return new SimpleItemVH(v);
    }

    @Override
    public void onBindViewHolder(SimpleItemVH holder, int position) {
        final User technicien = listeObjects.get(position);
        if(technicien == null) return;

        holder.nameTech.setText(technicien.getNom()+" "+technicien.getPrenom().substring(0,1)+".");
        holder.nbrIntervTech.setText(technicien.getNbr());
        holder.avatareTech.setImageDrawable(context.getDrawable(R.drawable.photo_login_1));
        holder.ratingTech.setNumStars(5);
        holder.ratingTech.setRating(Float.valueOf(technicien.getNbr()+""));
        holder.ratingNumberValue.setText(technicien.getNbr());
        holder.numberOfReviews.setText("(+ "+technicien.getNbr()+" avis )");
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(parentActivity, A3techViewEditProfilActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(A3techViewEditProfilActivity.ARG_USER_OBJECT, new Gson().toJson(technicien) );
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
        TextView  nameTech, nbrIntervTech;
        LinearLayout layoutContainerInfo;
        ImageView avatareTech;
        RatingBar ratingTech;
        TextView ratingNumberValue;
        TextView numberOfReviews;
RelativeLayout container;
        public SimpleItemVH(View itemView) {
            super(itemView);

            nameTech = (TextView) itemView.findViewById(R.id.name_tech);
            nbrIntervTech = (TextView) itemView.findViewById(R.id.nbr_intervention_tech);
            layoutContainerInfo = itemView.findViewById(R.id.container_informations_user);
            avatareTech = itemView.findViewById(R.id.avatare_technicien);
            ratingTech = itemView.findViewById(R.id.rating_tech);
            ratingNumberValue = itemView.findViewById(R.id.rating_nbr);
            numberOfReviews = itemView.findViewById(R.id.rating_nbr_text);
            container = itemView.findViewById(R.id.container_item_tech);
        }
    }
}