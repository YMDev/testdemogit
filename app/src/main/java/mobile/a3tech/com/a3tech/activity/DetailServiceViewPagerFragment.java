package mobile.a3tech.com.a3tech.activity;

import java.util.ArrayList;
import java.util.List;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.DetailServiceViewPagerAdapter;
import mobile.a3tech.com.a3tech.sliding.PagerServiceOffreItem;
import mobile.a3tech.com.a3tech.sliding.SlidingTabLayout;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.UtileColor;
import mobile.a3tech.com.a3tech.view.IOnFocusListenable;

public class DetailServiceViewPagerFragment extends FragmentActivity {

	DetailServiceViewPagerAdapter detailOffreRecruiterViewPagerAdapter;
	LinearLayout idDetailSrvc_linearLayoutRetour;
	SlidingTabLayout mSlidingTabLayout;
	private List<PagerServiceOffreItem> mTabs = new ArrayList<PagerServiceOffreItem>();
	ViewPager mViewPager;
	private View.OnClickListener retourListener = new View.OnClickListener() {
		public void onClick(View paramAnonymousView) {
			finish();
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  
		  super.onActivityResult(requestCode, resultCode, data);
	};

	
	@Override
	   public void onWindowFocusChanged(boolean hasFocus) {
	        super.onWindowFocusChanged(hasFocus);
	        Fragment currentFragment = getActiveFragment();
	        if(currentFragment instanceof IOnFocusListenable) {
	            ((IOnFocusListenable) currentFragment).onWindowFocusChanged(hasFocus);
	        }
	    }
	
	
	public Fragment getActiveFragment() {
	    if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
	        return null;
	    }
	    String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
	    return (Fragment) getSupportFragmentManager().findFragmentByTag(tag);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.services_viewpager_fragment);
		mTabs.add(new PagerServiceOffreItem(0,
				getString(R.string.tab_one_detail_service), getResources()
						.getColor(UtileColor.colors[2]), Color.GRAY));
		mTabs.add(new PagerServiceOffreItem(1,
				getString(R.string.tab_two_detail_offres), getResources()
						.getColor(UtileColor.colors[3]), Color.GRAY));
		mViewPager = ((ViewPager) findViewById(R.id.mPager));
		detailOffreRecruiterViewPagerAdapter = new DetailServiceViewPagerAdapter(
				getSupportFragmentManager(), mTabs);
		mViewPager.setAdapter(this.detailOffreRecruiterViewPagerAdapter);
		mSlidingTabLayout = ((SlidingTabLayout) findViewById(R.id.mTabs));
		mSlidingTabLayout.setViewPager(mViewPager);
		idDetailSrvc_linearLayoutRetour = ((LinearLayout) findViewById(R.id.idDetailSrvc_linearLayoutRetour));
		idDetailSrvc_linearLayoutRetour.setOnClickListener(retourListener);
		this.mSlidingTabLayout
				.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
					public int getDividerColor(int color) {
						return mTabs.get(color).getDividerColor();
					}

					public int getIndicatorColor(int color) {
						return mTabs.get(color).getIndicatorColor();
					}
				});
		  Bundle b = getIntent().getExtras();
	        if (b != null) {
	            
	            String idoffre = b.getString(Constant.KEY_EXTRA_DETAIL_MISSION_OFFRE_ID);
	            if(idoffre!=null && !idoffre.equals("") && !idoffre.equals("0"))
	            	mViewPager.setCurrentItem(1);
	        }
		
	}

}
