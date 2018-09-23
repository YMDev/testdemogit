package mobile.a3tech.com.a3tech.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.DetailOffreActivity;
import mobile.a3tech.com.a3tech.activity.ListOffresFragment;
import mobile.a3tech.com.a3tech.manager.OffresManager;
import mobile.a3tech.com.a3tech.model.Offre;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.view.CircleImageView;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class OffresAdapter extends BaseAdapter {
	private Context context;
	private Fragment fragment;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public ImageLoader imageLoader;
	private LayoutInflater inflater;
	private ListView listView;
	public List<Offre> offres;
	private int etatMission ;
	String password ;
	
	
	public int getEtatMission() {
		return etatMission;
	}

	public void setEtatMission(int etatMission) {
		this.etatMission = etatMission;
	}

	DisplayImageOptions options = new DisplayImageOptions.Builder()
			.cacheInMemory().cacheOnDisc().build();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);

	public void addToListResultats(List<Offre> paramList) {
		offres.addAll(paramList);
		notifyDataSetChanged();
	}

	public void clear() {
		this.offres.clear();
	}

	@Override
	public int getCount() {
		if (offres != null) {
			return offres.size();
		}
		return 0;
	}

	public Offre getItem(int position) {
		if (position < offres.size()) {
			return (Offre) offres.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (position < offres.size()) {
			return Long.parseLong(((Offre) this.offres.get(position))
					.getIdentifiant());
		}
		return 0;
	}

	public List<Offre> getOffres() {
		return offres;
	}

	public String getRandom(int paramInt1, int paramInt2, boolean paramBoolean) {
		paramInt1 = (int) (Math.random() * (paramInt2 + 1 - paramInt1))
				+ paramInt1;
		if (!paramBoolean) {
			return String.valueOf(paramInt1 / 10.0F);
		}
		return String.valueOf(paramInt1);
	}

	public OffresAdapter(Fragment fragment, List<Offre> offres,
			ListView listView,String password ) {
		this.context = fragment.getActivity();
		this.inflater = LayoutInflater.from(this.context);
		this.offres = offres;
		this.imageLoader = ImageLoader.getInstance();
		this.listView = listView;
		this.fragment = fragment ;
		this.password = password;
		
		
	}

	public void setOffres(List<Offre> offres) {
		this.offres = offres;
		notifyDataSetChanged();
	}

	private class ViewHolder {
		CircleImageView offre_row_picto_ci;
		TextView offre_row_textViewPseudo ;
		RatingBar offre_row_ratinBarEval ;
		TextView offre_row_commentaire_tv ;
		TextView offre_row_posted_by_date_tv ;
		TextView offre_row_status_tv ;
		LinearLayout idOffreRow_linearLayoutAccepter;
		LinearLayout idOffreRow_linearLayoutDecline;
		LinearLayout idOffreRow_linearLayoutView;
		TextView idOffreRow_textViewView ;
		TextView idOffreRow_tvbDecline ;
		LinearLayout srvc ;
	}
    // ok
	private View.OnClickListener accepteOffreListener = new View.OnClickListener() {
		public void onClick(View v) {
			final int position = listView.getPositionForView((View) v
					.getParent());
			Offre offre = (Offre) offres.get(position);
			ListOffresFragment.progressDialog = CustomProgressDialog
					.createProgressDialog(
							OffresAdapter.this.context,
							OffresAdapter.this.context
									.getString(R.string.txtMenu_dialogChargement));
			 OffresManager.getInstance().majOffre(offre.getIdentifiant(),offre.getConnectedUser(),"1",password, (DataLoadCallback) fragment);
		}
	};
    //annuler offre cote mission ok
	private View.OnClickListener deleteOffreListener = new View.OnClickListener() {
		public void onClick(View v) {
			final int position = OffresAdapter.this.listView
					.getPositionForView((View) v.getParent());
			Offre offre = (Offre) OffresAdapter.this.offres.get(position);
			ListOffresFragment.progressDialog = CustomProgressDialog
					.createProgressDialog(
							OffresAdapter.this.context,
							OffresAdapter.this.context
									.getString(R.string.txtMenu_dialogChargement));
			 OffresManager.getInstance().majOffre(offre.getIdentifiant(),offre.getConnectedUser(),"2",password, (DataLoadCallback) fragment);
			
		}
	};
	//annuler acceptation offre mettre statut � 0  ok
	private View.OnClickListener annulerAcceptationListener = new View.OnClickListener() {
		public void onClick(View v) {
			final int position = OffresAdapter.this.listView
					.getPositionForView((View) v.getParent());
			Offre offre = (Offre) OffresAdapter.this.offres.get(position);
			ListOffresFragment.progressDialog = CustomProgressDialog
					.createProgressDialog(
							OffresAdapter.this.context,
							OffresAdapter.this.context
									.getString(R.string.txtMenu_dialogChargement));
			 OffresManager.getInstance().majOffre(offre.getIdentifiant(),offre.getConnectedUser(),"0",password, (DataLoadCallback) fragment);
			
		}
	};
	

	
	
	
	
	

	private View.OnClickListener showCVListener = new View.OnClickListener() {
		public void onClick(View v) {
			final int position = OffresAdapter.this.listView
					.getPositionForView((View) v.getParent());
			Offre offre = (Offre) OffresAdapter.this.offres.get(position );
			Intent mainIntent = new Intent(fragment.getActivity(),
					DetailOffreActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("KEY_EXTRA_DETAIL_MISSION_USER_ID",
					offre.getMissionUser());
			bundle.putString("ID_MISSION", offre.getMissionId());
			bundle.putString("ID_OFFRE", offre.getIdentifiant());
			bundle.putString("MOTIVATION", offre.getCommentaire());
			mainIntent.putExtras(bundle);
			fragment.getActivity().startActivity(mainIntent);
		}
	};

	@Override
	public View getView(int position, View convertView,
			android.view.ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null || convertView.getTag() == null) {
			convertView = inflater.inflate(R.layout.offre_row, null);
			holder = new ViewHolder();
			
			holder.offre_row_picto_ci= (CircleImageView) convertView.findViewById(R.id.offre_row_picto_ci);
			holder.offre_row_textViewPseudo= (TextView) convertView.findViewById(R.id.offre_row_textViewPseudo);
			holder.offre_row_ratinBarEval = (RatingBar) convertView.findViewById(R.id.offre_row_ratinBarEval);
			holder. offre_row_commentaire_tv = (TextView) convertView.findViewById(R.id.offre_row_commentaire_tv);
			holder.offre_row_posted_by_date_tv = (TextView) convertView.findViewById(R.id.offre_row_posted_by_date_tv);
			holder.offre_row_status_tv= (TextView) convertView.findViewById(R.id.offre_row_status_tv);
			holder.idOffreRow_linearLayoutAccepter= (LinearLayout) convertView.findViewById(R.id.idOffreRow_linearLayoutAccepter);
			holder.idOffreRow_linearLayoutDecline= (LinearLayout) convertView.findViewById(R.id.idOffreRow_linearLayoutDecline);
			holder.idOffreRow_linearLayoutView= (LinearLayout) convertView.findViewById(R.id.idOffreRow_linearLayoutView);
			holder.idOffreRow_textViewView= (TextView) convertView.findViewById(R.id.idOffreRow_textViewView);
			holder.idOffreRow_tvbDecline= (TextView) convertView.findViewById(R.id.idOffreRow_tvbDecline);
			holder.srvc = (LinearLayout) convertView.findViewById(R.id.srvc);
			
			
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (position < offres.size()) {
			Offre offre = offres.get(position);
			imageLoader.displayImage(new StringBuilder(Constant.CHECK_EDU_PICTURES_URL).append(offre.getUserId()).append(".jpg").toString(),
					holder.offre_row_picto_ci, options);
			holder.offre_row_textViewPseudo.setText(offre.getPseudo());
			holder.offre_row_commentaire_tv.setText(offre.getCommentaire());
			String dateCreation = "";
			try {
				dateCreation = sdf.format(dateFormat.parse(offre
						.getDateCreation()));

			} catch (ParseException localParseException) {
			}
			holder.offre_row_posted_by_date_tv.setText(this.context
					.getString(R.string.txtDetailSrvc_txtPosteLe)
					+ " "
					+ dateCreation);
			String statuts[] = context.getResources().getStringArray(
					R.array.statuts_offres);
			
			int statut = 0;
			try{
				statut = Integer.parseInt(offre.getEtat());
			}catch (Exception e) {
			// TODO: handle exception
			}
			holder.offre_row_status_tv.setText(statuts[statut]);
			holder.offre_row_ratinBarEval.setRating(Float.parseFloat(offre.getNote()));
			holder.idOffreRow_linearLayoutAccepter.setOnClickListener(accepteOffreListener);
			holder.idOffreRow_linearLayoutView.setOnClickListener(showCVListener);
		
			
			switch (statut) {
			case Constant.STATUT_OFFRE_NOUVELLE:
				if(offre.getConnectedUser().equals(offre.getMissionUser()) && etatMission==0){
					holder.idOffreRow_linearLayoutAccepter.setVisibility(View.VISIBLE);
					holder.idOffreRow_linearLayoutDecline.setVisibility(View.VISIBLE);
					holder.idOffreRow_linearLayoutDecline.setOnClickListener(deleteOffreListener);
				}else if(!offre.getConnectedUser().equals(offre.getMissionUser())){
					holder.idOffreRow_linearLayoutAccepter.setVisibility(View.GONE);
					holder.idOffreRow_linearLayoutDecline.setVisibility(View.VISIBLE);
					holder.idOffreRow_tvbDecline.setText(context.getString(R.string.txtdetailOffre_textViewAnnulerOffreOffreur));
					holder.idOffreRow_linearLayoutDecline.setOnClickListener(deleteOffreListener);
				}else{
					holder.idOffreRow_linearLayoutAccepter.setVisibility(View.GONE);
					holder.idOffreRow_linearLayoutDecline.setVisibility(View.GONE);
				}
				holder.offre_row_status_tv.setTextColor(context.getResources().getColor(R.color.menu));
				if(etatMission!=0)holder.offre_row_status_tv.setVisibility(View.GONE);
				break;

			case Constant.STATUT_OFFRE_ACCEPTEE:
				if(offre.getConnectedUser().equals(offre.getMissionUser())){
					holder.idOffreRow_linearLayoutDecline.setVisibility(View.VISIBLE);
					holder.idOffreRow_tvbDecline.setText(context.getString(R.string.txtdetailOffre_textViewAnnulerOffreEmetteur));
					holder.idOffreRow_linearLayoutDecline.setOnClickListener(annulerAcceptationListener);
				}else if(!offre.getConnectedUser().equals(offre.getMissionUser())){
					holder.idOffreRow_linearLayoutDecline.setVisibility(View.VISIBLE);
					holder.idOffreRow_tvbDecline.setText(context.getString(R.string.txtdetailOffre_textViewAnnulerOffreOffreur));
					holder.idOffreRow_linearLayoutDecline.setOnClickListener(deleteOffreListener);
				}else{
					holder.idOffreRow_linearLayoutDecline.setVisibility(View.GONE);
				}
				holder.idOffreRow_linearLayoutAccepter.setVisibility(View.GONE);
				
				holder.idOffreRow_textViewView.setText(context.getString(R.string.txtdetailOffre_textViewvoirCoordonnees));
				holder.offre_row_status_tv.setTextColor(context.getResources().getColor(R.color.notifMove));
				holder.offre_row_status_tv.setVisibility(View.VISIBLE);
				break;

			default:
				break;
			}
			if(offre.isSelected()){
				holder.srvc.setBackgroundResource(R.drawable.service_contour_gray);
			}else{
				holder.srvc.setBackgroundResource(R.drawable.service_contour);
			}
		}
		return convertView;

	};

}
