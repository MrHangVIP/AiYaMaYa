package zsx.com.aiyamaya;

import android.app.Application;

import zsx.com.aiyamaya.item.UserItem;
import zsx.com.aiyamaya.util.SpfUtil;

/**
 * Created by moram on 2016/12/23.
 */

public class BaseApplication extends Application{

    private static BaseApplication instance;

    public static BaseApplication getAPPInstance(){
        return instance;
    }

    private UserItem mUser;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        //初始化缓存
        SpfUtil.init(this);
    }

    public UserItem getmUser() {
        return mUser;
    }

    public void setmUser(UserItem mUser) {
        this.mUser = mUser;
    }
}
