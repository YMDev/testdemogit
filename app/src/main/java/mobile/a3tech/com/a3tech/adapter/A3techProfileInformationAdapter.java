package mobile.a3tech.com.a3tech.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import eltos.simpledialogfragment.form.Input;
import eltos.simpledialogfragment.form.SimpleFormDialog;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techViewEditProfilActivity;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.test.SimpleAdapterCoordonnes;
import mobile.a3tech.com.a3tech.view.ExpandableTextView;

public class A3techProfileInformationAdapter extends RecyclerView.Adapter<A3techProfileInformationAdapter.A3techPIViewHolder> {
    Context context;
    List objectMenu;
    A3techUser user;
    private Boolean editMode = Boolean.FALSE;

    public A3techProfileInformationAdapter(Context con, List objectMenus, A3techUser vuser) {
        context = con;
        objectMenu = objectMenus;
        user = vuser;
        if (context instanceof onEditActionsLinster) {
            mEditListener = (onEditActionsLinster) context;
        }
    }

    public class A3techPIViewHolder extends RecyclerView.ViewHolder {
        private ExpandableTextView about;
        private RecyclerView recycyleCoordonnees;
        private ImageView editAbout, editCoordonnees;


        public A3techPIViewHolder(View view) {
            super(view);
            about = (ExpandableTextView) view.findViewById(R.id.detail_about_user);
            /*recycyleCoordonnees = view.findViewById(R.id.recycle_coordonnees);*/
            editAbout = view.findViewById(R.id.edit_action_about);
            editCoordonnees = view.findViewById(R.id.edit_action_coordonnees);
        }
    }


    @Override
    public A3techPIViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a3tech_element_fragment_profile_information, parent, false);

        return new A3techPIViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final A3techPIViewHolder holder, int position) {
        holder.about.setText(R.string.lorem);
        holder.recycyleCoordonnees.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        SimpleAdapterCoordonnes adapterrecycyleAccountSetting = new SimpleAdapterCoordonnes(context, objectMenu, user);
        holder.recycyleCoordonnees.setAdapter(adapterrecycyleAccountSetting);
        holder.recycyleCoordonnees.getAdapter().notifyDataSetChanged();
        holder.editCoordonnees.setVisibility(View.GONE);
        //holder.editAbout.setVisibility(View.GONE);
        // hundle edit action of about
        if (editMode) {
            holder.editAbout.setVisibility(View.VISIBLE);
            holder.editAbout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mEditListener != null)
                        mEditListener.editAboutActionEnabled(holder.about.getText().toString());
                }
            });
            holder.editCoordonnees.setVisibility(View.VISIBLE);
            holder.editCoordonnees.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mEditListener != null)
                        mEditListener.editAboutActionEnabled(holder.about.getText().toString());
                }
            });
        } else {
            holder.editAbout.setVisibility(View.GONE);
            holder.editCoordonnees.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }


    private onEditActionsLinster mEditListener;

    public interface onEditActionsLinster {
        void enableEditMode(Boolean enable);

        void editAboutActionEnabled(String oldValtype);
    }

    public onEditActionsLinster getmEditListener() {
        return mEditListener;
    }

    public void setmEditListener(onEditActionsLinster mEditListener) {
        this.mEditListener = mEditListener;
    }

    public void setEditMode(Boolean editMode) {
        this.editMode = editMode;
    }
}

