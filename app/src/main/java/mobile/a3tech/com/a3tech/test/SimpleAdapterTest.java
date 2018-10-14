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

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.activity.A3techLoginActivity;
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

    public SimpleAdapterTest(Context context) {
        this.context = context;
        preparelisteObjects();
    }

    public SimpleAdapterTest(Context context, List objectMenu) {
        this.context = context;
        listeObjects = objectMenu;
    }

    private void preparelisteObjects() {
        String[] nameArray = context.getResources().getStringArray(R.array.dessert_names);
        String[] descArray = context.getResources().getStringArray(R.array.dessert_descriptions);

        final int SIZE = nameArray.length;

        for (int i = 0; i < SIZE; i++) {
            ObjectMenu dessert = new ObjectMenu(
                    nameArray[i],
                    descArray[i],0,0
            );

            listeObjects.add(dessert);
        }
    }

    @Override
    public SimpleItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_simplevh, parent, false);

        return new SimpleItemVH(v);
    }

    @Override
    public void onBindViewHolder(SimpleItemVH holder, int position) {
        final ObjectMenu dessert = listeObjects.get(position);

        holder.txtTitle.setText(dessert.getName());
        holder.txtDesc.setText(dessert.getDescription());

        if(dessert.getId() == 4  && dessert.getType() == 2){
            holder.txtDesc.setVisibility(View.GONE);
            holder.txtTitle.setGravity(Gravity.CENTER_HORIZONTAL);
            holder.txtTitle.setTextColor(Color.RED);
            holder.next.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dessert.getId() == 4  && dessert.getType() == 2){
                    deconnexion();
                    Intent mainIntent = new Intent(context, A3techLoginActivity.class);
                    ((A3techHomeActivity) context).startActivity(mainIntent);
                    ((A3techHomeActivity) context).finish();
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
}