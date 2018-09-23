package mobile.a3tech.com.a3tech.manager;

import java.util.List;


import android.os.Handler;
import android.os.Message;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.VilleService;
import mobile.a3tech.com.a3tech.utils.Constant;

public class VilleManager implements Constant {

	private static VilleManager uniqueInstance = null;

	public static VilleManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new VilleManager();
		}
		return uniqueInstance;
	}

	public void listeVilles(final String pays,final String lang,final String idUser, final String password,final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_VILLE_MANAGER_LISTE_VILLE,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {
					VilleService service = new VilleService();
					List<Categorie> result = service.listeVilles(pays, lang,idUser,password);

					if (result == null) {
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					} else {
						Message message = handler.obtainMessage(0, result);
						handler.sendMessage(message);
					}

				} catch (EducationException e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}

			}
		}.start();
	}

	
	public void listeQuartiers(final String idVille,final String idUser, final String password,final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_QUARTIER_MANAGER_LISTE_QUARTIER,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {
					VilleService service = new VilleService();
					List<Categorie> result = service.listeQuartiers(idVille,idUser,password);

					if (result == null) {
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					} else {
						Message message = handler.obtainMessage(0, result);
						handler.sendMessage(message);
					}

				} catch (EducationException e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}

			}
		}.start();
	}

}
