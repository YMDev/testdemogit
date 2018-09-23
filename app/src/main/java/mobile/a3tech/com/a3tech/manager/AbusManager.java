package mobile.a3tech.com.a3tech.manager;


import android.os.Handler;
import android.os.Message;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.service.AbusService;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;

public class AbusManager implements Constant {
	
private static AbusManager uniqueInstance = null;

	
	
	public static AbusManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new AbusManager();
		}
		return uniqueInstance;
	}

	

	
	public void createAbus(final String idMission,final int idMessage,final int typeAbus,final String idUser,final String password,final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_ABUS_MANAGER_CREATE_ABUS,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					AbusService service = new AbusService();
					String result = service.createAbus(idMission,idMessage,typeAbus,idUser,password);

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
