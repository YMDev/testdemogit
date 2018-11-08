package mobile.a3tech.com.a3tech.manager;

import android.os.Handler;
import android.os.Message;

import java.util.Date;
import java.util.List;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.A3techNotification;
import mobile.a3tech.com.a3tech.model.A3techNotificationType;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.service.A3techNotificationService;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.MissionService;
import mobile.a3tech.com.a3tech.utils.Constant;

public class NotificationsManager implements Constant {

    private static NotificationsManager uniqueInstance = null;


    public static NotificationsManager getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new NotificationsManager();
        }
        return uniqueInstance;
    }

    public static A3techNotification getNotificationInstance(A3techUser userClient,
                                                             A3techUser tech, A3techMission mission,
                                                             A3techNotificationType type,
                                                             String commentaire,
                                                             String libelle) {
        A3techNotification notification = new A3techNotification();
        notification.setMission(mission);
        notification.setCommentaire(commentaire);
        notification.setDateCreation(new Date());
        notification.setTitre(libelle);
        notification.setTypeNotification(type);
        notification.setDateNotification(new Date());
        notification.setUserClient(userClient);
        notification.setUserTech(tech);
        return  notification;
    }

    public void createNotification(final A3techNotification notification, final DataLoadCallback dataLoadCallback) {


        final Handler handler = new Handler() {
            public void handleMessage(Message message) {
                if (message.obj instanceof Integer) {
                    dataLoadCallback.dataLoadingError((Integer) message.obj);
                } else {
                    dataLoadCallback.dataLoaded(message.obj, KEY_MISSION_MANAGER_CREATE_MISSION, 0);
                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                try {
                    A3techNotificationService service = new A3techNotificationService();
                    A3techNotification result = service.createNotification(notification);
                    Message message = handler.obtainMessage(0, result);
                    handler.sendMessage(message);

                } catch (EducationException e) {
                    Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
                    handler.sendMessage(message);
                }

            }
        }.start();
    }


    public void filtreNotification(final String lang, final Long userClientID, final String keyWord, final int typeOperation, final String start,
                                   final String limit, final String key, final Long missionID, final Long userTechID, final String password, final int order, final int type, final DataLoadCallback dataLoadCallback) {
        final Handler handler = new Handler() {
            // @Override
            public void handleMessage(Message message) {
                if (message.obj instanceof Integer) {
                    dataLoadCallback.dataLoadingError((Integer) message.obj);
                } else {
                    dataLoadCallback.dataLoaded(message.obj, KEY_MISSION_MANAGER_FILTRE_MISSION, typeOperation);
                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                try {


                    A3techNotificationService service = new A3techNotificationService();
                    List<A3techNotification> result = service.filtreNotification(lang, userClientID, keyWord, start, limit, key, missionID, userTechID, password, order, type);

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


    //delete mission

    public void deleteNotification(final Long idNotification, final Long idUser, final String password, final boolean isTech, final DataLoadCallback dataLoadCallback) {
        final Handler handler = new Handler() {
            // @Override
            public void handleMessage(Message message) {
                if (message.obj instanceof Integer) {
                    dataLoadCallback.dataLoadingError((Integer) message.obj);
                } else {
                    dataLoadCallback.dataLoaded(message.obj, KEY_MISSION_MANAGER_DELETE_MISSION, 0);
                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                try {
                    A3techNotificationService service = new A3techNotificationService();
                    String result = service.deleteNotification(idNotification, idUser, password, isTech);

                    if (!result.equals("OK")) {
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
