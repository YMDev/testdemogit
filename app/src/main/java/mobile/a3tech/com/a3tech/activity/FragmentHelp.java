package mobile.a3tech.com.a3tech.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.ViewPagerAdapter;
import mobile.a3tech.com.a3tech.utils.Menus;
import mobile.a3tech.com.a3tech.viewpagerindicator.CirclePageIndicator;
import mobile.a3tech.com.a3tech.viewpagerindicator.PageIndicator;


public class FragmentHelp extends Fragment {

	ViewPager mPager;
	PageIndicator mIndicator;
	private boolean searchCheck;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_help, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		ViewPagerAdapter adapter = new ViewPagerAdapter(FragmentHelp.this.getActivity());

		mPager = (ViewPager) getView().findViewById(R.id.obedevpanelpager);
		mPager.setAdapter(adapter);
		
		CirclePageIndicator indicator = (CirclePageIndicator) getView()
				.findViewById(R.id.indicator);
		mIndicator = indicator;
		indicator.setViewPager(mPager);

		final float density = getResources().getDisplayMetrics().density;
		indicator.setBackgroundColor(0xFFCCCCCC);
		indicator.setRadius(5 * density);
		indicator.setPageColor(0x880000FF);
		indicator.setFillColor(0xFF888888);
		indicator.setStrokeColor(0xFF000000);
		indicator.setStrokeWidth(2 * density);

	}
	
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);		
		inflater.inflate(R.menu.menu, menu);
		 	    
		SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(Menus.SEARCH));
	    searchView.setQueryHint(this.getString(R.string.search));
	    
//	    ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
//        .setHintTextColor(getResources().getColor(R.color.white));	    
//	    searchView.setOnQueryTextListener(OnQuerySearchView);
					    	   	    
		menu.findItem(Menus.ADD).setVisible(false);
		//menu.findItem(Menus.UPDATE).setVisible(false);		
		menu.findItem(Menus.SEARCH).setVisible(true);		
  	    
		searchCheck = false;	
	}	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {

		case Menus.ADD:
			break;				
		
					
			
		case Menus.SEARCH:
			searchCheck = true;
			break;
		}		
		return true;
	}	
	
	private OnQueryTextListener OnQuerySearchView = new OnQueryTextListener() {
		
		@Override
		public boolean onQueryTextSubmit(String arg0) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean onQueryTextChange(String arg0) {
			// TODO Auto-generated method stub
			if (searchCheck){
				// implement your search here
			}
			return false;
		}
	};

}
