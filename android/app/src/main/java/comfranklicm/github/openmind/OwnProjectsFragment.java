package comfranklicm.github.openmind;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import comfranklicm.github.openmind.Httprequests.HttpPostRunnable;
import comfranklicm.github.openmind.JsonParsing.JsonParser;
import comfranklicm.github.openmind.utils.DataBaseUtil;
import comfranklicm.github.openmind.utils.NetUtil;
import comfranklicm.github.openmind.utils.User;

public class OwnProjectsFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProjectListRecyViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      final   View view = inflater.inflate(R.layout.fg6, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        final Context context=getContext();
        adapter=new ProjectListRecyViewAdapter(context,2);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.demo_swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        User.getInstance().owninfos.clear();
                        if (NetUtil.isNetworkConnectionActive(getActivity())) {
                            HttpPostRunnable runnable = new HttpPostRunnable();
                            runnable.setActionId(8);
                            Thread thread = new Thread(runnable);
                            thread.start();
                            try {
                                thread.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
//                            runnable.setStrResult(" [  \n" +
//                                    "\" +\n" +
//                                    "                                \"            {  \\n\" +\n" +
//                                    "                                \"                month  : 201608,\\n\" +\n" +
//                                    "                                \"\\n\" +\n" +
//                                    "                                \"                active :  \\n\" +\n" +
//                                    "                                \"                [  \\n\" +\n" +
//                                    "                                \"                    { day : 1,  degree : 10 },  \\n\" +\n" +
//                                    "                                \"                    { day : 29, degree : 6 }  \\n\" +\n" +
//                                    "                                \"                ]  \\n\" +\n" +
//                                    "                                \"               \\n\" +\n" +
//                                    "                                \"            },  \\n\" +\n" +
//                                    "                                \"            {  \\n\" +\n" +
//                                    "                                \"              month  : 201609,\\n\" +\n" +
//                                    "                                \"              active :  \\n\" +\n" +
//                                    "                                \"                [  \\n\" +\n" +
//                                    "                                \"                    { day : 1,  degree : 10 },  \\n\" +\n" +
//                                    "                                \"                    { day : 29, degree : 6 }  \\n\" +\n" +
//                                    "                                \"                ]  \\n\" +\n" +
//                                    "                                \"            }  \\n\" +\n" +
//                                    "                                \"        ]");
                            JsonParser.ParseJson(8, runnable.getStrResult());
                            try {
                                DataBaseUtil dataBaseUtil = DataBaseUtil.getInstance(getActivity());
                                SQLiteDatabase writedb = dataBaseUtil.getWritableDatabase();
                                for (int i = 0; i < User.getInstance().owninfos.size(); i++) {
                                    Object[] arrayOfObject = new Object[9];
                                    arrayOfObject[0] = User.getInstance().owninfos.get(i).getProjectId();
                                    arrayOfObject[1] = User.getInstance().owninfos.get(i).getProjectName();
                                    arrayOfObject[2] = User.getInstance().owninfos.get(i).getOwnUser();
                                    arrayOfObject[3] = User.getInstance().owninfos.get(i).getOwnName();
                                    arrayOfObject[4] = User.getInstance().owninfos.get(i).getOwn_head();
                                    arrayOfObject[5] = User.getInstance().owninfos.get(i).getPubTime();
                                    arrayOfObject[6] = User.getInstance().owninfos.get(i).getLabel1();
                                    arrayOfObject[7] = User.getInstance().owninfos.get(i).getLabel2();
                                    arrayOfObject[8] = User.getInstance().owninfos.get(i).getIntroduction();
                                    writedb.execSQL("insert into ProjectInfo(id,proj_name,own_usr,own_name,own_head,pub_time,label1,label2,introduction) values(?,?,?,?,?,?,?,?,?)", arrayOfObject);
                                }
                                writedb.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        /*for (int i = 0; i < 5; i++) {
                            ProjectInfo projectInfo = new ProjectInfo();
                            projectInfo.setProjectName("ownprojectname" + num);
                            projectInfo.setIntroduction("Adobe illustrator是一种应用于出版、多媒体和在线图像的工业标准矢量插画的软件，作为一款非常好的图片处理工具，Adobe Illustrator广泛应用于印刷出版、海报书籍排版、专业插..." + num);
                            projectInfo.setPubTime("2016-8-28");
                            projectInfo.setOwnUser("ownwriter" + num);
                            projectInfo.setLabel1("ownlabel" + num + 1);
                            projectInfo.setLabel2("ownlabel" + num + 2);
                            User.getInstance().owninfos.add(projectInfo);
                            num++;
                        }*/
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(getActivity(), "网络连接失败，请检查网络", Toast.LENGTH_LONG).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        return view;
    }
}
