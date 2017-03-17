package zsx.com.aiyamaya.item;

import java.lang.reflect.Field;

import zsx.com.aiyamaya.R;

/**
 * Created by moram on 2017/1/19.
 */

public class EmojiItem {

    //[可爱]
    private String value;
    //emoji_1
    private String name;

    private int resId;

    public EmojiItem(String value,String name){
        this.value=value;
        this.name=name;
//        int id=getResouseIdByRef(name);
//        if(id !=0){
//            resId=id;
//        }
    }

    private int getResouseIdByRef(String name){
        try {
            Class cls=R.drawable.class;
            Field field = cls.getField(name);
            return field.getInt(name);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
