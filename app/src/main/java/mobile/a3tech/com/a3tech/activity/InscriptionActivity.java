package mobile.a3tech.com.a3tech.activity;

import java.io.File;
import java.util.regex.Pattern;


import org.codehaus.jackson.util.MinimalPrettyPrinter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.images.Util;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.ImageManager;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class InscriptionActivity extends Activity implements DataLoadCallback {

	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_GALLERY = 2;
	AlertDialog alertDialog;

	ProgressDialog dialog = null;

	CheckBox idInscription_checkBoxCGU;
	EditText idInscription_editTextConfirmPassword;
	EditText idInscription_editTextEmail;
	EditText idInscription_editTextNom;
	EditText idInscription_editTextPassword;
	EditText idInscription_editTextPrenom;
	LinearLayout idInscription_linearLayoutRetour;
	LinearLayout idInscription_relativeLayoutSincrire;
	ImageView idInsription_buttonCamera;
	String image_str;
	String nom = "";
	String prenom = "";
	String password ="" ;

	private String mAgentPictureName;
	private String mAgentPicturePath;

	private OnClickListener retourListener = new OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(InscriptionActivity.this,
					LoginActivity.class));
			finish();
		}
	};

	private OnClickListener alertValiderListener = new OnClickListener() {
		public void onClick(View v) {
			alertDialog.dismiss();
		}
	};

	private OnClickListener creationCompteListener = new OnClickListener() {

		public void onClick(View v) {
			nom = idInscription_editTextNom.getText().toString();
			prenom = idInscription_editTextPrenom.getText().toString();
			String email = idInscription_editTextEmail.getText().toString();
			 password = idInscription_editTextPassword.getText()
					.toString();
			String confirm = idInscription_editTextConfirmPassword.getText()
					.toString();
			boolean check = idInscription_checkBoxCGU.isChecked();
			if (nom.length() == 0 || !isValidEmailAddress(email)
					|| password.length() == 0 || confirm.length() == 0
					|| !check || !password.equals(confirm)) {
				Toast.makeText(
						getApplicationContext(),
						getString(R.string.txtInscription_msgInformationCorrectes),
						Toast.LENGTH_SHORT).show();
				return;
			}
			dialog = CustomProgressDialog.createProgressDialog(
					InscriptionActivity.this,
					getString(R.string.txtMenu_dialogChargement));
			UserManager
					.getInstance()
					.createAccount(
							nom,
							prenom,
							email,
							password,
							image_str,
							"",
							new StringBuilder(String.valueOf(prenom))
									.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)
									.append(nom.substring(0, 1)).toString(),
							InscriptionActivity.this);
		}
	};

	private OnCheckedChangeListener conditionCheckedChangeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				Handler handler = new Handler();
				handler.post(new Runnable() {
					@Override
					public void run() {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								InscriptionActivity.this);

						LayoutInflater inflater = InscriptionActivity.this
								.getLayoutInflater();
						View content = inflater.inflate(
								R.layout.conditions_dialog_box, null);

						WebView idConditions_webviewContent = (WebView) content
								.findViewById(R.id.idConditions_webviewContent);
						LinearLayout idCondition_relativeLayoutValider = (LinearLayout) content
								.findViewById(R.id.idCondition_relativeLayoutValider);
						idCondition_relativeLayoutValider
								.setOnClickListener(alertValiderListener);
						builder.setView(content);

						idConditions_webviewContent
								.loadUrl(Constant.CGU_URL_FR);

						alertDialog = builder.create();
						alertDialog.show();
					}
				});
			}

		}
	};

	private OnClickListener cameraListener = new OnClickListener() {
		public void onClick(View v) {
			captureImage();
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(1);
		setContentView(R.layout.inscription_activity);
		idInscription_relativeLayoutSincrire = (LinearLayout) findViewById(R.id.idInscription_relativeLayoutSincrire);
		idInsription_buttonCamera = (ImageView) findViewById(R.id.idInsription_buttonCamera);
		idInscription_editTextNom = (EditText) findViewById(R.id.idInscription_editTextNom);
		idInscription_editTextPrenom = (EditText) findViewById(R.id.idInscription_editTextPrenom);
		idInscription_editTextEmail = (EditText) findViewById(R.id.idInscription_editTextEmail);
		idInscription_editTextPassword = (EditText) findViewById(R.id.idInscription_editTextPassword);
		idInscription_editTextConfirmPassword = (EditText) findViewById(R.id.idInscription_editTextConfirmPassword);
		idInscription_checkBoxCGU = (CheckBox) findViewById(R.id.idInscription_checkBoxCGU);
		idInscription_linearLayoutRetour = (LinearLayout) findViewById(R.id.idInscription_linearLayoutRetour);
		idInscription_relativeLayoutSincrire
				.setOnClickListener(creationCompteListener);
		idInscription_linearLayoutRetour.setOnClickListener(retourListener);
		idInscription_checkBoxCGU
				.setOnCheckedChangeListener(conditionCheckedChangeListener);

		idInsription_buttonCamera.setOnClickListener(cameraListener);
	}

	private void captureImage() {
		final String[] items = new String[] {
				getString(R.string.txtInscription_pictureItemPrendreImage),
				getString(R.string.txtInscription_pictureItemChoisirImage),
				getString(R.string.txtInscription_pictureItemAnnuler) };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.txtInscription_pictureTitleDialog));
		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				File localFile = new File(Environment
						.getExternalStorageDirectory().toString()
						+ "/.KhodaraTempImages");
				if (!localFile.exists()) {
					localFile.mkdirs();
				}
				mAgentPictureName = (System.currentTimeMillis() + ".jpg");
				mAgentPicturePath = (localFile.getAbsolutePath() + "/" + mAgentPictureName);
				switch (item) {
				case 2:
					dialog.dismiss();
					return;
				case 0:
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(
							MediaStore.EXTRA_OUTPUT,
							Uri.fromFile(new File(localFile,
									InscriptionActivity.this.mAgentPictureName)));
					try {
						intent.putExtra("return-data", true);
						startActivityForResult(intent, PICK_FROM_CAMERA);
					} catch (Exception e) {
						// TODO: handle exception
					}
					break;
				case 1:
					Intent intent2 = new Intent();
					intent2.setType("image/*");
					intent2.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(Intent.createChooser(intent2,
							"Complete action using"), PICK_FROM_GALLERY);
				}

			}
		};
		builder.setItems(items, listener);
		builder.show();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != -1) {
			return;
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

					idInsription_buttonCamera.setImageBitmap(picture);
					image_str = ImageManager.getInstance().getString(picture);

				}
			} catch (Exception e) {
			}
			Util.deleteDirectory(new File(new StringBuilder(String
					.valueOf(Environment.getExternalStorageDirectory()
							.getAbsolutePath())).append("/.KhodaraTempImages")
					.toString()));
		}
	}



	private boolean isValidEmailAddress(String emailAddress) {
		return Pattern
				.compile(
						"^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+.([a-zA-Z])+([a-zA-Z])+",
						Pattern.CASE_INSENSITIVE).matcher(emailAddress).matches();
	}

	public void dataLoaded(Object data, int method, int i) {
		User user = (User) data;
		Editor editor = PreferenceManager.getDefaultSharedPreferences(
				getApplicationContext()).edit();
		editor.putString("identifiant", user.getIdentifiant());
		editor.putString("facebookId", user.getFacebookId());
		editor.putString("password", password);
		editor.putString("pseudo", user.getPseudo());
		editor.putString("mode", user.getMode());
		editor.putString("conMode", "application");
		editor.commit();
		this.dialog.dismiss();
		Intent mainIntent = new Intent(this, NavigationMain.class);
		mainIntent.putExtra("nomPrenom", this.prenom
				+ MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.nom);
		mainIntent.putExtra("nbr", user.getNbrServiceEmis());
		startActivity(mainIntent);
		finish();
	}

	public void onBackPressed() {
		startActivity(new Intent(this, LoginActivity.class));
		finish();
	}

	public void dataLoadingError(int errorCode) {
		if (errorCode == 0) {
			try {
				Toast.makeText(getApplicationContext(), "erreur",
						Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (errorCode == 1) {
			Toast.makeText(
					getApplicationContext(),
					getString(R.string.txtInscription_msg_verification_connexion_internet),
					Toast.LENGTH_SHORT).show();
		}
		if (errorCode == 10) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.txtInscription_msg_compte_existe_deja),
					Toast.LENGTH_SHORT).show();
		}
		this.dialog.dismiss();
	}

	public void dataLoadingError(String error) {
	}
}
