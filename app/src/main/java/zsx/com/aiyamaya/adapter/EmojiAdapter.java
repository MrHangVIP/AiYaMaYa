package zsx.com.aiyamaya.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.item.EmojiItem;

/**
 * Created by moram on 2017/1/19.
 */

public class EmojiAdapter extends BaseAdapter {

    private static final String TAG = "EmojiAdapter";
    private final String packageName;
    private  Resources res;

    private List<EmojiItem> emojiList;

    private Context context;

    public EmojiAdapter(Context context,List<EmojiItem> emojiList){
        this.context=context;
        this.emojiList=emojiList;
        res = context.getResources();
        packageName = context.getPackageName();
    }

    @Override
    public int getCount() {
        return emojiList.size();
    }

    @Override
    public Object getItem(int position) {
        return emojiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView ==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_emoji,null);
            holder.emojiIV=(ImageView)convertView.findViewById(R.id.ie_iv_emoji);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        int  imageResId= res.getIdentifier(emojiList.get(position).getName(), "drawable", packageName);
        emojiList.get(position).setResId(imageResId);
        holder.emojiIV.setImageResource(imageResId);

        return convertView;
    }

     class ViewHolder{
         ImageView emojiIV;
    }
}
