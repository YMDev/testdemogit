package mobile.a3tech.com.a3tech.manager;

import java.util.List;


import android.os.Handler;
import android.os.Message;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.Association;
import mobile.a3tech.com.a3tech.service.AssociationService;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;

public class AssociationManager implements Constant {

	private static AssociationManager uniqueInstance = null;

	public AssociationManager() {
	}

	public static AssociationManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new AssociationManager();
		}
		return uniqueInstance;
	}

	public void detailAssociation(final String idAssociation,final String idUser, final String password,
			final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_ASSOCIATION_DETAIL_ASSOCIATION, 0);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				try {
					AssociationService service = new AssociationService();
					Association result = service
							.detailAssociation(idAssociation,idUser,password);

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

	public void listeAssociation(final String idUser, final String password,final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_ASSOCIATION_LISTE_ASSOCIATION, 0);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				try {
					AssociationService service = new AssociationService();
					List<Association> result = service.listeAssociations(idUser, password);

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
