package mobile.a3tech.com.a3tech.activity;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.CategorieAdapter;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.utils.Constant;

public class LangueFragment extends Activity {

	AlertDialog alertDialog;

	int city;
	String connectedUser = "";

	Spinner idSettings_spinnerLangue;

	String langue;
	List<Categorie> langues;
	CategorieAdapter languesAdapter;
	int lastPosition;

	LinearLayout idSetting_linearLayoutRetour;

	private OnItemSelectedListener languesListener = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> adapterView, View arg1,
				int position, long arg3) {
			if (position == 0) {
				Toast.makeText(LangueFragment.this.getApplicationContext(),
						getString(R.string.txtSettings_msgChoixLangue),
						Toast.LENGTH_SHORT).show();
			} else if (position != lastPosition) {
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(LangueFragment.this);
				startActivity(new Intent(LangueFragment.this,
						SplashActivity.class));
				finish();
				Editor editor = prefs.edit();
				if (position == 1) {
					editor.putString("ApplicationLanguage",
							Constant.LANGUAGE_FRENSH);
				} else {
					editor.putString("ApplicationLanguage",
							Constant.LANGUAGE_ARABIC);
				}
				editor.commit();
			}
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(1);
		setContentView(R.layout.langue_fragment);

		idSetting_linearLayoutRetour = (LinearLayout) findViewById(R.id.idSetting_linearLayoutRetour);

		idSettings_spinnerLangue = (Spinner) findViewById(R.id.idSettings_spinnerLangue);

		langues = new ArrayList<Categorie>();
		for (String string : getResources().getStringArray(R.array.languages)) {
			Categorie categorie = new Categorie();
			categorie.setLibelle(string);
			langues.add(categorie);
		}
		languesAdapter = new CategorieAdapter(getApplicationContext(), langues);
		idSettings_spinnerLangue.setAdapter(languesAdapter);
		idSettings_spinnerLangue.setOnItemSelectedListener(languesListener);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		connectedUser = prefs.getString("identifiant", "");

		langue = prefs.getString("ApplicationLanguage",
				Constant.LANGUAGE_FRENSH);

		 if (langue.equals(Constant.LANGUAGE_ARABIC)) {
			idSettings_spinnerLangue.setSelection(2);
			lastPosition = 2;
		} else {
			idSettings_spinnerLangue.setSelection(1);
			lastPosition = 1;
		}

		idSetting_linearLayoutRetour.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

}
