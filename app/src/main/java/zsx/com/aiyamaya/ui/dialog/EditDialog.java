package zsx.com.aiyamaya.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.ui.activity.BaseActivity;
import zsx.com.aiyamaya.util.Constant;

/**
 * Created by moram on 2017/1/5.
 */

public abstract class EditDialog extends Dialog implements View.OnClickListener{

    private static final String TAG = "EditDialog";

    private BaseActivity mActivity;
    public String editContent;

    private  TextView confirmTV;
    private  EditText editET;
    private  TextView cancelTV;
    private  TextView titleTV;

    public EditDialog(Context context,String content) {
        super(context,R.style.progress_dialog);
        mActivity=(BaseActivity)context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_edit, null);
        titleTV= (TextView) view.findViewById(R.id.de_tv_title);
        cancelTV= (TextView) view.findViewById(R.id.de_tv_cancel);
        confirmTV= (TextView) view.findViewById(R.id.de_tv_confirm);
        editET=(EditText)view.findViewById(R.id.de_et_edit);
        editET.setHint(content);
        // dialog添加视图
        setContentView(view);// 设置布局

        WindowManager.LayoutParams windowParams = getWindow()
                .getAttributes();
        windowParams.width =  Constant.getScreenWidth(context); // 设置宽度
        windowParams.height =  Constant.getScreenHeight(context); // 设置高度
        getWindow().setAttributes(windowParams);
        setListener();
    }

    public void setListener(){
        cancelTV.setOnClickListener(this);
        confirmTV.setOnClickListener(this);
    }

    public void cancelAction(){
        dismiss();
        cancel();
    }

    public void confirmAction(){
        if("".equals(editET.getText().toString())){
            mActivity.toast("您还没有输入");
        }else{
            editContent=editET.getText().toString();
            confirmListener();
            cancelAction();
        }
    }

    protected abstract void confirmListener();

    public EditDialog setTitle(String title){
        titleTV.setText(title);
        return this;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.de_tv_cancel){
            cancelAction();
        }
        if(v.getId()==R.id.de_tv_confirm){
            confirmAction();
        }
    }
}
