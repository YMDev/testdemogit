package mobile.a3tech.com.a3tech.manager;

import java.util.List;


import android.os.Handler;
import android.os.Message;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.images.Image;
import mobile.a3tech.com.a3tech.model.Indicateur;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.ImageService;
import mobile.a3tech.com.a3tech.utils.Constant;

public class ImageManager implements Constant {

	private static ImageManager uniqueInstance = null;

	public ImageManager() {
	}

	public static ImageManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ImageManager();
		}
		return uniqueInstance;
	}

	public void listeImages(final String idEntreprise,final String idUser, final String password,
			final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_ASSOCIATION_LISTE_IMAGES, 0);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				try {
					ImageService service = new ImageService();
					List<Image> result = service.listeImages(idEntreprise,idUser, password);

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

	public void nbrImages(final String idEntreprise,final String idUser, final String password,
			final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_ASSOCIATION_NOMBRE_IMAGES, 0);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				try {
					ImageService service = new ImageService();
					Indicateur result = service.nbrImage(idEntreprise,idUser, password);

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
