package mobile.a3tech.com.a3tech.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.fragment.A3techSelectCategoryMissionFragment;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.test.SimpleAdapterCoordonnes;
import mobile.a3tech.com.a3tech.view.ExpandableTextView;

public class A3techSelectMissionCategoryAdapter extends RecyclerView.Adapter<A3techSelectMissionCategoryAdapter.A3techPIViewHolder> {
    Context context;
    List objectMenu;
    A3techSelectCategoryMissionFragment parent;

    public A3techSelectMissionCategoryAdapter(Context con, List objectMenus, A3techSelectCategoryMissionFragment vparent) {
        context = con;
        objectMenu = objectMenus;
        parent = vparent;
    }

    public class A3techPIViewHolder extends RecyclerView.ViewHolder {
        private TextView category;
        private TextView subCategory;
        private RelativeLayout container;

        public A3techPIViewHolder(View view) {
            super(view);
            category = (TextView) view.findViewById(R.id.title_category);
            subCategory = (TextView) view.findViewById(R.id.sub_title_category);
            container = (RelativeLayout) view.findViewById(R.id.container_item_category);

        }
    }


    @Override
    public A3techPIViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a3tech_item_select_category_mission, parent, false);

        return new A3techPIViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(A3techPIViewHolder holder, int position) {
        final Categorie categoryTmp = (Categorie) objectMenu.get(position);
        holder.category.setText(categoryTmp.getLibelle());
        holder.subCategory.setText(categoryTmp.getDescription());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.getmListener().actionNext(A3techSelectCategoryMissionFragment.ACTION_SELECT_CATEGORY_MISSION,categoryTmp);
            }
        });
    }

    @Override
    public int getItemCount() {
        return objectMenu.size();
    }

}

