package mobile.a3tech.com.a3tech.activity;

import java.util.ArrayList;
import java.util.List;


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

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.ServicesAdapter;
import mobile.a3tech.com.a3tech.manager.MissionManager;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.view.CustomListView;

public class ServicesRendusFragment extends Fragment  implements DataLoadCallback {
	
	private List<Mission> mList = new ArrayList<Mission>();
	private ServicesAdapter mAdapter;
	private CustomListView mListView;
	int start = 0;
	int limit = 10 ;
	String connectedUser = "";
	String password = "" ;
	String lang;

	
	public static ServicesRendusFragment newInstance() {
		ServicesRendusFragment fragment = new ServicesRendusFragment();
        return fragment;
    }	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		View rootView = inflater.inflate(R.layout.services_rendus_fragment, container, false);
		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT ));		
		return rootView;		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		  SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		  connectedUser = prefs.getString("identifiant", "");
		  password = prefs.getString("password", "");
	        
	        this.lang = prefs.getString("ApplicationLanguage", Constant.LANGUAGE_FRENSH);
		initView();
		
	}
	
	private void initView() {
		mAdapter = new ServicesAdapter(this.getActivity(), mList,true);
		mListView = (CustomListView) getView().findViewById(R.id.mListView);
		mListView.setAdapter(mAdapter);
		mListView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				start = 0;
				MissionManager.getInstance().missionsEmises(lang, Constant.REFRESH_DATA_FINISH, connectedUser,String.valueOf(start), String.valueOf(limit),password,ServicesRendusFragment.this);
			}
		});
		mListView.setOnLoadListener(new CustomListView.OnLoadMoreListener() {
			@Override
			public void onLoadMore() {
				MissionManager.getInstance().missionsEmises(lang,Constant.LOAD_DATA_FINISH,connectedUser,String.valueOf(start), String.valueOf(limit),password,ServicesRendusFragment.this);
				
			}
		});
		
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> paramAnonymousAdapterView,
					View paramAnonymousView, int position,
					long paramAnonymousLong) {
				 Mission mission = (Mission) mAdapter.getMissions().get(position - 1);
		            if (Integer.parseInt(mission.getStatut()) != 5) {
		                Intent mainIntent = new Intent(getActivity(), DetailServiceViewPagerFragment.class);
		                Bundle bundle = new Bundle();
		                bundle.putString(Constant.KEY_EXTRA_DETAIL_MISSION_USER_ID, mission.getOriginator());
		                bundle.putString(Constant.KEY_EXTRA_DETAIL_MISSION_ID, mission.getIdentifiant());
		                mainIntent.putExtras(bundle);
		                getActivity().startActivityForResult(mainIntent, 101);
		            }
			}
		});
	
		mListView.charger();
	}

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == -1) {
            String result = data.getStringExtra(Constant.RESULT_ACTION_CODE);
            if (result.equals(Constant.RESULT_ACTION_CODE_DELETE_MISSION) || result.equals(Constant.RESULT_ACTION_CODE_CREATE_MESSAGE) || result.equals(Constant.RESULT_ACTION_CODE_CREATE_OFFRE) || result.equals(Constant.RESULT_ACTION_CODE_DELETE_OFFRE)) {
                start = 0;
                mAdapter.clear();
                mAdapter.notifyDataSetChanged();
                mListView.charger();
            }
        }
    }
	
	@Override
	public void dataLoaded(Object data, int method,int typeOperation) {
		List<Mission> miss = (List<Mission>) data;
		if(typeOperation==Constant.LOAD_DATA_FINISH){
			mAdapter.addToListResultats(miss);
			mListView.onLoadMoreComplete(); 
		}else{
			mAdapter.clear();
			mAdapter.addToListResultats(miss);
			mListView.onRefreshComplete();
		}
		start = start + miss.size();
			
	}

	@Override
	public void dataLoadingError(int errorCode) {
		// TODO Auto-generated method stub

	}

    public void dataLoadingError(String error) {
    }
	
	
	


		
	

	



	

	
	
}
