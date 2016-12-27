package zsx.com.aiyamaya;

import android.app.Application;

/**
 * Created by moram on 2016/12/23.
 */

public class BaseApplication extends Application{

    private static BaseApplication instance;

    public static BaseApplication getAPPInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
}
