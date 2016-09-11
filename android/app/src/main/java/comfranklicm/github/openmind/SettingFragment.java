package comfranklicm.github.openmind;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import comfranklicm.github.openmind.Httprequests.HttpPostRunnable;
import comfranklicm.github.openmind.JsonParsing.JsonParser;
import comfranklicm.github.openmind.utils.DataBaseUtil;
import comfranklicm.github.openmind.utils.SynchronousData;
import comfranklicm.github.openmind.utils.User;
/**
 * Created and Modified by:LeiYuanYuan
 * Modified by:LiChangMao
 * Time:2016/8/31
 */
public class SettingFragment extends Fragment {
    View view;
    TextView fa_trash;
    TextView fa_refresh;
    TextView fa_moon_o;
    View dashed_line1;
    View dashed_line2;
    Button logoutButton;
    LinearLayout deleteall_btn;//清空本地数据的按钮
    LinearLayout synchron_btn;//数据同步按钮
    LinearLayout nightmode_btn;
    LinearLayout active;
    LinearLayout setting;
    LinearLayout aboutme;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.setting_layout, container,false);
        initlayout();
        return view;
    }
    private void initlayout() {
        synchron_btn=(LinearLayout)view.findViewById(R.id.linearLayout);
        deleteall_btn=(LinearLayout)view.findViewById(R.id.LinearLayout2);
        nightmode_btn=(LinearLayout)view.findViewById(R.id.LinearLayout3);
        nightmode_btn.setVisibility(View.GONE);
        dashed_line1=(View)view.findViewById(R.id.view);
        dashed_line2=(View)view.findViewById(R.id.view2);
        dashed_line1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        dashed_line2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        fa_trash=(TextView)view.findViewById(R.id.fa_trash_o);
        fa_trash.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_refresh=(TextView)view.findViewById(R.id.fa_refresh);
        fa_refresh.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_moon_o=(TextView)view.findViewById(R.id.fa_moon_o);
        fa_moon_o.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        logoutButton=(Button)view.findViewById(R.id.button);
        deleteall_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseUtil.getInstance(getActivity()).deleteDatabase(getActivity());
                Toast.makeText(getActivity(),"已清除本地数据",Toast.LENGTH_LONG).show();
            }
        });
        if(!User.getInstance().isLastLogin()&&!User.getInstance().isLogin()) {
            synchron_btn.setVisibility(View.GONE);
            dashed_line1.setVisibility(View.GONE);
            //dashed_line2.setVisibility(View.GONE);
            logoutButton.setVisibility(View.GONE);

        }else {
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpPostRunnable runnable=new HttpPostRunnable();
                    runnable.setActionId(4);
                    Thread thread=new Thread(runnable);
                    thread.start();
                    try{
                        thread.join();
                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    //runnable.setStrResult("{result:true}");
                    JsonParser.ParseJson(4, runnable.getStrResult());
                    if (User.getInstance().getLogoutResult().equals("true"))
                    {
                        User.getInstance().setIsLogin(false);
                        User.getInstance().setIsLastLogin(false);
                        DataBaseUtil.getInstance(getActivity()).deleteDatabase(getActivity());
                        User.getInstance().ownactives.clear();
                        User.getInstance().owninfos.clear();
                        User.getInstance().setPictureLink("");
                        MyActivity activity=(MyActivity)getActivity();
                        activity.setChioceItem(2);
                        Toast.makeText(getContext(),"登出成功",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getContext(),"登出失败",Toast.LENGTH_LONG).show();
                    }
                }
            });
            synchron_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SynchronousData.Sychronous();
                    //FragmentManager fm  = getActivity().getSupportFragmentManager();
                    if(User.getInstance().getSynchronousResult().equals("true")) {
                        try {
                            OwnProjectsFragment ownProjectsFragment = User.getInstance().getOwnProjectsFragment();
                            ownProjectsFragment.adapter.notifyDataSetChanged();

                            ActiveDegreeFragment activeDegreeFragment = User.getInstance().getActiveDegreeFragment();
                            activeDegreeFragment.adapter.notifyDataSetChanged();

                            DataBaseUtil dataBaseUtil=DataBaseUtil.getInstance(getActivity());
                            try {
                                SQLiteDatabase writedb = dataBaseUtil.getWritableDatabase();
                                writedb.execSQL("delete from ProjectInfo");
                                for (int i=0;i<User.getInstance().owninfos.size();i++) {
                                    Object[] arrayOfObject = new Object[9];
                                    arrayOfObject[0]=User.getInstance().owninfos.get(i).getProjectId();
                                    arrayOfObject[1]=User.getInstance().owninfos.get(i).getProjectName();
                                    arrayOfObject[2]=User.getInstance().owninfos.get(i).getOwnUser();
                                    arrayOfObject[3]=User.getInstance().owninfos.get(i).getOwnName();
                                    arrayOfObject[4]=User.getInstance().owninfos.get(i).getOwn_head();
                                    arrayOfObject[5]=User.getInstance().owninfos.get(i).getPubTime();
                                    arrayOfObject[6]=User.getInstance().owninfos.get(i).getLabel1();
                                    arrayOfObject[7]=User.getInstance().owninfos.get(i).getLabel2();
                                    arrayOfObject[8]=User.getInstance().owninfos.get(i).getIntroduction();
                                    writedb.execSQL("insert into ProjectInfo(id,proj_name,own_usr,own_name,own_head,pub_time,label1,label2,introduction) values(?,?,?,?,?,?,?,?,?)",arrayOfObject);
                                }
                                writedb.close();
                            }catch (SQLException e)
                            {
                                e.printStackTrace();
                            }
                            try {
                                SQLiteDatabase writedb = dataBaseUtil.getWritableDatabase();
                                writedb.execSQL("delete from ActiveInfo");
                                for (int i=0;i<User.getInstance().ownactives.size();i++)
                                {
                                    Object[] arrayOfObject = new Object[2];
                                    arrayOfObject[0]=User.getInstance().ownactives.get(i).getMonth();
                                    arrayOfObject[1]=User.getInstance().ownactives.get(i).getActive();
                                    writedb.execSQL("insert into ActiveInfo(month,active) values(?,?)",arrayOfObject);
                                }
                                writedb.close();
                            }catch (SQLException e)
                            {
                                e.printStackTrace();
                            }
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        Toast.makeText(getActivity(),"同步成功",Toast.LENGTH_LONG).show();
                    }else
                    {
                        Toast.makeText(getActivity(),"同步失败:"+User.getInstance().getSynchronousError(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
