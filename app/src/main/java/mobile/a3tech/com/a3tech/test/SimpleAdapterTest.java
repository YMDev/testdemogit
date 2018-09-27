package mobile.a3tech.com.a3tech.test;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mobile.a3tech.com.a3tech.R;

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
        ObjectMenu dessert = listeObjects.get(position);

        holder.txtTitle.setText(dessert.getName());
        holder.txtDesc.setText(dessert.getDescription());
    }

    @Override
    public int getItemCount() {
        return listeObjects != null ? listeObjects.size() : 0;
    }

    protected static class SimpleItemVH extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDesc;

        public SimpleItemVH(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.item_simplevh_txttitle);
            txtDesc = (TextView) itemView.findViewById(R.id.item_simplevh_txtdescription);
        }
    }
}