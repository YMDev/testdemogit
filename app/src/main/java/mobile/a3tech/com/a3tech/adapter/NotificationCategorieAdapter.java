package mobile.a3tech.com.a3tech.adapter;

import java.text.SimpleDateFormat;
import java.util.List;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.lazyimagelist.ImageLoader;
import mobile.a3tech.com.a3tech.model.Categorie;

public class NotificationCategorieAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;

	private List<Categorie> categories;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public ImageLoader imageLoader;
	AlertDialog alertDialog;

	public NotificationCategorieAdapter(Context context,
			List<Categorie> categories) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.categories = categories;
		this.imageLoader = new ImageLoader(context.getApplicationContext());
	}

	public void setCategories(List<Categorie> categories) {
		this.categories = categories;
		this.notifyDataSetChanged();
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if ((convertView == null) || (convertView.getTag() == null)) {
			convertView = inflater.inflate(R.layout.notification_service_row,
					null);

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

		if (position < categories.size()) {
			Categorie categorie = categories.get(position);

			holder.notif_srvc_row_srvc_libelle.setText(categorie.getLibelle());
			if(categorie.isSelected())
				holder.idNotifSrvc_checkBox.setChecked(true);
			else
				holder.idNotifSrvc_checkBox.setChecked(false);
			holder.notif_srvc_row_arrow_picto.setOnClickListener(notifSrvctListener);

		}

		return convertView;

	}

	private class ViewHolder {
		ImageView notif_srvc_row_srvc_picto;
		TextView notif_srvc_row_srvc_libelle;
		CheckBox idNotifSrvc_checkBox ;
		ImageView notif_srvc_row_arrow_picto ;

	}
	
	
	private OnClickListener notifSrvctListener = new OnClickListener() {
		public void onClick(View v) {
			Handler handler = new Handler();
			handler.post(new Runnable() {
				@Override
				public void run() {
					try {
						alertDialog.dismiss();
					} catch (Exception e) {
						// TODO: handle exception
					}
					AlertDialog.Builder builder = new AlertDialog.Builder(context);

				
					View content = inflater.inflate(
							R.layout.notif_csc_dialog, null);
					
					
					

					builder.setView(content);
					alertDialog = builder.create();
					alertDialog.show();
				}
			});
		}
	};

}
