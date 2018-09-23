package mobile.a3tech.com.a3tech.manager;

import java.util.List;

import android.os.Handler;
import android.os.Message;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.Offre;
import mobile.a3tech.com.a3tech.model.ReponseOffre;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.OffreService;
import mobile.a3tech.com.a3tech.utils.Constant;

public class OffresManager implements Constant {
	
private static OffresManager uniqueInstance = null;

	
	
	public static OffresManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new OffresManager();
		}
		return uniqueInstance;
	}

	
	/***detail mission ***/
	public void detailOffre(final String idOffre,final int method,final String idUser,final String password,
			final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				
					dataLoadCallback.dataLoaded(message.obj,KEY_OFFRE_MANAGER_DETAIL_OFFRE,method);
				
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					OffreService service = new OffreService();
					Offre offre = service.detailOffre(idOffre,idUser,password);
					
					if(offre == null){
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					}else{
						Message message = handler.obtainMessage(0, offre);
						handler.sendMessage(message);
					}
					
					
					
				}catch (EducationException e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}
				
			}
		}.start();
	}
	
	
	public void offresMission(final String idMission,final String idUser,final String password ,final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_OFFRE_MANAGER_OFFRES_MISSION,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					OffreService service = new OffreService();
					ReponseOffre result = service.offreMission(idMission,idUser,password);
					
					if(result==null){
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					}else{
						Message message = handler.obtainMessage(0, result);
						handler.sendMessage(message);
					}
	
				}catch (EducationException e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}
				
			}
		}.start();
	}
	
	public void createOffre(final String idMission,final String idUser,final String[] bitmaps,final String commentaire,final String password,final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_OFFRE_MANAGER_CREATE_OFFRE,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					OffreService service = new OffreService();
					String result = service.createOffre(idMission,idUser,bitmaps,commentaire,password);
					
					if(result==null){
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					}else{
						Message message = handler.obtainMessage(0, result);
						handler.sendMessage(message);
					}
					
					
					
				}catch (EducationException e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}
				
			}
		}.start();
	}
	
	public void updateOffre(final String idOffre,final String etat,final String idUser, final String password,final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_OFFRE_MANAGER_UPDATE_OFFRE,Integer.valueOf(etat));
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					OffreService service = new OffreService();
					String result  = service.updateeOffre(idOffre,etat,idUser,password);
					if(result==null){
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					}else{
						Message message = handler.obtainMessage(0, result);
						handler.sendMessage(message);
					}
				}catch (EducationException e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}
				
			}
		}.start();
	}
	
	public void majOffre(final String idOffre,final String idUser,final String etat,final String password,final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_OFFRE_MANAGER_MAJ_OFFRE,Integer.valueOf(idOffre));
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					OffreService service = new OffreService();
					String result  = service.majOffre(idOffre, idUser, etat,password);
					if(result==null){
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					}else{
						Message message = handler.obtainMessage(0, result);
						handler.sendMessage(message);
					}
				}catch (EducationException e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}
				
			}
		}.start();
	}
	
	public void deleteOffre(final String idOffre,final String idUser, final String password, final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_OFFRE_MANAGER_DELETE_OFFRE,0);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				try{
					OffreService service = new OffreService();
					String result  = service.deleteOffre(idOffre,idUser,password);
					
					if(result==null){
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					}else{
						Message message = handler.obtainMessage(0, result);
						handler.sendMessage(message);
					}
				}catch (EducationException e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}
				
			}
		}.start();
	}
	
	public void deleteOffreCoteEmetteur(final String idOffre,final String idUser, final String password, final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_OFFRE_MANAGER_DELETE_OFFRE_COTE_EMETTEUR,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					OffreService service = new OffreService();
					String result  = service.deleteOffreCoteEmetteur(idOffre,idUser,password);
					
					if(result==null){
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					}else{
						Message message = handler.obtainMessage(0, result);
						handler.sendMessage(message);
					}
					
					
					
				}catch (EducationException e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}
				
			}
		}.start();
	}



public void deleteOffreCoteOffreur(final String idOffre,final String type,final String idUser, final String password, final DataLoadCallback dataLoadCallback) {
	final Handler handler = new Handler() {
		// @Override
		public void handleMessage(Message message) {
			if (message.obj instanceof Integer) {
				dataLoadCallback.dataLoadingError((Integer)message.obj);
			} else {
				dataLoadCallback.dataLoaded(message.obj,KEY_OFFRE_MANAGER_DELETE_OFFRE_COTE_OFFREUR,0);
			}
		}
	};

	new Thread() {
		@Override
		public void run() {
			try{
				OffreService service = new OffreService();
				String result  = service.deleteOffreCoteEmetteur(idOffre,idUser,password);
				
				if(result==null){
					Message message = handler.obtainMessage(0, 0);
					handler.sendMessage(message);
				}else{
					Message message = handler.obtainMessage(0, result);
					handler.sendMessage(message);
				}
				
				
				
			}catch (EducationException e) {
				Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
				handler.sendMessage(message);
			}
			
		}
	}.start();
}
}
