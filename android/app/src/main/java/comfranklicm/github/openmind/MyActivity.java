package comfranklicm.github.openmind;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

import comfranklicm.github.openmind.Httprequests.HttpPostRunnable;
import comfranklicm.github.openmind.JsonParsing.JsonParser;
import comfranklicm.github.openmind.JsonParsing.LoginJsonParser;
import comfranklicm.github.openmind.JsonParsing.ViewAllProjectsJsonParser;
import comfranklicm.github.openmind.JsonParsing.ViewVoteProjectsJsonParser;
import comfranklicm.github.openmind.utils.Active;
import comfranklicm.github.openmind.utils.DataBaseUtil;
import comfranklicm.github.openmind.utils.MD5;
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
    private ProjectDetailFragment fg10;
    private ChildCommentListFragment fg11;
    private FileViewFragment fg12;
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
        Fresco.initialize(this);
        User.getInstance().setMyActivity(this);
        //User.getInstance().setAllView(this.getCurrentFocus());
        User.getInstance().addAllJsonParse();
        NetUtil.getInstance().setIpAddress("139.196.15.168");
        //NetUtil.getInstance().setIpAddress("1.1.1.1");
        NetUtil.getInstance().setPort("80");
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
       DataBaseUtil dataBaseUtil=DataBaseUtil.getInstance(this);
       SQLiteDatabase database=dataBaseUtil.getWritableDatabase();
       Cursor localCursor = database.rawQuery("select username,password,realname,department,signuptime from User" +
               " where id=?", new String[]{"1"});
       if(localCursor.getCount()>0)
       {
           User.getInstance().setIsLastLogin(true);
           localCursor.moveToFirst();
           int userNameColumnIndex=localCursor.getColumnIndex("username");
           User.getInstance().setUserName(localCursor.getString(userNameColumnIndex));
           int passwordColumnIndex=localCursor.getColumnIndex("password");
           User.getInstance().setPassWord(localCursor.getString(passwordColumnIndex));
           int realNameColumnIndex=localCursor.getColumnIndex("realname");
           User.getInstance().setRealName(localCursor.getString(realNameColumnIndex));
           int departmentColumnIndex=localCursor.getColumnIndex("department");
           User.getInstance().setDepartment(localCursor.getString(departmentColumnIndex));
           int signuptimeColumnIndex=localCursor.getColumnIndex("signuptime");
           User.getInstance().setRegisterTime(localCursor.getString(signuptimeColumnIndex));
           HttpPostRunnable runnable=new HttpPostRunnable();

           if (NetUtil.isNetworkConnectionActive(this))
           {
           Toast.makeText(this,"请检查网络连接",Toast.LENGTH_LONG).show();
           runnable.setActionId(2);
           runnable.setUsername(User.getInstance().getUserName());
           runnable.setPassword(MD5.getMD5Str(User.getInstance().getPassWord()));
           Thread t=new Thread(runnable);
           t.start();
           try{
               t.join();
           }catch (InterruptedException e)
           {
               e.printStackTrace();
           }
           //runnable.setStrResult("{\"result\":\"true\",\"realname\":\"李昌懋\",\"department\":\"软件学院\",\"signup_time\":\"2016-08-13\",\"head\":\"img/img.jpg\",\"token\":\"233\"}");
           try {
               ((LoginJsonParser) User.getInstance().baseJsonParsers.get(1)).LoginJsonParsing(runnable.getStrResult());
           }catch (Exception e)
           {
               Toast.makeText(this,"连接服务器失败",Toast.LENGTH_LONG).show();
           }
               //JsonParser.ParseJson(2, runnable.getStrResult());
           }
           else {
               Toast.makeText(this,"请检查网络连接",Toast.LENGTH_LONG).show();
           }
       }
       localCursor.close();
       if (User.getInstance().isLastLogin())
       {
           Cursor cursor=database.rawQuery("select * from ProjectInfo where 1=?",new String[]{"1"});
           int i=0;
            while (cursor.moveToNext())
            {
                int idColumnIndex=cursor.getColumnIndex("id");
                int nameColumnIndex=cursor.getColumnIndex("proj_name");
                int ownUserColumnIndex=cursor.getColumnIndex("own_usr");
                int ownNameColumnIndex=cursor.getColumnIndex("own_name");
                int ownHeadColumnIndex=cursor.getColumnIndex("own_head");
                int pubTimeColumnIndex=cursor.getColumnIndex("pub_time");
                int label1ColumnIndex=cursor.getColumnIndex("label1");
                int label2ColumnIndex=cursor.getColumnIndex("label2");
                int introductionColumnIndex=cursor.getColumnIndex("introduction");
                User.getInstance().owninfos.get(i).setProjectId(cursor.getString(idColumnIndex));
                User.getInstance().owninfos.get(i).setProjectName(cursor.getString(nameColumnIndex));
                User.getInstance().owninfos.get(i).setOwnUser(cursor.getString(ownUserColumnIndex));
                User.getInstance().owninfos.get(i).setOwnName(cursor.getString(ownNameColumnIndex));
                User.getInstance().owninfos.get(i).setOwn_head(cursor.getString(ownHeadColumnIndex));
                User.getInstance().owninfos.get(i).setPubTime(cursor.getString(pubTimeColumnIndex));
                User.getInstance().owninfos.get(i).setLabel1(cursor.getString(label1ColumnIndex));
                User.getInstance().owninfos.get(i).setLabel2(cursor.getString(label2ColumnIndex));
                User.getInstance().owninfos.get(i).setIntroduction(cursor.getString(introductionColumnIndex));
            }
           cursor.close();

           Cursor cursor1=database.rawQuery("select * from ActiveInfo where 1=?", new String[]{"1"});
           int j=0;
           while (cursor1.moveToNext())
           {
               int monthColumnIndex=cursor1.getColumnIndex("month");
               int activeColumnIndex=cursor1.getColumnIndex("active");
               User.getInstance().ownactives.get(i).setMonth(cursor1.getString(monthColumnIndex));
               User.getInstance().ownactives.get(i).setActive(cursor1.getString(activeColumnIndex));
               try {
                   JSONArray jsonArray=new JSONArray("["+cursor1.getString(activeColumnIndex)+"]");
                   List<Active>activeList=new ArrayList<Active>();
                   for (int k=0;k<jsonArray.length();k++)
                   {
                       JSONObject jsonObject=(JSONObject)jsonArray.get(k);
                       Active active=new Active();
                       active.setDay(jsonObject.getString("day"));
                       active.setDegree(jsonObject.getString("degree"));
                       activeList.add(active);
                   }
                   User.getInstance().ownactives.get(j).setActiveList(activeList);
               } catch (JSONException e) {
                   e.printStackTrace();
               }
               j++;
           }
           cursor1.close();
       }
       database.close();
       if (NetUtil.isNetworkConnectionActive(this)) {
           HttpPostRunnable runnable = new HttpPostRunnable();
           runnable.setActionId(7);
           runnable.setPageSize("5");
           runnable.setTime_max("" + System.currentTimeMillis() / 1000L);
           Thread thread = new Thread(runnable);
           thread.start();
           try {
               thread.join();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
//             runnable.setStrResult("[\n" +
//                     "            {  \n" +
//                     "                _id          : fsdfsdfsdf,  \n" +
//                     "\n" +
//                     "                proj_name    : sdafsadfsd,  \n" +
//                     "\n" +
//                     "                own_usr      : sdfsadfasdf,  \n" +
//                     "\n" +
//                     "                own_name     : dsfafdsafsdf,  \n" +
//                     "\n" +
//                     "                own_head     : sadfsdafsdfsdf,                \n" +
//                     "\n" +
//                     "                pub_time     : 发布时间戳,  \n" +
//                     "\n" +
//                     "                labels       : [ 标签1, 标签2, ... ],  \n" +
//                     "\n" +
//                     "                introduction : 项目简介  \n" +
//                     "            },  \n" +
//                     "            {  \n" +
//                     "                 _id          : sdgfdagsd,  \n" +
//                     "\n" +
//                     "                proj_name    : esfadsf,  \n" +
//                     "\n" +
//                     "                own_usr      : 发起人用户名,  \n" +
//                     "\n" +
//                     "                own_name     : 发起人姓名,  \n" +
//                     "\n" +
//                     "                own_head     : 发起人的头像,  \n" +
//                     "\n" +
//                     "                pub_time     : 发布时间戳,  \n" +
//                     "\n" +
//                     "                labels       : [ 标签1, 标签2, ... ],  \n" +
//                     "\n" +
//                     "                introduction : 项目简介  \n" +
//                     "            },\n" +
//                     "           {\n" +
//                     "                _id          : safdsdaga,  \n" +
//                     "\n" +
//                     "                proj_name    : sdafsdaf,  \n" +
//                     "\n" +
//                     "                own_usr      : 发起人用户名,  \n" +
//                     "\n" +
//                     "                own_name     : 发起人姓名,  \n" +
//                     "\n" +
//                     "                own_head     : sdafsdafsdf,  \n" +
//                     "      \n" +
//                     "\n" +
//                     "                pub_time     : 发布时间戳,  \n" +
//                     "\n" +
//                     "                labels       : [ 标签1, 标签2, ... ],  \n" +
//                     "\n" +
//                     "                introduction : 项目简介  \n" +
//                     "            },\n" +
//                     "                {\n" +
//                     "                 _id          : sdafadfgsd,  \n" +
//                     "\n" +
//                     "                proj_name    : 项目名称,  \n" +
//                     "\n" +
//                     "                own_usr      : 发起人用户名,  \n" +
//                     "\n" +
//                     "                own_name     : 发起人姓名,  \n" +
//                     "\n" +
//                     "                own_head     : 发起人的头像,   \n" +
//                     "\n" +
//                     "                pub_time     : 发布时间戳,  \n" +
//                     "\n" +
//                     "                labels       : [ 标签1, 标签2, ... ],  \n" +
//                     "\n" +
//                     "                introduction : 项目简介  \n" +
//                     "             },\n" +
//                     "              {\n" +
//                     "                _id          : sdafhujkd,  \n" +
//                     "\n" +
//                     "                proj_name    : 项目名称,  \n" +
//                     "\n" +
//                     "                own_usr      : 发起人用户名,  \n" +
//                     "\n" +
//                     "                own_name     : 发起人姓名,  \n" +
//                     "\n" +
//                     "                own_head     : 发起人的头像,                  \n" +
//                     "\n" +
//                     "                pub_time     : 发布时间戳,  \n" +
//                     "\n" +
//                     "                labels       : [ 标签1, 标签2, ... ],  \n" +
//                     "\n" +
//                     "                introduction : 项目简介  \n" +
//                     "              }            \n" +
//                     "]  ");
           try {
               ((ViewAllProjectsJsonParser) User.getInstance().baseJsonParsers.get(6)).ViewAllProjectsJsonParsing(runnable.getStrResult());
           } catch (NullPointerException e) {
               e.printStackTrace();
           }
           HttpPostRunnable runnable1 = new HttpPostRunnable();
           runnable1.setActionId(14);
           Thread thread1 = new Thread(runnable1);
           thread1.start();
           try {
               thread1.join();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
//           String str="[  \n" +
//                   "            {  \n" +
//                   "                _id          : 项目id,  \n" +
//                   "\n" +
//                   "                proj_name    : 项目名称,  \n" +
//                   "\n" +
//                   "                own_usr      : 发起人的用户名,  \n" +
//                   "\n" +
//                   "                own_name     : 发起人的名字,  \n" +
//                   "\n" +
//                   "                own_head     : 发起人的头像,  \n" +
//                   "                # 是一个url链接，指向oss中的一张图  \n" +
//                   "\n" +
//                   "                pub_time     : 1445599887,  \n" +
//                   "\n" +
//                   "                labels       : [ 标签1, 标签2, ... ],  \n" +
//                   "\n" +
//                   "                links         : [{address:dfadf,description:fdadjflaj},{address:dfafdjlafij,description:fjdajdkfl}],\n" +
//                   "\n" +
//                   "                introduction : 项目简介,  \n" +
//                   "\n" +
//                   "                score        : 票数  \n" +
//                   "            },  \n" +
//                   "           {  \n" +
//                   "                _id          : 项目id,  \n" +
//                   "\n" +
//                   "                proj_name    : 项目名称,  \n" +
//                   "\n" +
//                   "                own_usr      : 发起人的用户名,  \n" +
//                   "\n" +
//                   "                own_name     : 发起人的名字,  \n" +
//                   "\n" +
//                   "                own_head     : 发起人的头像,  \n" +
//                   "                # 是一个url链接，指向oss中的一张图  \n" +
//                   "\n" +
//                   "                pub_time     : 1445599887,  \n" +
//                   "\n" +
//                   "                labels       : [ 标签1, 标签2, ... ],  \n" +
//                   "\n" +
//                   "                links         : [{address:dfadf,description:fdadjflaj},{address:dfafdjlafij,description:fjdajdkfl}],  \n" +
//                   "\n" +
//                   "                introduction : 项目简介,  \n" +
//                   "\n" +
//                   "                score        : 票数  \n" +
//                   "            }\n" +
//                   "]  ";
           try {
               ((ViewVoteProjectsJsonParser) User.getInstance().baseJsonParsers.get(13)).ViewVoteProjectsJsonParsing(runnable1.getStrResult());
           } catch (NullPointerException e) {
               e.printStackTrace();
           }
       }else {
           Toast.makeText(this,"网络连接失败，请检查网络",Toast.LENGTH_LONG).show();
       }
     /*  for (int i=0;i<5;i++)
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
       }*/
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
            if (User.getInstance().isLastLogin()||User.getInstance().isLogin()) {
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
        if (fg10!=null){
            transaction.hide(fg10);
        }
        if (fg11!=null){
            transaction.hide(fg11);
        }
        if(fg12!=null){
            transaction.hide(fg12);
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
        User.getInstance().currentChildComments.clear();
        User.getInstance().currentParentComments.clear();
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
    public void transactiontoProjectDetail()//add by lyy 2016.8.31 切换到项目详情界面
    {
        final FragmentTransaction transaction = fManager.beginTransaction();
        hideFragments(transaction);
        fg10 = new ProjectDetailFragment();
        transaction.add(R.id.content, fg10);
        transaction.commit();
    }
    public void transactiontoChildComment()
    {
        final FragmentTransaction transaction = fManager.beginTransaction();
        hideFragments(transaction);
        fg11 = new ChildCommentListFragment();
        transaction.add(R.id.content, fg11);
        transaction.commit();
    }
    public void transactiontoFileView()
    {
        final FragmentTransaction transaction = fManager.beginTransaction();
        hideFragments(transaction);
        fg12 =new FileViewFragment();
        transaction.add(R.id.content,fg12);
        transaction.commit();
    }
}
