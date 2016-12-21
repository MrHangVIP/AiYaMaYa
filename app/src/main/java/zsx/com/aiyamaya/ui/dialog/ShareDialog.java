/******************************************************************************
 * @project kanfangbao2
 * @brief
 * @author fmh19
 * @module com.yipai.realestate.ui.dialog
 * @date 2016/5/19
 * @version 0.1
 * @history v0.1, 2016/5/19, by fmh19
 * <p/>
 * Copyright (C) 2016 Yipai.
 ******************************************************************************/
package zsx.com.aiyamaya.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.item.ShareItem;
import zsx.com.aiyamaya.model.ShareModel;
import zsx.com.aiyamaya.ui.activity.BaseActivity;

/**
 * Created by fmh19 on 2016/5/19.
 *
 */
public class ShareDialog extends Dialog implements View.OnClickListener, ShareItem.OnShareClickListener {

	private String TAG = ShareDialog.class.getSimpleName();
	BaseActivity mActivity;
	ShareItem item1,item2,item3,item4,item5;
	Button cancelView;
	public ShareDialog(Activity activity) {
		super(activity, R.style.TimeDialog);
		this.mActivity = (BaseActivity)activity;
		View view = LayoutInflater.from(activity).inflate(R.layout.dialog_share, null);
		item1 = (ShareItem) view.findViewById(R.id.item_1);
		item2 = (ShareItem) view.findViewById(R.id.item_2);
		item3 = (ShareItem) view.findViewById(R.id.item_3);
		item4 = (ShareItem) view.findViewById(R.id.item_4);
		item5 = (ShareItem) view.findViewById(R.id.item_5);
		setItems();

		cancelView = (Button) view.findViewById(R.id.btn_cancel);
		cancelView.setOnClickListener(this);

		Window win = getWindow();
		win.getDecorView().setPadding(0, 0, 0, 0);
		WindowManager.LayoutParams lp = win.getAttributes();
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		win.setAttributes(lp);
		win.setGravity(Gravity.BOTTOM);
		win.setWindowAnimations(R.style.AnimButton);
		setContentView(view);

	}

	private void setItems() {
		item1.setType(ShareItem.JumpTO.WEIXIN);
		item2.setType(ShareItem.JumpTO.WE_FREND);
		item3.setType(ShareItem.JumpTO.QQ_ZONE);
		item4.setType(ShareItem.JumpTO.QQ);
		item5.setType(ShareItem.JumpTO.WEIBO);

		item1.setOnShareListener(this);
		item2.setOnShareListener(this);
		item3.setOnShareListener(this);
		item4.setOnShareListener(this);
		item5.setOnShareListener(this);
	}

	@Override
	public void onClick(View view) {
		ShareModel shareModel = new ShareModel();
		switch (view.getId()){
			case R.id.btn_cancel:
				dismiss();
				break;
		}
	}

	@Override
	public void onShareClick(View view, ShareItem.JumpTO type) {

		switch (view.getId()){
			case R.id.item_1:
				mActivity.toast("微信");
				break;
			case R.id.item_2:
				mActivity.toast("朋友圈");
				break;
			case R.id.item_3:
				mActivity.toast("空间");
				break;
			case R.id.item_4:
				mActivity.toast("QQ");
				break;
			case R.id.item_5:
				mActivity.toast("微博");
				break;

		}
		dismiss();
	}
}
