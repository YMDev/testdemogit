package mobile.a3tech.com.a3tech.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTechnicien;

public class A3techDistanceBarView extends RelativeLayout {
    public A3techDistanceBarView(Context context, Double distanceKm, Double distanceParam) {
        super(context);
        distanceMaxParametree = distanceParam;
        distanceUser = distanceKm;
        initView(context);
    }
    public A3techDistanceBarView(Context context) {
        super(context);
        initView(context);
    }
    public A3techDistanceBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public A3techDistanceBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public A3techDistanceBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private Context pContext;
    private LayoutInflater mInflater;
    private Button spaceKm;
    private Button spaceBlank;
    private Double distanceUser;
    private Double distanceMaxParametree;
    private LinearLayout barDistance;

    private void initView(final Context context) {
        pContext = context;
        mInflater = LayoutInflater.from(context);
        RelativeLayout timeLineLayout = (RelativeLayout) mInflater.inflate(R.layout.a3tech_distance_bar_view, null);
        spaceKm = timeLineLayout.findViewById(R.id.space_distance);
        spaceBlank = timeLineLayout.findViewById(R.id.space_blank);
        barDistance = timeLineLayout.findViewById(R.id.bar_distance);

      /*  LinearLayout.LayoutParams paramsBarBlank = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams paramsBarColored = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        Double prcentrageColored = distanceUser * 100  / distanceMaxParametree;
        Double prcetrageBlank = 100 - prcentrageColored;
        paramsBarColored.weight = Double.valueOf(prcentrageColored /100).floatValue();
        paramsBarBlank.weight = Double.valueOf(prcetrageBlank /100).floatValue();
        spaceBlank.setLayoutParams(paramsBarBlank);
        spaceKm.setLayoutParams(paramsBarColored);*/
        addView(timeLineLayout);
    }

    public void updateDistance(Double distanceMaxParametree, Double distanceUser){
        this.distanceUser = distanceUser;
        this.distanceMaxParametree = distanceMaxParametree;

        Double prcentrageColored = distanceUser * 100  / distanceMaxParametree;
        Double prcetrageBlank = 100 - prcentrageColored;
        LinearLayout.LayoutParams paramsBarBlank = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT,Double.valueOf(prcetrageBlank /100).floatValue());
        LinearLayout.LayoutParams paramsBarColored = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT,Double.valueOf(prcentrageColored /100).floatValue());
        spaceBlank.setLayoutParams(paramsBarBlank);
        spaceKm.setLayoutParams(paramsBarColored);
        invalidate();
    }



}
