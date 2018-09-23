package mobile.a3tech.com.a3tech.activity;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;

public class OffresAssociationFragment extends Fragment implements
		DataLoadCallback {
	View rootView;

	public static OffresAssociationFragment newInstance() {
		return new OffresAssociationFragment();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.offres_association_fragment,
					container, false);
			rootView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		} else {
			if(rootView.getParent()!=null)
				((ViewGroup) rootView.getParent()).removeView(rootView);
		}
		return rootView;
	}

	@Override
	public void dataLoaded(Object data, int method, int j) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dataLoadingError(int errorCode) {
		// TODO Auto-generated method stub

	}

}
