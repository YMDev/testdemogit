package mobile.a3tech.com.a3tech.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.fragment.A3techAddUserNameFragment;
import mobile.a3tech.com.a3tech.fragment.A3techSelecteAccountFragment;
import mobile.a3tech.com.a3tech.fragment.A3techStep1CreatAccountFragment;
import mobile.a3tech.com.a3tech.model.User;

public class A3techCreateAccountActivity extends Activity implements A3techSelecteAccountFragment.OnFragmentInteractionListener, A3techStep1CreatAccountFragment.OnFragmentInteractionListener, A3techAddUserNameFragment.OnFragmentInteractionListener {
    ProgressBar progressbarAccountCreation;
    FrameLayout frameView;
    User account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        account = new User();
        setContentView(R.layout.a3tech_create_account_activity);

        frameView = findViewById(R.id.frame_create_account);
        progressbarAccountCreation = findViewById(R.id.progress_creation_account);
        setFragment(new A3techStep1CreatAccountFragment(), false, false);
    }

    protected void setFragment(Fragment fragment, boolean withAnim, boolean back) {
        FragmentTransaction t = getFragmentManager().beginTransaction();
        if (withAnim) {
            if (!back) {
                t.setCustomAnimations(R.animator.slide_in_left,
                        R.animator.slide_in_right, 0, 0);
            } else {
                t.setCustomAnimations(R.animator.slide_in_right_back,
                        R.animator.slide_in_left_back, 0, 0);
            }
        }
        t.replace(R.id.frame_create_account, fragment);
        t.commit();
    }

    private void progressBarchangeSmouthly(int progress) {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            // will update the "progress" propriety of seekbar until it reaches progress
            ObjectAnimator animation = ObjectAnimator.ofInt(progressbarAccountCreation, "progress", progress);
            animation.setDuration(500); // 0.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        } else
            progressbarAccountCreation.setProgress(progress); // no animation on Gingerbread or lower
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void backAction() {
        progressBarchangeSmouthly(progressbarAccountCreation.getProgress() - 20);
        switch (progressbarAccountCreation.getProgress() - 20) {
            case 0:
                setFragment(new A3techStep1CreatAccountFragment(), true, true);
                break;
            case 20:
                setFragment(new A3techSelecteAccountFragment(), true, true);
                break;
            case 40:
                setFragment(new A3techAddUserNameFragment(), true, true);
                break;
            default:
               // finish();
        }
    }

    @Override
    public void actionNext(Integer typeAction, Object data) {
        switch (typeAction) {
            case A3techSelecteAccountFragment.ACTION_SELECT_ACCOUNT:
                progressBarchangeSmouthly(progressbarAccountCreation.getProgress() + 20);
                if (((Integer) data) == A3techSelecteAccountFragment.ACCOUNT_HIRE) {
                    account.setCategorie("HIRE");
                } else if (((Integer) data) == A3techSelecteAccountFragment.ACCOUNT_HIRE) {
                    account.setCategorie("WORK");
                }
                setFragment(new A3techAddUserNameFragment(), true, false);
                break;
            case A3techAddUserNameFragment.ACTION_TYPE_USERNAME:
                progressBarchangeSmouthly(progressbarAccountCreation.getProgress() + 20);
                account.setNom(String.valueOf(data));
                System.out.print(account.getCategorie() + "  " + account.getNom());
                //setFragment(new A3techSelecteAccountFragment(), true, false);
                break;


        }
    }

    @Override
    public void actionNext(Integer typeAction) {
        switch (typeAction) {
            case A3techStep1CreatAccountFragment.ACTION_NEXT_CREATE:
                progressBarchangeSmouthly(progressbarAccountCreation.getProgress() + 20);
                setFragment(new A3techSelecteAccountFragment(), true, false);
                break;
            case A3techStep1CreatAccountFragment.ACTION_NEXT_FACEBOOK:
                break;

        }

    }
}
