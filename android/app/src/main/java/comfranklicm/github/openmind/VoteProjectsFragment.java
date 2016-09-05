package comfranklicm.github.openmind;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
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
import comfranklicm.github.openmind.utils.NetUtil;
import comfranklicm.github.openmind.utils.ProjectInfo;
import comfranklicm.github.openmind.utils.User;

public class VoteProjectsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProjectListRecyViewAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      final   View view = inflater.inflate(R.layout.fg5, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        final Context context=getContext();
        adapter=new ProjectListRecyViewAdapter(context,1);
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
                        User.getInstance().voteinfos.clear();
                        if (NetUtil.isNetworkConnectionActive(getActivity())) {
                            HttpPostRunnable runnable = new HttpPostRunnable();
                            runnable.setActionId(14);
                            Thread thread = new Thread(runnable);
                            thread.start();
                            try {
                                thread.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //runnable.setStrResult(
//                            String str="[  \n" +
//                                    "            {  \n" +
//                                    "                _id          : 项目id,  \n" +
//                                    "\n" +
//                                    "                proj_name    : 项目名称,  \n" +
//                                    "\n" +
//                                    "                own_usr      : 发起人的用户名,  \n" +
//                                    "\n" +
//                                    "                own_name     : 发起人的名字,  \n" +
//                                    "\n" +
//                                    "                own_head     : 发起人的头像,  \n" +
//                                    "                # 是一个url链接，指向oss中的一张图  \n" +
//                                    "\n" +
//                                    "                pub_time     : 1445599887,  \n" +
//                                    "\n" +
//                                    "                labels       : [ 标签1, 标签2, ... ],  \n" +
//                                    "\n" +
//                                    "                links         : [{address:dfadf,description:fdadjflaj},{address:dfafdjlafij,description:fjdajdkfl}],\n" +
//                                    "\n" +
//                                    "                introduction : 项目简介,  \n" +
//                                    "\n" +
//                                    "                score        : 票数  \n" +
//                                    "            },  \n" +
//                                    "           {  \n" +
//                                    "                _id          : 项目id,  \n" +
//                                    "\n" +
//                                    "                proj_name    : 项目名称,  \n" +
//                                    "\n" +
//                                    "                own_usr      : 发起人的用户名,  \n" +
//                                    "\n" +
//                                    "                own_name     : 发起人的名字,  \n" +
//                                    "\n" +
//                                    "                own_head     : 发起人的头像,  \n" +
//                                    "                # 是一个url链接，指向oss中的一张图  \n" +
//                                    "\n" +
//                                    "                pub_time     : 1445599887,  \n" +
//                                    "\n" +
//                                    "                labels       : [ 标签1, 标签2, ... ],  \n" +
//                                    "\n" +
//                                    "                links         : [{address:dfadf,description:fdadjflaj},{address:dfafdjlafij,description:fjdajdkfl}],  \n" +
//                                    "\n" +
//                                    "                introduction : 项目简介,  \n" +
//                                    "\n" +
//                                    "                score        : 票数  \n" +
//                                    "            }\n" +
//                                    "]  ";
                            JsonParser.ParseJson(14, runnable.getStrResult());
                      /*  for (int i = 0; i < 5; i++) {
                            ProjectInfo projectInfo = new ProjectInfo();
                            projectInfo.setProjectName("voteprojectname" + num);
                            projectInfo.setIntroduction("Adobe illustrator是一种应用于出版、多媒体和在线图像的工业标准矢量插画的软件，作为一款非常好的图片处理工具，Adobe Illustrator广泛应用于印刷出版、海报书籍排版、专业插..." + num);
                            projectInfo.setPubTime("2016-8-28");
                            projectInfo.setOwnUser("votewriter" + num);
                            projectInfo.setLabel1("votelabel" + num + 1);
                            projectInfo.setLabel2("votelabel" + num + 2);
                            User.getInstance().voteinfos.add(projectInfo);
                            num++;
                        }*/
                            adapter.notifyDataSetChanged();
                        }else
                        {
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
