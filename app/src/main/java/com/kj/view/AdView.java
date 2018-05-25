package com.kj.view;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.kj.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 广告轮播
 * 
 * @author guankui
 * 
 */
public class AdView extends RelativeLayout {

	public ViewPager mViewPager;
	private static final int MSG_CHANGE_PHOTO = 3000;
	/** 图片自动切换时间 */
	private static final int PHOTO_CHANGE_TIME = 3000;

	private LinearLayout mIndicator = null;
	private ImageView[] mIndicators;
	private FrameLayout[] mImageViews;
	public Handler mGgHandler = null;

	/**
	 * @param context
	 */
	public AdView(Context context) {
		super(context);
		initView(context);
	}

	public AdView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public AdView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	private void initView(Context context) {

		RelativeLayout layout = (RelativeLayout) LayoutInflater.from(context)
				.inflate(R.layout.omp_view, null);
		addView(layout);
		mViewPager = (ViewPager) layout.findViewById(R.id.vp_opm);
		mIndicator = (LinearLayout) layout.findViewById(R.id.ll_omp_point);
	}

	/**
	 * 获取网络数据
	 */
	public void setWebDate(Activity mActivity,ArrayList<ImageInfo> figure) {

		List<ImageInfo> mList = new ArrayList<ImageInfo>();
		for (int i = 0; i < figure.size(); i++) {
			ImageInfo bean = figure.get(i);
			mList.add(bean);
		}
		initViewPage(mActivity, mList);
	}

	public void initViewPage(Activity mActivity,
			final List<ImageInfo> mList) {
		initViews(mActivity, mList);

		setPointWithData(mActivity, mList);
		mViewPager.setCurrentItem(0);
		mViewPager.setAdapter(new ViewPagerAdapter());
		mViewPager.setOnPageChangeListener(new MyPageChangeListener());
		mViewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return false;
			}
		});
		mGgHandler.sendEmptyMessageDelayed(MSG_CHANGE_PHOTO, PHOTO_CHANGE_TIME);
	}

	/**
	 * 初始化view
	 */

	private void initViews(Activity mActivity, final List<ImageInfo> mList) {
		mGgHandler = null;
		mGgHandler = new Handler(mActivity.getMainLooper()) {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case MSG_CHANGE_PHOTO:
					int index = AdView.this.mViewPager.getCurrentItem();
					if (index == mList.size() - 1) {
						index = -1;
					}
					mViewPager.setCurrentItem(index + 1);
					mGgHandler.sendEmptyMessageDelayed(MSG_CHANGE_PHOTO,
							PHOTO_CHANGE_TIME);
					break;

				}
			}

		};
		// ======= 初始化ViewPager ========
		mIndicators = new ImageView[mList.size()];
		mIndicator.removeAllViews();
		mViewPager.removeAllViews();
		mImageViews = null;
	}

	public void colseHander() {
		Message msg = new Message();
		msg.what = 1;
		mGgHandler.dispatchMessage(msg);
	}

	public class ViewPagerAdapter extends PagerAdapter {

		public ViewPagerAdapter() {
		}

		@Override
		public int getCount() { // 获得size
			return mImageViews.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View view, int position, Object object) // �?毁Item
		{
			((ViewPager) view).removeView(mImageViews[position]);
		}

		@Override
		public Object instantiateItem(View view, int position) // 实例化Item
		{
			((ViewPager) view).addView(mImageViews[position], 0);

			return mImageViews[position];
		}

	}

	/**
	 * 根据获取的数据动态设置点图片
	 */

	private void setPointWithData(Activity mActivity,
			final List<ImageInfo> mList) {
		if (mList.size() <= 1) {
			mIndicator.setVisibility(View.GONE);
		}
		mImageViews = new FrameLayout[mList.size()];

		for (int i = 0; i < mIndicators.length; i++) {// 画点
			ImageView imageView = new ImageView(mActivity);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ViewAdapterUtil.getRunAreaBallXY(mActivity),
					ViewAdapterUtil.getRunAreaBallXY(mActivity));
			params.setMargins(2, 0, 2, 0);
			if (i != 0) {
				params.leftMargin = ViewAdapterUtil.getRunAreaBallXY(mActivity);
			}
			imageView.setLayoutParams(params);
			mIndicators[i] = imageView;
			if (i == 0) {
				mIndicators[i].setBackgroundResource(R.mipmap.n_02);
			} else {
				mIndicators[i].setBackgroundResource(R.mipmap.n_01);
			}

			mIndicator.addView(imageView);

			ActvityView mImageView = new ActvityView(mActivity);
			mImageViews[i] = mImageView;
			mImageView.showImage(mList.get(i), mActivity);

		}

	}

	/**
	 * 当ViewPager中页面的状�?�发生改�?
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		public void onPageSelected(int position) {
			setImageBackground(position);
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	private void setImageBackground(int selectItemsIndex) {
		for (int i = 0; i < mIndicators.length; i++) {
			if (i == selectItemsIndex) {
				mIndicators[i].setBackgroundResource(R.mipmap.n_02);
			} else {
				mIndicators[i].setBackgroundResource(R.mipmap.n_01);
			}
		}
	}

}
