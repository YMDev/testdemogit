package mobile.a3tech.com.a3tech.test;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mobile.a3tech.com.a3tech.Manifest;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.User;

/**
 * Created by Suleiman on 03/02/17.
 */

public class SimpleAdapterCoordonnes extends RecyclerView.Adapter<SimpleAdapterCoordonnes.SimpleItemVH> {

    //  Data
    private List<ObjectMenu> listeObjects = new ArrayList<>();

    private Context context;
    private User user;

    public SimpleAdapterCoordonnes(Context context) {
        this.context = context;
    }

    public SimpleAdapterCoordonnes(Context context, List objectMenu, User vUser) {
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
    public void onBindViewHolder(SimpleItemVH holder, int position) {
        final ObjectMenu objectMenu = listeObjects.get(position);

        holder.txtTitle.setText(objectMenu.getName());
        holder.txtDesc.setText(objectMenu.getDescription());
        if(objectMenu.getType() == 2){
            holder.layoutPhone.setVisibility(View.VISIBLE);
            holder.callAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) ==
                            PackageManager.PERMISSION_GRANTED)
                    {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+objectMenu.getName()));
                        context.startActivity(callIntent);
                    }

                }
            });
            holder.sendSmsAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse("smsto:"+objectMenu.getName());
                    Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                    it.putExtra("sms_body", "The SMS text");
                    context.startActivity(it);
                }
            });
        }else{
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
        ImageView callAction;
        ImageView sendSmsAction;

        public SimpleItemVH(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.item_simplevh_txttitle);
            txtDesc = (TextView) itemView.findViewById(R.id.item_simplevh_txtdescription);
            layoutPhone = itemView.findViewById(R.id.layout_actions_phone);
            callAction = itemView.findViewById(R.id.call_user);
            sendSmsAction = itemView.findViewById(R.id.send_msg);
        }
    }
}