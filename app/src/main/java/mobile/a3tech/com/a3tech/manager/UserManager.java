package mobile.a3tech.com.a3tech.manager;
import android.os.Handler;
import android.os.Message;

import java.util.List;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.model.A3techUserStatut;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.service.A3techUserService;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.UserService;
import mobile.a3tech.com.a3tech.utils.Constant;

public class UserManager implements Constant {

	private static UserManager uniqueInstance = null;

	public static UserManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new UserManager();
		}
		return uniqueInstance;
	}

	
	

	 
	 
	 public void loginfb(final String nom, final String prenom,
				final String facebookId, final String email,
				final DataLoadCallback dataLoadCallback) {
			final Handler handler = new Handler() {
				// @Override
				public void handleMessage(Message message) {
					if (message.obj instanceof Integer) {
						dataLoadCallback.dataLoadingError((Integer) message.obj);
					} else {
						dataLoadCallback.dataLoaded(message.obj,
								KEY_USER_MANAGER_LOGIN_FB,0);
					}
				}
			};

			new Thread() {
				@Override
				public void run() {
					try {

						A3techUserService service = new A3techUserService();
						A3techUser user = service.loginfb(nom, prenom, facebookId, email);
						if (user == null) {
							Message message = handler.obtainMessage(0, 0);
							handler.sendMessage(message);
						} else {
							Message message = handler.obtainMessage(0, user);
							handler.sendMessage(message);
						}
					} catch (EducationException e) {
						Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
						handler.sendMessage(message);
					}

				}
			}.start();
		}
	 
	 
	public void createAccount(final String nom, final String prenom,
			final String email, final String password, final String image,
			final String regId, final String pseudo,
			final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_USER_MANAGER_CREATE_ACCOUNT,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {

                    A3techUserService service = new A3techUserService();
					
					A3techUser user = service.createAccount(nom, prenom, email,
							password, image, regId, pseudo);
					if (user == null) {
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					} else {
						if (!user.getNew()) {
							Message message = handler.obtainMessage(0, 10);
							handler.sendMessage(message);
						} else {
							Message message = handler.obtainMessage(0, user);
							handler.sendMessage(message);
						}
					}
				} catch (EducationException e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}

			}
		}.start();
	}
	public void createAccountJson(final String userJson,
							  final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_USER_MANAGER_CREATE_ACCOUNT,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {

					A3techUserService service = new A3techUserService();

					A3techUser user = service.createAccount(userJson);
					if (user == null) {
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					} else {
						if (!user.getNew()) {
							Message message = handler.obtainMessage(0, 10);
							handler.sendMessage(message);
						} else {
							Message message = handler.obtainMessage(0, user);
							handler.sendMessage(message);
						}
					}
				} catch (EducationException e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}

			}
		}.start();
	}

	// login avec application
	public void login(final String email, final String password,
			final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_USER_MANAGER_LOGIN,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {
                    A3techUserService service = new A3techUserService();

					A3techUser user = service.getUser(email, password);
					if (user == null) {
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					} else {
						Message message = handler.obtainMessage(0, user);
						handler.sendMessage(message);
					}
				} catch (EducationException e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}

			}
		}.start();
	}
	
	// initialiser le mot de passe
	public void initPassword(final String email, final String newPassword,
			final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_USER_MANAGER_INIT_PASSWORD,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {
                    A3techUserService service = new A3techUserService();
					String result = service.initPassword(email, newPassword);
					if (result.equals("NOK")) {
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
	
	
	// initialiser le mot de passe
		public void getVersion(
				final DataLoadCallback dataLoadCallback) {
			final Handler handler = new Handler() {
				// @Override
				public void handleMessage(Message message) {
					if (message.obj instanceof Integer) {
						dataLoadCallback.dataLoadingError((Integer) message.obj);
					} else {
						dataLoadCallback.dataLoaded(message.obj,
								KEY_USER_GET_VERSION,0);
					}
				}
			};

			new Thread() {
				@Override
				public void run() {
					try {
                        A3techUserService service = new A3techUserService();
						String result = service.getVersion();
						if (result.equals("NOK")) {
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
	
	
	
	
		public void desabonnerUser(final String idUser,final String password,
				final DataLoadCallback dataLoadCallback) {
			final Handler handler = new Handler() {
				// @Override
				public void handleMessage(Message message) {
					if (message.obj instanceof Integer) {
						dataLoadCallback.dataLoadingError((Integer) message.obj);
					} else {
						dataLoadCallback.dataLoaded(message.obj,
								KEY_USER_MANAGER_DESABONNER_USER,0);
					}
				}
			};

			new Thread() {
				@Override
				public void run() {
					try {
                        A3techUserService service = new A3techUserService();
						String result = service.desabonnerUser(idUser,password);
						if (result.equals("NOK")) {
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


	// get profil de la personne
	public void getUserReviews(final String idUser,final String password,
						  final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {

			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_USER_MANAGER_GET_PROFIL,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {
                    A3techUserService service = new A3techUserService();
					List reviews = service.getUserReviews(idUser,password);
					if (reviews == null) {
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					} else {
						Message message = handler.obtainMessage(0, reviews);
						handler.sendMessage(message);
					}
				} catch (EducationException e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}

			}
		}.start();
	}
	
	// get profil de la personne
	public void getProfil(final String idUser,final String password,
			final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {

			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_USER_MANAGER_GET_PROFIL,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {
                    A3techUserService service = new A3techUserService();
					A3techUser user = service.getProfil(idUser,password);
					if (user == null) {
						Message message = handler.obtainMessage(0, Constant.ERROR_USER_NOT_FOUND);
						handler.sendMessage(message);
					} else {
						if(user.getStatut() != null && A3techUserStatut.SUSPENDU.getDiscreptionEnum() == user.getStatut().getDiscreptionEnum()){
							Message message = handler.obtainMessage(0, Constant.ERROR_USER_DISABLED);
							handler.sendMessage(message);
						}else{
							Message message = handler.obtainMessage(0, user);
							handler.sendMessage(message);
						}

					}
				} catch (EducationException e) {
					Message message = handler.obtainMessage(0, Constant.ERROR_USER_TECHNIQUE);
					handler.sendMessage(message);
				}

			}
		}.start();
	}
	
	
	
	public void checkMail(final String mail,final String idUser,final String password,
			final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {

			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_USER_MANAGER_CHECK_MAIL,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {
                    A3techUserService service = new A3techUserService();
					String result = service.checkMail(mail,idUser,password);
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
	// get profil de la personne
		public void checkMobile(final String tel,final String idUser,final String password,
				final DataLoadCallback dataLoadCallback) {
			final Handler handler = new Handler() {

				// @Override
				public void handleMessage(Message message) {
					if (message.obj instanceof Integer) {
						dataLoadCallback.dataLoadingError((Integer) message.obj);
					} else {
						dataLoadCallback.dataLoaded(message.obj,
								KEY_USER_MANAGER_CHECK_MOBILE,0);
					}
				}
			};

			new Thread() {
				@Override
				public void run() {
					try {
                        A3techUserService service = new A3techUserService();
						String result = service.checkMobile(tel,idUser,password);
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

		// get profil de la personne
				public void validerMobile(final String idUser,final String password,
						final DataLoadCallback dataLoadCallback) {
					final Handler handler = new Handler() {

						// @Override
						public void handleMessage(Message message) {
							if (message.obj instanceof Integer) {
								dataLoadCallback.dataLoadingError((Integer) message.obj);
							} else {
								dataLoadCallback.dataLoaded(message.obj,
										KEY_USER_MANAGER_VALIDER_MOBILE,0);
							}
						}
					};

					new Thread() {
						@Override
						public void run() {
							try {
                                A3techUserService service = new A3techUserService();
								String result = service.validerMobile(idUser,password);
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
				
	
	// enregistrer profil

	public void saveProfil(final String identifiant,final String nom,final String prenom,
			final String tel,final String pseudo,final String categorie,final String latitude,
			final String longitude,final String regId,final String image,final String mode,final String distance,
			final String srvc,final String dateNaissance,final String ville,final String adresse,final String password,final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_USER_MANAGER_SAVE_PROFIL,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {
                    A3techUserService service = new A3techUserService();
					String result = service.updateProfil(identifiant, nom, prenom, tel, pseudo, categorie, latitude, longitude, regId, image, mode, distance, srvc, dateNaissance, ville,adresse,password);
					if (result == null) {
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					} else {
						Message message = handler.obtainMessage(0, result);
						handler.sendMessage(message);
					}
				} catch (Exception e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}

			}
		}.start();
	}
	
	
	//
	// //contact support
	//
	//
	public void contactSupport(final String idUser, final String message,final String password,
			final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_USER_MANAGER_CONTACT_SUPPORT,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {
                    A3techUserService service = new A3techUserService();
					String result = service.contactSupport(idUser, message,password);
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
	
	// changer le mot de passe
	public void changePassword(final String oldPassword,
			final String newPassword, final String identifiant,
			final DataLoadCallback dataLoadCallback) {
		final Handler handler = new Handler() {
			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,KEY_USER_MANAGER_CHANGE_PASSWORD,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {
                    A3techUserService service = new A3techUserService();
					Integer result = service.changePassword(oldPassword,
							newPassword, identifiant);
					Message message = null;
					switch (result) {
					case 10:
						message = handler.obtainMessage(0, 10);
						handler.sendMessage(message);
						break;
					case 20:
						message = handler.obtainMessage(0, 20);
						handler.sendMessage(message);
						break;
					default:
						message = handler.obtainMessage(0, "OK");
						handler.sendMessage(message);
						break;
					}

				} catch (EducationException e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}

			}
		}.start();
	}


	public void getTechnicienNearLocation(final String latitude, final String longitude, final String perim, final int start, final int end,
										  final DataLoadCallback dataLoadCallback)   {

		final Handler handler = new Handler() {

			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_USER_MANAGER_GET_PROFIL,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {

                    A3techUserService service = new A3techUserService();
					List<A3techUser> user = service.getTechnicienNearLocation(latitude,longitude,perim, start, end);
					if (user == null) {
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					} else {
						Message message = handler.obtainMessage(0, user);
						handler.sendMessage(message);
					}
				} catch (Exception e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}

			}
		}.start();
	}

	public void getListeUserToEnabledForClient(final Long clientId,
											   final DataLoadCallback dataLoadCallback)   {

		final Handler handler = new Handler() {

			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_USER_MANAGER_GET_TECH_ENABLED,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {

					A3techUserService service = new A3techUserService();
					List<Long> user = service.getListeUserToEnabledForClient(clientId);
					if (user == null) {
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					} else {
						Message message = handler.obtainMessage(0, user);
						handler.sendMessage(message);
					}
				} catch (Exception e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}

			}
		}.start();
	}


	public void isTechEnabledForClient(final Long clientId, final Long techId,
										   final DataLoadCallback dataLoadCallback)   {

		final Handler handler = new Handler() {

			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_USER_MANAGER_IS_TECH_ENABLED,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {

					A3techUserService service = new A3techUserService();
					Boolean isEnabled = service.isTechnicienEnabledForClient(clientId, techId);
					if (isEnabled == null) {
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					} else {
						Message message = handler.obtainMessage(0, isEnabled);
						handler.sendMessage(message);
					}
				} catch (Exception e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}

			}
		}.start();
	}

	public void fetchSoldeDisponible(final Long userID,
									   final DataLoadCallback dataLoadCallback)   {

		final Handler handler = new Handler() {

			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_USER_MANAGER_IS_TECH_ENABLED,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {

					A3techUserService service = new A3techUserService();
					Double solde = service.fetchSoldeDisponible(userID);
					if (solde == null) {
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					} else {
						Message message = handler.obtainMessage(0, solde);
						handler.sendMessage(message);
					}
				} catch (Exception e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}

			}
		}.start();
	}


	public void fetchTechnicien(final String keyword, final int start, final int end,
										  final DataLoadCallback dataLoadCallback)   {

		final Handler handler = new Handler() {

			// @Override
			public void handleMessage(Message message) {
				if (message.obj instanceof Integer) {
					dataLoadCallback.dataLoadingError((Integer) message.obj);
				} else {
					dataLoadCallback.dataLoaded(message.obj,
							KEY_USER_MANAGER_GET_PROFIL,0);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				try {

					A3techUserService service = new A3techUserService();
					List<A3techUser> user = service.fetchTechnicien(keyword, start, end);
					if (user == null) {
						Message message = handler.obtainMessage(0, 0);
						handler.sendMessage(message);
					} else {
						Message message = handler.obtainMessage(0, user);
						handler.sendMessage(message);
					}
				} catch (Exception e) {
					Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
					handler.sendMessage(message);
				}

			}
		}.start();
	}



    public void saveUserNewLocation(final String latitude, final String longitude,final String userMail,
                                          final DataLoadCallback dataLoadCallback)   {

        final Handler handler = new Handler() {

            // @Override
            public void handleMessage(Message message) {
                if (message.obj instanceof Integer) {
                    dataLoadCallback.dataLoadingError((Integer) message.obj);
                } else {
                    dataLoadCallback.dataLoaded(message.obj,
                            KEY_USER_MANAGER_GET_PROFIL,0);
                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                try {

                    A3techUserService service = new A3techUserService();
                    Boolean savedStat = service.saveUserNewLocation(latitude,longitude,userMail);
                    if (savedStat == null) {
                        Message message = handler.obtainMessage(0, 0);
                        handler.sendMessage(message);
                    } else {
                        Message message = handler.obtainMessage(0, savedStat);
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    Message message = handler.obtainMessage(0, UNKNOWN_ERROR);
                    handler.sendMessage(message);
                }

            }
        }.start();
    }
}
