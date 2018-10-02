package mobile.a3tech.com.a3tech.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import mobile.a3tech.com.a3tech.fragment.A3techProfilInformationFragment;
import mobile.a3tech.com.a3tech.fragment.A3techSelecteAccountFragment;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.test.DummyFragment;

public class A3techProfilFragmentAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Profile", "Avis"};
    private Context context;
    private User user;

    public A3techProfilFragmentAdapter(FragmentManager fm, Context context, User userV) {
        super(fm);
        this.context = context;
        this.user = userV;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragmntToShow = null;
        switch (position) {
            case 0:
                fragmntToShow = A3techProfilInformationFragment.newInstance(user);
                break;
            case 1:
                fragmntToShow = new DummyFragment();
                break;
            default:
                fragmntToShow = new DummyFragment();
                break;
        }
        return fragmntToShow;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
