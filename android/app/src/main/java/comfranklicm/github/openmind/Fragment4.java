package comfranklicm.github.openmind;

import android.content.Context;
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

public class Fragment4 extends Fragment {
    private RecyclerView recyclerView;
    private ProjectListRecyViewAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    private int lastVisibleItem;
    private static int num=User.getInstance().allinfos.size();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fg4, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        final Context context=getContext();
        adapter=new ProjectListRecyViewAdapter(context,0);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (adapter.getItemCount()!=0&&newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()&&User.getInstance().getReturnCount()==5) {
                    adapter.changeMoreStatus(ProjectListRecyViewAdapter.LOADING_MORE);
                    adapter.notifyDataSetChanged();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           /* for (int i=0;i<5;i++)
                            {
                                ProjectInfo projectInfo=new ProjectInfo();
                                projectInfo.setProjectName("allprojectname" + num);
                                projectInfo.setIntroduction("Adobe illustrator是一种应用于出版、多媒体和在线图像的工业标准矢量插画的软件，作为一款非常好的图片处理工具，Adobe Illustrator广泛应用于印刷出版、海报书籍排版、专业插..." + num);
                                projectInfo.setPubTime("2016-8-28");
                                projectInfo.setOwnUser("allwriter" + num);
                                projectInfo.setLabel1("alllabel" + num + 1);
                                projectInfo.setLabel2("alllabel" + num + 2);
                                User.getInstance().allinfos.add(projectInfo);
                                adapter.notifyDataSetChanged();
                                num++;
                            }*/
                            if (NetUtil.isNetworkConnectionActive(getActivity())) {
                                HttpPostRunnable runnable = new HttpPostRunnable();
                                runnable.setActionId(7);
                                runnable.setPageSize("5");
                                runnable.setTime_max(User.getInstance().getMinimumTime());
                                Thread thread = new Thread(runnable);
                                thread.start();
                                try {
                                    thread.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                JsonParser.ParseJson(7, runnable.getStrResult());
                                adapter.notifyDataSetChanged();
                                num = num + 5;
                            } else {
                                Toast.makeText(getActivity(), "网络连接失败，请检查网络", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, 1000);
                    adapter.notifyDataSetChanged();
                    adapter.changeMoreStatus(ProjectListRecyViewAdapter.PULLUP_LOAD_MORE);
                }else {
                    adapter.changeMoreStatus(2);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = num;
            }
        });
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.demo_swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        User.getInstance().allinfos.clear();
                        num = 0;
                        if (NetUtil.isNetworkConnectionActive(getActivity())) {
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
                            //runnable.setStrResult("");
                            JsonParser.ParseJson(7, runnable.getStrResult());
                       /* for (int i = 0; i < 5; i++) {
                            ProjectInfo projectInfo = new ProjectInfo();
                            projectInfo.setProjectName("allprojectname" + num);
                            projectInfo.setIntroduction("Adobe illustrator是一种应用于出版、多媒体和在线图像的工业标准矢量插画的软件，作为一款非常好的图片处理工具，Adobe Illustrator广泛应用于印刷出版、海报书籍排版、专业插..." + num);
                            projectInfo.setPubTime("2016-8-28");
                            projectInfo.setOwnUser("allwriter" + num);
                            projectInfo.setLabel1("alllabel" + num + 1);
                            projectInfo.setLabel2("alllabel" + num + 2);
                            User.getInstance().allinfos.add(projectInfo);
                            num++;
                        }*/
                            num = num + 5;
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "网络连接失败，请检查网络", Toast.LENGTH_LONG).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        return view;
    }
}
