package zsx.com.aiyamaya.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dawn on 2016/5/21.
 * 分享的实体类
 */
public class ShareModel implements Serializable {
    private String shareTitle;//分享标题
    private String shareDescription;//分享的内容描述
    private String shareContent;//分享的文本
    private String sharePath;//分享的地址
    private Bitmap shareBitmap;//分享的图片
    private String shareClickPath;//点击跳转的地址
    private String sharePath2;//分享的地址备用
    private ArrayList<String> shareImgs;//分享的图片的集合

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareDescription() {
        return shareDescription;
    }

    public void setShareDescription(String shareDescription) {
        this.shareDescription = shareDescription;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getSharePath() {
        return sharePath;
    }

    public void setSharePath(String sharePath) {
        this.sharePath = sharePath;
    }

    public Bitmap getShareBitmap() {
        return shareBitmap;
    }

    public void setShareBitmap(Bitmap shareBitmap) {
        this.shareBitmap = shareBitmap;
    }

    public String getShareClickPath() {
        return shareClickPath;
    }

    public void setShareClickPath(String shareClickPath) {
        this.shareClickPath = shareClickPath;
    }

    public String getSharePath2() {
        return sharePath2;
    }

    public void setSharePath2(String sharePath2) {
        this.sharePath2 = sharePath2;
    }

    public ArrayList<String> getShareImgs() {
        return shareImgs;
    }

    public void setShareImgs(ArrayList<String> shareImgs) {
        this.shareImgs = shareImgs;
    }
}
