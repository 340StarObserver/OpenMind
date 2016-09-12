package comfranklicm.github.openmind;

import android.content.Context;
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
import comfranklicm.github.openmind.JsonParsing.ViewVoteProjectsJsonParser;
import comfranklicm.github.openmind.utils.NetUtil;
import comfranklicm.github.openmind.utils.User;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/7
 */
public class VoteProjectsFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProjectListRecyViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      final   View view = inflater.inflate(R.layout.fg5, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        final Context context=getContext();
        adapter=new ProjectListRecyViewAdapter(context,1);
        User.getInstance().projectListRecyViewAdapter = adapter;
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        User.getInstance().projectListRecyViewAdapter = adapter;
        User.getInstance().votenumbers.clear();
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
            // JsonParser.ParseJson(14, runnable.getStrResult());
            try {
                ((ViewVoteProjectsJsonParser) User.getInstance().baseJsonParsers.get(13)).ViewVoteProjectsJsonParsing(runnable.getStrResult());
            }catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(getActivity(),"连接服务器失败",Toast.LENGTH_LONG).show();
            }
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
        } else {
            Toast.makeText(getActivity(), "网络连接失败，请检查网络", Toast.LENGTH_LONG).show();
        }
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.demo_swiperefreshlayout);
        User.getInstance().projectListRecyViewAdapter = adapter;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        User.getInstance().votenumbers.clear();
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
                            // JsonParser.ParseJson(14, runnable.getStrResult());
                            ((ViewVoteProjectsJsonParser) User.getInstance().baseJsonParsers.get(13)).ViewVoteProjectsJsonParsing(runnable.getStrResult());

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
                            User.getInstance().projectListRecyViewAdapter = adapter;
                        } else {
                            Toast.makeText(getActivity(), "网络连接失败，请检查网络", Toast.LENGTH_LONG).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
                User.getInstance().projectListRecyViewAdapter = adapter;
            }
        });
        return view;
    }

    public ProjectListRecyViewAdapter getAdapter() {
        return adapter;
    }
}
