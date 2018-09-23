package mobile.a3tech.com.a3tech.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.Categorie;

public class NotificationServiceAdapter extends BaseAdapter {

	private ListView listView;
	private Context context;
	private LayoutInflater inflater;
	private List<Categorie> categories;

	public NotificationServiceAdapter(Context context,
			List<Categorie> categories, ListView listView) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.categories = categories;
		this.listView = listView;

	}

	public void setCategories(List<Categorie> categories) {
		this.categories = categories;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (categories != null) {
			if (categories.size() > categories.size())
				return categories.size() + 1;
			else
				return categories.size();
		}
		return 0;
	}

	@Override
	public Categorie getItem(int position) {
		if (position < categories.size())
			return categories.get(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		if (position < categories.size())
			return Long.parseLong(categories.get(position).getIdentifiant());
		else
			return 0;
	}

	private class ViewHolder {
		CheckBox idNotifSrvc_checkBox;
		ImageView notif_srvc_row_arrow_picto;
		TextView notif_srvc_row_srvc_libelle;
		ImageView notif_srvc_row_srvc_picto;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null || convertView.getTag() == null) {
			convertView = this.inflater.inflate(
					R.layout.notification_service_row, null);
			holder = new ViewHolder();
			holder.notif_srvc_row_srvc_picto = (ImageView) convertView
					.findViewById(R.id.notif_srvc_row_srvc_picto);
			holder.notif_srvc_row_srvc_libelle = (TextView) convertView
					.findViewById(R.id.notif_srvc_row_srvc_libelle);
			holder.idNotifSrvc_checkBox = (CheckBox) convertView
					.findViewById(R.id.idNotifSrvc_checkBox);
			holder.notif_srvc_row_arrow_picto = (ImageView) convertView
					.findViewById(R.id.notif_srvc_row_arrow_picto);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position < this.categories.size()) {
			final Categorie categorie = (Categorie) this.categories
					.get(position);
			try {
				holder.notif_srvc_row_srvc_picto.setImageResource(Integer
						.parseInt(categorie.getLogo()));
				holder.notif_srvc_row_arrow_picto.setVisibility(View.GONE);
			} catch (Exception e) {
				holder.notif_srvc_row_srvc_picto.setVisibility(View.GONE);
				holder.notif_srvc_row_arrow_picto.setVisibility(View.GONE);
			}
			try {
				holder.notif_srvc_row_srvc_libelle.setText(Integer
						.parseInt(categorie.getLibelle()));
			} catch (Exception e2) {
				holder.notif_srvc_row_srvc_libelle.setText(categorie
						.getLibelle());
			}
			if (categorie.isSelected()) {
				holder.idNotifSrvc_checkBox.setChecked(true);
			} else {
				holder.idNotifSrvc_checkBox.setChecked(false);
			}
			holder.idNotifSrvc_checkBox
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							categorie.setSelected(isChecked);

						}
					});
		}
		return convertView;
	}

}
