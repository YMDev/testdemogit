package mobile.a3tech.com.a3tech.activity;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.util.MinimalPrettyPrinter;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import bakerj.backgrounddarkpopupwindow.BackgroundDarkPopupWindow;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.CategorieAdapter;
import mobile.a3tech.com.a3tech.adapter.ChatAdapter;
import mobile.a3tech.com.a3tech.capturephoto.util.CapturePhoto;
import mobile.a3tech.com.a3tech.images.ToasterToLoadDrawableResource;
import mobile.a3tech.com.a3tech.images.ToasterToLoadUrl;
import mobile.a3tech.com.a3tech.images.Util;
import mobile.a3tech.com.a3tech.manager.AbusManager;
import mobile.a3tech.com.a3tech.manager.EvaluationManager;
import mobile.a3tech.com.a3tech.manager.FormeRemunerationManager;
import mobile.a3tech.com.a3tech.manager.ImageManager;
import mobile.a3tech.com.a3tech.manager.MessagesManager;
import mobile.a3tech.com.a3tech.manager.MissionManager;
import mobile.a3tech.com.a3tech.manager.MissionSuiviManager;
import mobile.a3tech.com.a3tech.manager.OffresManager;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.model.FormeRemuneration;
import mobile.a3tech.com.a3tech.model.Message;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.model.Offre;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.DateManager;
import mobile.a3tech.com.a3tech.view.CircleImageView;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;
import mobile.a3tech.com.a3tech.view.Helper;
import mobile.a3tech.com.a3tech.view.IOnFocusListenable;

import org.codehaus.jackson.util.MinimalPrettyPrinter;
import com.google.android.gms.plus.PlusShare;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.twotoasters.android.horizontalimagescroller.image.ImageToLoad;
import com.twotoasters.android.horizontalimagescroller.widget.HorizontalImageScroller;
import com.twotoasters.android.horizontalimagescroller.widget.HorizontalImageScrollerAdapter;

public class DetailServiceActivity extends Fragment implements DataLoadCallback,IOnFocusListenable {

	String connectedUser ;
	String password ;
	String pseudoConnectedUser ;
	String conMode ;
	String missionId ;
	String userId ;
	Mission mission ;
	int statut ;
	ImageLoader imageLoader;
	CircleImageView idDetailMissionSrvc_circleImaleProfilPicto;
	
	TextView idDetailMissionSrvc_textViewPseudo;
	RatingBar idDetailMissionSrvc_ratinBarEval;
	TextView idDetailSrvc_textViewService;
	TextView idDetailSrvc_textViewIlya;
	ImageView idDetailSrvc_imageViewServiceImg;
	TextView idDetailSrvc_textViewCatSubCat;
	TextView idDetailSrvc_textViewDescription;
	TextView idDetailSrvc_textViewEtat ;
	TextView idDetailSrvc_textViewTypeTrocValue;
	TextView idDetailSrvc_textViewObjetRecherche ;
	TextView idDetailSrvc_textViewObjetRechercheValue ;
	TextView idDetailSrvc_textViewLieuValue ;
	TextView idDetailSrvc_textViewEcheance ;
	TextView idDetailSrvc_textViewtStatut ;
	TextView idDetailSrvc_textViewId ;
	private BackgroundDarkPopupWindow mPopupWindow;
	 LinearLayout idDetailSrvc_linearLayoutPiecesJointes;
	 
	 LinearLayout idDetailSrvc_linearLayoutSuivreMission;
	 ImageButton idDetailSrvc_imageViewDeleteMission ;
	 LinearLayout idDetailSrvc_linearLayoutSignalerAbbus;
	 LinearLayout idDetailSrvc_linearLayoutPartagerFacebook;
	 ImageButton idDetailSrvc_buttonSignalerUnAbbus;
	 Spinner idAbus_spinnerTypeAbus ;
	
	HorizontalImageScroller scroller_androids;
	ArrayList<ImageToLoad> androidToasters = new ArrayList<ImageToLoad>();
	HorizontalImageScrollerAdapter adapter;
	
	 RelativeLayout idDetailSrvc_relativeLayoutMessage;
	 ImageButton idDetailSrvc_buttonSendMessage;
	 ListView idDetailSrvc_listeChatPublic;
	 EditText idDetailSrvc_editMessageText;
	 LinearLayout idDetailSrvc_linearLayoutProposerOffre;
	 RelativeLayout idDetailSrvc_linearLayoutProposerOffreFragment ;
	 ImageButton btnClose ;
	 LinearLayout idDetailSrvc_linearLayoutAnnulerOffre ;
	 LinearLayout idDetailSrvc_linearLayoutValiderOffre ;
	 EditText idDetailSrvc_editRepOffreurText ;
	 ImageButton idDetailSrvc_ImageButtonAttachementOne;
	 ImageButton idDetailSrvc_ImageButtonAttachementTwo;
	 ImageButton idDetailSrvc_ImageButtonAttachementTree;

	 ImageView idDetailSrvc_ImageviewAttachementOne;
	 ImageView idDetailSrvc_ImageviewAttachementTwo;
	 ImageView idDetailSrvc_ImageviewAttachementTree;
	 
	 LinearLayout idDetailSrvc_linearLayoutEvaluation ;
	 RatingBar ratingDialog_ratingBarDialog;
	 
	 public static AlertDialog alertDialog;
	Offre offre ;
	CategorieAdapter typeAbusAdapter;
	List<Categorie> typeabuss;
	boolean hasOffre = false ;
	String checkphone ="" ;
	
	
	DisplayImageOptions options   = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().build();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
    public static SimpleDateFormat formatterHM= new SimpleDateFormat("dd/MM/yyyy HH:mm");
    SimpleDateFormat sdfHeure  = new SimpleDateFormat("HH:mm");
    
    String dateChatPublic = "";
    List<Message> messagesPublics;
    ChatAdapter listAdapterPublic;
    public static ProgressDialog progressDialog = null;
    
   
	
	Point p;
	String urls ="";
	int suivi = 0;
	int abus = 0;
	 
	String[] bitmaps = new String[] { "", "", "" };
	private CapturePhoto capture;
	int selectedView;
	private String mAgentPictureName;
	private String mAgentPicturePath;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_GALLERY = 2;
	public static final int REQUEST_CAMERA = 1000;
	public static final int SELECT_FILE = 1001;
	View rootView;
	String cat ;
	String description ;
	private void showEvent(String eventType) {
        Message message = new Message();
        message.setType(eventType);
        message.setUserId(connectedUser);
       
        message.setPseudo(pseudoConnectedUser);
        message.setUserIdOriginator(connectedUser);
        if (eventType.equals("4") || eventType.equals("5") || eventType.equals("8")|| eventType.equals("3")) {
           
            message.setPseudoOriginator(pseudoConnectedUser);
        }
        Date date2 = new Date();
        String dateCreation = formatter.format(date2);
        message.setHeure(sdfHeure.format(date2));
        if (dateCreation.equals(dateChatPublic)) {
            message.setDateCreation("");
        } else {
            message.setDateCreation(dateCreation);
        }
        dateChatPublic = dateCreation;
        messagesPublics.add(message);
        listAdapterPublic.notifyDataSetChanged();
        Helper.getListViewSize(idDetailSrvc_listeChatPublic, this.getActivity());
    }
	
    private OnClickListener signalerAbusListener = new OnClickListener() {
        public void onClick(View v) {
            if (abus == 0) {
                Builder builder = new Builder(DetailServiceActivity.this.getActivity());
                View content = getActivity().getLayoutInflater().inflate(R.layout.abus_dialog, null);
                 idAbus_spinnerTypeAbus = (Spinner) content.findViewById(R.id.idAbus_spinnerTypeAbus);
                
                typeabuss = getTypeAbus();
        		typeAbusAdapter = new CategorieAdapter(getActivity().getApplicationContext(), typeabuss);
        		idAbus_spinnerTypeAbus.setAdapter(typeAbusAdapter);
        		idAbus_spinnerTypeAbus.setSelection(0);
        		
        		
                LinearLayout idMessageDialog_linearLayoutAnnuler = (LinearLayout) content.findViewById(R.id.idMessageDialog_linearLayoutAnnuler);
                LinearLayout idMessageDialog_linearLayoutValider = (LinearLayout) content.findViewById(R.id.idMessageDialog_linearLayoutValider);
                
                idMessageDialog_linearLayoutValider.setOnClickListener(alertValiderAbusListener);
                idMessageDialog_linearLayoutAnnuler.setOnClickListener(alertAnnulerListener);
                builder.setTitle(getString(R.string.txtDetailSrvc_textAlertTitleSignalerAbus));
                builder.setView(content);
                alertDialog = builder.create();
                alertDialog.show();
            }
        }
    };
    
	public List<Categorie> getTypeAbus() {

		List<Categorie> categories = new ArrayList<Categorie>();
		Categorie hint = new Categorie();
		hint.setLibelle(getString(R.string.txtAbus_choisirAbus));

		Categorie service = new Categorie();
		service.setIdentifiant("0");
		service.setLibelle(getString(R.string.txtAbus_choixAbusContrefacon));
		
		Categorie objet = new Categorie();
		objet.setIdentifiant("1");
		objet.setLibelle(getString(R.string.txtAbus_choixAbusContreViolation));
		
		categories.add(hint);
		categories.add(service);
		categories.add(objet);
		
		return categories;

	}
    
    private OnClickListener alertValiderAbusListener = new OnClickListener() {
        public void onClick(View v) {
        	if (idAbus_spinnerTypeAbus.getSelectedItemPosition() == 0) {
    			Toast.makeText(getActivity().getApplicationContext(),
    					R.string.txtAbus_msgChoixTypeAbus, Toast.LENGTH_SHORT).show();
    			((TextView) idAbus_spinnerTypeAbus.getSelectedView())
    					.setError(getString(R.string.txtAbus_msgChoixTypeAbus));
    			
    		}else{
	            abus = 1;
	            idDetailSrvc_buttonSignalerUnAbbus.setImageResource(R.drawable.button_signaler_abbus_n);
	            DetailServiceActivity.progressDialog = CustomProgressDialog.createProgressDialog(DetailServiceActivity.this.getActivity(), getString(R.string.txtMenu_dialogChargement));
	            AbusManager.getInstance().createAbus(missionId,0,idAbus_spinnerTypeAbus.getSelectedItemPosition(), connectedUser,password, DetailServiceActivity.this);
	            alertDialog.dismiss();
	    		}
        }
    };
	
	public static DetailServiceActivity newInstance() {
		return new DetailServiceActivity();
	}
	
	
	   private OnClickListener SuivreMissionListener = new OnClickListener() {
	        public void onClick(View v) {
	            if (suivi == 0) {
	                suivi = 1;
	                progressDialog = CustomProgressDialog.createProgressDialog(DetailServiceActivity.this.getActivity(), getString(R.string.txtMenu_dialogChargement));
	                MissionSuiviManager.getInstance().createMissionSuivi(missionId, connectedUser,password, DetailServiceActivity.this);
	            }
	        }
	    };
    private OnClickListener partagerFacebookListener =new OnClickListener() {
        public void onClick(View v) {
            if (conMode.equals("application")) {
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.txtDetailSrvc_textConnexionFacebookObligatoire), Toast.LENGTH_SHORT).show();
                return;
            }
            Intent mainIntent = new Intent(DetailServiceActivity.this.getActivity(), FacebookActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constant.RESULT_ACTION_CODE_FACEBOOK_SRC, Constant.RESULT_ACTION_CODE_FACEBOOK_SRC_DETAIL);
            bundle.putString("name", cat);
            bundle.putString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, description);
            bundle.putString("caption", "");
            mainIntent.putExtras(bundle);
            startActivityForResult(mainIntent, Constant.SPLASH_FACEBOOK_REQUEST_CODE_DETAIL);
        }
    };
	 
	@Override
	public void dataLoaded(Object data, int method, int typeOperation) {
		
	        switch (method) {
	        	case Constant.KEY_ABUS_MANAGER_CREATE_ABUS :
	                progressDialog.dismiss();
	                Toast.makeText(getActivity().getApplicationContext(), R.string.txtDetailSrvc_txtMessageResultAbus, Toast.LENGTH_LONG).show();
	                break;
	        	  case Constant.KEY_MISSION_SUIVI_MANAGER_SUIVRE_MISSION /*27*/:
	                  progressDialog.dismiss();
	                  Toast.makeText(getActivity().getApplicationContext(), R.string.txtDetailSrvc_txtMessageResultMissionSuivi, Toast.LENGTH_LONG).show();
	                  break;
	              case Constant.KEY_MISSION_MANAGER_DELETE_MISSION /*28*/:
	                  progressDialog.dismiss();
	                  Toast.makeText(getActivity().getApplicationContext(), R.string.txtDetailSrvc_txtMissionSupprimeSucces, Toast.LENGTH_LONG).show();
	                  Intent returnIntent = new Intent();
	                  returnIntent.putExtra(Constant.RESULT_ACTION_CODE, Constant.RESULT_ACTION_CODE_DELETE_MISSION);
	                  getActivity().setResult(-1, returnIntent);
	                  getActivity().finish();
	                  break;
	            case Constant.KEY_MISSION_MANAGER_DETAIL_MISSION :
	                mission = (Mission) data;
	                userId = mission.getOriginator();
	                statut = Integer.parseInt(mission.getStatut());
	                showDetailMission();
	               
	                break;
	            case Constant.KEY_MESSAGE_MANAGER_CREATE_MESSAGE :
	                if (((String) data).equals("OK")) {
	                    showMessage(idDetailSrvc_editMessageText.getText().toString(), String.valueOf(0));
	                    idDetailSrvc_editMessageText.setText("");
	                } else {
	                    Toast.makeText(getActivity().getApplicationContext(), R.string.txtDetailSrvc_txtViewMissionChangee, Toast.LENGTH_LONG).show();
	                    Intent returnIntentMsg = new Intent();
	                    returnIntentMsg.putExtra(Constant.RESULT_ACTION_CODE, Constant.RESULT_ACTION_CODE_CREATE_MESSAGE);
	                    getActivity().setResult(-1, returnIntentMsg);
	                    getActivity().finish();
	                }
	                progressDialog.dismiss();
	                break;
	            case Constant.KEY_OFFRE_MANAGER_CREATE_OFFRE /*23*/:
	                if (((String) data).equals("OK")) {
	                    showEvent("2");
	                    idDetailSrvc_linearLayoutProposerOffre.setVisibility(View.GONE);
	                    idDetailSrvc_linearLayoutProposerOffreFragment.setVisibility(View.GONE);
	                    progressDialog.dismiss();
	                    Toast.makeText( getActivity().getApplicationContext(), R.string.txtDetailSrvc_txtMessageResultOffre, Toast.LENGTH_LONG).show();
	                   
	                }else{
		                progressDialog.dismiss();
		                Toast.makeText( getActivity().getApplicationContext(), R.string.txtDetailSrvc_txtViewMissionAffectee, Toast.LENGTH_LONG).show();
		                Intent returnIntentMsg = new Intent();
		                returnIntentMsg.putExtra(Constant.RESULT_ACTION_CODE, Constant.RESULT_ACTION_CODE_CREATE_OFFRE);
		                getActivity().setResult(-1, returnIntentMsg);
		                getActivity().finish();
	                }
	                break;
	            case Constant.KEY_OFFRE_MANAGER_UPDATE_OFFRE /*29*/:
	                if (((String) data).equals("OK")) {
	                	if(typeOperation==1)
	                		showEvent("4");
	                	else
	                		showEvent("8");
	                } else {
	                    Toast.makeText( getActivity().getApplicationContext(), R.string.txtDetailSrvc_txtMessageResultAcceptOffreNOK, Toast.LENGTH_LONG).show();
	                }
	                progressDialog.dismiss();
	                break;
	            case Constant.KEY_OFFRE_MANAGER_DELETE_OFFRE /*29*/:
	                if (((String) data).equals("OK")) {
                		showEvent("3");
	                } else {
	                    Toast.makeText( getActivity().getApplicationContext(), R.string.txtDetailSrvc_txtMessageResultAcceptOffreNOK, Toast.LENGTH_LONG).show();
	                }
	                progressDialog.dismiss();
	                break;
	            case Constant.KEY_OFFRE_MANAGER_DELETE_OFFRE_COTE_OFFREUR /*29*/:
	                if (((String) data).equals("OK")) {
                		showEvent("9");
	                } else {
	                    Toast.makeText( getActivity().getApplicationContext(), R.string.txtDetailSrvc_txtMessageResultAcceptOffreNOK, Toast.LENGTH_LONG).show();
	                }
	                progressDialog.dismiss();
	                break;
	            case Constant.KEY_EVALUATION_MANAGER_CREATE_EVALUATION /*32*/:
	                Toast.makeText(getActivity().getApplicationContext(), R.string.txtDetailSrvc_txtMessageResultEvaluation, Toast.LENGTH_LONG).show();
	                hideEvaluationAndChat();
	                progressDialog.dismiss();
	                break;
	           
	        }
		
	}
	
	   public void hideEvaluationAndChat() {
	        idDetailSrvc_relativeLayoutMessage.setVisibility(View.GONE);
	        idDetailSrvc_linearLayoutEvaluation.setVisibility(View.GONE);
	    }
	

    public void showOffres(List<Offre> ofrs) {
    	
    	
    	if(!connectedUser.equals(userId)){
  	        for (Offre offre : ofrs) {
  	            if (offre.getUserId().equals(connectedUser)) {
  	            	hasOffre = true ;
  	            }
  	        }
  	        if(statut==0 && hasOffre==false)idDetailSrvc_linearLayoutProposerOffre.setVisibility(View.VISIBLE);
    	}
    	for (Offre offr : ofrs) {
			if(offr.getEtat()!=null&& !offr.getEtat().equals("") && offr.getEtat().equals("1")) offre= offr;
		}
       
       
    }
    
    
	private void selectImage(int id) {
		CharSequence[] items = new CharSequence[] {
				getString(R.string.txtPostage2_attachementItemPrendreImage),
				getString(R.string.txtPostage2_attachementItemChoisirImage),
				getString(R.string.txtPostage2_attachementItemAnnuler) };
		Builder builder = new Builder(this.getActivity());
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
					Toast.makeText(getActivity().getApplicationContext(), getString(R.string.txtProfil_choisirdelagalerie), Toast.LENGTH_LONG).show();
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
	
	   private OnClickListener enregistrerOffreListener = new OnClickListener() {
	        public void onClick(View v) {
	            String commentaire = idDetailSrvc_editRepOffreurText.getText().toString();
	            if (commentaire.equals("")) {
	                Toast.makeText( getActivity().getApplicationContext(), R.string.txtDetailSrvc_txtMessageOffreObligatoire, Toast.LENGTH_LONG).show();
	                return;
	            }
	           progressDialog = CustomProgressDialog.createProgressDialog(DetailServiceActivity.this.getActivity(), getString(R.string.txtMenu_dialogChargement));
	            OffresManager.getInstance().createOffre(missionId, connectedUser, bitmaps, commentaire,password, DetailServiceActivity.this);
	        }
	    };
	
	private void showMessage(String text, String publicPrive) {
        Message message = new Message();
        message.setTexte(text);
        message.setUserId(connectedUser);
        Date date2 = new Date();
        String dateCreation = formatter.format(date2);
        message.setHeure(sdfHeure.format(date2));
        message.setType(publicPrive);
        message.setPseudo(pseudoConnectedUser);
        if (dateCreation.equals(dateChatPublic)) {
            message.setDateCreation("");
        } else {
            message.setDateCreation(dateCreation);
        }
        dateChatPublic = dateCreation;
        messagesPublics.add(message);
        listAdapterPublic.notifyDataSetChanged();
        Helper.getListViewSize(idDetailSrvc_listeChatPublic, this.getActivity());
           
      
    }
	public void showMessages(List<Message> messages) {
        for (Message message : messages) {
            String dateCreations = "";
            
            try {
                Date dateCreation = dateFormat.parse(message.getDateCreation());
                dateCreations = formatter.format(dateCreation);
                message.setHeure(sdfHeure.format(dateCreation));
            } catch (ParseException e) {
            }
            if (dateCreations.equals(dateChatPublic)) {
                message.setDateCreation("");
            } else {
                message.setDateCreation(dateCreations);
            }
            dateChatPublic = dateCreations;
            messagesPublics.add(message);
           
        }
     
        listAdapterPublic.notifyDataSetChanged();
        Helper.getListViewSize(idDetailSrvc_listeChatPublic, this.getActivity());
    }
	
	private void _setupToasterScroller(OnItemClickListener onItemClickListener) {
        androidToasters.add(new ToasterToLoadDrawableResource(R.drawable.portrait, "Adit Shukla"));
        adapter = new HorizontalImageScrollerAdapter(this.getActivity(), androidToasters);
        adapter.setLoadingImageResourceId(R.drawable.generic_toaster);
        adapter.setImageSize((int) getResources().getDimension(R.dimen.image_size));
        adapter.setDefaultImageFailedToLoadResourceId(R.drawable.generic_toaster);
        scroller_androids.setAdapter(adapter);
        scroller_androids.setOnItemClickListener(onItemClickListener);
    }
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	 
	   int[] location = new int[2];
	   
	   CircleImageView  idDetailMissionSrvc_circleImaleProfilPicto = (CircleImageView) getView().findViewById(R.id.idDetailMissionSrvc_circleImaleProfilPicto);
	 
	   // Get the x, y location and store it in the location[] array
	   // location[0] = x, location[1] = y.
	   idDetailMissionSrvc_circleImaleProfilPicto.getLocationOnScreen(location);
	 
	   //Initialize the Point with x, and y positions
	   p = new Point();
	   p.x = location[0];
	   p.y = location[1];
	}
	
	  private void showDetailMission() {
	        Integer[] elapsed;
	        String echeanceMission;
	        imageLoader.displayImage(new StringBuilder(Constant.CHECK_EDU_PICTURES_URL).append(mission.getOriginator()).append(".jpg").toString(), idDetailMissionSrvc_circleImaleProfilPicto, options);
	        idDetailMissionSrvc_textViewPseudo.setText(Html.fromHtml(mission.getPseudo()));
	        idDetailMissionSrvc_ratinBarEval.setRating(Float.parseFloat(mission.getNote()));
	        idDetailSrvc_textViewId.setText(missionId);
	        try{
	        	 Date membre = dateFormat.parse(mission.getMembre());
	        	 idDetailSrvc_textViewService.setText(getString(R.string.txtDetailSrvc_textViewService) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + formatter.format(membre));
	        }catch(Exception e){
	        	
	        }
	        try {
	            elapsed = DateManager.elapsedTime(dateFormat.parse(mission.getDateCreation()), new Date());
	            echeanceMission = getString(R.string.txtDetailSrvc_textViewIlya) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
	            if (elapsed[0].intValue() != 0) {
	                echeanceMission = new StringBuilder(String.valueOf(echeanceMission)).append(elapsed[0]).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(getString(R.string.txtDetailSrvc_textJours)).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString();
	            } else if (elapsed[1].intValue() != 0) {
	                echeanceMission = new StringBuilder(String.valueOf(echeanceMission)).append(elapsed[1]).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(getString(R.string.txtDetailSrvc_textHeure)).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString();
	            } else if (elapsed[2].intValue() != 0) {
	                echeanceMission = new StringBuilder(String.valueOf(echeanceMission)).append(elapsed[2]).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(getString(R.string.txtDetailSrvc_textMinute)).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString();
	            } else {
	                echeanceMission = new StringBuilder(String.valueOf(echeanceMission)).append("1 ").append(getString(R.string.txtDetailSrvc_textMinute)).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString();
	            }
	            idDetailSrvc_textViewIlya.setText(echeanceMission);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        FormeRemuneration formeRemuneration =   FormeRemunerationManager
					.getInstance().getFormeRemunerationSrvc(Integer.parseInt(mission.getService()));
	        idDetailSrvc_imageViewServiceImg.setImageResource(formeRemuneration.getDrawable());
	        
	        String categorie = getString(formeRemuneration.getLibelle());
			if (mission.getCatDescription() != null && !mission.getCatDescription().equals(""))
				categorie = categorie + " > " + mission.getCatDescription();
			cat = categorie;
			description = mission.getArticle();
			idDetailSrvc_textViewCatSubCat.setText(Html.fromHtml(categorie));
			
			
			idDetailSrvc_textViewDescription.setText(Html.fromHtml("<b><i>"+getString(R.string.txtDetailSrvc_textViewMonOffre)+"</i></b>  "+mission.getArticle()));
			if(mission.getTypetroc()!=null && !mission.getTypetroc().equals("0") && mission.getEtatarticle()!=null && !mission.getEtatarticle().equals("") && mission.getEtatarticle().equals("0")){
				idDetailSrvc_textViewEtat.setText(getString(R.string.txtDetailSrvc_textViewNouvelArticle));
				idDetailSrvc_textViewEtat.setVisibility(View.VISIBLE);
			}
			if(mission.getTypetroc()!=null && !mission.getTypetroc().equals("0") && mission.getEtatarticle()!=null && !mission.getEtatarticle().equals("") && mission.getEtatarticle().equals("1")){
				idDetailSrvc_textViewEtat.setText(getString(R.string.txtDetailSrvc_textViewAncienArticle));
				idDetailSrvc_textViewEtat.setVisibility(View.VISIBLE);
			}
			idDetailSrvc_textViewEtat.setVisibility(View.GONE);
			if(mission.getTypetroc()!=null && mission.getTypetroc().equals("0"))
				idDetailSrvc_textViewTypeTrocValue.setText(getString(R.string.txtDetailSrvc_textViewTypeTrocServiceValue));
			if(mission.getTypetroc()!=null && mission.getTypetroc().equals("1"))
				idDetailSrvc_textViewTypeTrocValue.setText(getString(R.string.txtDetailSrvc_textViewTypeTrocObjet));
			try{
				idDetailSrvc_textViewObjetRechercheValue.setText(Html.fromHtml(mission.getObjetrecherche()));
			}catch(Exception e){
				
			}
//			if(mission.getContre()!=null && mission.getContre().equals("0"))idDetailSrvc_textViewObjetRecherche.setText(getString(R.string.txtDetailSrvc_textViewTypeTrocService));
//			if(mission.getContre()!=null && mission.getContre().equals("1"))idDetailSrvc_textViewObjetRecherche.setText(getString(R.string.txtPostage2_edittextChoixContreObjet));
			//if(mission.getContre()!=null && mission.getContre().equals("2"))
			idDetailSrvc_textViewObjetRecherche.setText(getString(R.string.txtPostage2_edittextChoixEchangeContreValue));
			if(mission.getContre()!=null && mission.getContre().equals("3")){
				//idDetailSrvc_textViewObjetRecherche.setText(getString(R.string.txtPostage2_edittextChoixContreObjet));
				idDetailSrvc_textViewObjetRechercheValue.setText(getString(R.string.txt_detailsrvc_msg_jattendvotreproposition));
			}
			
			idDetailSrvc_textViewLieuValue.setText(mission.getAdresse());
			 try {
	            Date datem = dateFormat.parse(mission.getDateFin());
	            Date now = new Date();
	            String htmlf = "<b>" + getString(R.string.txtDetailSrvc_textViewEcheance) + "</b>" + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
	            echeanceMission = "";
	            if (datem.after(now)) {
	                elapsed = DateManager.elapsedTime(now, datem);
	                if (elapsed[0].intValue() != 0) {
	                    echeanceMission = new StringBuilder(String.valueOf(echeanceMission)).append(elapsed[0]).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(getString(R.string.txtDetailSrvc_textJours)).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString();
	                } else if (elapsed[1].intValue() != 0) {
	                    echeanceMission = new StringBuilder(String.valueOf(echeanceMission)).append(elapsed[1]).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(getString(R.string.txtDetailSrvc_textHeure)).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString();
	                } else if (elapsed[2].intValue() != 0) {
	                    echeanceMission = new StringBuilder(String.valueOf(echeanceMission)).append(elapsed[2]).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(getString(R.string.txtDetailSrvc_textMinute)).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString();
	                } else {
	                    echeanceMission = new StringBuilder(String.valueOf(echeanceMission)).append("1").append(getString(R.string.txtDetailSrvc_textMinute)).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString();
	                }
	            } else {
	                echeanceMission = "0";
	            }
	            idDetailSrvc_textViewEcheance.setText(Html.fromHtml(new StringBuilder(String.valueOf(htmlf)).append("<FONT COLOR=\"red\" >").append(echeanceMission).append("</FONT>").toString()));
	        }catch (ParseException e2) {
		    }
			List<String> bitmaps = new ArrayList<String>();
			if(mission.getUrl1()!=null && !mission.getUrl1().equals(""))
				bitmaps.add(mission.getUrl1());
			if(mission.getUrl2()!=null && !mission.getUrl2().equals(""))
				bitmaps.add(mission.getUrl2());
			if(mission.getUrl3()!=null && !mission.getUrl3().equals(""))
				bitmaps.add(mission.getUrl3());
			
			
			int statut = 0;
	        if (!(mission.getStatut() == null || mission.getStatut().equals(""))) {
	            statut = Integer.parseInt(mission.getStatut());
	        }
	        switch (statut) {
	            case Constant.STATUT_MISSION_ACCEPTE /*1*/:
	                idDetailSrvc_textViewtStatut.setText(new StringBuilder(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(getString(R.string.txtDetailSrvc_txtStatutAccepte)).toString());
	                idDetailSrvc_textViewtStatut.setTextColor(getResources().getColor(R.color.menu));
	                break;
	            case Constant.STATUT_MISSION_FINALE /*2*/:
	            case Constant.STATUT_MISSION_CLOTURE /*2*/:
	                idDetailSrvc_textViewtStatut.setText(new StringBuilder(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(getString(R.string.txtDetailSrvc_txtStatutCloture)).toString());
	                idDetailSrvc_textViewtStatut.setTextColor(getResources().getColor(R.color.notifMove));
	                break;
	            case Constant.STATUT_MISSION_EXPIRE /*3*/:
	                idDetailSrvc_textViewtStatut.setText(new StringBuilder(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(getString(R.string.txtDetailSrvc_txtStatutExpire)).toString());
	                idDetailSrvc_textViewtStatut.setTextColor(getResources().getColor(R.color.DarkGray));
	                break;
	            case Constant.STATUT_MISSION_SUPPRIME /*5*/:
	                idDetailSrvc_textViewtStatut.setText(new StringBuilder(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(getString(R.string.txtDetailSrvc_txtStatutSupprime)).toString());
	                idDetailSrvc_textViewtStatut.setTextColor(getResources().getColor(R.color.DarkGray));
	                break;
	            default:
	                idDetailSrvc_textViewtStatut.setText(new StringBuilder(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(getString(R.string.txtDetailSrvc_txtStatutEnRecherche)).toString());
	                idDetailSrvc_textViewtStatut.setTextColor(getResources().getColor(R.color.viber));
	                idDetailSrvc_relativeLayoutMessage.setVisibility(View.VISIBLE);
	                break;
	        }
	        if (!userId.equals(connectedUser)) {
	            idDetailSrvc_linearLayoutSuivreMission.setVisibility(View.VISIBLE);
	        } else if (statut == 1 || statut == 0) {
	            idDetailSrvc_imageViewDeleteMission.setVisibility(View.VISIBLE);
	        }
	        
			 showAttachments(bitmaps);
			 showMessages(mission.getMessages());
			 showOffres(mission.getOffres());
			 showEvaluation();
			 
			 
     }
	
	  public void  onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			if (resultCode != -1) {
				return;
			}
			if (requestCode == PICK_FROM_CAMERA || requestCode == PICK_FROM_GALLERY) {
				if (requestCode == PICK_FROM_GALLERY) {
					Util.saveImage(Util.getBitmap(
							Util.getRealPathFromURI(this.getActivity(), data.getData()),
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
						ImageView imageView = (ImageView) getView().findViewById(selectedView);
						imageView.setImageBitmap(picture);
						switch (selectedView) {
						case R.id.idDetailSrvc_ImageviewAttachementOne:
							idDetailSrvc_ImageButtonAttachementOne.setVisibility(View.VISIBLE);
							bitmaps[0] = mobile.a3tech.com.a3tech.utils.ImageManager.getInstance().getString(picture);
							break;
						case R.id.idDetailSrvc_ImageviewAttachementTwo:
							idDetailSrvc_ImageButtonAttachementTwo.setVisibility(View.VISIBLE);
							bitmaps[1] = mobile.a3tech.com.a3tech.utils.ImageManager.getInstance().getString(picture);
							break;
						default:
							idDetailSrvc_ImageButtonAttachementTree.setVisibility(View.VISIBLE);
							bitmaps[2] = mobile.a3tech.com.a3tech.utils.ImageManager.getInstance().getString(picture);
							break;
						}
					}
				} catch (Exception e) {
				}
				Util.deleteDirectory(new File(new StringBuilder(String
						.valueOf(Environment.getExternalStorageDirectory()
								.getAbsolutePath())).append("/.KhodaraTempImages")
						.toString()));
			}
		}

		
	@Override
	public void dataLoadingError(int errorCode) {
		Toast.makeText(getActivity().getApplicationContext(),R.string.txtSplash_messageCheckConnexion,Toast.LENGTH_SHORT).show();
		try {
			progressDialog.dismiss();
		} catch (Exception e) {
			
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.detail_service,
					container, false);
			rootView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		} else {
			((ViewGroup) rootView.getParent()).removeView(rootView);
		}

		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle bundle) {
		super.onActivityCreated(bundle);
		if (bundle == null) {
			 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
		        connectedUser = prefs.getString("identifiant", "");
		       password =  prefs.getString("password", "");
		        pseudoConnectedUser = prefs.getString("pseudo", "");
		        this.checkphone = prefs.getString("checkphone", "");
		        conMode = prefs.getString("conMode", "");
		        Bundle b = getActivity().getIntent().getExtras();
		        if (b != null) {
		            missionId = b.getString(Constant.KEY_EXTRA_DETAIL_MISSION_ID);
		            userId = b.getString(Constant.KEY_EXTRA_DETAIL_MISSION_USER_ID);
		        }
		       
		        initViews();
		        MissionManager.getInstance().detailMission( missionId, connectedUser,password, this);
		}
	}
	
	
	 
	 private OnItemClickListener onItemClickListener =new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
	            Intent i = new Intent(DetailServiceActivity.this.getActivity(), FullScreenViewActivity.class);
	            i.putExtra("position", position);
	            i.putExtra("urls", urls);
	            startActivity(i);
	        }
	    };
	 
	    
	    public void showAttachments(List<String> jointes) {
	        if (jointes == null || jointes.size() <= 0) {
	            idDetailSrvc_linearLayoutPiecesJointes.setVisibility(View.GONE);
	            return;
	        }
	        androidToasters.remove(0);
	        for (String pieceJointe : jointes) {
	            ImageToLoad load = new ToasterToLoadUrl(getGravatarUrl(pieceJointe), pieceJointe);
	            urls += pieceJointe + ";";
	            androidToasters.add(load);
	        }
	        urls = urls.substring(0, urls.length() - 1);
	        adapter.notifyDataSetChanged();
	    }
	    
	    private String getGravatarUrl(String url) {
	    	return new StringBuilder(
					Constant.CHECK_EDU_IMAGES_URL).append(url)
					.toString();
	       
	    }

	 private void initViews() {
	     
	        imageLoader = ImageLoader.getInstance();
	        idDetailMissionSrvc_circleImaleProfilPicto = (CircleImageView) getView().findViewById(R.id.idDetailMissionSrvc_circleImaleProfilPicto);
	        idDetailMissionSrvc_textViewPseudo = (TextView) getView().findViewById(R.id.idDetailMissionSrvc_textViewPseudo);
	        idDetailMissionSrvc_ratinBarEval = (RatingBar) getView().findViewById(R.id.idDetailMissionSrvc_ratinBarEval);
	        idDetailSrvc_textViewService = (TextView) getView().findViewById(R.id.idDetailSrvc_textViewService);
	        idDetailSrvc_textViewIlya = (TextView) getView().findViewById(R.id.idDetailSrvc_textViewIlya);
	        idDetailSrvc_imageViewServiceImg = (ImageView) getView().findViewById(R.id.idDetailSrvc_imageViewServiceImg);
	        idDetailSrvc_textViewCatSubCat = (TextView) getView().findViewById(R.id.idDetailSrvc_textViewCatSubCat);
	        idDetailSrvc_textViewDescription = (TextView) getView().findViewById(R.id.idDetailSrvc_textViewDescription);
	    	idDetailSrvc_textViewEtat  = (TextView) getView().findViewById(R.id.idDetailSrvc_textViewEtat);
	    	idDetailSrvc_textViewTypeTrocValue  = (TextView) getView().findViewById(R.id.idDetailSrvc_textViewTypeTrocValue);
	    	
	    	idDetailSrvc_textViewObjetRecherche  = (TextView) getView().findViewById(R.id.idDetailSrvc_textViewObjetRecherche);
	    	idDetailSrvc_textViewObjetRechercheValue  = (TextView) getView().findViewById(R.id.idDetailSrvc_textViewObjetRechercheValue);
	    	idDetailSrvc_textViewLieuValue  = (TextView) getView().findViewById(R.id.idDetailSrvc_textViewLieuValue);
	    	idDetailSrvc_textViewEcheance =  (TextView) getView().findViewById(R.id.idDetailSrvc_textViewEcheance);
	    	idDetailMissionSrvc_circleImaleProfilPicto.setOnClickListener(showProfilListener);
	    	idDetailSrvc_linearLayoutPiecesJointes = (LinearLayout) getView().findViewById(R.id.idDetailSrvc_linearLayoutPiecesJointes);
	    	idDetailSrvc_relativeLayoutMessage =(RelativeLayout)getView().findViewById(R.id.idDetailSrvc_relativeLayoutMessage);
	    	idDetailSrvc_textViewtStatut =(TextView)getView().findViewById(R.id.idDetailSrvc_textViewtStatut);
	    	 scroller_androids = (HorizontalImageScroller) getView().findViewById(R.id.scroller_androids);
	    	 
	    	 idDetailSrvc_listeChatPublic = (ListView) getView().findViewById(R.id.idDetailSrvc_listeChatPublic);
	         messagesPublics = new ArrayList<Message>();
	    	 listAdapterPublic = new ChatAdapter(this.getActivity(), messagesPublics, userId,connectedUser,DetailServiceActivity.this,password);
	         idDetailSrvc_listeChatPublic.setAdapter(listAdapterPublic);
	    	 _setupToasterScroller( onItemClickListener);
	    	 
	    	 idDetailSrvc_buttonSendMessage = (ImageButton) getView().findViewById(R.id.idDetailSrvc_buttonSendMessage);
	    	 idDetailSrvc_editMessageText = (EditText) getView().findViewById(R.id.idDetailSrvc_editMessageText);
	         idDetailSrvc_buttonSendMessage.setOnClickListener(sendMessageListener);
	         
	         idDetailSrvc_linearLayoutProposerOffre = (LinearLayout) getView().findViewById(R.id.idDetailSrvc_linearLayoutProposerOffre);
	         idDetailSrvc_linearLayoutProposerOffre.setOnClickListener(propositionOffreListener);
	         
	         idDetailSrvc_linearLayoutProposerOffreFragment = (RelativeLayout) getView().findViewById(R.id.idDetailSrvc_linearLayoutProposerOffreFragment);
	         btnClose = (ImageButton)getView().findViewById(R.id.btnClose);
	          idDetailSrvc_linearLayoutAnnulerOffre  = (LinearLayout) getView().findViewById(R.id.idDetailSrvc_linearLayoutAnnulerOffre);
	    	  idDetailSrvc_linearLayoutValiderOffre  = (LinearLayout) getView().findViewById(R.id.idDetailSrvc_linearLayoutValiderOffre);
	    	  idDetailSrvc_linearLayoutValiderOffre.setOnClickListener(enregistrerOffreListener);
	         btnClose.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					idDetailSrvc_linearLayoutProposerOffreFragment.setVisibility(View.GONE);
					idDetailSrvc_linearLayoutProposerOffre.setVisibility(View.VISIBLE);
					
				}
			});

	         idDetailSrvc_linearLayoutAnnulerOffre.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					idDetailSrvc_linearLayoutProposerOffreFragment.setVisibility(View.GONE);
					idDetailSrvc_linearLayoutProposerOffre.setVisibility(View.VISIBLE);
					
				}
			});
	         
	        
	    	  idDetailSrvc_editRepOffreurText  = (EditText) getView().findViewById(R.id.idDetailSrvc_editRepOffreurText);
	    	  idDetailSrvc_ImageButtonAttachementOne  = (ImageButton) getView().findViewById(R.id.idDetailSrvc_ImageButtonAttachementOne);
	    	  idDetailSrvc_ImageButtonAttachementTwo  = (ImageButton) getView().findViewById(R.id.idDetailSrvc_ImageButtonAttachementTwo);
	    	  idDetailSrvc_ImageButtonAttachementTree  = (ImageButton) getView().findViewById(R.id.idDetailSrvc_ImageButtonAttachementTree);

	    	  idDetailSrvc_ImageviewAttachementOne  = (ImageView) getView().findViewById(R.id.idDetailSrvc_ImageviewAttachementOne);
	    	  idDetailSrvc_ImageviewAttachementTwo  = (ImageView) getView().findViewById(R.id.idDetailSrvc_ImageviewAttachementTwo);
	    	  idDetailSrvc_ImageviewAttachementTree  = (ImageView) getView().findViewById(R.id.idDetailSrvc_ImageviewAttachementTree);
	    	  idDetailSrvc_ImageButtonAttachementOne.setOnClickListener(deleteCaptureListener);
	    	  idDetailSrvc_ImageButtonAttachementTwo.setOnClickListener(deleteCaptureListener);
	    	  idDetailSrvc_ImageButtonAttachementTree.setOnClickListener(deleteCaptureListener);
	    	  idDetailSrvc_ImageviewAttachementOne.setOnClickListener(captureListener);
	    	  idDetailSrvc_ImageviewAttachementTwo.setOnClickListener(captureListener);
	    	  idDetailSrvc_ImageviewAttachementTree.setOnClickListener(captureListener);
	    	  idDetailSrvc_linearLayoutEvaluation =    (LinearLayout) getView().findViewById(R.id.idDetailSrvc_linearLayoutEvaluation);
	    	  idDetailSrvc_linearLayoutEvaluation.setOnClickListener(evaluationListener);
	    	  idDetailSrvc_textViewId   = (TextView) getView().findViewById(R.id.idDetailSrvc_textViewId);
	    	  
	    	  int[] location = new int[2];
	    	  
	    	  idDetailSrvc_linearLayoutSuivreMission = (LinearLayout) getView().findViewById(R.id.idDetailSrvc_linearLayoutSuivreMission);
	    	  idDetailSrvc_imageViewDeleteMission = (ImageButton) getView().findViewById(R.id.idDetailSrvc_imageViewDeleteMission);
    		  idDetailSrvc_linearLayoutSignalerAbbus = (LinearLayout) getView().findViewById(R.id.idDetailSrvc_linearLayoutSignalerAbbus);
    		  idDetailSrvc_linearLayoutPartagerFacebook = (LinearLayout) getView().findViewById(R.id.idDetailSrvc_linearLayoutPartagerFacebook);
    		  idDetailSrvc_buttonSignalerUnAbbus = (ImageButton) getView().findViewById(R.id.idDetailSrvc_buttonSignalerUnAbbus);

		      idDetailSrvc_linearLayoutSuivreMission.setOnClickListener(SuivreMissionListener);
	          idDetailSrvc_linearLayoutPartagerFacebook.setOnClickListener(partagerFacebookListener);
	          idDetailSrvc_linearLayoutSignalerAbbus.setOnClickListener(signalerAbusListener);
	          idDetailSrvc_imageViewDeleteMission.setOnClickListener(deleteMissionListener);
	   	 
	   	   // Get the x, y location and store it in the location[] array
	   	   // location[0] = x, location[1] = y.
	   	   idDetailMissionSrvc_circleImaleProfilPicto.getLocationOnScreen(location);
	   	 
	   	   //Initialize the Point with x, and y positions
	   	   p = new Point();
	   	   p.x = location[0];
	   	   p.y = location[1];
	    	
	       
	    }
	 
	 private OnClickListener deleteMissionListener = new OnClickListener() {
	        public void onClick(View v) {
	            Builder builder = new Builder(DetailServiceActivity.this.getActivity());
	            View content = getActivity().getLayoutInflater().inflate(R.layout.message_dialog, null);
	            LinearLayout idMessageDialog_linearLayoutAnnuler = (LinearLayout) content.findViewById(R.id.idMessageDialog_linearLayoutAnnuler);
	            LinearLayout idMessageDialog_linearLayoutValider = (LinearLayout) content.findViewById(R.id.idMessageDialog_linearLayoutValider);
	            ((TextView) content.findViewById(R.id.idMessageDialog_textView)).setText(R.string.txtDetailSrvc_textAlertMessageDeleteMission);
	            idMessageDialog_linearLayoutValider.setOnClickListener(alertValiderDeleteMissionListener);
	            idMessageDialog_linearLayoutAnnuler.setOnClickListener(alertAnnulerListener);
	            builder.setTitle(getString(R.string.txtDetailSrvc_textAlertTitleDeleteMission));
	            builder.setView(content);
	            alertDialog = builder.create();
	            alertDialog.show();
	        }
	    };
	 
	    private OnClickListener  alertValiderDeleteMissionListener = new OnClickListener() {
	        public void onClick(View v) {
	            alertDialog.dismiss();
	            progressDialog = CustomProgressDialog.createProgressDialog(DetailServiceActivity.this.getActivity(), getString(R.string.txtMenu_dialogChargement));
	            MissionManager.getInstance().deleteMission(missionId,connectedUser,password, DetailServiceActivity.this);
	        }
	    };
	    
	 private OnClickListener evaluationListener = new OnClickListener() {
         public void onClick(View v) {
             new Handler().post(new Runnable() {
					
					@Override
					public void run() {
						Builder builder = new Builder(DetailServiceActivity.this.getActivity());
	                    View content = getActivity().getLayoutInflater().inflate(R.layout.rating_dialog_box, null);
	                    TextView myTitle = (TextView) content.findViewById(R.id.myTitle);
	                    LinearLayout idRatingDialog_linearLayoutValider = (LinearLayout) content.findViewById(R.id.idRatingDialog_linearLayoutValider);
	                    LinearLayout idRatingDialog_linearLayoutAnnuler = (LinearLayout) content.findViewById(R.id.idRatingDialog_linearLayoutAnnuler);
	                    builder.setTitle(R.string.txtDetailSrvc_viewDialogRatingMessage);
	                    ratingDialog_ratingBarDialog = (RatingBar) content.findViewById(R.id.ratingDialog_ratingBarDialog);
	                    idRatingDialog_linearLayoutValider.setOnClickListener(alertValiderRatingListener);
	                    idRatingDialog_linearLayoutAnnuler.setOnClickListener(alertAnnulerListener);
	                    builder.setView(content);
	                    alertDialog = builder.create();
	                    alertDialog.show();
						
					}
				});
         }
     };
     
     private OnClickListener alertAnnulerListener = new OnClickListener() {
         public void onClick(View v) {
             alertDialog.dismiss();
         }
     };
     private OnClickListener alertValiderRatingListener = new OnClickListener() {
         public void onClick(View v) {
             String rating = String.valueOf(ratingDialog_ratingBarDialog.getRating());
             alertDialog.dismiss();
             DetailServiceActivity.progressDialog = CustomProgressDialog.createProgressDialog(DetailServiceActivity.this.getActivity(), getString(R.string.txtMenu_dialogChargement));
             EvaluationManager.getInstance().createEvaluation(offre.getUserId(), connectedUser, rating, missionId,password, DetailServiceActivity.this);
         }
     };
     
  
	 
	  private void showEvaluation() {
	        if ((statut == Constant.STATUT_MISSION_ACCEPTE || statut==Constant.STATUT_MISSION_CLOTURE)  && 
	        		((connectedUser.equals(userId) && (mission.getEvalmission()==100 ||mission.getEvalmission()==101)) ||
	        				(offre!=null && hasOffre== true && (mission.getEvalmission()==100 ||mission.getEvalmission()==110)) )) {
	            idDetailSrvc_linearLayoutEvaluation.setVisibility(View.VISIBLE);
	        }
	    }
	 
	 private OnClickListener captureListener = new OnClickListener() {
			public void onClick(View v) {
				int targetID = v.getId();
				selectedView = targetID;
				selectImage(targetID);
			}
		};
	 
		private OnClickListener deleteCaptureListener = new OnClickListener() {
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.idDetailSrvc_ImageButtonAttachementOne:
					bitmaps[0] = "";
					idDetailSrvc_ImageviewAttachementOne
							.setImageResource(R.drawable.creercompte_btnaddphoto);
					idDetailSrvc_ImageButtonAttachementOne.setVisibility(View.GONE);
					break;
				case R.id.idDetailSrvc_ImageButtonAttachementTwo:
					bitmaps[1] = "";
					idDetailSrvc_ImageviewAttachementOne
							.setImageResource(R.drawable.creercompte_btnaddphoto);
					idDetailSrvc_ImageButtonAttachementTwo.setVisibility(View.GONE);
					break;
				case R.id.idDetailSrvc_ImageButtonAttachementTree:
					bitmaps[2] = "";
					idDetailSrvc_ImageviewAttachementOne
							.setImageResource(R.drawable.creercompte_btnaddphoto);
					idDetailSrvc_ImageButtonAttachementTree.setVisibility(View.GONE);
				default:
				}
			}
		};
	 


	  
	    
	  
	 private OnClickListener propositionOffreListener =new OnClickListener() {
	        public void onClick(View v) {
	        	SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(getActivity());
	        	checkphone = prefs.getString("checkphone", "");
	        	if(checkphone==null || checkphone.equals("")){
	        		Toast.makeText(getActivity().getApplicationContext(),
							R.string.txtLogin_messageConfirmationMobile,
							Toast.LENGTH_LONG).show();
	        		
	        		
					Intent mainIntent = new Intent(getActivity(),
							ProfilFragment.class);
					Bundle bundle = new Bundle();
					bundle.putString("src","phone");
					mainIntent.putExtras(bundle);
					startActivity(mainIntent);
					
	        		//startActivity(new Intent(getActivity(), ProfilFragment.class));
	        	}else{
		        	idDetailSrvc_linearLayoutProposerOffreFragment.setVisibility(View.VISIBLE);
		        	idDetailSrvc_linearLayoutProposerOffre.setVisibility(View.GONE);
	        	}
	        }
	    };
	 

	    private OnClickListener sendMessageListener = new OnClickListener() {
	        public void onClick(View v) {
	            if (idDetailSrvc_editMessageText.getText().toString().length() == 0) {
	                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.txtDetailSrvc_textMessageVide), Toast.LENGTH_SHORT).show();
	                return;
	            }
	            progressDialog = CustomProgressDialog.createProgressDialog(DetailServiceActivity.this.getActivity(), getString(R.string.txtMenu_dialogChargement));
	            MessagesManager.getInstance().createMessage(missionId, connectedUser, idDetailSrvc_editMessageText.getText().toString(), 0,null,password, DetailServiceActivity.this);
	        }
	    };
	    

	 
	 private OnClickListener showProfilListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			View view = getActivity().getLayoutInflater().inflate(R.layout.pop_profil, null);
			 mPopupWindow = new BackgroundDarkPopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT,
		                WindowManager.LayoutParams.WRAP_CONTENT);
			 CircleImageView  idPopProfil_circleImaleProfilPicto  = (CircleImageView) view.findViewById(R.id.idPopProfil_circleImaleProfilPicto);
			 TextView idPopProfil_textViewPseudo   = (TextView) view.findViewById(R.id.idPopProfil_textViewPseudo);
			 RatingBar idPropProfil_ratinBarEval   = (RatingBar) view.findViewById(R.id.idPropProfil_ratinBarEval);
			 TextView idPopProfil_textViewMembreDepuis   = (TextView) view.findViewById(R.id.idPopProfil_textViewMembreDepuis);
			 TextView idPopProfil_textViewNbrPoints   = (TextView) view.findViewById(R.id.idPopProfil_textViewNbrPoints);
			 
			 ImageView idPopProfil_imageviewPhone  = (ImageView) view.findViewById(R.id.idPopProfil_imageviewPhone); 
			 ImageView idPopProfil_imageviewPhoneCheck  = (ImageView) view.findViewById(R.id.idPopProfil_imageviewPhoneCheck);
			 ImageView idPopProfil_imageviewEmail  = (ImageView) view.findViewById(R.id.idPopProfil_imageviewEmail);
			 ImageView idPopProfil_imageviewEmailCheck  = (ImageView) view.findViewById(R.id.idPopProfil_imageviewEmailCheck);
			 ImageView idPopProfil_imageviewFacebook  = (ImageView) view.findViewById(R.id.idPopProfil_imageviewFacebook);
			 ImageView idPopProfil_imageviewFacebookCheck  = (ImageView) view.findViewById(R.id.idPopProfil_imageviewFacebookCheck);
			 
			 int popupWidth = 1200;
             int popupHeight = 800;
             
             imageLoader.displayImage(new StringBuilder(Constant.CHECK_EDU_PICTURES_URL).append(mission.getOriginator()).append(".jpg").toString(), idPopProfil_circleImaleProfilPicto, options);
             idPopProfil_textViewPseudo.setText(Html.fromHtml(mission.getPseudo()));
             idPropProfil_ratinBarEval.setRating(Float.parseFloat(mission.getNote()));
             try{
	        	 Date membre = dateFormat.parse(mission.getMembre());
	        	 idPopProfil_textViewMembreDepuis.setText(getString(R.string.txtDetailSrvc_textViewService) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + formatter.format(membre));
	        }catch(Exception e){
	        	
	        }
             idPopProfil_textViewNbrPoints.setText(getString(R.string.txtDetailSrvc_textViewNbrPoints) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + " 25");
             if(mission.getCheckmail()!=null && mission.getCheckmail().equals("1")){
            	 idPopProfil_imageviewEmail.setImageResource(R.drawable.green_email);
    			 idPopProfil_imageviewEmailCheck.setImageResource(R.drawable.green_check);
             }
             if(mission.getCheckphone()!=null && !mission.getCheckphone().equals("")){
            	  idPopProfil_imageviewPhone.setImageResource(R.drawable.green_phone); 
    			  idPopProfil_imageviewPhoneCheck.setImageResource(R.drawable.green_check);
             }
             if(mission.getFacebookid()!=null && !mission.getFacebookid().equals("")){
            	  idPopProfil_imageviewFacebook.setImageResource(R.drawable.green_facebook);
    			  idPopProfil_imageviewFacebookCheck.setImageResource(R.drawable.green_check);
             }
             
		     mPopupWindow.setFocusable(true);
		     mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		     mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
		       
             mPopupWindow.setDarkStyle(-1);
             mPopupWindow.setDarkColor(Color.parseColor("#a0000000"));
             mPopupWindow.resetDarkPosition();
                
             mPopupWindow.setWidth(popupWidth);
                //mPopupWindow.setHeight(popupHeight);
               
             int OFFSET_X = 120;
             int OFFSET_Y = 120;
                
             mPopupWindow.darkFillScreen();
                //mPopupWindow.showAtLocation(idDetailMissionSrvc_circleImaleProfilPicto, Gravity.NO_GRAVITY, p.x, p.y );
             mPopupWindow.showAtLocation(idDetailMissionSrvc_circleImaleProfilPicto, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);
                
		}
	};

	
}