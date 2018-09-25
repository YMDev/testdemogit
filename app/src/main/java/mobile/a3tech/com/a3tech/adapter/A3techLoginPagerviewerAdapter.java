package mobile.a3tech.com.a3tech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import mobile.a3tech.com.a3tech.R;

public class A3techLoginPagerviewerAdapter extends PagerAdapter {

    Context context;
    int[] flag;
    LayoutInflater inflater;

    public A3techLoginPagerviewerAdapter(Context context,  int[] flag) {
        this.context = context;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return flag.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imgflag;
        TextView labelToShow;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.a3tech_fragment_pager, container,
                false);

        // Locate the ImageView in viewpager_item.xml
        imgflag = (ImageView) itemView.findViewById(R.id.section_img);
        labelToShow = (TextView) itemView.findViewById(R.id.label_to_show);
        // Capture position and set to the ImageView
        imgflag.setImageResource(flag[position]);
        labelToShow.setText("3TECH 3TECH 3TECH 3TECH 3TECH 3TECH 3TECH 3TECH  ");
        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
