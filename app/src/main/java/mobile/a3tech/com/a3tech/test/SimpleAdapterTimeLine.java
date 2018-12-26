package mobile.a3tech.com.a3tech.test;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import mobile.a3tech.com.a3tech.model.A3techEvenementiMission;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.A3techNotification;
import mobile.a3tech.com.a3tech.utils.DateStuffs;
import mobile.a3tech.com.a3tech.utils.LetterTileProvider;
import mobile.a3tech.com.a3tech.view.CircleImageView;

/**
 * Created by Suleiman on 03/02/17.
 */

public class SimpleAdapterTimeLine extends RecyclerView.Adapter<SimpleAdapterTimeLine.SimpleItemVH> {


    private static final int VIEW_TYPE_TOP = 0;
    private static final int VIEW_TYPE_MIDDLE = 1;
    private static final int VIEW_TYPE_BOTTOM = 2;

    //  Data
    private List<A3techNotification> listeObjects = new ArrayList<>();

    private Context context;
    private Activity parentActivity;

    public SimpleAdapterTimeLine(Context context) {
        this.context = context;
    }

    public SimpleAdapterTimeLine(Context context, List objectMenu, Activity parent) {
        this.context = context;
        listeObjects = objectMenu;
        parentActivity = parent;
    }

    public void addEvenementMission(A3techNotification missionV) {
        if(this.listeObjects == null) this.listeObjects = new ArrayList<A3techNotification>();
        this.listeObjects.add(missionV);
        this.notifyDataSetChanged();
    }


    @Override
    public SimpleItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a3tech_time_line_mession, parent, false);

        return new SimpleItemVH(v);
    }

    @Override
    public void onBindViewHolder(SimpleItemVH holder, int position) {
        final A3techNotification evenementiMission = listeObjects.get(position);
        if (evenementiMission == null) return;
        /*switch(holder.getItemViewType()) {
            case VIEW_TYPE_TOP:
                holder.mItemLine.setBackground(context.getResources().getDrawable(R.drawable.line_bg_top));
                break;
            case VIEW_TYPE_MIDDLE:
                holder.mItemLine.setBackground(context.getResources().getDrawable(R.drawable.line_bg_middle));
                break;
            case VIEW_TYPE_BOTTOM:
                holder.mItemLine.setBackground(context.getResources().getDrawable(R.drawable.line_bg_bottom));
                break;
        }*/

         if(evenementiMission.getTitre() != null){
            holder.titreEvenement.setText(evenementiMission.getTitre());
         }

         if(evenementiMission.getDateCreation() != null){
             Date dateEvenet = evenementiMission.getDateCreation();
             holder.timeEvent.setText(DateStuffs.dateToString(DateStuffs.TIME_FORMAT, dateEvenet));
         }else{
             holder.timeEvent.setVisibility(View.GONE);
         }

         if(evenementiMission.getCommentaire() != null){
            holder.discreptionEvenement.setText(evenementiMission.getCommentaire());
         }else{
            holder.discreptionEvenement.setVisibility(View.GONE);
         }

         holder.userResponsable.setImageBitmap(new  LetterTileProvider(context).getLetterTile("BBB", "KK",140,140));

    }

    @Override
    public int getItemCount() {
        return listeObjects != null ? listeObjects.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_TOP;
        } else if (position == listeObjects.size() - 1) {
            return VIEW_TYPE_BOTTOM;
        }
        return VIEW_TYPE_MIDDLE;
    }

    protected static class SimpleItemVH extends RecyclerView.ViewHolder {
        TextView titreEvenement;
        TextView discreptionEvenement;
        TextView timeEvent;
        RelativeLayout mItemLine;
        CircleImageView userResponsable;

        public SimpleItemVH(View itemView) {
            super(itemView);
            titreEvenement = itemView.findViewById(R.id.item_title_event);
            discreptionEvenement = itemView.findViewById(R.id.item_subtitle_event);
            mItemLine = (RelativeLayout) itemView.findViewById(R.id.item_line);
            userResponsable = itemView.findViewById(R.id.user_resp);
            timeEvent = itemView.findViewById(R.id.item_time_event);
        }
    }


}