package com.kj.view;


import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kj.R;


public class ActvityView extends FrameLayout {

	private ImageView iv;
	private Activity mContext;

	public ActvityView(Context context) {
		super(context, null);
		initView(context);
	}

	public ActvityView(Context context, AttributeSet paramAttributeSet) {
		super(context, paramAttributeSet);
		initView(context);

	}

	private void initView(Context context) {
		LayoutInflater.from(context).inflate(R.layout.runarea_activity, this,
				true);
		iv = (ImageView) findViewById(R.id.runarea_activty_bg);
	}

	/**
	 * @Description: 显示图片
	 * @throws
	 */

	public void showImage(ImageInfo mBean, Activity mContext) {
		this.mContext = mContext;
			ImageUtils.setWebImage(mContext, mBean.getImagepath(), iv,
					R.drawable.sy_banner);
	}

	/**
	 * @Description: 图片点击
	 * @param
	 * @return
	 * @throws
	 */
	private void ivClick(final ImageInfo mBean) {
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!StringUtils.isEmpty(mBean.getUrl())) {
//					Intent intent = new Intent(mContext, WebviewActivity.class);
//					intent.putExtra("title", mBean.title);
//					intent.putExtra("requestPayUrl", mBean.url);
//					mContext.startActivity(intent);
				}

			}
		});
	}

}
