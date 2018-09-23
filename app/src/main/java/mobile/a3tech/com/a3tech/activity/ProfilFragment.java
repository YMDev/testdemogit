package mobile.a3tech.com.a3tech.activity;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.CategorieAdapter;
import mobile.a3tech.com.a3tech.images.Util;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.manager.VilleManager;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.ImageManager;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class ProfilFragment extends Activity implements DataLoadCallback {
   
    SimpleDateFormat DbdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);
   
    
    private String connectedUser;
    private String password ;
    int count = 0;
    protected DateFormat dateFormat;
    AlertDialog alertDialog;
    EditText idNousEcrireDialog_editTextMessage ;
    String email = "";
    Uri fileUri;
    Categorie hintVille;
    ImageView idProfil_buttonCamera;
    EditText idProfil_editTextAdresse ;
    EditText idProfil_editTextEmail;
    EditText idProfil_editTextMobile;
    EditText idProfil_editTextNom;
    EditText idProfil_editTextPrenom;
    EditText idProfil_editTextPseudo;
    LinearLayout idProfil_linearLayoutSave;
  
    Spinner idProfil_spinnerVille;
    String idVille;
    public ImageLoader imageLoader;
    String image_str = "";
    String lang;
    String nom = "";
    DisplayImageOptions options = new Builder().cacheInMemory().cacheOnDisc().build();
    int pays;
    String prenom = "";
    ProgressDialog progressDialog = null;
    String pseudo = "";
    
    String telephone = "";
    String adresse = "" ;
    User user;
    List<Categorie> villes;
    CategorieAdapter villesAdapter;
    LinearLayout idProfil_linearLayoutRetour ;
    
    LinearLayout idProfil_linearLayoutFacebook ;
    ImageView idProfil_imageviewFacebookCheck ;
    LinearLayout idProfil_linearLayoutMail ;
    ImageView idProfil_imageviewMailCheck;
    LinearLayout idProfil_linearLayoutPhone ;
    ImageView idProfil_imageviewPhoneCheck ;
    String src = "" ;
  
    private String mAgentPictureName;
	private String mAgentPicturePath;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_GALLERY = 2;
	public static final int REQUEST_CAMERA = 1000;
	public static final int SELECT_FILE = 1001;
	String code ;
    
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(1);
		setContentView(R.layout.profil_fragment);
		dateFormat = android.text.format.DateFormat.getMediumDateFormat(getApplicationContext());
		idProfil_linearLayoutRetour = (LinearLayout) findViewById(R.id.idProfil_linearLayoutRetour);
        idProfil_editTextNom = (EditText) findViewById(R.id.idProfil_editTextNom);
        idProfil_editTextPrenom = (EditText) findViewById(R.id.idProfil_editTextPrenom);
        idProfil_editTextEmail = (EditText) findViewById(R.id.idProfil_editTextEmail);
        idProfil_editTextPseudo = (EditText) findViewById(R.id.idProfil_editTextPseudo);
        idProfil_editTextAdresse = (EditText) findViewById(R.id.idProfil_editTextAdresse);
        
        idProfil_editTextMobile = (EditText) findViewById(R.id.idProfil_editTextMobile);
        idProfil_linearLayoutSave = (LinearLayout) findViewById(R.id.idProfil_linearLayoutSave);
        idProfil_buttonCamera = (ImageView) findViewById(R.id.idProfil_buttonCamera);
        idProfil_spinnerVille = (Spinner) findViewById(R.id.idProfil_spinnerVille);
        imageLoader = ImageLoader.getInstance();
        
        
         idProfil_linearLayoutFacebook = (LinearLayout) findViewById(R.id.idProfil_linearLayoutFacebook);
         idProfil_imageviewFacebookCheck = (ImageView) findViewById(R.id.idProfil_imageviewFacebookCheck);
         idProfil_linearLayoutMail = (LinearLayout) findViewById(R.id.idProfil_linearLayoutMail);
         idProfil_imageviewMailCheck = (ImageView) findViewById(R.id.idProfil_imageviewMailCheck);
         idProfil_linearLayoutPhone = (LinearLayout) findViewById(R.id.idProfil_linearLayoutPhone);
         idProfil_imageviewPhoneCheck = (ImageView) findViewById(R.id.idProfil_imageviewPhoneCheck);
         idProfil_linearLayoutPhone.setOnClickListener(sendSMSListener);
        idProfil_linearLayoutSave.setOnClickListener(saveProfilListener);
        idProfil_imageviewMailCheck.setOnClickListener(sendemailListener);
        
        progressDialog = CustomProgressDialog.createProgressDialog(this, getString(R.string.txtMenu_dialogChargement));
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        connectedUser = prefs.getString("identifiant", "");
        password = prefs.getString("password", "");
        idProfil_buttonCamera.setOnClickListener(captureListener);
        idProfil_linearLayoutRetour.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
        Bundle b = getIntent().getExtras();
        if (b != null) {
            src = b.getString("src");
        }
        
        lang = prefs.getString("ApplicationLanguage", Constant.LANGUAGE_FRENSH);
        pays = prefs.getInt("city", 3);
        hintVille = new Categorie();
        hintVille.setLibelle(getString(R.string.txtProfil_spinnerVille));
        villes = new ArrayList<Categorie>();
        villes.add(hintVille);
        villesAdapter = new CategorieAdapter(this.getApplicationContext(), villes);
        idProfil_spinnerVille.setAdapter(villesAdapter);
        idProfil_spinnerVille.setSelection(0);
        imageLoader.displayImage(new StringBuilder(Constant.CHECK_EDU_PICTURES_URL).append(connectedUser).append(".jpg").toString(), idProfil_buttonCamera, options);
        UserManager.getInstance().getProfil(connectedUser,password, this);
        VilleManager.getInstance().listeVilles(String.valueOf(pays), lang,connectedUser,password, this);
      
    }

 
    private OnClickListener sendSMSListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(idProfil_editTextMobile.getText().toString().equals("")){
				Toast.makeText(getApplicationContext(), R.string.txtProfil_msgChoixTelephone, Toast.LENGTH_LONG).show();
			}else{
				progressDialog = CustomProgressDialog.createProgressDialog(ProfilFragment.this, getString(R.string.txtMenu_dialogChargement));
				UserManager.getInstance().checkMobile(idProfil_editTextMobile.getText().toString(),connectedUser,password, ProfilFragment.this);
			}
		}
	};
	private OnClickListener sendemailListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(idProfil_editTextEmail.getText().toString().equals("")){
				 Toast.makeText(getApplicationContext(), R.string.txtProfil_msgChoixEmail, Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(getApplicationContext(), R.string.txtProfil_msgValidationEmail, Toast.LENGTH_LONG).show();
				//progressDialog = CustomProgressDialog.createProgressDialog(ProfilFragment.this, getString(R.string.txtMenu_dialogChargement));
				UserManager.getInstance().checkMail(idProfil_editTextEmail.getText().toString(),connectedUser,password, ProfilFragment.this);
			}
		}
	};
    
    private OnClickListener saveProfilListener = new  OnClickListener() {
		@Override
		public void onClick(View v) {
			 nom = idProfil_editTextNom.getText().toString();
	         prenom = idProfil_editTextPrenom.getText().toString();
	         pseudo = idProfil_editTextPseudo.getText().toString();
	         telephone = idProfil_editTextMobile.getText().toString();
	         adresse = idProfil_editTextAdresse.getText().toString();
            if (idProfil_spinnerVille.getSelectedItemPosition() == 0) {
                Toast.makeText(getApplicationContext(), R.string.txtProfil_msgChoixVille, Toast.LENGTH_SHORT).show();
                ((TextView) idProfil_spinnerVille.getSelectedView()).setError(getString(R.string.txtProfil_msgChoixVille));
                return;
            }
            String ville = ((Categorie) villes.get(idProfil_spinnerVille.getSelectedItemPosition())).getIdentifiant();
            progressDialog = CustomProgressDialog.createProgressDialog(ProfilFragment.this, getString(R.string.txtMenu_dialogChargement));
            UserManager.getInstance().saveProfil(connectedUser, nom, prenom, telephone, pseudo, null, null, null, null, image_str, null, null, null, null, ville,adresse,password, ProfilFragment.this);
		}
	};

   

    public void dataLoadingError(int errorCode) {
        Toast.makeText(getApplicationContext(), getString(R.string.txtString_error_chargement), Toast.LENGTH_SHORT).show();
        this.progressDialog.dismiss();
    }
    private OnClickListener captureListener = new OnClickListener() {
		public void onClick(View v) {
		
			selectImage();
		}
	};

	
	private void selectImage() {
		CharSequence[] items = new CharSequence[] {
				getString(R.string.txtPostage2_attachementItemPrendreImage),
				getString(R.string.txtPostage2_attachementItemChoisirImage),
				getString(R.string.txtPostage2_attachementItemAnnuler) };
		AlertDialog.Builder builder = new AlertDialog.Builder(ProfilFragment.this);
		builder.setTitle(getString(R.string.txtPostage2_textViewAttachmentTitleDialog));
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				File folder = new File(new StringBuilder(String
						.valueOf(Environment.getExternalStorageDirectory()
								.toString())).append("/.KhodaraTempImages")
						.toString());
				if (!folder.exists()) {
					folder.mkdirs();
				}
				mAgentPictureName = System.currentTimeMillis() + ".jpg";
				mAgentPicturePath = folder.getAbsolutePath() + "/"
						+ mAgentPictureName;
				switch (item) {
				case 0 /* 0 */:
					Intent intent = new Intent(
							"android.media.action.IMAGE_CAPTURE");
					intent.putExtra("output",
							Uri.fromFile(new File(folder, mAgentPictureName)));
					intent.putExtra("return-data", true);
					startActivityForResult(intent, PICK_FROM_CAMERA);
					break;
				case 1 /* 1 */:
					Toast.makeText(ProfilFragment.this, getString(R.string.txtProfil_choisirdelagalerie), Toast.LENGTH_LONG).show();
					Intent intent2 = new Intent();
					intent2.setType("image/*");
					intent2.setAction("android.intent.action.GET_CONTENT");
					intent2.putExtra("return-data", true);
					startActivityForResult(intent2, PICK_FROM_GALLERY);
					break;
				default:
					dialog.dismiss();
				}
			}
		});
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
					idProfil_buttonCamera.setImageBitmap(picture);
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
	
	
	private OnClickListener annulerListener = new OnClickListener() {

		public void onClick(View v) {
			alertDialog.dismiss();
		}
	};
	
	private OnClickListener validerCodeListener = new OnClickListener() {
		public void onClick(View v) {
			if(code.equals(idNousEcrireDialog_editTextMessage.getText().toString())){
				alertDialog.dismiss();
				progressDialog = CustomProgressDialog.createProgressDialog(ProfilFragment.this, getString(R.string.txtMenu_dialogChargement));
				UserManager.getInstance().validerMobile(connectedUser,password, ProfilFragment.this);
			}else{
				Toast.makeText(getApplicationContext(), "Code saisi erron� !", Toast.LENGTH_SHORT).show();
			}
			
		}
	};
	
	public void validerMobile(){
		new Handler().post(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), getString(R.string.txtProfil_veuillezpatienter),Toast.LENGTH_LONG).show();
				AlertDialog.Builder builder = new AlertDialog.Builder(ProfilFragment.this);
				View content = ProfilFragment.this.getLayoutInflater()
						.inflate(R.layout.nous_ecrire_dialog, null);
				TextView idNousEcrireDialog_textViewMessage = (TextView) content.findViewById(R.id.idNousEcrireDialog_textViewMessage);
			    idNousEcrireDialog_editTextMessage = (EditText) content.findViewById(R.id.idNousEcrireDialog_editTextMessage);
				LinearLayout idNousEcrire_linearLayoutAnnuler = (LinearLayout) content.findViewById(R.id.idNousEcrire_linearLayoutAnnuler);
				LinearLayout idNousEcrire_linearLayoutValider =(LinearLayout) content.findViewById(R.id.idNousEcrire_linearLayoutValider);
				builder.setTitle(ProfilFragment.this
						.getString(R.string.txtLogin_title_dialog_valider_code));
				idNousEcrireDialog_textViewMessage.setText(ProfilFragment.this
						.getString(R.string.txtLogin_message_dialog_valider_code));
				idNousEcrireDialog_editTextMessage.setHint(ProfilFragment.this
						.getString(R.string.txtLogin_message_dialog_saisir_le_code_recu));
				idNousEcrireDialog_textViewMessage.setHint("Saisir le code ");
				idNousEcrire_linearLayoutValider.setOnClickListener(validerCodeListener);
				idNousEcrire_linearLayoutAnnuler.setOnClickListener(annulerListener);
				builder.setView(content);
				alertDialog = builder.create();
				alertDialog.show();

			}
		});
	}
	
    public void dataLoadingError(String error) {
    }
    public void dataLoaded(Object data, int method, int i) {
        int j;
        switch (method) {
            case Constant.KEY_USER_MANAGER_GET_PROFIL :
                user = (User) data;
                nom = user.getNom();
                idProfil_editTextNom.setText(Html.fromHtml(nom));
                prenom = user.getPrenom();
                idProfil_editTextPrenom.setText(Html.fromHtml(prenom));
                email = user.getEmail();
                if(email!=null && !email.equals(""))
                	idProfil_editTextEmail.setEnabled(false);
                idProfil_editTextEmail.setText(email);
                telephone = user.getTelephone();
                idProfil_editTextMobile.setText(user.getTelephone());
                pseudo = user.getPseudo();
                idProfil_editTextPseudo.setText(Html.fromHtml(pseudo));
                adresse = user.getAdresse();
                try{
                	 idProfil_editTextAdresse.setText(Html.fromHtml(adresse));
                }catch (Exception e) {
					// TODO: handle exception
				}
               
                 
                idVille = user.getIdVille();
                if(user.getFacebookId()!=null && !user.getFacebookId().equals("")){
                	idProfil_linearLayoutFacebook.setBackgroundColor(getResources().getColor(R.color.green_dark));
                	idProfil_linearLayoutFacebook.setClickable(false);
                	idProfil_imageviewFacebookCheck.setImageResource(R.drawable.white_check);
                }
                if(user.getCheckmail()!=null && user.getCheckmail().equals("1")  ){
                	idProfil_linearLayoutMail.setBackgroundColor(getResources().getColor(R.color.green_dark));
                	idProfil_linearLayoutMail.setClickable(false);
                	idProfil_imageviewMailCheck.setImageResource(R.drawable.white_check);
                }
                if(user.getCheckphone()!=null && !user.getCheckphone().equals("")){
                	idProfil_linearLayoutPhone.setBackgroundColor(getResources().getColor(R.color.green_dark));
                	idProfil_linearLayoutPhone.setClickable(false);
                	idProfil_imageviewPhoneCheck.setImageResource(R.drawable.white_check);
                	Editor editor = PreferenceManager.getDefaultSharedPreferences(
        					getApplicationContext()).edit();
        			 editor.putString("checkphone", "1");
        			
        			editor.commit();
                }
               
                count++;
                if (count == 2) {
                    if (!(idVille == null || idVille.equals(""))) {
                        for (j = 1; j < villes.size(); j++) {
                            if (((Categorie) villes.get(j)).getIdentifiant().equals(idVille)) {
                                idProfil_spinnerVille.setSelection(j);
                            }
                            villesAdapter.notifyDataSetChanged();
                        }
                    }
                    progressDialog.dismiss();
                }
                break;
            case Constant.KEY_USER_MANAGER_CHECK_MOBILE :
            	code =(String)data;
//            	progressDialog.dismiss();
//            	validerMobile();
//                break;
            case Constant.KEY_USER_MANAGER_VALIDER_MOBILE :
                Toast.makeText(getApplicationContext(), "Mise � jour effectu�e avec succ�s",Toast.LENGTH_SHORT).show();
                Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                editor.putString("checkphone", "1");
                editor.commit();
                idProfil_linearLayoutPhone.setBackgroundColor(getResources().getColor(R.color.green_dark));
                idProfil_linearLayoutPhone.setClickable(false);
            	idProfil_imageviewPhoneCheck.setImageResource(R.drawable.white_check);
                progressDialog.dismiss();
                if(src!=null && !src.equals(""))
                	finish();
                break;
            case Constant.KEY_USER_MANAGER_SAVE_PROFIL :
                Toast.makeText(getApplicationContext(), getString(R.string.txtProfil_msg_profil_updated),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                imageLoader.clearMemoryCache();
                imageLoader.clearDiscCache();
                break;
            case Constant.KEY_VILLE_MANAGER_LISTE_VILLE :
                villes.addAll((List<Categorie>) data);
                villesAdapter.notifyDataSetChanged();
                count++;
                if (count == 2) {
                    if (!(idVille == null || idVille.equals(""))) {
                        for (j = 1; j < villes.size(); j++) {
                            if (((Categorie) villes.get(j)).getIdentifiant().equals(idVille)) {
                                idProfil_spinnerVille.setSelection(j);
                            }
                            villesAdapter.notifyDataSetChanged();
                        }
                    }
                    progressDialog.dismiss();
                }
                break;
           
        }
    }
}
