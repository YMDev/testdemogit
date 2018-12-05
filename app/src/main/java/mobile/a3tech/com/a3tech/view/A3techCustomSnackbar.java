package mobile.a3tech.com.a3tech.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mobile.a3tech.com.a3tech.R;

public final class A3techCustomSnackbar
        extends BaseTransientBottomBar<A3techCustomSnackbar> {


    protected A3techCustomSnackbar(@NonNull ViewGroup parent, @NonNull View content, @NonNull ContentViewCallback contentViewCallback) {
        super(parent, content, contentViewCallback);
    }

    public static A3techCustomSnackbar make(ViewGroup parent, int duration, String txt) {
        // inflate custom layout
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.a3tech_toast_v2, parent, false);
        TextView contentToast = (TextView) view.findViewById(R.id.snackbar_text);
        // create with custom view
        contentToast.setText(txt);
        ContentViewCallback callback= new ContentViewCallback(view);
        A3techCustomSnackbar customSnackbar = new A3techCustomSnackbar(parent, view, callback);
        customSnackbar.setDuration(duration);
        customSnackbar.setDuration(duration);
        customSnackbar.show();
        return customSnackbar;
    }


    private static class ContentViewCallback
            implements BaseTransientBottomBar.ContentViewCallback {

        // view inflated from custom layout
        private View view;

        public ContentViewCallback(View view) {
            this.view = view;
        }

        @Override
        public void animateContentIn(int delay, int duration) {
            // TODO: handle enter animation
        }

        @Override
        public void animateContentOut(int delay, int duration) {
            // TODO: handle exit animation
        }
    }

}