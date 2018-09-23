package mobile.a3tech.com.a3tech.adapter;

import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import mobile.a3tech.com.a3tech.sliding.PagerItem;

public class MesServicesViewPagerAdapter extends FragmentPagerAdapter {

	private List<PagerItem> mTabs;
	protected Hashtable<Integer, WeakReference<Fragment>> fragmentReferences;
	public MesServicesViewPagerAdapter(FragmentManager fragmentManager, List<PagerItem> mTabs) {
		super(fragmentManager);
		this.fragmentReferences = new Hashtable<Integer, WeakReference<Fragment>>();
		this.mTabs = mTabs;
	
        
	}

	@Override
    public Fragment getItem(int i) {
        Fragment fragment = ((PagerItem) this.mTabs.get(i)).createFragment();
        this.fragmentReferences.put(Integer.valueOf(i), new WeakReference<Fragment>(fragment));
        return fragment;
    }
	 @Override
	public int getItemPosition(Object object) {
        return -2;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).getTitle();
    }
    
    
    public Fragment getFragment(int fragmentId) {
        WeakReference<Fragment> ref = (WeakReference<Fragment>) this.fragmentReferences.get(Integer.valueOf(fragmentId));
        return ref == null ? null : (Fragment) ref.get();
    }
}