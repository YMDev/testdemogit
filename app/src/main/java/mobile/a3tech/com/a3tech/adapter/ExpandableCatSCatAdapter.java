package mobile.a3tech.com.a3tech.adapter;

import java.util.List;


import android.app.AlertDialog;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.model.SortCategorie;

public class ExpandableCatSCatAdapter extends BaseExpandableListAdapter {
    AlertDialog alertDialog;
    private Context context;
    EditText editText;
    Categorie icategorie;
    private SortCategorie sortCategorie;

   
    class CatHolder {
        TextView categorieName;

     
    }

    class ScatHolder {
        RelativeLayout idNotifSrv_relativeLayoutSthemeName;
        TextView name;

      
    }

    public ExpandableCatSCatAdapter(Context context, SortCategorie sortCategorie, AlertDialog alertDialog, EditText editText, Categorie categorie) {
        this.context = context;
        this.sortCategorie = sortCategorie;
        this.alertDialog = alertDialog;
        this.editText = editText;
        this.icategorie = categorie;
    }

    public Object getChild(int catPosition, int scatPosition) {
        return ((List) this.sortCategorie.getSsCategories().get(((Categorie) this.sortCategorie.getCategories().get(catPosition)).getIdentifiant())).get(scatPosition);
    }

    public long getChildId(int catPosition, int scatPosition) {
        return (long) scatPosition;
    }

    public int getChildrenCount(int catPosition) {
        Categorie categorie = (Categorie) this.sortCategorie.getCategories().get(catPosition);
        if (this.sortCategorie.getSsCategories().get(categorie.getIdentifiant()) == null) {
            return 0;
        }
        return ((List) this.sortCategorie.getSsCategories().get(categorie.getIdentifiant())).size();
    }

    public Object getGroup(int catPosition) {
        return this.sortCategorie.getCategories().get(catPosition);
    }

    public int getGroupCount() {
        return this.sortCategorie.getCategories().size();
    }

    public long getGroupId(int catPosition) {
        return (long) catPosition;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int catPosition, int scatPosition) {
        return true;
    }

    public View getChildView(int catPosition, int scatPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ScatHolder childHolder;
        final Categorie sousCategorie = (Categorie) getChild(catPosition, scatPosition);
       final Categorie categorie = (Categorie) getGroup(catPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.stheme_row, null);
            childHolder = new ScatHolder();
            childHolder.name = (TextView) convertView.findViewById(R.id.subcategoryName);
            childHolder.idNotifSrv_relativeLayoutSthemeName = (RelativeLayout) convertView.findViewById(R.id.idNotifSrv_relativeLayoutSthemeName);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ScatHolder) convertView.getTag();
        }
        childHolder.name.setText(Html.fromHtml(sousCategorie.getLibelle()));
        childHolder.idNotifSrv_relativeLayoutSthemeName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 ExpandableCatSCatAdapter.this.alertDialog.dismiss();
		            ExpandableCatSCatAdapter.this.icategorie.setIdentifiant(sousCategorie.getIdentifiant());
		            ExpandableCatSCatAdapter.this.icategorie.setParentId(categorie.getIdentifiant());
		            ExpandableCatSCatAdapter.this.editText.setText(Html.fromHtml(new StringBuilder(String.valueOf(categorie.getLibelle())).append(", ").append(sousCategorie.getLibelle()).toString()));

				
			}
		});
        return convertView;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        CatHolder missionHolder;
        Categorie categorie = (Categorie) getGroup(groupPosition);
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.theme_row, null);
            missionHolder = new CatHolder();
            missionHolder.categorieName = (TextView) view.findViewById(R.id.categoryName);
            view.setTag(missionHolder);
        } else {
            missionHolder = (CatHolder) view.getTag();
        }
        missionHolder.categorieName.setTypeface(null, 1);
        missionHolder.categorieName.setText(Html.fromHtml(categorie.getLibelle()));
        return view;
    }
}
