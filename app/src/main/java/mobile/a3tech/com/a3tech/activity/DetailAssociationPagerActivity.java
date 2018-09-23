package mobile.a3tech.com.a3tech.activity;

import java.util.ArrayList;
import java.util.List;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.DetailAssociationPagerAdapter;
import mobile.a3tech.com.a3tech.adapter.GalleryPictureAdapter;
import mobile.a3tech.com.a3tech.images.Image;
import mobile.a3tech.com.a3tech.manager.ImageManager;
import mobile.a3tech.com.a3tech.model.Indicateur;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.sliding.PagerAssociationItem;
import mobile.a3tech.com.a3tech.sliding.SlidingTabLayout;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.UtileColor;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class DetailAssociationPagerActivity extends FragmentActivity implements
		DataLoadCallback {

	Dialog countDialog;
	DetailAssociationPagerAdapter detailAssociationPagerAdapter;
	String associationId = "";
	LinearLayout idDetailAssociation_linearLayoutRetour;

	RelativeLayout idDetailAssociation_relativeLayoutGalerie;

	ImageView idStore_imageGalerie;
	TextView idStore_textCount;
	ImageLoader imageLoader;
	List<Image> images;
	SlidingTabLayout mSlidingTabLayout;
	private List<PagerAssociationItem> mTabs = new ArrayList<PagerAssociationItem>();
	ViewPager mViewPager;
	DisplayImageOptions options = new DisplayImageOptions.Builder()
			.cacheInMemory().cacheOnDisc().build();
	ProgressDialog progressDialog = null;
	String connectedUser = "";
	String password = "" ;

	private View.OnClickListener afficherImages = new View.OnClickListener() {
		public void onClick(View v) {
			progressDialog = CustomProgressDialog.createProgressDialog(
					DetailAssociationPagerActivity.this,
					DetailAssociationPagerActivity.this
							.getString(R.string.txtMenu_dialogChargement));
			ImageManager.getInstance().listeImages(associationId,connectedUser,password,
					DetailAssociationPagerActivity.this);
		}
	};

	private View.OnClickListener retourListener = new View.OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};

	private void showGalery() {
		View view = getLayoutInflater().inflate(R.layout.galerie, null);
		GridView categoriesGridView = (GridView) view
				.findViewById(R.id.categoriesGridView);
		Button idGallery_btn = (Button) view.findViewById(R.id.idGallery_btn);
		idGallery_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				countDialog.hide();
			}
		});
		categoriesGridView.setAdapter(new GalleryPictureAdapter(this, images
				.toArray(new Image[images.size()]), categoriesGridView));
		countDialog = new Dialog(this, R.style.TransparentDialog2);
		countDialog.setContentView(view);
		countDialog.getWindow().getAttributes();
		countDialog.getWindow().setLayout(-1, -1);
		countDialog.show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(1);
		setContentView(R.layout.detail_association_pager_activity);
		imageLoader = ImageLoader.getInstance();
		idDetailAssociation_linearLayoutRetour = (LinearLayout) findViewById(R.id.idDetailAssociation_linearLayoutRetour);
		idStore_imageGalerie = (ImageView) findViewById(R.id.idStore_imageGalerie);
		idStore_textCount = (TextView) findViewById(R.id.idStore_textCount);

		this.idDetailAssociation_relativeLayoutGalerie = (RelativeLayout) findViewById(R.id.idDetailAssociation_relativeLayoutGalerie);
		this.idDetailAssociation_relativeLayoutGalerie
				.setOnClickListener(this.afficherImages);
		this.idDetailAssociation_linearLayoutRetour
				.setOnClickListener(this.retourListener);
		Bundle b = getIntent().getExtras();
		if (b != null) {
			this.associationId = b
					.getString(Constant.KEY_EXTRA_DETAIL_ASSOCIATION_ID);
		}
		this.imageLoader.displayImage(Constant.CHECK_EDU_LOGOS_URL
				+ this.associationId + ".jpg", this.idStore_imageGalerie,
				this.options);
		this.mTabs.add(new PagerAssociationItem(0,
				getString(R.string.tab_association_zero), getResources()
						.getColor(UtileColor.colors[2]), Color.GRAY));
		this.mTabs.add(new PagerAssociationItem(1,
				getString(R.string.tab_association_tree), getResources()
						.getColor(UtileColor.colors[3]), Color.GRAY));

		this.mViewPager = ((ViewPager) findViewById(R.id.mPager));
		this.detailAssociationPagerAdapter = new DetailAssociationPagerAdapter(
				getSupportFragmentManager(), this.mTabs);
		this.mViewPager.setAdapter(this.detailAssociationPagerAdapter);
		this.mSlidingTabLayout = ((SlidingTabLayout) findViewById(R.id.mTabs));
		this.mSlidingTabLayout.setViewPager(this.mViewPager);
		this.mSlidingTabLayout
				.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
					public int getDividerColor(int dividColor) {
						return mTabs.get(dividColor).getDividerColor();
					}

					public int getIndicatorColor(int indicator) {
						return mTabs.get(indicator).getIndicatorColor();
					}
				});
		 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		  connectedUser = prefs.getString("identifiant", "");
		  password = prefs.getString("password", "");
		ImageManager.getInstance().nbrImages(connectedUser,password,associationId, this);
	}

	@Override
	public void dataLoaded(Object data, int method, int j) {
		switch (method) {
		case Constant.KEY_ASSOCIATION_NOMBRE_IMAGES:
			Indicateur indicateur = (Indicateur) data;
			idStore_textCount.setText(indicateur.getNbrImages());

			break;

		default:
			List<Image> imgs = (List<Image>) data;
			images = new ArrayList<Image>();
			images.addAll(imgs);
			progressDialog.dismiss();
			showGalery();
			break;
		}

	}

	@Override
	public void dataLoadingError(int errorCode) {
		// TODO Auto-generated method stub

	}
}
