package comfranklicm.github.openmind;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import comfranklicm.github.openmind.utils.ProjectInfo;
import comfranklicm.github.openmind.utils.User;

/**
 * Created by FrankLicm on 2016/8/28.
 */
public class ProjectListRecyViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private int type;
    private static final int TYPE_ITEM =0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //顶部FootView
    //上拉加载更多
    public static final int  PULLUP_LOAD_MORE=0;
    //正在加载中
    public static final int  LOADING_MORE=1;
    //上拉加载更多状态-默认为0
    private int load_more_status=0;
    public ProjectListRecyViewAdapter(Context context,int type){
         this.context=context;
        this.type=type;
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView cardviewitem_title;
        TextView cardviewitem_desc;
        TextView text_date;
        TextView text_writer;
        TextView Label1;
        TextView Label2;
        TextView textView1;
        TextView textView2;
        public ProjectViewHolder(View itemView) {
            super(itemView);
            cardView =(CardView)itemView.findViewById(R.id.card_view);
            cardviewitem_title= (TextView) itemView.findViewById(R.id.cardviewitem_title);
            cardviewitem_desc= (TextView) itemView.findViewById(R.id.cardviewitem_desc);
            Label1=(TextView)itemView.findViewById(R.id.cardviewitem_label1);
            Label2=(TextView)itemView.findViewById(R.id.cardviewitem_label2);
            text_date=(TextView)itemView.findViewById(R.id.text_date);
            text_writer=(TextView)itemView.findViewById(R.id.text_writer);
            textView1=(TextView)itemView.findViewById(R.id.fa_star);
            textView2=(TextView)itemView.findViewById(R.id.fa_user);
        }
    }
    public static class FootViewHolder extends  RecyclerView.ViewHolder{
        private TextView foot_view_item_tv;
        public FootViewHolder(View view) {
            super(view);
            foot_view_item_tv=(TextView)view.findViewById(R.id.foot_view_item_tv);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int j=position;
        if(holder instanceof ProjectViewHolder) {
            switch (type) {
                //全部项目
                case 0: {
                    ((ProjectViewHolder) holder).text_writer.setText(User.getInstance().allinfos.get(j).getOwnUser());
                    ((ProjectViewHolder) holder).text_date.setText(User.getInstance().allinfos.get(j).getPubTime());
                    ((ProjectViewHolder) holder).cardviewitem_title.setText(User.getInstance().allinfos.get(j).getProjectName());
                    ((ProjectViewHolder) holder).cardviewitem_desc.setText(User.getInstance().allinfos.get(j).getIntroduction());
                    ((ProjectViewHolder) holder).Label1.setText(User.getInstance().allinfos.get(j).getLabel1());
                    ((ProjectViewHolder) holder).Label2.setText(User.getInstance().allinfos.get(j).getLabel2());
                    ((ProjectViewHolder) holder).textView1.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                    ((ProjectViewHolder) holder).textView2.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                    break;
                }
                //投票中项目
                case 1: {
                    ((ProjectViewHolder) holder).text_writer.setText(User.getInstance().voteinfos.get(j).getOwnUser());
                    ((ProjectViewHolder) holder).text_date.setText(User.getInstance().voteinfos.get(j).getPubTime());
                    ((ProjectViewHolder) holder).cardviewitem_title.setText(User.getInstance().voteinfos.get(j).getProjectName());
                    ((ProjectViewHolder) holder).cardviewitem_desc.setText(User.getInstance().voteinfos.get(j).getIntroduction());
                    ((ProjectViewHolder) holder).Label1.setText(User.getInstance().voteinfos.get(j).getLabel1());
                    ((ProjectViewHolder) holder).Label2.setText(User.getInstance().voteinfos.get(j).getLabel2());
                    ((ProjectViewHolder) holder).textView1.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                    ((ProjectViewHolder) holder).textView2.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                    break;
                }
                //自己的项目
                case 2: {
                    ((ProjectViewHolder) holder).text_writer.setText(User.getInstance().owninfos.get(j).getOwnUser());
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
        }else if(holder instanceof FootViewHolder){
            FootViewHolder footViewHolder=(FootViewHolder)holder;
            switch (load_more_status){
                case PULLUP_LOAD_MORE:
                    footViewHolder.foot_view_item_tv.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footViewHolder.foot_view_item_tv.setText("正在加载更多数据...");
                    break;
                default:
                    footViewHolder.foot_view_item_tv.setText("已经没有更多数据了");
                    break;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(context).inflate(R.layout.proj_info_item, parent, false);
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
        int count=0;
        switch (type)
        {
            case 0:
            {
                count=User.getInstance().allinfos.size()+1;
                break;
            }
            case 1:
            {
                count=User.getInstance().voteinfos.size();
                break;
            }
            case 2:
            {
                count=User.getInstance().owninfos.size();
                break;
            }
            default:
            {
                break;
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (type==0) {
            if (position + 1 == getItemCount()) {
                return TYPE_FOOTER;
            } else {
                return TYPE_ITEM;
            }
        }
        else return TYPE_ITEM;
        //return super.getItemViewType(position);
    }
    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     * @param status 加载状态
     */
    public void changeMoreStatus(int status){
        load_more_status=status;
        notifyDataSetChanged();
    }
}
