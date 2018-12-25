package mobile.a3tech.com.a3tech.test;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.model.A3techReviewMission;
import mobile.a3tech.com.a3tech.model.Avis;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.utils.DateStuffs;
import mobile.a3tech.com.a3tech.utils.LetterTileProvider;

/**
 * Created by Suleiman on 03/02/17.
 */

public class SimpleAdapterReviews extends RecyclerView.Adapter<SimpleAdapterReviews.SimpleItemVH> {

    //  Data
    private List<A3techReviewMission> listeObjects = new ArrayList<>();

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

    public void addMissionb(A3techReviewMission missionV){
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
        final A3techReviewMission review = listeObjects.get(position);
        if (review == null) return;

        holder.reviewContent.setText(review.getCommentaire());
        holder.ratingBar.setNumStars(5);
        holder.ratingBar.setRating(Float.valueOf(review.getRating()));
        holder.dateReview.setText(DateStuffs.dateToString(DateStuffs.TIME_FORMAT, review.getDateEvaluation()));
        StringBuilder par = new StringBuilder("");
        if(review.getMission() != null && review.getMission().getClient() != null){
            par.append(review.getMission().getClient().getNom() != null ?
                    review.getMission().getClient().getNom().toUpperCase() : "");
            par.append(" ");

            par.append(review.getMission().getClient().getPrenom() != null ?
                    review.getMission().getClient().getPrenom().toUpperCase().substring(0,1)+"." : "");
        }
        final LetterTileProvider tileProvider = new LetterTileProvider(context);
        if(review.getMission() != null && review.getMission().getClient() != null){
            if(review.getMission().getClient().getId_photo_profil() != null){
                try{
                    Picasso.get().load(review.getMission().getClient().getId_photo_profil()).centerCrop().into(holder.avatare);
                }catch (Exception e){
                    e.printStackTrace();
                    final Bitmap letterTile = tileProvider.getLetterTile(review.getMission().getClient().getNom(), review.getMission().getClient().getNom(), 129, 129);
                    holder.avatare.setImageBitmap(letterTile);
                }
            }else{
                final Bitmap letterTile = tileProvider.getLetterTile(review.getMission().getClient().getNom(), review.getMission().getClient().getNom(), 129, 129);
                holder.avatare.setImageBitmap(letterTile);
            }
        }else{
            final Bitmap letterTile = tileProvider.getLetterTile("TECH", "TECH", 129, 129);
            holder.avatare.setImageBitmap(letterTile);
        }
        holder.userReview.setText(par.toString() != "" ? "par "+par:"par "+context.getResources().getString(R.string.unkown));
        holder.ratingValue.setText(review.getRating()+"");
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