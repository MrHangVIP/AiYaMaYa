package zsx.com.aiyamaya.item;

import org.json.JSONObject;

/**
 * Created by moram on 2017/1/11.
 */

public class TouTiaoItem {

    private String reason;

    private JSONObject result;

    private int error_code;

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
