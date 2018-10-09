package mobile.a3tech.com.a3tech.test;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
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
import mobile.a3tech.com.a3tech.activity.A3techDisplayTechniciensListeActivity;
import mobile.a3tech.com.a3tech.activity.A3techViewEditProfilActivity;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.utils.LetterTileProvider;
import mobile.a3tech.com.a3tech.utils.SphericalUtil;
import mobile.a3tech.com.a3tech.utils.StringStuffs;

/**
 * Created by Suleiman on 03/02/17.
 */

public class SimpleAdapterTechnicien extends RecyclerView.Adapter<SimpleAdapterTechnicien.SimpleItemVH> {

    //  Data
    private List<User> listeObjects = new ArrayList<>();

    private Mission mission;
    private Context context;
    private Activity parentActivity;

    public static final int REQUEST_DISPLAY_TECH_FROM_MISSION = 545;
    public static final int REQUEST_DISPLAY_TECH_FROM_HOME = 546;
    public static final String SRC_FROM_HOME_BROWSE_TECH = "BROWSE_TECH";
    public static final String SRC_FROM_HOME_ADD_MISSION = "ADD_MISSION";
    public SimpleAdapterTechnicien(Context context) {
        this.context = context;
    }

    public SimpleAdapterTechnicien(Context context, List objectMenu, Activity parent, Mission vMission) {
        this.context = context;
        listeObjects = objectMenu;
        parentActivity = parent;
        mission = vMission;
    }


    @Override
    public SimpleItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a3tech_item_technicien, parent, false);

        return new SimpleItemVH(v);
    }

    @Override
    public void onBindViewHolder(SimpleItemVH holder, int position) {
        if(position == listeObjects.size()){
            final float scale = context.getResources().getDisplayMetrics().density;
            int pixels = (int) (70 * scale + 0.5f);
            holder.container.getLayoutParams().height = pixels;
            holder.container.setVisibility(View.INVISIBLE);
            return;
        }
        final User technicien = listeObjects.get(position);
        if (technicien == null) return;

        holder.nameTech.setText(technicien.getNom() + " " + technicien.getPrenom().substring(0, 1) + ".");
       /* String adresseFromGpsLocation = StringStuffs.getAdresseFromGpsLocation(Double.valueOf(technicien.getLatitude()),Double.valueOf(technicien.getLongitude()),context);
      */
        final LetterTileProvider tileProvider = new LetterTileProvider(context);
        final Bitmap letterTile = tileProvider.getLetterTile(technicien.getNom(), technicien.getNom(), 88, 88);

        holder.adresse.setText(technicien.getAdresse());
        holder.avatareTech.setImageBitmap(letterTile);
        holder.ratingTech.setNumStars(5);
        holder.ratingTech.setRating(Float.valueOf(technicien.getRating() + ""));
        holder.ratingNumberValue.setText(technicien.getRating());
        holder.numberOfReviews.setText("(+ " + technicien.getNbrReviews() + " avis )");
        GradientDrawable backCheckPhone = (GradientDrawable) ((RelativeLayout)holder.checkPhone.getParent()).getBackground();
        backCheckPhone.setColorFilter(context.getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
        Double distance = SphericalUtil.computeDistanceBetween(new LatLng(Double.valueOf(technicien.getLatitude()),Double.valueOf(technicien.getLongitude())), new LatLng(Double.valueOf("52.736291655910925"), Double.valueOf("-8.261718750000002")));
        holder.distanceEnKm.setText(Math.round((distance/1000) * 100.0) / 100.0+ " Km de distance");
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(parentActivity, A3techViewEditProfilActivity.class);
                Bundle bundle = new Bundle();
                if(parentActivity instanceof A3techAddMissionActivity){
                    bundle.putString(A3techViewEditProfilActivity.ARG_SRC_ACTION, SRC_FROM_HOME_ADD_MISSION);
                    bundle.putString(A3techViewEditProfilActivity.ARG_USER_OBJECT, new Gson().toJson(technicien));
                    bundle.putString(A3techViewEditProfilActivity.ARG_MISSION_OBJECT, new Gson().toJson(mission));
                    mainIntent.putExtras(bundle);
                    parentActivity.startActivityForResult(mainIntent, REQUEST_DISPLAY_TECH_FROM_MISSION);
                }else if(parentActivity instanceof A3techDisplayTechniciensListeActivity){
                    bundle.putString(A3techViewEditProfilActivity.ARG_SRC_ACTION, SRC_FROM_HOME_BROWSE_TECH);
                    bundle.putString(A3techViewEditProfilActivity.ARG_USER_OBJECT, new Gson().toJson(technicien));
                    bundle.putString(A3techViewEditProfilActivity.ARG_MISSION_OBJECT, new Gson().toJson(mission));
                    mainIntent.putExtras(bundle);
                    parentActivity.startActivityForResult(mainIntent, REQUEST_DISPLAY_TECH_FROM_HOME);
                }


            }

        });

    }

    @Override
    public int getItemCount() {
        return listeObjects != null ? listeObjects.size()+1 : 0;
    }

    protected static class SimpleItemVH extends RecyclerView.ViewHolder {
        TextView nameTech, distanceEnKm;
        LinearLayout layoutContainerInfo;
        ImageView avatareTech;
        RatingBar ratingTech;
        TextView ratingNumberValue;
        TextView numberOfReviews;
        ImageView disponibilite, checkPhone,checkMail;
        TextView adresse;
        RelativeLayout container;

        public SimpleItemVH(View itemView) {
            super(itemView);

            nameTech = (TextView) itemView.findViewById(R.id.name_tech);
            distanceEnKm = (TextView) itemView.findViewById(R.id.distance);
            layoutContainerInfo = itemView.findViewById(R.id.container_informations_user);
            avatareTech = itemView.findViewById(R.id.avatare_technicien);
            ratingTech = itemView.findViewById(R.id.rating_tech);
            ratingNumberValue = itemView.findViewById(R.id.rating_nbr);
            numberOfReviews = itemView.findViewById(R.id.rating_nbr_text);
            container = itemView.findViewById(R.id.container_item_tech);
            disponibilite = itemView.findViewById(R.id.disponibilite);
            checkMail = itemView.findViewById(R.id.check_mail);
            checkPhone = itemView.findViewById(R.id.check_phone);

            adresse = itemView.findViewById(R.id.adresse_alpha);
        }
    }
}