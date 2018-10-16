package mobile.a3tech.com.a3tech.test;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.util.CollectionUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techViewEditProfilActivity;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.PermissionsStuffs;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;

/**
 * Created by Suleiman on 03/02/17.
 */

public class SimpleAdapterCoordonnes extends RecyclerView.Adapter<SimpleAdapterCoordonnes.SimpleItemVH> {

    //  Data
    private List<ObjectMenu> listeObjects = new ArrayList<>();

    private Context context;
    private A3techUser user;

    public SimpleAdapterCoordonnes(Context context) {
        this.context = context;
    }

    public SimpleAdapterCoordonnes(Context context, List objectMenu, A3techUser vUser) {
        this.context = context;
        listeObjects = objectMenu;
        user = vUser;
    }


    @Override
    public SimpleItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a3tech_item_coordonnees_account, parent, false);

        return new SimpleItemVH(v);
    }

    @Override
    public void onBindViewHolder(final SimpleItemVH holder, int position) {
        final ObjectMenu objectMenu = listeObjects.get(position);


        Boolean displayPhone = Boolean.FALSE;
        if (objectMenu.getType() == 2) {
            UserManager.getInstance().isTechEnabledForClient(PreferencesValuesUtils.getConnectedUser((A3techViewEditProfilActivity) context).getId(), user.getId(),new DataLoadCallback() {
                @Override
                public void dataLoaded(Object data, int method, int typeOperation) {
                    Boolean isEnabled = (Boolean) data;
                    if (isEnabled != null && isEnabled) {
                        holder.container.setVisibility(View.VISIBLE);
                        holder.txtTitle.setText(objectMenu.getName());
                        holder.txtDesc.setText(objectMenu.getDescription());
                        holder.layoutPhone.setVisibility(View.VISIBLE);
                        holder.callAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) ==
                                        PackageManager.PERMISSION_GRANTED) {
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + objectMenu.getName()));
                                    context.startActivity(callIntent);
                                } else {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        ((A3techViewEditProfilActivity) context).requestPermissions(PermissionsStuffs.CALL_PHONE, PermissionsStuffs.ICALL_PHONE_REQUEST);
                                    }
                                }
                            }
                        });
                        holder.sendSmsAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Uri uri = Uri.parse("smsto:" + objectMenu.getName());
                                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                                it.putExtra("sms_body", "The SMS text");
                                context.startActivity(it);
                            }
                        });
                    } else {
                        holder.container.setVisibility(View.GONE);
                        holder.layoutPhone.setVisibility(View.GONE);
                    }
                }

                @Override
                public void dataLoadingError(int errorCode) {

                }
            });
        } else {
            holder.txtTitle.setText(objectMenu.getName());
            holder.txtDesc.setText(objectMenu.getDescription());
            holder.layoutPhone.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listeObjects != null ? listeObjects.size() : 0;
    }

    protected static class SimpleItemVH extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDesc;
        LinearLayout layoutPhone;
        RelativeLayout container;
        ImageView callAction;
        ImageView sendSmsAction;

        public SimpleItemVH(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.item_simplevh_txttitle);
            txtDesc = (TextView) itemView.findViewById(R.id.item_simplevh_txtdescription);
            layoutPhone = itemView.findViewById(R.id.layout_actions_phone);
            callAction = itemView.findViewById(R.id.call_user);
            sendSmsAction = itemView.findViewById(R.id.send_msg);
            container = itemView.findViewById(R.id.container_item_info);
        }
    }
}