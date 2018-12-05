package mobile.a3tech.com.a3tech.animation;


import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgresseAnimation extends Animation {
    private ProgressBar progressBar;
    private TextView textProgression;
    private float from;
    private float  to;

    public ProgresseAnimation(ProgressBar progressBar,TextView textProgression, float from, float to) {
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
        this.textProgression = textProgression;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
        textProgression.setText(Math.round(value)+" %");
    }

}