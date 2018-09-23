package mobile.a3tech.com.a3tech.manager;

import java.util.List;


import android.os.Handler;
import android.os.Message;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.service.CategorieService;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;

public class CategorieManager implements Constant {

	private static CategorieManager uniqueInstance = null;

	public static CategorieManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new CategorieManager();
		}
		return uniqueInstance;
	}

	public void listeCategories(final String identifiant, final String servic,
			final String type, final String parentId,final String lang,final String idUser, final String password,
			final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_CATEGORIE_MANAGER_LISTE_CATEGORIE,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {
					CategorieService service = new CategorieService();
					List<Categorie> result = service.listeCategories(
							identifiant, servic, type, parentId,lang,idUser,password);

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
