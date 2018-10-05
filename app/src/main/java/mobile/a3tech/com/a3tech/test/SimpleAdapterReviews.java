package mobile.a3tech.com.a3tech.test;


import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.model.Avis;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.utils.DateStuffs;

/**
 * Created by Suleiman on 03/02/17.
 */

public class SimpleAdapterReviews extends RecyclerView.Adapter<SimpleAdapterReviews.SimpleItemVH> {

    //  Data
    private List<Avis> listeObjects = new ArrayList<>();

    private Context context;
    private Activity parentActivity;

    public SimpleAdapterReviews(Context context) {
        this.context = context;
    }

    public SimpleAdapterReviews(Context context, List objectMenu, Activity parent) {
        this.context = context;
        listeObjects = objectMenu;
        parentActivity = parent;
    }

    public void addMissionb(Avis missionV){
        this.listeObjects.add(missionV);
        this.notifyDataSetChanged();
    }


    @Override
    public SimpleItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a3tech_item_review, parent, false);

        return new SimpleItemVH(v);
    }

    @Override
    public void onBindViewHolder(SimpleItemVH holder, int position) {
        final Avis review = listeObjects.get(position);
        if (review == null) return;

        holder.reviewContent.setText(review.getAvantage());
        holder.ratingBar.setNumStars(5);
        holder.ratingBar.setRating(Float.valueOf(review.getNote()));
        holder.dateReview.setText(DateStuffs.dateToString(DateStuffs.TIME_FORMAT, new Date()));
        holder.userReview.setText("Par Mouad BOIJKRA");
        holder.ratingValue.setText(review.getNote());
        holder.avatare.setImageDrawable(context.getResources().getDrawable(R.drawable.image11));
    }

    @Override
    public int getItemCount() {
        return listeObjects != null ? listeObjects.size() : 0;
    }

    protected static class SimpleItemVH extends RecyclerView.ViewHolder {
        LinearLayout layoutContainerInfo;
        TextView ratingValue, reviewContent, dateReview, userReview;
        ImageView avatare;

        RatingBar ratingBar;
        public SimpleItemVH(View itemView) {
            super(itemView);
            layoutContainerInfo = itemView.findViewById(R.id.container_informations_mission);
            ratingValue = itemView.findViewById(R.id.rating_nbr);
            ratingBar = itemView.findViewById(R.id.rating_tech);
            reviewContent = itemView.findViewById(R.id.review_content);
            avatare = itemView.findViewById(R.id.avatare_review_actor);
            dateReview = itemView.findViewById(R.id.date_review);
            userReview = itemView.findViewById(R.id.user_review_name);
        }
    }
}