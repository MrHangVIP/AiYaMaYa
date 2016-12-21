package zsx.com.aiyamaya.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by dawn on 2016/5/7.
 * note:
 * 首页fragment的适配器
 */
public class LoginAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragments;
	private List<String> titles;

	public LoginAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
		super(fm);
		this.fragments = fragments;
		this.titles = titles;
	}

	@Override
	public Fragment getItem(int position) {
		if (fragments != null && fragments.size() > position)
			return fragments.get(position);
		return null;
	}

	@Override
	public int getCount() {
		if (titles != null)
			return titles.size();
		return 0;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (titles != null && titles.size() > position)
			return titles.get(position);
		return super.getPageTitle(position);
	}
}
