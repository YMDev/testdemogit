package mobile.a3tech.com.a3tech.test;


import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techDisplayMissionActivity;
import mobile.a3tech.com.a3tech.activity.A3techMissionListeActivity;
import mobile.a3tech.com.a3tech.model.A3techDisponibility;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.A3techMissionStatut;
import mobile.a3tech.com.a3tech.utils.DateStuffs;

/**
 * Created by Suleiman on 03/02/17.
 */

public class SimpleAdapterDisponibility extends RecyclerView.Adapter<SimpleAdapterDisponibility.SimpleItemVH> {

    //  Data
    private List<A3techDisponibility> listeObjects = new ArrayList<>();

    private Context context;

    public SimpleAdapterDisponibility(Context context) {
        this.context = context;
    }

    public SimpleAdapterDisponibility(Context context, List objectMenu) {
        this.context = context;
        listeObjects = objectMenu;
    }

    public void addMissionb(A3techDisponibility missionV) {
        this.listeObjects.add(missionV);
        this.notifyDataSetChanged();
    }


    @Override
    public SimpleItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a3tech_item_disponibilite, parent, false);

        return new SimpleItemVH(v);
    }

    @Override
    public void onBindViewHolder(final SimpleItemVH holder, int position) {
        final A3techDisponibility dispo = listeObjects.get(position);
        if (dispo == null) return;
        holder.timeFrom.setText(DateStuffs.dateToString(DateStuffs.HOURS_MINUTES_FORMAT,new Date(dispo.getTimeFrom())) + "");
        holder.timeTo.setText(DateStuffs.dateToString(DateStuffs.HOURS_MINUTES_FORMAT,new Date(dispo.getTimeTo())) + "");

        holder.rangeBar.setTickCount(25 * 4);//SMALLEST_HOUR_FRACTION = 4;
        holder.rangeBar.setThumbIndices(1,25);
        holder.rangeBar.setTickHeight(0);
        holder.rangeBar.setThumbRadius(0);
        holder.rangeBar.setConnectingLineWeight(3);
        holder.rangeBar.setBarWeight(6);
        holder.rangeBar.setBarColor(context.getResources().getColor(R.color.semi_transparent));
        // Sets the display values of the indices
    }

    @Override
    public int getItemCount() {
        return listeObjects != null ? listeObjects.size() : 0;
    }


    protected static class SimpleItemVH extends RecyclerView.ViewHolder {
        TextView timeFrom;
        TextView timeTo;
        RangeBar rangeBar;

        public SimpleItemVH(View itemView) {
            super(itemView);
            timeTo = itemView.findViewById(R.id.to_time);
            timeFrom = itemView.findViewById(R.id.from_time);
            rangeBar = itemView.findViewById(R.id.rangebar);

        }
    }


}