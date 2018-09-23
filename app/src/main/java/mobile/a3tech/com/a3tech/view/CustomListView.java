package mobile.a3tech.com.a3tech.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import mobile.a3tech.com.a3tech.R;

/**
 * ListView下拉刷新和加载更多
 * 
 * @change JohnWatson 根�?�原作者修改�?��?修改�?修改的啦。�?是原声版本。
 * 正在修改，�?��?�会加上   刷新滑动时候的 音效
 */
public class CustomListView extends ListView implements OnScrollListener {

	private final static String TAG = CustomListView.class.getName();
	/** 日期模�?� */
	private final static String DATE_FORMAT_STR = "dd/MM/yyyy HH:mm";

	/** 手指在�?幕上滑动�?离与实际headview的便宜�?离比例 **/
	private final static int HEADMOVESIZE = 3;

	/**
	 * 四�?滑动状�?
	 */
	private final static int RELEASE_TO_REFRESH = 0; // 手指�?在�?幕上，进入�?�以刷新的状�?
	private final static int PULL_TO_REFRESH = 1; // 下拉中，准备刷新，但是还没有到�?�以刷新的状�?
	private final static int REFRESHING = 2; // 刷新中
	private final static int REFRESHDONE = 3; // 刷新完�?

	private final static int LOADING = 4; // 底部加载更多中

	/** 加载中 */
	private final static int ENDINT_LOADING = 1;
	/** 手动完�?刷新 */
	private final static int ENDINT_MANUAL_LOAD_DONE = 2;
	/** 自动完�?刷新 */
	private final static int ENDINT_AUTO_LOAD_DONE = 3;

	/** HeadView刷新的状�? **/
	private int headState;
	/** FootView刷新的状�? **/
	private int endState;

	/** 是�?��?�以加载更多 **/
	private boolean canLoadMore = false;
	/** 是�?��?�以下拉刷新 **/
	private boolean canRefresh = false;
	/** 是�?��?�以自动加载更多（�?先判断是�?�有加载更多，如果没有，这个flag也没有�?义） */
	private boolean isAutoLoadMore = true;
	/** 下拉刷新完�?�?�是�?�显示第一�?�Item */
	private boolean isMoveToFirstItemAfterRefresh = false;

	/** -------------------------------�?�丽的分割线----------------------------------- **/

	private LayoutInflater mInflater;

	private LinearLayout headView; // 顶部刷新view
	private TextView tipsTextView; // 刷新显示的�?置
	private TextView lastUpdatedTextView; // 最�?�刷新时间文字
	private ImageView arrowImageView; // 刷新的箭头
	private ProgressBar progressBar; // 刷新progressBar

	private View endRootView; // 底部加载View
	private ProgressBar endLoadProgressBar; // 底部加载中的progressBar
	private TextView endLoadTipsTextView; // 底部加载显示的文字

	/** headView动画 */
	private RotateAnimation arrowAnim;
	/** headView�??转动画 */
	private RotateAnimation arrowReverseAnim;

	/** 用于�?�?startY的值在一个完整的touch事件中�?�被记录一次 */
	private boolean isRecored;

	private int headViewWidth;
	private int headViewHeight;

	/** 起始Y�??标�?置 **/
	private int startY;
	/** 是�?�由RELEASE_To_REFRESH状�?转�?��?�的 **/
	private boolean isBack;

	private int firstItemIndex;
	private int lastItemIndex;
	private int count;
	private boolean enoughCount;// 是�?�足够数�?充满�?幕

	private OnRefreshListener refreshListener;
	private OnLoadMoreListener loadMoreListener;

	public CustomListView(Context pContext, AttributeSet pAttrs) {
		super(pContext, pAttrs);
		init(pContext);
	}

	public CustomListView(Context pContext) {
		super(pContext);
		init(pContext);
	}

	public CustomListView(Context pContext, AttributeSet pAttrs, int pDefStyle) {
		super(pContext, pAttrs, pDefStyle);
		init(pContext);
	}

	public boolean isCanLoadMore() {
		return canLoadMore;
	}

	public void setCanLoadMore(boolean pCanLoadMore) {
		canLoadMore = pCanLoadMore;
		if (canLoadMore && getFooterViewsCount() == 0) {
			addFooterView();
		}
	}

	public boolean isCanRefresh() {
		return canRefresh;
	}

	public void setCanRefresh(boolean pCanRefresh) {
		canRefresh = pCanRefresh;
	}

	public boolean isAutoLoadMore() {
		return isAutoLoadMore;
	}

	public void setAutoLoadMore(boolean pIsAutoLoadMore) {
		isAutoLoadMore = pIsAutoLoadMore;
	}

	public boolean isMoveToFirstItemAfterRefresh() {
		return isMoveToFirstItemAfterRefresh;
	}

	public void setMoveToFirstItemAfterRefresh(boolean pIsMoveToFirstItemAfterRefresh) {
		isMoveToFirstItemAfterRefresh = pIsMoveToFirstItemAfterRefresh;
	}

	/**
	 * �?始化�?作
	 * 
	 */
	private void init(Context pContext) {
		setCacheColorHint(pContext.getResources().getColor(R.color.transparent));
		mInflater = LayoutInflater.from(pContext);
		addHeadView();
		setOnScrollListener(this);
		initHeadArrowAnimation(0);
	}

	/**
	 * 添加下拉刷新的HeadView
	 * 
	 */
	private void addHeadView() {
		headView = (LinearLayout) mInflater.inflate(R.layout.head, null);
		arrowImageView = (ImageView) headView.findViewById(R.id.head_arrowImageView);
		arrowImageView.setMinimumWidth(70);
		arrowImageView.setMinimumHeight(50);
		progressBar = (ProgressBar) headView.findViewById(R.id.head_progressBar);
		tipsTextView = (TextView) headView.findViewById(R.id.head_tipsTextView);
		lastUpdatedTextView = (TextView) headView.findViewById(R.id.head_lastUpdatedTextView);
		measureView(headView);
		headViewHeight = headView.getMeasuredHeight();
		headViewWidth = headView.getMeasuredWidth();
		headView.setPadding(0, -1 * headViewHeight, 0, 0);
		headView.invalidate();
		addHeaderView(headView, null, false);
		headState = REFRESHDONE;
	}

	/**
	 * 添加加载更多FootView
	 * 
	 */
	
	public void charger(){
		if (canLoadMore) {
			if (canRefresh) {
				
				if (endState != ENDINT_LOADING && headState != REFRESHING) {
					endState = ENDINT_LOADING;
					onLoadMore();
				}
			} else if (endState != ENDINT_LOADING) {
				
				endState = ENDINT_LOADING;
				onLoadMore();
			}
		}
	}
	private void addFooterView() {
		endRootView = mInflater.inflate(R.layout.listfooter_more, null);
		endRootView.setVisibility(View.VISIBLE);
		endLoadProgressBar = (ProgressBar) endRootView.findViewById(R.id.pull_to_refresh_progress);
		endLoadTipsTextView = (TextView) endRootView.findViewById(R.id.load_more);
		endRootView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (canLoadMore) {
					if (canRefresh) {
						// 当�?�以下拉刷新时，如果FootView没有正在加载，并且HeadView没有正在刷新，�?�?�以点击加载更多。
						if (endState != ENDINT_LOADING && headState != REFRESHING) {
							endState = ENDINT_LOADING;
							onLoadMore();
						}
					} else if (endState != ENDINT_LOADING) {
						// 当�?能下拉刷新时，FootView�?正在加载时，�?�?�以点击加载更多。
						endState = ENDINT_LOADING;
						onLoadMore();
					}
				}
			}
		});
		addFooterView(endRootView);
		if (isAutoLoadMore) {
			endState = ENDINT_AUTO_LOAD_DONE;
		} else {
			endState = ENDINT_MANUAL_LOAD_DONE;
		}
	}

	/**
	 * �?始化下拉刷新的箭头的动画效果
	 * 
	 */
	private void initHeadArrowAnimation(final int pAnimDuration) {

		int _Duration;

		if (pAnimDuration > 0) {
			_Duration = pAnimDuration;
		} else {
			_Duration = 250;
		}
		Interpolator _Interpolator = new LinearInterpolator();
		arrowAnim = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		arrowAnim.setInterpolator(_Interpolator);
		arrowAnim.setDuration(_Duration);
		arrowAnim.setFillAfter(true);
		arrowReverseAnim = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		arrowReverseAnim.setInterpolator(_Interpolator);
		arrowReverseAnim.setDuration(_Duration);
		arrowReverseAnim.setFillAfter(true);
	}

	/**
	 * 测�?HeadView宽高
	 * 
	 */
	private void measureView(View pChild) {
		ViewGroup.LayoutParams p = pChild.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;

		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		pChild.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 下拉刷新监�?�接�?�
	 * 
	 */
	public interface OnRefreshListener {
		public void onRefresh();
	}

	/**
	 * 加载更多监�?�接�?�
	 * 
	 */
	public interface OnLoadMoreListener {
		public void onLoadMore();
	}

	public void setOnRefreshListener(OnRefreshListener pRefreshListener) {
		if (pRefreshListener != null) {
			refreshListener = pRefreshListener;
			canRefresh = true;
		}
	}

	public void setOnLoadListener(OnLoadMoreListener pLoadMoreListener) {
		if (pLoadMoreListener != null) {
			loadMoreListener = pLoadMoreListener;
			canLoadMore = true;
			if (canLoadMore && getFooterViewsCount() == 0) {
				addFooterView();
			}
		}
	}

	/**
	 * 正在下拉刷新
	 * 
	 */
	private void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	/**
	 * 下拉刷新完�?
	 * 
	 */
	public void onRefreshComplete() {
		
		if (isMoveToFirstItemAfterRefresh)
			setSelection(0);
		headState = REFRESHDONE;
		// 最近更新: Time
		lastUpdatedTextView.setText(getResources().getString(R.string.p2refresh_refresh_lasttime)
				+ new SimpleDateFormat(DATE_FORMAT_STR).format(new Date()));
		changeHeaderViewByState();
	}

	/**
	 * 正在加载更多，FootView显示 ： 加载中...
	 */
	private void onLoadMore() {
		if (loadMoreListener != null) {
			// 加载中...
			endLoadTipsTextView.setText(R.string.p2refresh_doing_end_refresh);
			endLoadTipsTextView.setVisibility(View.VISIBLE);
			endLoadProgressBar.setVisibility(View.VISIBLE);

			loadMoreListener.onLoadMore();
		}
	}

	/**
	 * 加载更多完�?
	 */
	public void onLoadMoreComplete() {
		if (isAutoLoadMore) {
			endState = ENDINT_AUTO_LOAD_DONE;
		} else {
			endState = ENDINT_MANUAL_LOAD_DONE;
		}
		changeEndViewByState();
	}

	/**
	 * 更新刷新时间
	 */
	public void setAdapter(BaseAdapter adapter) {
		// 最近更新: Time
		lastUpdatedTextView.setText(getResources().getString(R.string.p2refresh_refresh_lasttime)
				+ new SimpleDateFormat(DATE_FORMAT_STR, Locale.CHINA).format(new Date()));
		super.setAdapter(adapter);
	}

	/**
	 * 判断滑动到ListView底部没
	 */
	@Override
	public void onScroll(AbsListView pView, int pFirstVisibleItem, int pVisibleItemCount, int pTotalItemCount) {
		firstItemIndex = pFirstVisibleItem;
		lastItemIndex = pFirstVisibleItem + pVisibleItemCount - 2;
		count = pTotalItemCount - 2;
		if (pTotalItemCount > pVisibleItemCount) {
			enoughCount = true;
			// endingView.setVisibility(View.VISIBLE);
		} else {
			enoughCount = false;
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView pView, int pScrollState) {
		if (canLoadMore) {// 存在加载更多功能
			if (lastItemIndex == count && pScrollState == SCROLL_STATE_IDLE) {
				// SCROLL_STATE_IDLE=0，滑动�?�止
				if (endState != ENDINT_LOADING) {
					if (isAutoLoadMore) {// 自动加载更多，我们让FootView显示 “更 多�?
						if (canRefresh) {
							// 存在下拉刷新并且HeadView没有正在刷新时，FootView�?�以自动加载更多。
							if (headState != REFRESHING) {
								// FootView显示 : 更 多 ---> 加载中...
								endState = ENDINT_LOADING;
								onLoadMore();
								changeEndViewByState();
							}
						} else {// 没有下拉刷新，我们直接进行加载更多。
								// FootView显示 : 更 多 ---> 加载中...
							endState = ENDINT_LOADING;
							onLoadMore();
							changeEndViewByState();
						}
					} else {// �?是自动加载更多，我们让FootView显示 “点击加载�?
							// FootView显示 : 点击加载 ---> 加载中...
						endState = ENDINT_MANUAL_LOAD_DONE;
						changeEndViewByState();
					}
				}
			}
		} else if (endRootView != null && endRootView.getVisibility() == VISIBLE) {
			// �?然关闭加载更多功能之�?�，我们�?移除FootView。
			Log.w(TAG, "this.removeFooterView(endRootView);...");
			endRootView.setVisibility(View.GONE);
			this.removeFooterView(endRootView);
		}
	}

	/**
	 * 改�?�加载更多状�?
	 * 
	 */
	private void changeEndViewByState() {
		if (canLoadMore) {
			// �?许加载更多
			switch (endState) {
			case ENDINT_LOADING:// 刷新中

				// 加载中...
				if (endLoadTipsTextView.getText().equals(R.string.p2refresh_doing_end_refresh)) {
					break;
				}
				endLoadTipsTextView.setText(R.string.p2refresh_doing_end_refresh);
				endLoadTipsTextView.setVisibility(View.VISIBLE);
				endLoadProgressBar.setVisibility(View.VISIBLE);
				break;
			case ENDINT_MANUAL_LOAD_DONE:// 手动刷新完�?

				// 点击加载
				endLoadTipsTextView.setText(R.string.p2refresh_end_click_load_more);
				endLoadTipsTextView.setVisibility(View.VISIBLE);
				endLoadProgressBar.setVisibility(View.GONE);

				endRootView.setVisibility(View.VISIBLE);
				break;
			case ENDINT_AUTO_LOAD_DONE:// 自动刷新完�?

				// 更 多
				endLoadTipsTextView.setText(R.string.p2refresh_end_load_more);
				endLoadTipsTextView.setVisibility(View.VISIBLE);
				endLoadProgressBar.setVisibility(View.GONE);

				endRootView.setVisibility(View.VISIBLE);
				break;
			default:
				// 原�?�的代�?是为了： 当所有item的高度�?于ListView本身的高度时，
				// �?�?�?掉FootView，大家自己去原作者的代�?�?�考。

				// if (enoughCount) {
				// endRootView.setVisibility(View.VISIBLE);
				// } else {
				// endRootView.setVisibility(View.GONE);
				// }
				break;
			}
		}
	}

	public boolean onTouchEvent(MotionEvent event) {

		if (canRefresh) {
			if (canLoadMore && endState == ENDINT_LOADING) {
				// 如果存在加载更多功能，并且当�?正在加载更多，默认�?�?许下拉刷新，必须加载完毕�?��?能使用。
				return super.onTouchEvent(event);
			}

			switch (event.getAction()) {

			case MotionEvent.ACTION_DOWN:
				if (firstItemIndex == 0 && !isRecored) {
					isRecored = true;
					startY = (int) event.getY();
				}
				break;

			case MotionEvent.ACTION_UP:

				if (headState != REFRESHING && headState != LOADING) {
					if (headState == REFRESHDONE) {

					}
					if (headState == PULL_TO_REFRESH) {
						headState = REFRESHDONE;
						changeHeaderViewByState();
					}
					if (headState == RELEASE_TO_REFRESH) {
						headState = REFRESHING;
						changeHeaderViewByState();
						onRefresh();
					}
				}

				isRecored = false;
				isBack = false;

				break;

			case MotionEvent.ACTION_MOVE:
				int tempY = (int) event.getY();

				// Log.v("111", mHeadState+"");
				if (!isRecored && firstItemIndex == 0) {
					Log.v("111", "come in");
					isRecored = true;
					startY = tempY;
				}

				if (headState != REFRESHING && isRecored && headState != LOADING) {

					// �?�则如果当列表超出�?幕的�?，当在上推的时候，列表会�?�时进行滚动
					// �?�以�?�手去刷新了
					if (headState == RELEASE_TO_REFRESH) {
						setSelection(0);
						if (((tempY - startY) / HEADMOVESIZE < headViewHeight) && (tempY - startY) > 0) {
							Log.v("111", "RELEASE_TO_REFRESH�?�以刷新状�?");
							headState = PULL_TO_REFRESH;
							changeHeaderViewByState();
						} else if (tempY - startY <= 0) {
							Log.v("111", "RELEASE_TO_REFRESH->tempY - mStartY<= 0");
							headState = REFRESHDONE;
							changeHeaderViewByState();
						}
						// 往下拉了，或者还没有上推到�?幕顶部掩盖head的地步
					}
					if (headState == PULL_TO_REFRESH) {
						setSelection(0);
						// 下拉到�?�以进入RELEASE_TO_REFRESH的状�?
						if ((tempY - startY) / HEADMOVESIZE >= headViewHeight) {
							Log.v("111", "PULL_TO_REFRESH�?�以刷新状�?");
							headState = RELEASE_TO_REFRESH;
							isBack = true;
							changeHeaderViewByState();
						} else if (tempY - startY <= 0) {
							Log.v("111", "PULL_TO_REFRESH->tempY - mStartY <= 0");
							headState = REFRESHDONE;
							changeHeaderViewByState();
						}
					}

					if (headState == REFRESHDONE) {
						if (tempY - startY > 0) {
							headState = PULL_TO_REFRESH;
							changeHeaderViewByState();
						}
					}

					if (headState == PULL_TO_REFRESH) {
						headView.setPadding(0, -1 * headViewHeight + (tempY - startY) / HEADMOVESIZE, 0, 0);

					}

					if (headState == RELEASE_TO_REFRESH) {
						headView.setPadding(0, (tempY - startY) / HEADMOVESIZE - headViewHeight, 0, 0);
					}
				}
				break;
			}
		}

		return super.onTouchEvent(event);
	}

	/**
	 * HeadView状�?改�?�时，更新界�?�
	 * 
	 */
	private void changeHeaderViewByState() {
		switch (headState) {
		case RELEASE_TO_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextView.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(arrowAnim);
			// �?�开刷新
			tipsTextView.setText(R.string.p2refresh_release_refresh);

			break;
		case PULL_TO_REFRESH:
			progressBar.setVisibility(View.GONE);
			tipsTextView.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(arrowReverseAnim);
				// 下拉刷新
				tipsTextView.setText(R.string.p2refresh_pull_to_refresh);
			} else {
				// 下拉刷新
				tipsTextView.setText(R.string.p2refresh_pull_to_refresh);
			}
			break;

		case REFRESHING:
			headView.setPadding(0, 0, 0, 0);

			// �?�生的建议：
			// 实际上这个的setPadding�?�以用动画�?�代替。

			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			// 正在刷新...
			tipsTextView.setText(R.string.p2refresh_doing_head_refresh);
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			break;
		case REFRESHDONE:
			headView.setPadding(0, -1 * headViewHeight, 0, 0);
			// 此处�?�以改进，�?�上所述。
			progressBar.setVisibility(View.GONE);
			arrowImageView.clearAnimation();
			arrowImageView.setImageResource(R.drawable.arrow);
			// 下拉刷新
			tipsTextView.setText(R.string.p2refresh_pull_to_refresh);
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			break;
		}
	}

}
