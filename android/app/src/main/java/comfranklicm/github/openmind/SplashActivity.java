package comfranklicm.github.openmind;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import me.relex.photodraweeview.PhotoDraweeView;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/11
 */
public class SplashActivity extends Activity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_splash);
        //Toast.makeText(this,"引导页启动",Toast.LENGTH_LONG).show();
        Uri uri=Uri.parse("asset:///image/splash.jpg");
      final   PhotoDraweeView simpleDraweeView=(PhotoDraweeView)findViewById(R.id.simpleDraweeView);
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
        boolean mFirst = isFirstEnter(SplashActivity.this,SplashActivity.this.getClass().getName());
        if(mFirst)
            mHandler.sendEmptyMessageDelayed(SWITCH_GUIDACTIVITY,2000);
        else
            mHandler.sendEmptyMessageDelayed(SWITCH_MAINACTIVITY,2000);
        //startMainAvtivity();
    }
    private static final String SHAREDPREFERENCES_NAME = "my_pref";
    private static final String KEY_GUIDE_ACTIVITY = "guide_activity";
    private boolean isFirstEnter(Context context,String className){
        if(context==null || className==null||"".equalsIgnoreCase(className))return false;
        String mResultStr = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_WORLD_READABLE)
                .getString(KEY_GUIDE_ACTIVITY, "");//取得所有类名 如 com.my.MainActivity
        if(mResultStr.equalsIgnoreCase("false"))
            return false;
        else
            return true;
    }
    private void startMainAvtivity() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                intent = new Intent(SplashActivity.this, MyActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();//结束本Activity
            }
        }, 1500);//设置执行时间
    }
    private final static int SWITCH_MAINACTIVITY = 1000;
    private final static int SWITCH_GUIDACTIVITY = 1001;
    public Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SWITCH_MAINACTIVITY:
                    Intent mIntent = new Intent();
                    mIntent.setClass(SplashActivity.this, MyActivity.class);
                    SplashActivity.this.startActivity(mIntent);
                    SplashActivity.this.finish();
                    break;
                case SWITCH_GUIDACTIVITY:
                    mIntent = new Intent();
                    mIntent.setClass(SplashActivity.this, GuideActivity.class);
                    SplashActivity.this.startActivity(mIntent);
                    SplashActivity.this.finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
