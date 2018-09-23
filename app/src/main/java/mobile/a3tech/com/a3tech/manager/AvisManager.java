package mobile.a3tech.com.a3tech.manager;

import java.util.List;


import android.os.Handler;
import android.os.Message;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.Avis;
import mobile.a3tech.com.a3tech.service.AvisService;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;

public class AvisManager implements Constant {

	private static AvisManager uniqueInstance = null;

	public AvisManager() {
	}

	public static AvisManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new AvisManager();
		}
		return uniqueInstance;
	}

	public void avisAssociation(final String idAssociation,final String idUser, final String password,
			final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_AVISS_ASSOCIATION, 0);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				try {
					AvisService service = new AvisService();
					List<Avis> result = service.avisAssociation(idAssociation,idUser,password);

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

	public void createAvis(final String idAssociation, final String avantage,
			final String inconvenient, final String rating,
			final String statut,final String idUser, final String password, final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_CREATE_AVIS, 0);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				try {
					AvisService service = new AvisService();
					String result = service.createAvis(idAssociation, avantage,
							inconvenient, rating, statut,idUser,password);

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

	public void createAvis(final String identifiant,final String idUser, final String password,
			final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_DELETE_AVIS, 0);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				try {
					AvisService service = new AvisService();
					String result = service.deleteAvis(identifiant,idUser,password);

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
