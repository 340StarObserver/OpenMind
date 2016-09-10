package comfranklicm.github.openmind;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import comfranklicm.github.openmind.utils.User;

public class Fragment2 extends Fragment {
	View view;
	OwnProjectsFragment fg6;
	private ViewPager mPaper;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fg2, container,false);
		initlayout();
		mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {

			@Override
			public int getCount() {
				return mFragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return mFragments.get(arg0);
			}
		};
		mPaper.setAdapter(mAdapter);
		mPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			private int currentIndex;
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				switch (position)
				{
					case 0:
					{
						break;
					}
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		return view;
	}
	private void initlayout(){
		mPaper=(ViewPager)view.findViewById(R.id.view_pager);
		fg6=new OwnProjectsFragment();
		User.getInstance().setOwnProjectsFragment(fg6);
		mFragments.add(fg6);
	}
}
