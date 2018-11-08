package mobile.a3tech.com.a3tech.test;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techAddMissionActivity;
import mobile.a3tech.com.a3tech.activity.A3techDisplayTechniciensListeActivity;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.activity.A3techSearchMissionsResultsActivity;
import mobile.a3tech.com.a3tech.activity.A3techViewEditProfilActivity;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.service.GPSTracker;
import mobile.a3tech.com.a3tech.utils.LetterTileProvider;
import mobile.a3tech.com.a3tech.utils.PermissionsStuffs;
import mobile.a3tech.com.a3tech.utils.SphericalUtil;
import mobile.a3tech.com.a3tech.view.A3techDistanceBarView;

/**
 * Created by Suleiman on 03/02/17.
 */

public class SimpleAdapterTechnicien extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    //  Data
    private List<A3techUser> listeObjects = new ArrayList<>();

    private A3techMission mission;
    private Context context;
    private Activity parentActivity;

    public static final int REQUEST_DISPLAY_TECH_FROM_MISSION = 545;
    public static final int REQUEST_DISPLAY_TECH_FROM_HOME = 546;
    public static final String SRC_FROM_HOME_BROWSE_TECH = "BROWSE_TECH";
    public static final String SRC_FROM_HOME_ADD_MISSION = "ADD_MISSION";
    private Double latitude;
    private Double longitude;


    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public SimpleAdapterTechnicien(Context context) {
        this.context = context;
    }

    public SimpleAdapterTechnicien(Context context, List objectMenu, Activity parent, A3techMission vMission, RecyclerView recyclerView) {
        this.context = context;
        listeObjects = objectMenu;
        parentActivity = parent;
        mission = vMission;
        //get user location
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh = null;
        if (viewType == VIEW_PROG) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.a3tech_progress_loading_more, parent, false);

            vh = new ProgressViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.a3tech_item_technicien, parent, false);
            vh = new SimpleItemVH(v);
        }
        return vh;
    }


    @Override
    public int getItemViewType(int position) {
        if (listeObjects.size() > position) {
            return listeObjects.get(position) != null ? VIEW_ITEM : VIEW_PROG;
        } else {
            return -1;
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder == null) return;
        if (holder instanceof SimpleItemVH) {
            if (position == listeObjects.size()) {
                final float scale = context.getResources().getDisplayMetrics().density;
                int pixels = (int) (70 * scale + 0.5f);
                ((SimpleItemVH) holder).container.getLayoutParams().height = pixels;
                ((SimpleItemVH) holder).container.setVisibility(View.INVISIBLE);
                return;
            }
            final A3techUser technicien = listeObjects.get(position);
            if (technicien == null) return;

            ((SimpleItemVH) holder).nameTech.setText(technicien.getNom() + " " + technicien.getPrenom().substring(0, 1) + ".");
            /* String adresseFromGpsLocation = StringStuffs.getAdresseFromGpsLocation(Double.valueOf(technicien.getLatitude()),Double.valueOf(technicien.getLongitude()),context);
             */
            final LetterTileProvider tileProvider = new LetterTileProvider(context);
            final Bitmap letterTile = tileProvider.getLetterTile(technicien.getNom(), technicien.getNom(), 129, 129);

            ((SimpleItemVH) holder).adresse.setText(technicien.getAdresse());
            ((SimpleItemVH) holder).avatareTech.setImageBitmap(letterTile);
            ((SimpleItemVH) holder).ratingTech.setNumStars(5);
            ((SimpleItemVH) holder).ratingTech.setRating(Float.valueOf(technicien.getRating() + ""));
            ((SimpleItemVH) holder).ratingNumberValue.setText(technicien.getRating() + "");
            ((SimpleItemVH) holder).numberOfReviews.setText("(+ " + technicien.getNbrReview() + " avis )");
            GradientDrawable backCheckPhone = (GradientDrawable) ((RelativeLayout) ((SimpleItemVH) holder).checkPhone.getParent()).getBackground();
            backCheckPhone.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            updateDistance(technicien, ((SimpleItemVH) holder));
            ((SimpleItemVH) holder).container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mainIntent = new Intent(parentActivity, A3techViewEditProfilActivity.class);
                    Bundle bundle = new Bundle();
                    if (parentActivity instanceof A3techAddMissionActivity) {
                        bundle.putString(A3techViewEditProfilActivity.ARG_SRC_ACTION, SRC_FROM_HOME_ADD_MISSION);
                        bundle.putString(A3techViewEditProfilActivity.ARG_USER_OBJECT, new Gson().toJson(technicien));
                        bundle.putString(A3techViewEditProfilActivity.ARG_MISSION_OBJECT, new Gson().toJson(mission));
                        mainIntent.putExtras(bundle);
                        parentActivity.startActivityForResult(mainIntent, REQUEST_DISPLAY_TECH_FROM_MISSION);
                    } else if (parentActivity instanceof A3techDisplayTechniciensListeActivity) {
                        bundle.putString(A3techViewEditProfilActivity.ARG_SRC_ACTION, SRC_FROM_HOME_BROWSE_TECH);
                        bundle.putString(A3techViewEditProfilActivity.ARG_USER_OBJECT, new Gson().toJson(technicien));
                        bundle.putString(A3techViewEditProfilActivity.ARG_MISSION_OBJECT, new Gson().toJson(mission));
                        mainIntent.putExtras(bundle);
                        parentActivity.startActivityForResult(mainIntent, REQUEST_DISPLAY_TECH_FROM_HOME);
                    } else if (parentActivity instanceof A3techSearchMissionsResultsActivity) {
                        bundle.putString(A3techViewEditProfilActivity.ARG_SRC_ACTION, SRC_FROM_HOME_BROWSE_TECH);
                        bundle.putString(A3techViewEditProfilActivity.ARG_USER_OBJECT, new Gson().toJson(technicien));
                        bundle.putString(A3techViewEditProfilActivity.ARG_MISSION_OBJECT, new Gson().toJson(mission));
                        mainIntent.putExtras(bundle);
                        parentActivity.startActivityForResult(mainIntent, REQUEST_DISPLAY_TECH_FROM_HOME);
                    }


                }

            });
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }


    }


    public void addListe(List users) {
        if (listeObjects == null) listeObjects = new ArrayList<>();
        listeObjects.addAll(users);

    }


    public void addOject(A3techUser usser) {
        listeObjects.add(usser);
        notifyItemInserted(listeObjects.size() - 1);
    }

    public List getListObject() {
        if (listeObjects == null) listeObjects = new ArrayList<>();
        return listeObjects;
    }

    @Override
    public int getItemCount() {
        return listeObjects != null ? listeObjects.size() + 1 : 0;
    }

    protected static class SimpleItemVH extends RecyclerView.ViewHolder {
        TextView nameTech;
        LinearLayout layoutContainerInfo;
        ImageView avatareTech;
        RatingBar ratingTech;
        TextView ratingNumberValue;
        TextView numberOfReviews;
        ImageView disponibilite, checkPhone, checkMail;
        TextView adresse, distance_value;
        RelativeLayout container;


        private ProgressBar distance;


        public SimpleItemVH(View itemView) {
            super(itemView);

            nameTech = (TextView) itemView.findViewById(R.id.name_tech);
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
            distance = itemView.findViewById(R.id.distance);
            distance_value = itemView.findViewById(R.id.distance_value);
        }
    }

    public void updateDistance(final A3techUser technicien, final SimpleItemVH holder) {
        if (latitude == 0d && longitude == 0d) {
            holder.distance_value.setText("N/A" + " KM");
            return;
        }
        Double distance = SphericalUtil.computeDistanceBetween(new LatLng(Double.valueOf(technicien.getLatitude()), Double.valueOf(technicien.getLongitude())), new LatLng(latitude, longitude));
        final Double distanceUser = Math.round((distance / 1000) * 100.0) / 100.0;
        final Double distanceMaxParametree = 100d;
        final Double prcentrageColored = distanceUser * 100 / distanceMaxParametree;
        progressBarchangeSmouthly(Math.round(Double.valueOf(prcentrageColored).floatValue()), holder.distance);
        holder.distance_value.setText(distanceUser + " KM");

    }

    private void progressBarchangeSmouthly(int progress, ProgressBar progressBar) {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            // will update the "progress" propriety of seekbar until it reaches progress
            ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", progress);
            animation.setDuration(300); // 0.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        } else
            progressBar.setProgress(progress); // no animation on Gingerbread or lower
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar_load_more);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }


}