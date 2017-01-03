package zsx.com.aiyamaya.item;

import java.io.Serializable;

/**
 * Created by moram on 2016/12/30.
 */

public class ResultItem implements Serializable{

    private String result;

    private String data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
