package comfranklicm.github.openmind;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import comfranklicm.github.openmind.Httprequests.HttpPostRunnable;
import comfranklicm.github.openmind.JsonParsing.VoteJsonParser;
import comfranklicm.github.openmind.utils.User;

/**
 * Created by FrankLicm on 2016/8/28.
 */
public class ProjectListRecyViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    private static final int TYPE_ITEM =0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //顶部FootView
    private Context context;
    private int type;
    //上拉加载更多状态-默认为0
    private int load_more_status=0;
    public ProjectListRecyViewAdapter(Context context,int type){
         this.context=context;
         this.type=type;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final int j = position;

        switch (type) {
            //全部项目
            case 0: {
                if (holder instanceof ProjectViewHolder) {
                    ((ProjectViewHolder) holder).text_writer.setText(User.getInstance().allinfos.get(j).getOwnUser());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String time = sdf.format(new Date(Long.parseLong(User.getInstance().allinfos.get(j).getPubTime()) * 1000));
                    ((ProjectViewHolder) holder).text_date.setText(time);
                    ((ProjectViewHolder) holder).cardviewitem_title.setText(User.getInstance().allinfos.get(j).getProjectName());
                    ((ProjectViewHolder) holder).cardviewitem_desc.setText(User.getInstance().allinfos.get(j).getIntroduction());
                    ((ProjectViewHolder) holder).Label1.setText(User.getInstance().allinfos.get(j).getLabel1());
                    ((ProjectViewHolder) holder).Label2.setText(User.getInstance().allinfos.get(j).getLabel2());
                    ((ProjectViewHolder) holder).textView1.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                    ((ProjectViewHolder) holder).textView2.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                    ((ProjectViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {//add by lyy 2016.9.1
                        @Override
                        public void onClick(View v) {
                            User.getInstance().setCurrentProject(User.getInstance().allinfos.get(j));
                            User.getInstance().getMyActivity().transactiontoProjectDetail();
                        }
                    });
                } else if (holder instanceof FootViewHolder) {
                    FootViewHolder footViewHolder = (FootViewHolder) holder;
                    switch (load_more_status) {
                        case PULLUP_LOAD_MORE:
                            footViewHolder.progressBar.setVisibility(View.VISIBLE);
                            footViewHolder.foot_view_item_tv.setText("上拉加载更多...");
                            break;
                        case LOADING_MORE:
                            footViewHolder.foot_view_item_tv.setText("正在加载更多数据...");
                            break;
                        default:
                            footViewHolder.foot_view_item_tv.setText("已经没有更多数据了");
                            footViewHolder.progressBar.setVisibility(View.INVISIBLE);
                            break;
                    }
                }
                break;
            }
            //投票中项目
            case 1: {
                ((ProjectViewHolder) holder).text_writer.setText(User.getInstance().voteinfos.get(j).getOwnUser());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String time = sdf.format(new Date(Long.parseLong(User.getInstance().voteinfos.get(j).getPubTime()) * 1000));
                ((ProjectViewHolder) holder).text_date.setText(time);
                ((ProjectViewHolder) holder).cardviewitem_title.setText(User.getInstance().voteinfos.get(j).getProjectName());
                ((ProjectViewHolder) holder).cardviewitem_desc.setText(User.getInstance().voteinfos.get(j).getIntroduction());
                ((ProjectViewHolder) holder).Label1.setText(User.getInstance().voteinfos.get(j).getLabel1());
                ((ProjectViewHolder) holder).Label2.setText(User.getInstance().voteinfos.get(j).getLabel2());
                ((ProjectViewHolder) holder).textView1.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                ((ProjectViewHolder) holder).textView2.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                if (User.getInstance().voteinfos.get(j).getEverVoted().equals("true")) {
                    ((ProjectViewHolder) holder).praiseLayout.setClickable(false);
                    User.getInstance().voteinfos.get(j).setScore("" + (Integer.valueOf(User.getInstance().voteinfos.get(j).getScore()) + 1));
                    ((ProjectViewHolder) holder).textView6.setText(User.getInstance().voteinfos.get(j).getScore());
                    ((ProjectViewHolder) holder).textView3.setText(R.string.fa_thumbs_up);
                    ((ProjectViewHolder) holder).textView3.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                } else {
                    ((ProjectViewHolder) holder).textView3.setText(R.string.fa_thumbs_o_up);
                    ((ProjectViewHolder) holder).textView3.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                    ((ProjectViewHolder) holder).textView6.setText(User.getInstance().voteinfos.get(j).getScore());
                    ((ProjectViewHolder) holder).praiseLayout.setClickable(true);
                    ((ProjectViewHolder) holder).praiseLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HttpPostRunnable r = new HttpPostRunnable();
                            r.setActionId(15);
                            r.setProjectId(User.getInstance().voteinfos.get(j).getProjectId());
                            Thread t = new Thread(r);
                            t.start();
                            try {
                                t.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ((VoteJsonParser) User.getInstance().baseJsonParsers.get(14)).VoteJsonParsing(r.getStrResult());
                            if (User.getInstance().getVoteResult().equals("true")) {
                                ((ProjectViewHolder) holder).praiseLayout.setClickable(false);
                                User.getInstance().voteinfos.get(j).setScore("" + (Integer.getInteger(User.getInstance().voteinfos.get(j).getScore()) + 1));
                                ((ProjectViewHolder) holder).textView6.setText(User.getInstance().voteinfos.get(j).getScore());
                                ((ProjectViewHolder) holder).textView3.setText(R.string.fa_thumbs_up);
                                ((ProjectViewHolder) holder).textView3.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                            } else {
                                Toast.makeText(context, "投票失败:" + User.getInstance().getVoteError(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                ((ProjectViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User.getInstance().setCurrentProject(User.getInstance().voteinfos.get(j));
                        User.getInstance().getMyActivity().transactiontoVoteProjectDetail();
                    }
                });
                break;
            }
            //自己的项目
            case 2: {
                ((ProjectViewHolder) holder).text_writer.setText(User.getInstance().owninfos.get(j).getOwnUser());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String time = sdf.format(new Date(Long.parseLong(User.getInstance().owninfos.get(j).getPubTime()) * 1000));
                ((ProjectViewHolder) holder).text_date.setText(time);
                ((ProjectViewHolder) holder).text_date.setText(User.getInstance().owninfos.get(j).getPubTime());
                ((ProjectViewHolder) holder).cardviewitem_title.setText(User.getInstance().owninfos.get(j).getProjectName());
                ((ProjectViewHolder) holder).cardviewitem_desc.setText(User.getInstance().owninfos.get(j).getIntroduction());
                ((ProjectViewHolder) holder).Label1.setText(User.getInstance().owninfos.get(j).getLabel1());
                ((ProjectViewHolder) holder).Label2.setText(User.getInstance().owninfos.get(j).getLabel2());
                ((ProjectViewHolder) holder).textView1.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                ((ProjectViewHolder) holder).textView2.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                break;
            }
            default:
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v;
            if (type == 1) {
                v = LayoutInflater.from(context).inflate(R.layout.proj_vote_info_item, parent, false);
            } else {
                v = LayoutInflater.from(context).inflate(R.layout.proj_info_item, parent, false);
            }
            ProjectViewHolder viewHolder = new ProjectViewHolder(v);
            return viewHolder;
        } else if (viewType == TYPE_FOOTER) {
            View foot_view = LayoutInflater.from(context).inflate(R.layout.recycler_load_more_layout, parent, false);
            FootViewHolder footViewHolder = new FootViewHolder(foot_view);
            return footViewHolder;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        switch (type) {
            case 0: {
                count = User.getInstance().allinfos.size() + 1;
                break;
            }
            case 1: {
                count = User.getInstance().voteinfos.size();
                break;
            }
            case 2: {
                count = User.getInstance().owninfos.size();
                break;
            }
            default: {
                break;
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (type == 0) {
            if (position + 1 == getItemCount()) {
                return TYPE_FOOTER;
            } else {
                return TYPE_ITEM;
            }
        } else return TYPE_ITEM;
        //return super.getItemViewType(position);
    }

    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     *
     * @param status 加载状态
     */
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView cardviewitem_title;
        TextView cardviewitem_desc;
        TextView text_date;
        TextView text_writer;
        TextView Label1;
        TextView Label2;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView6;
        RelativeLayout praiseLayout;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            cardviewitem_title = (TextView) itemView.findViewById(R.id.cardviewitem_title);
            cardviewitem_desc = (TextView) itemView.findViewById(R.id.cardviewitem_desc);
            Label1 = (TextView) itemView.findViewById(R.id.cardviewitem_label1);
            Label2 = (TextView) itemView.findViewById(R.id.cardviewitem_label2);
            text_date = (TextView) itemView.findViewById(R.id.text_date);
            text_writer = (TextView) itemView.findViewById(R.id.text_writer);
            textView1 = (TextView) itemView.findViewById(R.id.fa_star);
            textView2 = (TextView) itemView.findViewById(R.id.fa_user);
            textView3 = (TextView) itemView.findViewById(R.id.textView3);
            textView6 = (TextView) itemView.findViewById(R.id.textView6);
            praiseLayout = (RelativeLayout) itemView.findViewById(R.id.praiserelativeLayout);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        private TextView foot_view_item_tv;
        private ProgressBar progressBar;

        public FootViewHolder(View view) {
            super(view);
            foot_view_item_tv = (TextView) view.findViewById(R.id.foot_view_item_tv);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }
}
