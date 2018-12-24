package mobile.a3tech.com.a3tech.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

import eltos.simpledialogfragment.SimpleDialog;
import eltos.simpledialogfragment.form.Input;
import eltos.simpledialogfragment.form.SimpleFormDialog;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.A3techProfilFragmentAdapter;
import mobile.a3tech.com.a3tech.adapter.A3techProfileInformationAdapter;
import mobile.a3tech.com.a3tech.fragment.A3techProfilInformationFragment;
import mobile.a3tech.com.a3tech.fragment.A3techReviewsFragment;
import mobile.a3tech.com.a3tech.images.Util;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTechnicien;
import mobile.a3tech.com.a3tech.utils.ImageCompression;
import mobile.a3tech.com.a3tech.utils.ImageManager;
import mobile.a3tech.com.a3tech.utils.ImagesStuffs;
import mobile.a3tech.com.a3tech.utils.LetterTileProvider;
import mobile.a3tech.com.a3tech.view.CircleImageView;

public class A3techViewEditProfilActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener, A3techReviewsFragment.OnFragmentInteractionListener, SimpleDialog.OnDialogResultListener {
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.72f;
    private static final float PERCENTAGE_TO_SHOW_TOOLBAR = 0.7f;
    private static final float PERCENTAGE_TO_HIDE_CIRCLE_IMAGE = 0.5f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    private static final int ALPHA_ANIMATIONS_DURATION_TOOL = 400;
    public static final String ARG_USER_OBJECT = "ARG_USER_OBJECT";
    public static final String MODE_MY_ACCOUNT = "MODE_MY_ACCOUNT";
    public static final String ARG_SRC_ACTION = "ARG_SRC_ACTION";
    public static final String ARG_MISSION_OBJECT = "ARG_MISSION_OBJECT";
    public static final String TAG_IGNORE_MODIFICATION_DIALOGUE  = "TAG_IGNORE_MODIFICATION_DIALOGUE";
    private static final int PICK_FROM_CAMERA = 31;
    private static final int PICK_FROM_GALLERY = 32;
    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
    private boolean mIsTheToolbarVisible = false;
    private boolean mIsTheCircleVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;

    private TabLayout tabLayouProfil;
    private ViewPager viewPagerProfil;
    private A3techProfilFragmentAdapter adaoter;

    private ImageView editAbout, editCoordonnees;
    private TextView userNamePname, categoryUser;
    private Boolean isFromHomeAccount = false;
    private Boolean isFromAddMission = false;
    private Boolean isFromBrowseTech = false;
    private Boolean isFromDisplayMission = false;
    private Boolean isModeEdition = false;
    private LinearLayout validationContainer;
    private LinearLayout containerNameUSer;
    private Button btnValidationSelection;
    A3techUser userToDisplay;
    A3techMission mission;
    private FloatingActionButton fabEdit, fabHire, fabSave;
    private RelativeLayout frameViewLayout;
    private TextView titleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String jsonMyObject = null;
        String jsonMyObjectMission = null;
        String srcAction = null;
        Bundle b = getIntent().getExtras();
        if (b != null) {
            jsonMyObject = b.getString(ARG_USER_OBJECT);
            srcAction = b.getString(ARG_SRC_ACTION);
            jsonMyObjectMission = b.getString(ARG_MISSION_OBJECT);
        }
        if (jsonMyObject != null) {
            userToDisplay = new Gson().fromJson(jsonMyObject, A3techUser.class);
        }
        if (jsonMyObjectMission != null) {
            mission = new Gson().fromJson(jsonMyObjectMission, A3techMission.class);
        } else {
            mission = new A3techMission();
        }
        if (srcAction != null && srcAction.equalsIgnoreCase(A3techHomeActivity.ACTION_FROM_A3techHomeActivity)) {
            isFromHomeAccount = true;
            isFromBrowseTech = false;
            isFromAddMission = false;
            isFromDisplayMission = false;
        } else if (srcAction != null && srcAction.equalsIgnoreCase(SimpleAdapterTechnicien.SRC_FROM_HOME_ADD_MISSION)) {
            isFromAddMission = true;
            isFromHomeAccount = false;
            isFromBrowseTech = false;
            isFromDisplayMission = false;
        } else if (srcAction != null && srcAction.equalsIgnoreCase(SimpleAdapterTechnicien.SRC_FROM_HOME_BROWSE_TECH)) {
            isFromBrowseTech = true;
            isFromHomeAccount = false;
            isFromAddMission = false;
            isFromDisplayMission = false;
        } else if (srcAction != null && srcAction.equalsIgnoreCase(A3techDisplayMissionActivity.SRC_FROM_DISPLAY_MISSION)) {
            isFromBrowseTech = false;
            isFromHomeAccount = false;
            isFromAddMission = false;
            isFromDisplayMission = true;
        }


        setContentView(R.layout.a3tech_actvity_view_edit_profile_v2);

        bindActivity();
        adaoter = new A3techProfilFragmentAdapter(getSupportFragmentManager(),
                A3techViewEditProfilActivity.this, userToDisplay);
        adaoter.setModeMyAccount(isFromHomeAccount);
        viewPagerProfil.setAdapter(adaoter);

        tabLayouProfil.post(new Runnable() {
            @Override
            public void run() {
                tabLayouProfil.setupWithViewPager(viewPagerProfil);
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(this);
        setTabBG(R.drawable.tab_left_select, R.drawable.tab_right_unselect);
        tabLayouProfil.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerProfil.setCurrentItem(tab.getPosition());
                if (tabLayouProfil.getSelectedTabPosition() == 0) {
                    setTabBG(R.drawable.tab_left_select, R.drawable.tab_right_unselect);
                } else {
                    setTabBG(R.drawable.tab_left_unselect, R.drawable.tab_right_select);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        startAlphaAnimation(titleToolbar, 0, View.INVISIBLE);

    }

    private void bindActivity() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        viewPagerProfil = (ViewPager) findViewById(R.id.viewpager_profile_detail);
        tabLayouProfil = (TabLayout) findViewById(R.id.tabs_profil);
        fabEdit = findViewById(R.id.fabEdit);
        fabHire = findViewById(R.id.fabhire);
        fabSave = findViewById(R.id.fabSave);
        frameViewLayout = findViewById(R.id.frame_view_account_detail);
        editAbout = findViewById(R.id.edit_action_about);
        editCoordonnees = findViewById(R.id.edit_action_coordonnees);
        containerNameUSer = findViewById(R.id.container_name_user);
        final LetterTileProvider tileProvider = new LetterTileProvider(A3techViewEditProfilActivity.this);
        final Bitmap letterTile = tileProvider.getLetterTile(userToDisplay.getNom(), userToDisplay.getNom(), 88, 88);
        titleToolbar = findViewById(R.id.user_name_toolbar);

        userNamePname = findViewById(R.id.txt_name_p);
        String nameConnectedUser = "";
        String nameConnectedUserToolbar = "";
        if (userToDisplay != null && userToDisplay.getNom() != null && userToDisplay.getPrenom() != null) {
            nameConnectedUser = userToDisplay.getNom().toUpperCase() + " " + userToDisplay.getPrenom().toUpperCase();
            nameConnectedUserToolbar = userToDisplay.getNom().toUpperCase() + " " + userToDisplay.getPrenom().toUpperCase().substring(0,1)+".";
        }
        titleToolbar.setText(nameConnectedUserToolbar);
        userNamePname.setText(nameConnectedUser);
        categoryUser = findViewById(R.id.txt_category);
        if (userToDisplay.getCategorie() != null){
            categoryUser.setVisibility(View.VISIBLE);
            categoryUser.setText(userToDisplay.getCategorie().getDescription());
        }else{
            categoryUser.setVisibility(View.GONE);
        }


        /*backBtn = findViewById(R.id.back_home_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                A3techViewEditProfilActivity.this.finish();
            }
        });*/
        if (isFromHomeAccount) {
            fabHire.setVisibility(View.GONE);
            fabSave.setVisibility(View.GONE);
            fabEdit.setVisibility(View.VISIBLE);
        } else {
            fabHire.setVisibility(View.VISIBLE);
            fabSave.setVisibility(View.GONE);
            fabEdit.setVisibility(View.GONE);
        }

        fabHire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mission.setTechnicien(userToDisplay);
                Intent resultIntent = new Intent();
                resultIntent.putExtra(A3techAddMissionActivity.TAG_RESULT_FROM_SELECT_TECH, new Gson().toJson(mission));
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });


        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO save profile change
                enableEditionMode(Boolean.FALSE);
            }
        });

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableEditionMode(Boolean.TRUE);
            }
        });
    }

    private void enableEditionMode(Boolean isModeEditionV) {
        if (isModeEditionV) {
            startAlphaAnimation(fabEdit, 200, View.INVISIBLE);
            startAlphaAnimation(fabSave, 200, View.VISIBLE);
            //pour lancer le mode edition dy fragment "PROFIL"
            if (mDataUpdateListener != null) mDataUpdateListener.onDataUpdate(true);
            isModeEdition = Boolean.TRUE;
        } else {
            startAlphaAnimation(fabEdit, 200, View.VISIBLE);
            startAlphaAnimation(fabSave, 200, View.INVISIBLE);
            //pour lancer le mode edition dy fragment "PROFIL"
            if (mDataUpdateListener != null) mDataUpdateListener.onDataUpdate(false);
            isModeEdition = Boolean.FALSE;
            mAppBarLayout.setExpanded(true);

        }

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
        handleToolbarTitleVisibility(percentage);
        /*handleToolbarTitleVisibility(percentage);
       // handleAlphaOnToolbar(percentage);*/
        handleAlphaOnCircleImage(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(titleToolbar, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(titleToolbar, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(titleToolbar, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(titleToolbar, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    private void handleAlphaOnToolbar(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TOOLBAR) {
            if (mIsTheToolbarVisible) {
                startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION_TOOL, View.VISIBLE);
                mIsTheToolbarVisible = false;
            }

        } else {

            if (!mIsTheToolbarVisible) {
                startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION_TOOL, View.INVISIBLE);
                mIsTheToolbarVisible = true;
            }
        }
    }


    private void handleAlphaOnCircleImage(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_CIRCLE_IMAGE) {
            if (mIsTheCircleVisible) {
                startAlphaAnimation(containerNameUSer, ALPHA_ANIMATIONS_DURATION_TOOL, View.INVISIBLE);
                if (isFromHomeAccount) {
                    if (!isModeEdition) {
                        startAlphaAnimation(fabEdit, ALPHA_ANIMATIONS_DURATION_TOOL, View.INVISIBLE);
                    } else {
                        /*startAlphaAnimation(fabSave, ALPHA_ANIMATIONS_DURATION_TOOL, View.GONE);*/
                    }
                } else {
                    startAlphaAnimation(fabHire, ALPHA_ANIMATIONS_DURATION_TOOL, View.INVISIBLE);
                }

                mIsTheCircleVisible = false;
            }

        } else {

            if (!mIsTheCircleVisible) {
                startAlphaAnimation(containerNameUSer, ALPHA_ANIMATIONS_DURATION_TOOL, View.VISIBLE);
                if (isFromHomeAccount) {
                    if (!isModeEdition) {
                        startAlphaAnimation(fabEdit, ALPHA_ANIMATIONS_DURATION_TOOL, View.VISIBLE);
                    } else {
                        /*startAlphaAnimation(fabSave, ALPHA_ANIMATIONS_DURATION_TOOL, View.VISIBLE);*/
                    }
                } else {
                    startAlphaAnimation(fabHire, ALPHA_ANIMATIONS_DURATION_TOOL, View.VISIBLE);
                }
                mIsTheCircleVisible = true;
            }
        }
    }


    private void adjusteMArginForFrameDetailAccount(int top) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        int topDpx = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, top, getResources().getDisplayMetrics()));
        layoutParams.setMargins(0, topDpx, 0, 50);
        frameViewLayout.setLayoutParams(layoutParams);
    }

    public void startAlphaAnimation(View v, long duration, int visibility) {
        Animation alphaAnimation = (visibility == View.VISIBLE)
                ? AnimationUtils.loadAnimation(A3techViewEditProfilActivity.this, R.anim.scale_up)
                : AnimationUtils.loadAnimation(A3techViewEditProfilActivity.this, R.anim.scale_down);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        v.startAnimation(alphaAnimation);
        v.setVisibility(visibility);
    }

    private void setTabBG(int tab1, int tab2) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ViewGroup tabStrip = (ViewGroup) tabLayouProfil.getChildAt(0);
            View tabView1 = tabStrip.getChildAt(0);
            View tabView2 = tabStrip.getChildAt(1);
            if (tabView1 != null) {
                int paddingStart = tabView1.getPaddingStart();
                int paddingTop = tabView1.getPaddingTop();
                int paddingEnd = tabView1.getPaddingEnd();
                int paddingBottom = tabView1.getPaddingBottom();
                ViewCompat.setBackground(tabView1, AppCompatResources.getDrawable(tabView1.getContext(), tab1));
                ViewCompat.setPaddingRelative(tabView1, paddingStart, paddingTop, paddingEnd, paddingBottom);
            }

            if (tabView2 != null) {
                int paddingStart = tabView2.getPaddingStart();
                int paddingTop = tabView2.getPaddingTop();
                int paddingEnd = tabView2.getPaddingEnd();
                int paddingBottom = tabView2.getPaddingBottom();
                ViewCompat.setBackground(tabView2, AppCompatResources.getDrawable(tabView2.getContext(), tab2));
                ViewCompat.setPaddingRelative(tabView2, paddingStart, paddingTop, paddingEnd, paddingBottom);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*getMenuInflater().inflate(R.menu.menu_view_profil, menu);*/
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }




   /* @Override
    public void editAboutActionEnabled(String oldVal) {
        displayAboutEditDialogue(oldVal);
    }*/

    private static final String TAG_EDIT_ABOUT_INPUT = "TAG_EDIT_ABOUT_INPUT";
    private static final String TAG_EDIT_ABOUT_DIALOG = "TAG_EDIT_ABOUT_DIALOG";

    private void displayAboutEditDialogue(String aboutOld) {
        SimpleFormDialog.build().title(getResources().getString(R.string.edit_about_profil)).cancelable(true)
                .fields(Input.plain(TAG_EDIT_ABOUT_INPUT).required(true).text(aboutOld)
                        .hint(R.string.hint_edit_add_about_profil)
                        .inputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE))
                .show(A3techViewEditProfilActivity.this, TAG_EDIT_ABOUT_DIALOG);
    }

    DataUpdateListener mDataUpdateListener;

    @Override
    public boolean onResult(@NonNull String dialogTag, int which, @NonNull Bundle extras) {
        if(dialogTag.equals(TAG_IGNORE_MODIFICATION_DIALOGUE)){
            if(which == BUTTON_POSITIVE){
                enableEditionMode(false);
            }
        }
        return false;
    }

    public interface DataUpdateListener {
        void onDataUpdate(Boolean modeEdit);
    }

    public void setmDataUpdateListener(DataUpdateListener mDataUpdateListener) {
        this.mDataUpdateListener = mDataUpdateListener;
    }

    private String mAgentPictureName;
    private String mAgentPicturePath;
    Uri imageUri;

    private void selectImage() {
        CharSequence[] items = new CharSequence[]{
                getString(R.string.txtPostage2_attachementItemPrendreImage),
                getString(R.string.txtPostage2_attachementItemChoisirImage),
                getString(R.string.txtPostage2_attachementItemAnnuler)};
        AlertDialog.Builder builder = new AlertDialog.Builder(A3techViewEditProfilActivity.this);
        builder.setTitle(getString(R.string.txtPostage2_textViewAttachmentTitleDialog));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                switch (item) {
                    case 0 /* 0 */:
                        String[] permission = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA"};
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(permission, 201);
                        }

                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "New Picture");
                        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                        imageUri = getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, PICK_FROM_CAMERA);
                        break;
                    case 1 /* 1 */:
                        String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.READ_INTERNAL_STORAGE"};
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(permissions, 201);
                        }
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }

                        Intent intentPck = new Intent();
                        intentPck.setType("image/*");
                        intentPck.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intentPck, "Select Picture"), PICK_FROM_GALLERY);
                        break;
                    default:
                        dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    String image_str;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) {
            return;
        }
        if (requestCode == PICK_FROM_GALLERY) {
            Uri selectedImage = data.getData();
            try {
                ImageCompression ic = new ImageCompression(getBaseContext());
                // Bitmap selectedPic = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                Bitmap photo = ic.compressImage(ImagesStuffs.getRealPathFromURI(A3techViewEditProfilActivity.this, selectedImage));
                if (photo != null) {
                    image_str = ImageManager.getInstance().getString(photo);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        if (requestCode == PICK_FROM_CAMERA) {
            try {

                ImageCompression ic = new ImageCompression(getBaseContext());
                Bitmap photo = ic.compressImage(ImagesStuffs.getRealPathFromURI(A3techViewEditProfilActivity.this, imageUri));
                if (photo != null) {
                    image_str = ImageManager.getInstance().getString(photo);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_FROM_CAMERA || requestCode == PICK_FROM_GALLERY) {
            if (requestCode == PICK_FROM_GALLERY) {
                Util.saveImage(Util.getBitmap(
                        Util.getRealPathFromURI(this, data.getData()),
                        Util.MAX_IMAGE_SIZE), mAgentPictureName.replace(".jpg",
                        ""));
            }
            try {
                Bitmap picture = Util.getBitmap(mAgentPicturePath,
                        Util.MAX_IMAGE_SIZE);
                if (picture != null) {
                    picture = Util.adjustImageOrientation(mAgentPicturePath,
                            picture);
                } else {
                    new File(mAgentPicturePath).exists();
                }
                if (picture != null) {
                    image_str = ImageManager.getInstance().getString(picture);
                }
            } catch (Exception e) {
            }
            Util.deleteDirectory(new File(new StringBuilder(String
                    .valueOf(Environment.getExternalStorageDirectory()
                            .getAbsolutePath())).append("/KhodaraTempImages")
                    .toString()));
        }
    }


    @Override
    public void onBackPressed() {

        if (isModeEdition) {
            SimpleDialog.build().theme(R.style.SimpleDialogThemeProfile).title(R.string.ignore_modifications_label).msg(R.string.ignore_modifications_msg).pos(R.string.save).neg(R.string.cancel).show(A3techViewEditProfilActivity.this, TAG_IGNORE_MODIFICATION_DIALOGUE);
        }else{
            super.onBackPressed();
        }
    }
}
