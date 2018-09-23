package mobile.a3tech.com.a3tech.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Toaster {
  private static final int SHORT_TOAST_DURATION = 2000;

  private Toaster() {}

  public static void makeLongToast(Context context,String text, long durationInMillis) {
    final Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
    t.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);

    new android.os.CountDownTimer(Math.max(durationInMillis - SHORT_TOAST_DURATION, 1000), 1000) {
      @Override
      public void onFinish() {
        t.show();
      }

      @Override
      public void onTick(long millisUntilFinished) {
        t.show();
      }
    }.start();
  }
}