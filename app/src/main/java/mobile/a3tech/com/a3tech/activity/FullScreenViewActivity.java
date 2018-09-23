package mobile.a3tech.com.a3tech.activity;

import java.util.ArrayList;
import java.util.Arrays;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.FullScreenImageAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.WindowManager;

public class FullScreenViewActivity extends Activity {
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);
        this.viewPager = (ViewPager) findViewById(R.id.pager);
        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        this.adapter = new FullScreenImageAdapter(this, new ArrayList(Arrays.asList(i.getStringExtra("urls").split(";"))), getScreenWidth(), getScreenHeigh());
        this.viewPager.setAdapter(this.adapter);
        this.viewPager.setCurrentItem(position);
    }

    public int getScreenWidth() {
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        try {
            display.getSize(point);
        } catch (NoSuchMethodError e) {
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        return point.x;
    }

    public int getScreenHeigh() {
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        try {
            display.getSize(point);
        } catch (NoSuchMethodError e) {
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        return point.y;
    }
}
