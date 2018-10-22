package mobile.a3tech.com.a3tech.test;


import android.content.Context;
import android.content.Intent;
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
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.utils.DateStuffs;
import mobile.a3tech.com.a3tech.view.A3techTimeLineMissionEventItem;

/**
 * Created by Suleiman on 03/02/17.
 */

public class SimpleAdapterNotifications extends RecyclerView.Adapter<SimpleAdapterNotifications.SimpleItemVH> {

    //  Data
    private List<A3techMission> listeObjects = new ArrayList<>();

    private Context context;
    private A3techHomeActivity parentActivity;

    public SimpleAdapterNotifications(Context context) {
        this.context = context;
    }

    public SimpleAdapterNotifications(Context context, List objectMenu, A3techHomeActivity parent) {
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
                .inflate(R.layout.a3tech_item_notificiation, parent, false);

        return new SimpleItemVH(v);
    }

    @Override
    public void onBindViewHolder(SimpleItemVH holder, int position) {
        final A3techMission missionTmp = listeObjects.get(position);
        if (missionTmp == null) return;
        holder.itemNotification.setExpandableLayoutInRecycleView(Boolean.TRUE);
         holder.itemNotification.initDataMission(missionTmp);
    }

    @Override
    public int getItemCount() {
        return listeObjects != null ? listeObjects.size() : 0;
    }


    protected static class SimpleItemVH extends RecyclerView.ViewHolder {
        A3techTimeLineMissionEventItem itemNotification;

        public SimpleItemVH(View itemView) {
            super(itemView);
            itemNotification = itemView.findViewById(R.id.item_notifcation);
        }
    }


}