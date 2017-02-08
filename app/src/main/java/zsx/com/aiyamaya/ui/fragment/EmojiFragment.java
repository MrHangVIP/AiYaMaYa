package zsx.com.aiyamaya.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import zsx.com.aiyamaya.BaseApplication;
import zsx.com.aiyamaya.R;
import zsx.com.aiyamaya.adapter.EmojiAdapter;
import zsx.com.aiyamaya.item.EmojiItem;
import zsx.com.aiyamaya.ui.activity.WritePostActivity;

/**
 * Created by moram on 2017/1/19.
 */

public class EmojiFragment extends BaseFragment{

    private static final String TAG = "EmojiFragment";
    private GridView emojiGV;

    @Override
    protected View getLayout(LayoutInflater inflater, ViewGroup container) {
        View view =inflater.inflate(R.layout.fragment_emoji,container,false);
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        emojiGV=(GridView)view.findViewById(R.id.fe_gv_emoji);
        emojiGV.setAdapter(new EmojiAdapter(getActivity(), BaseApplication.getEmojiItemList()));
    }

    @Override
    protected void initEvent() {
        emojiGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EmojiItem emojiItem=BaseApplication.getEmojiItemList().get(position);
                ((WritePostActivity)getActivity()).addEmoji(emojiItem.getValue(),emojiItem.getResId());
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
