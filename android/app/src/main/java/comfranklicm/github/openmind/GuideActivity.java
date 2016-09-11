package comfranklicm.github.openmind;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import java.util.ArrayList;

import me.relex.photodraweeview.PhotoDraweeView;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/11
 */
public class GuideActivity extends Activity {
    private ViewPager viewPager;

    /**装分页显示的view的数组*/
    private ArrayList<View> pageViews;
    private ImageView imageView;

    /**将小圆点的图片用数组表示*/
    private ImageView[] imageViews;

    //包裹滑动图片的LinearLayout
    private ViewGroup viewPics;

    //包裹小圆点的LinearLayout
    private ViewGroup viewPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_guide);
        //Toast.makeText(this, "引导页2启动", Toast.LENGTH_LONG).show();
        //将要分页显示的View装入数组中
        LayoutInflater inflater = getLayoutInflater();
        pageViews = new ArrayList<View>();
        pageViews.add(inflater.inflate(R.layout.viewpager_page1, null));
        pageViews.add(inflater.inflate(R.layout.viewpager_page2, null));
        pageViews.add(inflater.inflate(R.layout.viewpager_page3, null));
        //创建imageviews数组，大小是要显示的图片的数量
        imageViews = new ImageView[pageViews.size()];
        //从指定的XML文件加载视图
        viewPics = (ViewGroup) inflater.inflate(R.layout.activity_guide, null);

        //实例化小圆点的linearLayout和viewpager
        viewPoints = (ViewGroup) viewPics.findViewById(R.id.viewGroup);
        viewPager = (ViewPager) viewPics.findViewById(R.id.guidePages);
        //添加小圆点的图片
        for(int i=0;i<pageViews.size();i++){
            imageView = new ImageView(GuideActivity.this);
            //设置小圆点imageview的参数
            imageView.setLayoutParams(new ViewGroup.LayoutParams(20,20));//创建一个宽高均为20 的布局
            imageView.setPadding(20, 0, 20, 0);
            //将小圆点layout添加到数组中
            imageViews[i] = imageView;

            //默认选中的是第一张图片，此时第一个小圆点是选中状态，其他不是
            if(i==0){
                imageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);
            }else{
                imageViews[i].setBackgroundResource(R.drawable.page_indicator);
            }

            //将imageviews添加到小圆点视图组
            viewPoints.addView(imageViews[i]);
        }
        //显示滑动图片的视图
        setContentView(viewPics);

        viewPager.setAdapter(new GuidePageAdapter());
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
    }
    private static final String SHAREDPREFERENCES_NAME = "my_pref";
    private static final String KEY_GUIDE_ACTIVITY = "guide_activity";
    private void setGuided(){
        SharedPreferences settings = getSharedPreferences(SHAREDPREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_GUIDE_ACTIVITY, "false");
        editor.commit();
    }
    class GuidePageAdapter extends PagerAdapter {

        //销毁position位置的界面
        @Override
        public void destroyItem(View v, int position, Object arg2) {
            // TODO Auto-generated method stub
            ((ViewPager)v).removeView(pageViews.get(position));

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        //获取当前窗体界面数
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return pageViews.size();
        }

        //初始化position位置的界面
        @Override
        public Object instantiateItem(View v, int position) {
            // TODO Auto-generated method stub
            ((ViewPager) v).addView(pageViews.get(position));
            if(position==0)
            {
                Uri uri=Uri.parse("asset:///image/splash1.jpg");
                final PhotoDraweeView simpleDraweeView=(PhotoDraweeView)v.findViewById(R.id.imageview1);
                PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
                controller.setUri(uri);
                controller.setOldController(simpleDraweeView.getController());
                controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (imageInfo == null || simpleDraweeView == null) {
                            return;
                        }
                        simpleDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                    }
                });
                controller.setAutoPlayAnimations(true);
                simpleDraweeView.setController(controller.build());
            }else if(position==1)
            {
                Uri uri=Uri.parse("asset:///image/splash2.jpg");
                final PhotoDraweeView simpleDraweeView=(PhotoDraweeView)v.findViewById(R.id.imageview2);
                PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
                controller.setUri(uri);
                controller.setOldController(simpleDraweeView.getController());
                controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (imageInfo == null || simpleDraweeView == null) {
                            return;
                        }
                        simpleDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                    }
                });
                controller.setAutoPlayAnimations(true);
                simpleDraweeView.setController(controller.build());
            }else if(position==2)
            {
                Uri uri=Uri.parse("asset:///image/splash3.jpg");
                final PhotoDraweeView simpleDraweeView=(PhotoDraweeView)v.findViewById(R.id.imageview3);
                PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
                controller.setUri(uri);
                controller.setOldController(simpleDraweeView.getController());
                controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (imageInfo == null || simpleDraweeView == null) {
                            return;
                        }
                        simpleDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                    }
                });
                controller.setAutoPlayAnimations(true);
                simpleDraweeView.setController(controller.build());
                TextView textView=(TextView)v.findViewById(R.id.jingruapp);
                textView.setClickable(true);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //设置已经引导
                        setGuided();

                        //跳转
                        Intent mIntent = new Intent();
                        mIntent.setClass(GuideActivity.this, MyActivity.class);
                        GuideActivity.this.startActivity(mIntent);
                        GuideActivity.this.finish();
                    }
                });
            }
            // 测试页卡1内的按钮事件
            if (position == 2) {
                //Button btn = (Button) v.findViewById(R.id.btn_close_guide);
                //btn.setOnClickListener(Button_OnClickListener);
            }

            return pageViews.get(position);
        }

        // 判断是否由对象生成界面
        @Override
        public boolean isViewFromObject(View v, Object arg1) {
            // TODO Auto-generated method stub
            return v == arg1;
        }



        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return super.getItemPosition(object);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }
    }


    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            for(int i=0;i<imageViews.length;i++){
                imageViews[position].setBackgroundResource(R.drawable.page_indicator_focused);
                //不是当前选中的page，其小圆点设置为未选中的状态
                if(position !=i){
                    imageViews[i].setBackgroundResource(R.drawable.page_indicator);
                }
            }

        }
    }
}
