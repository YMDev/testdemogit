package mobile.a3tech.com.a3tech.manager;

import java.util.List;


import android.os.Handler;
import android.os.Message;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.MessageService;
import mobile.a3tech.com.a3tech.utils.Constant;

public class MessagesManager implements Constant {
	
private static MessagesManager uniqueInstance = null;

	
	
	public static MessagesManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new MessagesManager();
		}
		return uniqueInstance;
	}

	
	public void messagesMission(final String idMission,final String idUser, final String password,final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_MESSAGE_MANAGER_MESSAGE_MISSION,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					MessageService service = new MessageService();
					List<mobile.a3tech.com.a3tech.model.Message> result = service.messageMission(idMission,idUser,password);
					
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
	
	public void createMessage(final String idMission,final String idUser,final String texte,final int type,final String idOffre, final String password,final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_MESSAGE_MANAGER_CREATE_MESSAGE,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					MessageService service = new MessageService();
					String result = service.createMessage(idMission,idUser,texte,String.valueOf(type),idOffre,password);
					
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
