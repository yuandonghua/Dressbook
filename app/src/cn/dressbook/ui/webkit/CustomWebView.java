package cn.dressbook.ui.webkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;

/**
 * A convenient extension of WebView.
 */
public class CustomWebView extends WebView {

	private Context mContext;

	private int mProgress = 100;

	private boolean mIsLoading = false;

	private String mLoadedUrl;

	private static boolean mBoMethodsLoaded = false;

	private static Method mOnPauseMethod = null;
	private static Method mOnResumeMethod = null;
	private static Method mSetFindIsUp = null;
	private static Method mNotifyFindDialogDismissed = null;

	/**
	 * Constructor.
	 * 
	 * @param context
	 *            The current context.
	 */
	public CustomWebView(Context context) {
		super(context);

		mContext = context;
		initializeOptions();
		loadMethods();
	}

	/**
	 * Constructor.
	 * 
	 * @param context
	 *            The current context.
	 * @param attrs
	 *            The attribute set.
	 */
	public CustomWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		initializeOptions();
		loadMethods();
	}

	@Override
	public boolean canGoForward() {

		return super.canGoForward();

	}

	@Override
	public void addView(View child) {
		super.addView(child);

	}

	/**
	 * Initialize the WebView with the options set by the user through
	 * preferences.
	 */
	public void initializeOptions() {
		WebSettings settings = getSettings();

		// User settings
		// settings.setJavaScriptEnabled(Controller.getInstance().getPreferences().getBoolean(Constants.PREFERENCES_BROWSER_ENABLE_JAVASCRIPT,
		// true));
		// settings.setUseWideViewPort(Controller.getInstance().getPreferences().getBoolean(Constants.PREFERENCES_BROWSER_USE_WIDE_VIEWPORT,
		// true));
		// settings.setLoadWithOverviewMode(Controller.getInstance().getPreferences().getBoolean(Constants.PREFERENCES_BROWSER_LOAD_WITH_OVERVIEW,
		// false));
		settings.setSaveFormData(true);
		settings.setSavePassword(true);
//		settings.setUserAgentString();

		// settings.setRenderPriority(RenderPriority.HIGH);
		// settings.setBlockNetworkImage(true);
		requestFocus();

		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);

		// 阅读模式，但导致视频无法在网页播放
		// setLayerType(View.LAYER_TYPE_SOFTWARE, null);

		// mContext.getHolder().setType(SurfaceHolder.SURFACE_TYPE_HARDWARE);
		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);

		if (Build.VERSION.SDK_INT <= 7) {
			 settings.setPluginState(WebSettings.PluginState.OFF);
		} else {
			settings.setPluginState(WebSettings.PluginState.ON);
		}

		settings.setSupportZoom(true);


		// Technical settings
		settings.setSupportMultipleWindows(true);
		setLongClickable(true);
		setScrollbarFadingEnabled(true);
		setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		setDrawingCacheEnabled(true);

		settings.setAppCacheEnabled(true);
		settings.setDatabaseEnabled(true);
		settings.setDomStorageEnabled(true);
	}

	public void setLayerType(int intType) {
		// 阅读模式，但导致视频无法在网页播放
		if (intType == View.LAYER_TYPE_SOFTWARE) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		} else if (intType == View.LAYER_TYPE_HARDWARE) {
			setLayerType(View.LAYER_TYPE_HARDWARE, null);
		} else {
			setLayerType(View.LAYER_TYPE_NONE, null);
		}

	}

	@SuppressLint("NewApi")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		final int action = ev.getAction();

		// Enable / disable zoom support in case of multiple pointer, e.g.
		// enable zoom when we have two down pointers, disable with one pointer
		// or when pointer up.
		// We do this to prevent the display of zoom controls, which are not
		// useful and override over the right bubble.
		if ((action == MotionEvent.ACTION_DOWN)
				|| (action == MotionEvent.ACTION_POINTER_DOWN)
				|| (action == MotionEvent.ACTION_POINTER_1_DOWN)
				|| (action == MotionEvent.ACTION_POINTER_2_DOWN)
				|| (action == MotionEvent.ACTION_POINTER_3_DOWN)) {
			if (ev.getPointerCount() > 1) {
				this.getSettings().setBuiltInZoomControls(true);
				this.getSettings().setSupportZoom(true);
			} else {
				this.getSettings().setBuiltInZoomControls(false);
				this.getSettings().setSupportZoom(false);
			}
		} else if ((action == MotionEvent.ACTION_UP)
				|| (action == MotionEvent.ACTION_POINTER_UP)
				|| (action == MotionEvent.ACTION_POINTER_1_UP)
				|| (action == MotionEvent.ACTION_POINTER_2_UP)
				|| (action == MotionEvent.ACTION_POINTER_3_UP)) {
			this.getSettings().setBuiltInZoomControls(false);
			this.getSettings().setSupportZoom(false);
		}

		return super.onTouchEvent(ev);
	}

	@Override
	public void loadUrl(String url) {
		mLoadedUrl = url;
		super.loadUrl(url);
	}


	/**
	 * Set the current loading progress of this view.
	 * 
	 * @param progress
	 *            The current loading progress.
	 */
	public void setProgress(int progress) {
		mProgress = progress;
	}

	/**
	 * Get the current loading progress of the view.
	 * 
	 * @return The current loading progress of the view.
	 */
	public int getProgress() {
		return mProgress;
	}

	/**
	 * Triggered when a new page loading is requested.
	 */
	public void notifyPageStarted() {
		mIsLoading = true;
	}

	/**
	 * Triggered when the page has finished loading.
	 */
	public void notifyPageFinished() {
		mProgress = 100;
		mIsLoading = false;
	}

	/**
	 * Check if the view is currently loading.
	 * 
	 * @return True if the view is currently loading.
	 */
	public boolean isLoading() {
		return mIsLoading;
	}

	/**
	 * Get the loaded url, e.g. the one asked by the user, without redirections.
	 * 
	 * @return The loaded url.
	 */
	public String getLoadedUrl() {
		return mLoadedUrl;
	}

	/**
	 * Reset the loaded url.
	 */
	public void resetLoadedUrl() {
		mLoadedUrl = null;
	}

	public boolean isSameUrl(String url) {
		if (url != null) {
			return url.equalsIgnoreCase(this.getUrl());
		}

		return false;
	}

	/**
	 * Perform an 'onPause' on this WebView through reflexion.
	 */
	public void doOnPause() {
		if (mOnPauseMethod != null) {
			try {

				mOnPauseMethod.invoke(this);

			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
	}

	/**
	 * Perform an 'onResume' on this WebView through reflexion.
	 */
	public void doOnResume() {
		if (mOnResumeMethod != null) {
			try {

				mOnResumeMethod.invoke(this);

			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
	}

	public void doSetFindIsUp(boolean value) {
		if (mSetFindIsUp != null) {
			try {

				mSetFindIsUp.invoke(this, value);

			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
	}

	public void doNotifyFindDialogDismissed() {
		if (mNotifyFindDialogDismissed != null) {
			try {

				mNotifyFindDialogDismissed.invoke(this);

			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
	}

	/**
	 * Load static reflected methods.
	 */
	private void loadMethods() {

		if (!mBoMethodsLoaded) {

			try {

				mOnPauseMethod = WebView.class.getMethod("onPause");
				mOnResumeMethod = WebView.class.getMethod("onResume");

			} catch (SecurityException e) {
				mOnPauseMethod = null;
				mOnResumeMethod = null;
			} catch (NoSuchMethodException e) {
				mOnPauseMethod = null;
				mOnResumeMethod = null;
			}

			try {

				mSetFindIsUp = WebView.class.getMethod("setFindIsUp",
						Boolean.TYPE);
				mNotifyFindDialogDismissed = WebView.class
						.getMethod("notifyFindDialogDismissed");

			} catch (SecurityException e) {
				mSetFindIsUp = null;
				mNotifyFindDialogDismissed = null;
			} catch (NoSuchMethodException e) {
				mSetFindIsUp = null;
				mNotifyFindDialogDismissed = null;
			}

			mBoMethodsLoaded = true;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		invalidate();
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

}
