package mobile.a3tech.com.a3tech.manager;

import java.util.List;


import android.os.Handler;
import android.os.Message;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.PieceJointe;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.PieceJointeService;
import mobile.a3tech.com.a3tech.utils.Constant;

public class PieceJointeManager implements Constant {
	
	
	private static PieceJointeManager uniqueInstance = null;

	public static PieceJointeManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new PieceJointeManager();
		}
		return uniqueInstance;
	}
	
	public void listePiecesJointes(final String idMission,final String idUser, final String password,final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,37,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					PieceJointeService service = new PieceJointeService();
					List<PieceJointe> result = service.listePiecesJointes(idMission,idUser,password);
					
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
