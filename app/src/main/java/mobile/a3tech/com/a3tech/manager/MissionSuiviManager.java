package mobile.a3tech.com.a3tech.manager;


import android.os.Handler;
import android.os.Message;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.MissionSuiviService;
import mobile.a3tech.com.a3tech.utils.Constant;

public class MissionSuiviManager implements Constant {
	
private static MissionSuiviManager uniqueInstance = null;

	
	
	public static MissionSuiviManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new MissionSuiviManager();
		}
		return uniqueInstance;
	}

	

	
	public void createMissionSuivi(final String idMission,final String idUser,final String password,final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_MISSION_SUIVI_MANAGER_SUIVRE_MISSION,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					MissionSuiviService service = new MissionSuiviService();
					String result = service.createMissionSuivi(idMission,idUser,password);

					if(result==null){
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					}else{
						Message message = handler.obtainMessage(0, "abus");
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
