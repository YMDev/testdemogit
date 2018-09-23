package mobile.a3tech.com.a3tech.activity;



import android.os.Bundle;
import android.view.Window;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerFragment;

import mobile.a3tech.com.a3tech.R;

public class YoutubeActivity extends YouTubeBaseActivity implements
		OnInitializedListener {

	public static final String API_KEY = "AIzaSyBg5Yi9U1n0cFtSbXBp6bbqyOlmW3lVVoc";
	private static final int RQS_ErrorDialog = 1;
	String VIDEO_ID = "";
	private MyPlaybackEventListener myPlaybackEventListener;
	private MyPlayerStateChangeListener myPlayerStateChangeListener;
	private YouTubePlayer youTubePlayer;
	private YouTubePlayerFragment youTubePlayerFragment;

	@Override
	public void onInitializationFailure(Provider provider,
			YouTubeInitializationResult result) {
		if (result.isUserRecoverableError()) {
			result.getErrorDialog(this, 1).show();
		}

	}

	private final class MyPlaybackEventListener implements
			YouTubePlayer.PlaybackEventListener {
		private MyPlaybackEventListener() {
		}

		public void onBuffering(boolean paramBoolean) {
		}

		public void onPaused() {
		}

		public void onPlaying() {
		}

		public void onSeekTo(int paramInt) {
		}

		public void onStopped() {
		}
	}

	private final class MyPlayerStateChangeListener implements
			YouTubePlayer.PlayerStateChangeListener {
		private MyPlayerStateChangeListener() {
		}

		public void onAdStarted() {
		}

		public void onError(YouTubePlayer.ErrorReason paramErrorReason) {
		}

		public void onLoaded(String paramString) {
		}

		public void onLoading() {
		}

		public void onVideoEnded() {
		}

		public void onVideoStarted() {
		}
	}

	@Override
	public void onInitializationSuccess(Provider provider,
			YouTubePlayer player, boolean flag) {
		this.youTubePlayer = player;
		this.youTubePlayer
				.setPlayerStateChangeListener(this.myPlayerStateChangeListener);
		this.youTubePlayer
				.setPlaybackEventListener(this.myPlaybackEventListener);
		if (!flag) {
			player.cueVideo(this.VIDEO_ID);
		}
		this.youTubePlayer.setFullscreen(true);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.youtube_activity);
		youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager()
				.findFragmentById(R.id.youtubeplayerfragment);
		youTubePlayerFragment.initialize(API_KEY, this);

		Bundle b = getIntent().getExtras();
		if (b != null) {
			VIDEO_ID = b.getString("vid");
		}
		myPlayerStateChangeListener = new MyPlayerStateChangeListener();
		myPlaybackEventListener = new MyPlaybackEventListener();
	}

}
