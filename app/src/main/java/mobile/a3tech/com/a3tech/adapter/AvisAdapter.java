package mobile.a3tech.com.a3tech.adapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.AvisAssociationFragment;
import mobile.a3tech.com.a3tech.model.Avis;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class AvisAdapter extends BaseAdapter {

	Fragment fragment;
	private LayoutInflater inflater;
	private ListView listView;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sdfHMS = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
	private List<Avis> aviss;
	private Context context;
	Dialog countDialog;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	AlertDialog alertDialog;

	public AvisAdapter(Fragment fragment, List<Avis> aviss, ListView listView) {
		this.context = fragment.getActivity();
		this.inflater = LayoutInflater.from(this.context);
		this.aviss = aviss;
		this.listView = listView;
		this.fragment = fragment;
	}

	private View.OnClickListener alertAnnulerSuppression = new View.OnClickListener() {
		public void onClick(View paramAnonymousView) {
			alertDialog.dismiss();
		}
	};

	private View.OnClickListener annulerListener = new View.OnClickListener() {
		public void onClick(View paramAnonymousView) {
			countDialog.dismiss();
		}
	};

	private View.OnClickListener deleteAvisListener = new View.OnClickListener() {
		public void onClick(View v) {
			final int position = listView.getPositionForView((View) v
					.getParent());
			Avis avis = (Avis) aviss.get(position);
			confirmationSuppression(avis.getIdentifiant(), position);
		}
	};

	@Override
	public int getCount() {
		if (aviss != null) {
			if (aviss.size() > aviss.size())
				return aviss.size() + 1;
			else
				return aviss.size();
		}
		return 0;
	}

	@Override
	public Avis getItem(int position) {
		if (position < this.aviss.size()) {
			return (Avis) aviss.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (position < aviss.size())
			return Long.parseLong(aviss.get(position).getIdentifiant());
		else
			return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if ((convertView == null) || (convertView.getTag() == null)) {
			convertView = this.inflater.inflate(R.layout.avis_row, null);
			holder = new ViewHolder();
			holder.avis_row_date_publication = (TextView) convertView
					.findViewById(R.id.avis_row_date_publication);
			holder.avis_row_avantage = (TextView) convertView
					.findViewById(R.id.avis_row_avantage);
			holder.idDetailAssociation_ratingBar = (RatingBar) convertView
					.findViewById(R.id.idDetailAssociation_ratingBar);
			holder.avis_row_inconvenient = (TextView) convertView
					.findViewById(R.id.avis_row_inconvenient);
			holder.idDetailAssociation_ib_cancel_avis = (ImageButton) convertView
					.findViewById(R.id.idDetailAssociation_ib_cancel_avis);
			holder.avis_row_statut = (TextView) convertView
					.findViewById(R.id.avis_row_statut);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position < aviss.size()) {
			Avis avis = aviss.get(position);
			String dateCreation = "";
			try {
				dateCreation = sdf.format(dateFormat.parse(avis
						.getDate_creation()));

			} catch (Exception e) {
				e.printStackTrace();
			}
			holder.avis_row_date_publication.setText(dateCreation);
			holder.avis_row_avantage.setText(avis.getAvantage());
			holder.avis_row_inconvenient.setText(avis.getInconvenient());
			holder.idDetailAssociation_ratingBar.setRating(Float.parseFloat(avis
					.getNote()));
			String[] types = this.context.getResources().getStringArray(
					R.array.type_employes);
			try{
			holder.avis_row_statut.setText(types[Integer.parseInt(avis
					.getStatut())]);
			}catch(Exception e){
				holder.avis_row_statut.setText(types[0]);
			}
			holder.idDetailAssociation_ib_cancel_avis
					.setOnClickListener(this.deleteAvisListener);
			holder.idDetailAssociation_ib_cancel_avis.setVisibility(View.GONE);
		}
		return convertView;

	}

	private void confirmationSuppression(String identifiant, final int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
		View view = this.inflater.inflate(R.layout.message_dialog, null);
		TextView localTextView = (TextView) view
				.findViewById(R.id.idMessageDialog_textView);
		LinearLayout idMessageDialog_linearLayoutValider = (LinearLayout) view
				.findViewById(R.id.idMessageDialog_linearLayoutValider);
		LinearLayout idMessageDialog_linearLayoutAnnuler = (LinearLayout) view
				.findViewById(R.id.idMessageDialog_linearLayoutAnnuler);
		localTextView.setText(R.string.txtMessageDialog_textViewDeleteAvis);
		idMessageDialog_linearLayoutValider
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View paramAnonymousView) {
						alertDialog.dismiss();
						AvisAssociationFragment.progressDialog = CustomProgressDialog.createProgressDialog(
								context,
								context.getString(R.string.txtMenu_dialogChargement));
						aviss.remove(position);
						notifyDataSetChanged();
					}
				});
		idMessageDialog_linearLayoutAnnuler
				.setOnClickListener(this.alertAnnulerSuppression);
		builder.setTitle(R.string.txtProfilRow_msgTitleConfirmationSuppression);
		builder.setView(view);
		this.alertDialog = builder.create();
		this.alertDialog.show();
	}

	private class ViewHolder {
		TextView avis_row_avantage;
		TextView avis_row_date_publication;
		TextView avis_row_inconvenient;
		TextView avis_row_statut;
		ImageButton idDetailAssociation_ib_cancel_avis;
		RatingBar idDetailAssociation_ratingBar;
	}

	public void setAviss(List<Avis> avis) {
		this.aviss = avis;
		notifyDataSetChanged();
	}

	public List<Avis> getAviss() {
		return this.aviss;
	}
}
