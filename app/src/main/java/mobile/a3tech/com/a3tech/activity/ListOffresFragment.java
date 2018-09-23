package mobile.a3tech.com.a3tech.activity;

import java.util.ArrayList;
import java.util.List;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.OffresAdapter;
import mobile.a3tech.com.a3tech.manager.OffresManager;
import mobile.a3tech.com.a3tech.model.Offre;
import mobile.a3tech.com.a3tech.model.ReponseOffre;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;
import mobile.a3tech.com.a3tech.view.Helper;

public class ListOffresFragment extends Fragment implements DataLoadCallback {

	String connectedUser = "";
	String password ="" ;

	private ListView idDetailSrvc_listeOffres;
	String missionId;
	String offreId = "";
	String isMessageNotif = "" ;
	String statut;
	String missionUser;
	List<Offre> offres = new ArrayList<Offre>();
	OffresAdapter offresAdapter;
	ImageButton idDetailSrvc_Row_imageButtonRefresh ;
	

	public static ProgressDialog progressDialog;

	public static ListOffresFragment newInstance() {
		return new ListOffresFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater
				.inflate(R.layout.list_offres, container, false);
		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		return rootView;
	}

	@Override
	public void dataLoadingError(int errorCode) {
		Toast.makeText(getActivity().getApplicationContext(),R.string.txtSplash_messageCheckConnexion,Toast.LENGTH_SHORT).show();
		try {
			progressDialog.dismiss();
		} catch (Exception e) {
			
		}

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState == null) {
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(getActivity()
							.getApplicationContext());
			connectedUser = prefs.getString("identifiant", "");
			password = prefs.getString("password", "");
			Bundle b = getActivity().getIntent().getExtras();
			if (b != null) {
				this.missionId = b
						.getString(Constant.KEY_EXTRA_DETAIL_MISSION_ID);
				isMessageNotif = b
						.getString(Constant.KEY_EXTRA_DETAIL_MESSAGE_ID);
				this.missionUser = b
						.getString(Constant.KEY_EXTRA_DETAIL_MISSION_USER_ID);
			    this.offreId = b
					    .getString(Constant.KEY_EXTRA_DETAIL_MISSION_OFFRE_ID);
			}
			initView();
		}
	}
	
	@Override
	public void onResume() {
		  if (offreId != null && !offreId.equals("") && !offreId.equals("0") && isMessageNotif!=null && !isMessageNotif.equals("") && !isMessageNotif.equals("0")) {
			  Intent mainIntent = new Intent(getActivity(),
						DetailOffreActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("KEY_EXTRA_DETAIL_MISSION_USER_ID",
						missionUser);
				bundle.putString("ID_MISSION", missionId);
				bundle.putString("ID_OFFRE", offreId);
				mainIntent.putExtras(bundle);
				getActivity().startActivity(mainIntent);
				offreId="";
	        }
		super.onResume();
	}
	
	public void showOffres(List<Offre> ofrs) {
		for (Offre offre : ofrs) {
			offre.setConnectedUser(connectedUser);
			offre.setIsOK(Integer.valueOf(statut) == 0 ? true : false);
			if(offreId!=null && offreId.equals(offre.getIdentifiant())){
				offre.setSelected(true);
			}else{
				offre.setSelected(false);
			}
		}
		offres.removeAll(offres);
		offres.addAll(ofrs);
		idDetailSrvc_listeOffres.setVisibility(View.VISIBLE);
		offresAdapter.notifyDataSetChanged();
		Helper.getListViewSize(idDetailSrvc_listeOffres, this.getActivity());
		

	}

	@Override
	public void dataLoaded(Object data, int method, int function) {
		switch (method) {
		case Constant.KEY_OFFRE_MANAGER_OFFRES_MISSION:
			ReponseOffre reponseOffre =(ReponseOffre)data;
			statut = reponseOffre.getResult();
			showOffres(reponseOffre.getOffres());
			offreId = "" ;
			try{
				progressDialog.dismiss();
			}catch (Exception e) {
				// TODO: handle exception
			}
			break;
		case Constant.KEY_OFFRE_MANAGER_MAJ_OFFRE:
			String result =(String)data;
			int OffreId = function;
			if(result.equals("Cas1")){
				Toast.makeText(getContext(), getString(R.string.txtOffre_annulerAcceptationOffre), Toast.LENGTH_LONG);
				for (Offre offre : offres) {
					if(offre.getIdentifiant().equals(String.valueOf(OffreId))){
						offre.setEtat("0");
					}
				}
				offresAdapter.setEtatMission(0);
				offresAdapter.notifyDataSetChanged();
				Helper.getListViewSize(idDetailSrvc_listeOffres, this.getActivity());
			}
			if(result.equals("Cas2")){
				Toast.makeText(getContext(), getString(R.string.txtOffre_offreSupprimee), Toast.LENGTH_LONG);
				Offre off = null;
				for (Offre offre : offres) {
					if(offre.getIdentifiant().equals(String.valueOf(OffreId))){
						off = offre ;
					}
				}
				if(off!=null)offres.remove(off);
				offresAdapter.setEtatMission(0);
				offresAdapter.notifyDataSetChanged();
				Helper.getListViewSize(idDetailSrvc_listeOffres, this.getActivity());			
			}
			if(result.equals("Cas3")){
				Toast.makeText(getContext(), getString(R.string.txtOffre_accepterOffre), Toast.LENGTH_LONG);
				for (Offre offre : offres) {
					if(offre.getIdentifiant().equals(String.valueOf(OffreId))){
						offre.setEtat("1");
					}
				}
				offresAdapter.setEtatMission(1);
				offresAdapter.notifyDataSetChanged();
				Helper.getListViewSize(idDetailSrvc_listeOffres, this.getActivity());
				
			}
			if(result.equals("Cas4")){
				Toast.makeText(getContext(), getString(R.string.txtOffre_offreSupprimee), Toast.LENGTH_LONG);
				Offre off = null;
				for (Offre offre : offres) {
					if(offre.getIdentifiant().equals(String.valueOf(OffreId))){
						off = offre ;
					}
				}
				if(off!=null)offres.remove(off);
				offresAdapter.setEtatMission(0);
				offresAdapter.notifyDataSetChanged();
				Helper.getListViewSize(idDetailSrvc_listeOffres, this.getActivity());
				
			}
			if(result.equals("Cas5")){
				Toast.makeText(getContext(), getString(R.string.txtOffre_succesSuppressionOffre), Toast.LENGTH_LONG);
				Offre off = null;
				for (Offre offre : offres) {
					if(offre.getIdentifiant().equals(String.valueOf(OffreId))){
						off = offre ;
					}
				}
				if(off!=null)offres.remove(off);
				offresAdapter.setEtatMission(0);
				offresAdapter.notifyDataSetChanged();
				Helper.getListViewSize(idDetailSrvc_listeOffres, this.getActivity());
				
			}
			if(result.equals("Cas6")){
				Toast.makeText(getContext(), getString(R.string.txtOffre_offreSupprimee), Toast.LENGTH_LONG);
				Offre off = null;
				for (Offre offre : offres) {
					if(offre.getIdentifiant().equals(String.valueOf(OffreId))){
						off = offre ;
					}
				}
				if(off!=null)offres.remove(off);
				offresAdapter.setEtatMission(0);
				offresAdapter.notifyDataSetChanged();
				Helper.getListViewSize(idDetailSrvc_listeOffres, this.getActivity());
				
			}
			if(result.equals("Cas7")){
				Toast.makeText(getContext(), getString(R.string.txtOffre_offrePasASupprimee), Toast.LENGTH_LONG);
				offresAdapter.setEtatMission(2);
				offresAdapter.notifyDataSetChanged();
				Helper.getListViewSize(idDetailSrvc_listeOffres, this.getActivity());
			}
			if(result.equals("Cas8")){
				Toast.makeText(getContext(), getString(R.string.txtOffre_offreSupprimee), Toast.LENGTH_LONG);
				Offre off = null;
				for (Offre offre : offres) {
					if(offre.getIdentifiant().equals(String.valueOf(OffreId))){
						off = offre ;
					}
				}
				if(off!=null)offres.remove(off);
				offresAdapter.notifyDataSetChanged();
				Helper.getListViewSize(idDetailSrvc_listeOffres, this.getActivity());
			}
			if(result.equals("Cas9")){
				Toast.makeText(getContext(), getString(R.string.txtOffre_offreSupprimee), Toast.LENGTH_LONG);
				Offre off = null;
				for (Offre offre : offres) {
					if(offre.getIdentifiant().equals(String.valueOf(OffreId))){
						off = offre ;
					}
				}
				if(off!=null)offres.remove(off);
				offresAdapter.setEtatMission(0);
				offresAdapter.notifyDataSetChanged();
				Helper.getListViewSize(idDetailSrvc_listeOffres, this.getActivity());
				
			}
			if(result.equals("Cas10")){
				Toast.makeText(getContext(), getString(R.string.txtOffre_offreSupprimee), Toast.LENGTH_LONG);
				Offre off = null;
				for (Offre offre : offres) {
					if(offre.getIdentifiant().equals(String.valueOf(OffreId))){
						off = offre ;
					}
				}
				if(off!=null)offres.remove(off);
				offresAdapter.notifyDataSetChanged();
				Helper.getListViewSize(idDetailSrvc_listeOffres, this.getActivity());
				
			}
			
		default:
			progressDialog.dismiss();
			break;
		}

	}

	private void initView() {
		idDetailSrvc_listeOffres = (ListView) getView().findViewById(
				R.id.idDetailSrvc_listeOffres);
		idDetailSrvc_Row_imageButtonRefresh = (ImageButton) getView().findViewById(
				R.id.idDetailSrvc_Row_imageButtonRefresh);
		idDetailSrvc_Row_imageButtonRefresh.bringToFront();
		idDetailSrvc_Row_imageButtonRefresh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				progressDialog = CustomProgressDialog.createProgressDialog(ListOffresFragment.this.getActivity(), getString(R.string.txtMenu_dialogChargement));
				OffresManager.getInstance().offresMission(missionId,connectedUser,password,
						ListOffresFragment.this);
			}
		});
		offresAdapter = new OffresAdapter(ListOffresFragment.this, offres,
				idDetailSrvc_listeOffres,password);
		idDetailSrvc_listeOffres.setAdapter(offresAdapter);
		OffresManager.getInstance().offresMission(missionId,connectedUser,password,
				ListOffresFragment.this);

	}

}
