package mobile.a3tech.com.a3tech.manager;

import java.util.List;

import android.os.Handler;
import android.os.Message;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.Evaluation;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.EvaluationService;
import mobile.a3tech.com.a3tech.utils.Constant;

public class EvaluationManager implements Constant {
	
private static EvaluationManager uniqueInstance = null;

	
	
	public static EvaluationManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new EvaluationManager();
		}
		return uniqueInstance;
	}

	
	public void listeEvaluation(final String lang, final int typeOp,final String idUser,final String type, final String start,final String limit,final String password,
			final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_EVALUATION_MANAGER_LISTE_EVALUATION,typeOp);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					EvaluationService service = new EvaluationService();
					List<Evaluation> result = service.listeEvaluation(lang,idUser,type,start,limit,password);
					
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
	
	public void createEvaluation(final String idEvalue,final String idEvaluateur,final String rating,final String idMission,final String password,
			final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer)message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_EVALUATION_MANAGER_CREATE_EVALUATION,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try{
					EvaluationService service = new EvaluationService();
					String result = service.createEvaluation(idEvalue,idEvaluateur,rating,idMission,password);
					
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
