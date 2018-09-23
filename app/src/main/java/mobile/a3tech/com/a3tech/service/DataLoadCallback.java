package mobile.a3tech.com.a3tech.service;

public interface DataLoadCallback {
	public void dataLoaded(Object data, int method, int typeOperation);
	public void dataLoadingError(int errorCode);
}
