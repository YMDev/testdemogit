package mobile.a3tech.com.a3tech.adapter;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.test.SimpleAdapterCoordonnes;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTest;
import mobile.a3tech.com.a3tech.view.ExpandableTextView;

public class A3techProfileInformationAdapter extends RecyclerView.Adapter<A3techProfileInformationAdapter.A3techPIViewHolder> {
   Context context;
   List objectMenu;
   User user;
   public A3techProfileInformationAdapter(Context con,List objectMenus, User vuser){
       context = con;
       objectMenu = objectMenus;
       user = vuser;
   }

    public class A3techPIViewHolder extends RecyclerView.ViewHolder {
        private ExpandableTextView about;
        private RecyclerView recycyleCoordonnees;

        public A3techPIViewHolder(View view) {
            super(view);
            about = (ExpandableTextView) view.findViewById(R.id.detail_about_user);
            recycyleCoordonnees = view.findViewById(R.id.recycle_coordonnees);
        }
    }


    @Override
    public A3techPIViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a3tech_element_fragment_profile_information, parent, false);

        return new A3techPIViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(A3techPIViewHolder holder, int position) {
        holder.about.setText(R.string.lorem);
        holder.recycyleCoordonnees.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        SimpleAdapterCoordonnes adapterrecycyleAccountSetting = new SimpleAdapterCoordonnes(context, objectMenu, user);
        holder.recycyleCoordonnees.setAdapter(adapterrecycyleAccountSetting);
        holder.recycyleCoordonnees.getAdapter().notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

}

