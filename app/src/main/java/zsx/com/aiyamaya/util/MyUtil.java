
package zsx.com.aiyamaya.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;


import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import zsx.com.aiyamaya.listener.ErrorListener;
import zsx.com.aiyamaya.listener.ResponseListener;
import zsx.com.aiyamaya.listener.ServiceCommandError;

public final class MyUtil {
	
	static public String T = "AIYAMAYA";

	static private Handler handler;

	static private final int NUM_OF_THREADS = 20;

	static private Executor executor;

	static {
		createExecutor();
	}

	static void createExecutor() {
		MyUtil.executor = Executors.newFixedThreadPool(NUM_OF_THREADS, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread th = new Thread(r);
				th.setName("2nd Screen BG");
				return th;
			}
		});
	}

	public static void runOnUI(Runnable runnable) {
		if (handler == null) {
			handler = new Handler(Looper.getMainLooper());
		}
		handler.post(runnable);
	}

	public static void runInBackground(Runnable runnable, boolean forceNewThread) {
		if (forceNewThread || isMain()) {
			executor.execute(runnable);
		} else {
			runnable.run();
		}
	}

	public static void runInBackground(Runnable runnable) {
		runInBackground(runnable, false);
	}

	public static Executor getExecutor() {
		return executor;
	}

	public static boolean isMain() {
		return Looper.myLooper() == Looper.getMainLooper();
	}

	public static <T> void postSuccess(final ResponseListener<T> listener, final T object) {
		if (listener == null)
			return;
		MyUtil.runOnUI(new Runnable() {
			@Override
			public void run() {
//				listener.onSuccess(object);
			}
		});
	}

	public static void postError(final ErrorListener listener, final ServiceCommandError error) {
		if (listener == null)
			return;

		MyUtil.runOnUI(new Runnable() {

			@Override
			public void run() {
				listener.onError(error);
			}
		});
	}

	public static byte[] convertIpAddress(int ip) {
		return new byte[] { (byte) (ip & 0xFF), (byte) ((ip >> 8) & 0xFF), (byte) ((ip >> 16) & 0xFF),
				(byte) ((ip >> 24) & 0xFF) };
	}

	public static long getTime() {
		return TimeUnit.MILLISECONDS.toSeconds(new Date().getTime());
	}

	public static InetAddress getIpAddress(Context context) throws UnknownHostException {
		WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
		int ip = wifiInfo.getIpAddress();

		if (ip == 0) {
			return null;
		} else {
			byte[] ipAddress = convertIpAddress(ip);
			return InetAddress.getByAddress(ipAddress);
		}
	}
	
	public static void MyLogV(String msg){
		MyLogV(null,msg);
	}
	
	public static void MyLogV(String tag,String msg){
		if(IsDebug){
			if(tag==null){
				tag=T;
			}
			Log.v(tag, msg);
		}
	}
	public static void MyLogD(String msg){
		MyLogD(null,msg);
	}
	
	public static void MyLogD(String tag,String msg){
		if(IsDebug){
			if(tag==null){
				tag=T;
			}
			Log.d(null, msg);
		}
	}
	
	public static void MyLogI(String msg){
		MyLogI(null,msg);
	}
	
	public static void MyLogI(String tag,String msg){
		if(IsDebug){
			if(tag==null){
				tag=T;
			}
			Log.i(tag, msg);
		}
	}
	
	public static void MyLogW(String msg){
		MyLogW(null,msg);
	}
	
	public static void MyLogW(String tag,String msg){
		if(IsDebug){
			if(tag==null){
				tag=T;
			}
			Log.w(tag, msg);
		}
	}
	
	public static void MyLogE(String msg){
		MyLogE(null,msg);
	}
	
	public static void MyLogE(String tag,String msg){
		if(IsDebug){
			if(tag==null){
				tag=T;
			}
			Log.e(tag, msg);
		}
	}

	public static void setDebugLog(boolean isdebug) {
		IsDebug = isdebug;
	}

	private static boolean IsDebug=true;

	/**
	 * 日志等级
	 * 
	 * @author moram
	 *
	 */
	public enum LogLevel {
		VERBOUS, DEBUG, INFO, WARING, ERROR,
	}	
	
	public void cancle(){
	}
}