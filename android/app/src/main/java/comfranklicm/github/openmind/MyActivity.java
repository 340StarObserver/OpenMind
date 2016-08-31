package comfranklicm.github.openmind;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import comfranklicm.github.openmind.utils.NetUtil;
import comfranklicm.github.openmind.utils.ProjectInfo;
import comfranklicm.github.openmind.utils.User;


public class MyActivity extends FragmentActivity implements OnClickListener{

	private Fragment1 fg1;
	private Fragment2 fg2;
	private Fragment3 fg3;
	private LoginFragment fg7;
    private RegisterFragment fg8;
    private SettingFragment fg9;
	private RelativeLayout course_layout;
	private RelativeLayout found_layout;
	private RelativeLayout settings_layout;
	private ImageView course_image;
	private ImageView found_image;
	private ImageView settings_image;
	private TextView course_text;
	private TextView settings_text;
	private TextView found_text;
	private int whirt = 0xFFFFFFFF;
	private int gray = 0xFF7597B3;
	private int blue =0xFF0AB2FB;
	FragmentManager fManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        NetUtil.getInstance().setIpAddress("192.168.252.6");
        NetUtil.getInstance().setPort("8081");
		fManager = getSupportFragmentManager();
        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.card_view), iconFont);
        initdatas();
		initViews();
        if (!NetUtil.isNetworkConnectionActive(this))
        {
            Toast.makeText(this,"请检查网络连接",Toast.LENGTH_LONG).show();
        }
	}

	public void initViews()
	{
		course_image = (ImageView) findViewById(R.id.course_image);
		found_image = (ImageView) findViewById(R.id.found_image);
		settings_image = (ImageView) findViewById(R.id.setting_image);
		course_text = (TextView) findViewById(R.id.course_text);
		found_text = (TextView) findViewById(R.id.found_text);
		settings_text = (TextView) findViewById(R.id.setting_text);
		course_layout = (RelativeLayout) findViewById(R.id.course_layout);
		found_layout = (RelativeLayout) findViewById(R.id.found_layout);
		settings_layout = (RelativeLayout) findViewById(R.id.setting_layout);
		course_layout.setOnClickListener(this);
		found_layout.setOnClickListener(this);
		settings_layout.setOnClickListener(this);
        setChioceItem(0);
    }
   public void initdatas()
   {
       for (int i=0;i<5;i++)
       {
           ProjectInfo projectInfo=new ProjectInfo();
           projectInfo.setProjectName("allprojectname"+i);
           projectInfo.setIntroduction("Adobe illustrator是一种应用于出版、多媒体和在线图像的工业标准矢量插画的软件，作为一款非常好的图片处理工具，Adobe Illustrator广泛应用于印刷出版、海报书籍排版、专业插..." + i);
           projectInfo.setPubTime("2016-8-28");
           projectInfo.setOwnUser("allwriter" + i);
           projectInfo.setLabel1("alllabel" + i + 1);
           projectInfo.setLabel2("alllabel" + i + 2);
           User.getInstance().allinfos.add(projectInfo);
       }
       for (int i=0;i<5;i++)
       {
           ProjectInfo projectInfo=new ProjectInfo();
           projectInfo.setProjectName("voteprojectname" + i);
           projectInfo.setIntroduction("Adobe illustrator是一种应用于出版、多媒体和在线图像的工业标准矢量插画的软件，作为一款非常好的图片处理工具，Adobe Illustrator广泛应用于印刷出版、海报书籍排版、专业插..." + i);
           projectInfo.setPubTime("2016-8-28");
           projectInfo.setOwnUser("votewriter" + i);
           projectInfo.setLabel1("votelabel" + i + 1);
           projectInfo.setLabel2("votelabel" + i + 2);
           User.getInstance().voteinfos.add(projectInfo);
       }
       for (int i=0;i<5;i++)
       {
           ProjectInfo projectInfo=new ProjectInfo();
           projectInfo.setProjectName("ownprojectname" + i);
           projectInfo.setIntroduction("Adobe illustrator是一种应用于出版、多媒体和在线图像的工业标准矢量插画的软件，作为一款非常好的图片处理工具，Adobe Illustrator广泛应用于印刷出版、海报书籍排版、专业插..." + i);
           projectInfo.setPubTime("2016-8-28");
           projectInfo.setOwnUser("ownwriter");
           projectInfo.setLabel1("ownlabel" + i + 1);
           projectInfo.setLabel2("ownlabel" + i + 2);
           User.getInstance().owninfos.add(projectInfo);
       }
   }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       if (keyCode == KeyEvent.KEYCODE_BACK ){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.course_layout:
			setChioceItem(0);
			break;
	    case R.id.found_layout:
	    	setChioceItem(1);
	    	break;
	    case R.id.setting_layout:
	    	setChioceItem(2);
	    	break;
	    default:
			break;
		}
	}

	public void setChioceItem(int index)
	{
		final FragmentTransaction transaction = fManager.beginTransaction();
		clearChioce();
		hideFragments(transaction);
		switch (index) {
		case 0:
			course_image.setImageResource(R.drawable.ic_tabbar_found_pressed);
			course_text.setTextColor(blue);
			course_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
            User.getInstance().setPageNumber(0);
            if (fg1 == null) {
                fg1 = new Fragment1();
                transaction.add(R.id.content, fg1);
            } else {
                transaction.show(fg1);  
            }
            transaction.commit();
            break;
		case 1:
			found_image.setImageResource(R.drawable.ic_tabbar_course_pressed);
			found_text.setTextColor(blue);
			found_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
            User.getInstance().setPageNumber(1);
            if (User.getInstance().isLogin()) {
                if (fg2 == null) {
                    fg2 = new Fragment2();
                    transaction.add(R.id.content, fg2);
                } else {
                    transaction.show(fg2);
                }
                transaction.commit();
            }else {
                transactiontoLogin();
            }
            break;      
		
		 case 2:
			settings_image.setImageResource(R.drawable.ic_tabbar_settings_pressed);  
			settings_text.setTextColor(blue);
			settings_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
             User.getInstance().setPageNumber(2);
             fg3 = new Fragment3();
             transaction.add(R.id.content, fg3);
             transaction.commit();
            break;                 
		}

	}

	private void hideFragments(FragmentTransaction transaction) {  
        if (fg1 != null) {  
            transaction.hide(fg1);  
        }  
        if (fg2 != null) {  
            transaction.hide(fg2);  
        }  
        if (fg3 != null) {  
            transaction.hide(fg3);  
        }
		if (fg7!=null){
			transaction.hide(fg7);
		}
        if (fg8!=null){
            transaction.hide(fg8);
        }
        if (fg9!=null){
            transaction.hide(fg9);
        }
    }

	public void clearChioce()
	{
		course_image.setImageResource(R.drawable.ic_tabbar_found_normal);
		course_layout.setBackgroundColor(whirt);
		course_text.setTextColor(gray);
		found_image.setImageResource(R.drawable.ic_tabbar_course_normal);
		found_layout.setBackgroundColor(whirt);
		found_text.setTextColor(gray);
		settings_image.setImageResource(R.drawable.ic_tabbar_settings_normal);
		settings_layout.setBackgroundColor(whirt);
		settings_text.setTextColor(gray);
	}
	public void transactiontoLogin()
	{
		final FragmentTransaction transaction = fManager.beginTransaction();
		hideFragments(transaction);
        fg7 = new LoginFragment();
        transaction.add(R.id.content, fg7);
        transaction.commit();
	}
    public void transactiontoRegister()
    {
        final FragmentTransaction transaction = fManager.beginTransaction();
        hideFragments(transaction);
        fg8 = new RegisterFragment();
        transaction.add(R.id.content, fg8);
        transaction.commit();
    }
    public void transactiontoSetting()//add by lyy 2016.8.31 切换到设置界面
    {
        final FragmentTransaction transaction = fManager.beginTransaction();
        hideFragments(transaction);
        fg9 = new SettingFragment();
        transaction.add(R.id.content, fg9);
        transaction.commit();
    }
}
