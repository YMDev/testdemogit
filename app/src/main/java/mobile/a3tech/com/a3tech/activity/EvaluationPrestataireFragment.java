package mobile.a3tech.com.a3tech.activity;

import java.util.ArrayList;
import java.util.List;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.EvaluationAdapter;
import mobile.a3tech.com.a3tech.manager.EvaluationManager;
import mobile.a3tech.com.a3tech.model.Evaluation;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.view.CustomListView;

public class EvaluationPrestataireFragment extends Fragment implements DataLoadCallback {
	
	EvaluationAdapter listAdapter;
	private List<Evaluation> evaluations = new ArrayList<Evaluation>();
	CustomListView idEvaluation_listViewEvaluations;
	String lang;
	int start = 0;
	int limit = 10;
	String connectedUser ;
	String password ;

	
	public static EvaluationPrestataireFragment newInstance() {
		EvaluationPrestataireFragment fragment = new EvaluationPrestataireFragment();
        return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.evaluation_fragment,
				container, false);
		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		connectedUser = prefs.getString("identifiant", "");
		password = prefs.getString("password", "");
        lang = prefs.getString("ApplicationLanguage", Constant.LANGUAGE_FRENSH);
		initView() ;

	}
	
	
	private void initView() {
		listAdapter = new EvaluationAdapter(this.getActivity(), evaluations);
		idEvaluation_listViewEvaluations = (CustomListView) getView().findViewById(R.id.idEvaluation_listViewEvaluations);
		idEvaluation_listViewEvaluations.setAdapter(listAdapter);
		idEvaluation_listViewEvaluations.setOnRefreshListener(new CustomListView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				start = 0;
				EvaluationManager.getInstance().listeEvaluation(lang,Constant.REFRESH_DATA_FINISH,connectedUser, "1", String.valueOf(start), String.valueOf(limit),password, EvaluationPrestataireFragment.this);
				
			}
		});
		idEvaluation_listViewEvaluations.setOnLoadListener(new CustomListView.OnLoadMoreListener() {
			@Override
			public void onLoadMore() {
				EvaluationManager.getInstance().listeEvaluation(lang,Constant.LOAD_DATA_FINISH,connectedUser, "1", String.valueOf(start), String.valueOf(limit),password, EvaluationPrestataireFragment.this);
				
			}
		});
		idEvaluation_listViewEvaluations.charger();
	}

	

	
	@Override
	public void dataLoaded(Object data, int method,int typeOperation) {
		List<Evaluation> miss = (List<Evaluation>) data;
		if(typeOperation==Constant.LOAD_DATA_FINISH){
			listAdapter.addToListResultats(miss);
			idEvaluation_listViewEvaluations.onLoadMoreComplete(); 
		}else{
			listAdapter.clear();
			listAdapter.addToListResultats(miss);
			idEvaluation_listViewEvaluations.onRefreshComplete();
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
