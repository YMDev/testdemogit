package mobile.a3tech.com.a3tech.activity;

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
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.AssociationGridAdapter;
import mobile.a3tech.com.a3tech.manager.AssociationManager;
import mobile.a3tech.com.a3tech.model.Association;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class AssociationsFragment extends Fragment implements DataLoadCallback {

	AssociationGridAdapter gridAdapter;
	List<Association> grids;
	GridView idAssociations_grid;
	ProgressDialog progressDialog = null;
	 private String connectedUser;
	    private String password ;

	@Override
	public void dataLoaded(Object data, int method, int j) {
		List<Association> enr = (List<Association>) data;
		this.grids.addAll(enr);
		this.gridAdapter.notifyDataSetChanged();
		this.progressDialog.dismiss();

	}

	@Override
	public void dataLoadingError(int errorCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//setHasOptionsMenu(true);
		idAssociations_grid = ((GridView) getView().findViewById(
				R.id.idAssociations_grid));
		grids = new ArrayList<Association>();
		
		gridAdapter = new AssociationGridAdapter(grids, this);
		idAssociations_grid.setAdapter(gridAdapter);
		idAssociations_grid
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(
							AdapterView<?> parent,
							View paramAnonymousView, int position,
							long id) {
						Association association = (Association) gridAdapter
								.getAssociations().get(position);
						Intent mainIntent = new Intent(getActivity(),
								DetailAssociationPagerActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("KEY_EXTRA_DETAIL_ASSOCIATION_ID",
								association.getIdentifiant());
						mainIntent.putExtras(bundle);
						AssociationsFragment.this.getActivity().startActivity(
								mainIntent);
					}
				});
		 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		  connectedUser = prefs.getString("identifiant", "");
		  password = prefs.getString("password", "");
		progressDialog = CustomProgressDialog.createProgressDialog(
				getActivity(), getString(R.string.txtMenu_dialogChargement));
		AssociationManager.getInstance().listeAssociation(connectedUser,password,this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.associations_grid, container,
				false);
		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		return rootView;

	}
}
