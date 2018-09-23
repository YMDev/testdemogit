package mobile.a3tech.com.a3tech.adapter;


import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.viewpagerindicator.IconPagerAdapter;


public class ViewPagerAdapter extends PagerAdapter implements IconPagerAdapter {
	Context context ;
	public ViewPagerAdapter(Context context) {
		this.context = context;
	}
    public int getCount() {
        return 5;
    }
    public Object instantiateItem(View collection, int position) {
        LayoutInflater inflater = (LayoutInflater) collection.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      
		
        View view = inflater.inflate(R.layout.help_view, null);
        WebView idAbout_webViewContent=(WebView) view.findViewById(R.id.idAbout_webViewContent);
        int pos = position+1;
		idAbout_webViewContent.loadUrl(Constant.CHECK_TSAM_HELP_URL+"page"+pos+".html");
        ((ViewPager) collection).addView(view, 0);
        return view;
    }
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }
    @Override
    public Parcelable saveState() {
        return null;
    }
	@Override
	public int getIconResId(int index) {
		// TODO Auto-generated method stub
		return 0;
	}
}