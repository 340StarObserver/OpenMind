package comfranklicm.github.openmind;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Fragment1 extends Fragment implements View.OnClickListener {
    View view;
    Fragment fg4;
    Fragment fg5;
    private ViewPager mPaper;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private TextView alltext;
    private TextView votetext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	    view = inflater.inflate(R.layout.fg1, container, false);
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
                resetcolor();
                switch (position)
                {
                    case 0:
                    {
                        alltext.setBackgroundColor(Color.parseColor("#444444"));
                        //votetext.setBackgroundColor(Color.parseColor("#000000"));
                        break;
                    }
                    case 1:
                    {
                        //alltext.setBackgroundColor(Color.parseColor("#000000"));
                        votetext.setBackgroundColor(Color.parseColor("#444444"));
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
    private void resetcolor(){
        alltext.setBackgroundColor(Color.parseColor("#000000"));
        votetext.setBackgroundColor(Color.parseColor("#000000"));
    }
    private void initlayout(){
        alltext=(TextView)view.findViewById(R.id.main_top_all);
        alltext.setBackgroundColor(Color.parseColor("#444444"));
        votetext=(TextView)view.findViewById(R.id.main_top_vote);
        mPaper=(ViewPager)view.findViewById(R.id.view_pager);
        alltext.setOnClickListener(this);
        votetext.setOnClickListener(this);

        fg4=new Fragment4();
        fg5=new Fragment5();

        mFragments.add(fg4);
        mFragments.add(fg5);
    }
    @Override
    public void onClick(View v) {
          switch (v.getId())
          {
              case R.id.main_top_all: {
                  resetcolor();
                  alltext.setBackgroundColor(Color.parseColor("#444444"));
                  votetext.setBackgroundColor(Color.parseColor("#000000"));
                  mPaper.setCurrentItem(0);
                  break;
              }
              case R.id.main_top_vote: {
                  votetext.setBackgroundColor(Color.parseColor("#444444"));
                  votetext.setBackgroundColor(Color.parseColor("#000000"));
                  mPaper.setCurrentItem(1);
                  break;
              }
          }
    }
}
