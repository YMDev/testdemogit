package mobile.a3tech.com.a3tech.test;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import eltos.simpledialogfragment.SimpleDialog;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techAccountActivity;
import mobile.a3tech.com.a3tech.activity.A3techAddMissionActivity;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.activity.A3techLoginActivity;
import mobile.a3tech.com.a3tech.activity.A3techTechnicienAvailabilityActivity;
import mobile.a3tech.com.a3tech.activity.A3techViewEditProfilActivity;
import mobile.a3tech.com.a3tech.fragment.A3techMissionsHomeFragment;
import mobile.a3tech.com.a3tech.images.Image;

/**
 * Created by Suleiman on 03/02/17.
 */

public class SimpleAdapterTest extends RecyclerView.Adapter<SimpleAdapterTest.SimpleItemVH> {

    //  Data
    private List<ObjectMenu> listeObjects = new ArrayList<>();

    private Context context;
    private OnDeconnexion mDecListener;


    public void setmDecListener(OnDeconnexion mDecListener) {
        this.mDecListener = mDecListener;
    }

    public SimpleAdapterTest(Context context) {
        this.context = context;
    }

    public SimpleAdapterTest(Context context, List objectMenu) {
        this.context = context;
        listeObjects = objectMenu;
    }


    @Override
    public SimpleItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_simplevh, parent, false);

        return new SimpleItemVH(v);
    }

    @Override
    public void onBindViewHolder(SimpleItemVH holder, int position) {
        final ObjectMenu menuItem = listeObjects.get(position);

        holder.txtTitle.setText(menuItem.getName());
        holder.txtDesc.setText(menuItem.getDescription());

        if (menuItem.getId() == 4 && menuItem.getType() == 2) {
            holder.txtDesc.setVisibility(View.GONE);
            holder.txtTitle.setGravity(Gravity.CENTER_HORIZONTAL);
            holder.txtTitle.setTextColor(Color.RED);
            holder.next.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menuItem.getCode() != null && menuItem.getCode().equals(ObjectMenu.CODE_DECONNEXION)) {
                    SimpleDialog.build().pos(R.string.deconnexion).title(R.string.deconnexion_label).neg(R.string.cancel).msg(R.string.msg_deconnexion).theme(R.style.SimpleDialogThemeProfile).show((A3techAccountActivity) context, "DEC");
                } else if (menuItem.getCode() != null && menuItem.getCode().equals(ObjectMenu.CODE_DISPO)) {
                    Intent mainIntent = new Intent(context, A3techTechnicienAvailabilityActivity.class);
                    context.startActivity(mainIntent);
                } else if (menuItem.getCode() != null && menuItem.getCode().equals(ObjectMenu.CODE_DEMANDER_MISSION)) {
                    Intent mainIntent = new Intent(context, A3techAddMissionActivity.class);
                    context.startActivity(mainIntent);
                } else if (menuItem.getCode() != null && menuItem.getCode().equals(ObjectMenu.CODE_RECOMMANDER_TECH)) {
                   /* Intent mainIntent = new Intent(context, A3techTechnicienAvailabilityActivity.class);
                    context.startActivity(mainIntent);*/
                } else if (menuItem.getCode() != null && menuItem.getCode().equals(ObjectMenu.CODE_DEVENIR_TECH)) {
                   /* Intent mainIntent = new Intent(context, A3techTechnicienAvailabilityActivity.class);
                    context.startActivity(mainIntent);*/
                }
            }
        });
    }

    public void deconnexion() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("MyCredentials", "");
        editor.putString("facebookId", "");
        editor.putString("conMode", "");
        editor.putString("ApplicationLanguage", "");
        editor.putString("identifiant", "");

     /*   GCMRegistrar.checkDevice(this.getActivity());
        GCMRegistrar.checkManifest(this.getActivity());
        GCMRegistrar.unregister(this.getActivity());*/
        editor.commit();
    }

    @Override
    public int getItemCount() {
        return listeObjects != null ? listeObjects.size() : 0;
    }

    protected static class SimpleItemVH extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDesc;
        ImageView next;

        public SimpleItemVH(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.item_simplevh_txttitle);
            txtDesc = (TextView) itemView.findViewById(R.id.item_simplevh_txtdescription);
            next = itemView.findViewById(R.id.next_flag);
        }
    }

    public interface OnDeconnexion {
        void deconnexion();
    }
}