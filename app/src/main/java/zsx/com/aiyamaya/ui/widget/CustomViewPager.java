/******************************************************************************
 * @project kanfangbao2
 * @brief
 * @author fmh19
 * @module com.yipai.realestate.ui.widget
 * @date 2016/6/2
 * @version 0.1
 * @history v0.1, 2016/6/2, by fmh19
 * <p/>
 * Copyright (C) 2016 Yipai.
 ******************************************************************************/
package zsx.com.aiyamaya.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by fmh19 on 2016/6/2.
 * 可设置是否滚动
 */
public class CustomViewPager extends ViewPager {

	private boolean isPagingEnabled = true;

	public CustomViewPager(Context context) {
		super(context);
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.isPagingEnabled && super.onTouchEvent(event);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		return this.isPagingEnabled && super.onInterceptTouchEvent(event);
	}

	public void setPagingEnabled(boolean b) {
		this.isPagingEnabled = b;
	}


}
