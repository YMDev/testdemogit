package mobile.a3tech.com.a3tech.manager;

import java.util.List;


import android.os.Handler;
import android.os.Message;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.MissionService;
import mobile.a3tech.com.a3tech.utils.Constant;

public class MissionManager implements Constant {
	
private static MissionManager uniqueInstance = null;

	
	
	public static MissionManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new MissionManager();
		}
		return uniqueInstance;
	}

	public void createMission(final String connectedUser,final String typeTransaction,final String categorie,final String sousCategorie,final String idTypeTroc ,final String titre,
			final String article,final String idEtatArticle, final String contre,final String objetRechercheTitre,final String objetRecherche,final String lieu,final String latitude,final String longitude,final String idVille,
			final String[] bitmap,final String echeance, final String typeSponsoring,final String typePaiement,final String dateDebut,final String dateFin,final String password, final DataLoadCallback dataLoadCallback) {
		
         
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_MISSION_MANAGER_CREATE_MISSION,0);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				try{
					MissionService service = new MissionService();
					String result = service.createMission(connectedUser,typeTransaction,categorie,sousCategorie,idTypeTroc ,titre,article,idEtatArticle,
							contre,objetRechercheTitre,objetRecherche,lieu,latitude,longitude,idVille,bitmap,echeance,
							typeSponsoring, typePaiement, dateDebut,dateFin,password);
					Message message = handler.obtainMessage(0, result);
					handler.sendMessage(message);

				}catch (EducationException e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}

			}
		}.start();
	}
	
	
	/***detail mission ***/
	public void detailMission(final String idMission,final String userId,final String password,
			final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				
					dataLoadCallback.dataLoaded(message.obj,KEY_MISSION_MANAGER_DETAIL_MISSION,0);
				
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					MissionService service = new MissionService();
					Mission mission = service.detailMission(  idMission,  userId,password);
					
					if(mission == null){
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					}else{
						Message message = handler.obtainMessage(0, mission);
						handler.sendMessage(message);
					}
					
					
					
				}catch (EducationException e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}
				
			}
		}.start();
	}
	
	
	
	public void filtreMission(final String lang, final String connectedUser,final String keyWord,final String distance,final String services,final int typeOperation,final String start,
			final String limit,final String key,final String typeTransaction,final String premium,final String password,final int order,final int type, final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_MISSION_MANAGER_FILTRE_MISSION,typeOperation);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
				
					
					MissionService service = new MissionService();
					List<A3techMission> result = service.filtreMission(lang, connectedUser, keyWord, distance, services, start, limit, key,typeTransaction,premium,password,order,type);
					
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
	

	
	
	
	public void missionsEmises(final String lang,final int typeOperation,final String idUser,final String start,final String limit,final String password, final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_MISSION_MANAGER_MISSIONS_EMISES,typeOperation);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					MissionService service = new MissionService();
					List<Mission> result = service.missionsEmises(lang,idUser,start,limit,password);
					
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
	
	
	public void missionsSuivies(final int typeOperation,final String idUser,final String start,final String limit,final String password,final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_MISSION_MANAGER_MISSIONS_SUIVIES,typeOperation);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					MissionService service = new MissionService();
					List<Mission> result = service.missionsSuivies(idUser,start,limit,password);
					
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
	
	
	public void missionsSuiviesChat(final String lang, final int typeOperation,final String idUser,final String start,final String limit,final String password,final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_MISSION_MANAGER_MISSIONS_SUIVIES_CHAT,typeOperation);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					MissionService service = new MissionService();
					List<Mission> result = service.missionsSuiviesChat(lang,idUser,start,limit,password);
					
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
	
	
	//delete mission
	
		public void deleteMission(final String identifiant,final String idUser, final String password, final DataLoadCallback dataLoadCallback) {
			final Handler handler = new Handler() {
				// @Override
				public void handleMessage(Message message) {
					if (message.obj instanceof Integer) {
						dataLoadCallback.dataLoadingError((Integer)message.obj);
					} else {
						dataLoadCallback.dataLoaded(message.obj,KEY_MISSION_MANAGER_DELETE_MISSION,0);
					}
				}
			};

			new Thread() {
				@Override
				public void run() {
					try{
						MissionService service = new MissionService();
						String result = service.deleteMission(identifiant,idUser,password);
						
						if(!result.equals("OK")){
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



	public void updateMission(final A3techMission mission,final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_USER_MANAGER_UPDATE_MISSION,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					MissionService service = new MissionService();
					A3techMission result = service.updateMission(mission);

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
