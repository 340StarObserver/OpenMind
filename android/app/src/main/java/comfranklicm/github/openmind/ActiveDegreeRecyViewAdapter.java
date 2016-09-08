package comfranklicm.github.openmind;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import comfranklicm.github.openmind.utils.User;

/**
 * Created by lyy on 2016/9/5.
 */
public class ActiveDegreeRecyViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //    private static final int TYPE_ITEM =0;  //普通Item View
//    private static final int TYPE_FOOTER = 1;  //顶部FootView
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    private Context context;
    private String year, month;
    private String date;
    //上拉加载更多状态-默认为0
    private int load_more_status = 0;

    public ActiveDegreeRecyViewAdapter(Context context, String date) {
        this.context = context;
        this.date = date;
        this.year = date.substring(0, 4);
        this.month = date.substring(4);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int j = position;
        for (int i = 0; i < User.getInstance().ownactives.get(j).getActiveList().size(); i++) {
            if (Integer.valueOf(User.getInstance().ownactives.get(j).getActiveList().get(i).getDegree()) > 10) {
                //根据活跃度的高度来更改方格的背景颜色
                ((ActiveDegreeViewHolder) holder).day[Integer.valueOf(User.getInstance().ownactives.get(j).getActiveList().get(i).getDay())].setBackgroundColor(Color.GRAY);
            } else if (Integer.valueOf(User.getInstance().ownactives.get(j).getActiveList().get(i).getDegree()) > 20) {
                ((ActiveDegreeViewHolder) holder).day[Integer.valueOf(User.getInstance().ownactives.get(j).getActiveList().get(i).getDay())].setBackgroundColor(Color.BLACK);
            } else if (true) {
                //......
            }
        }
        switch (month) {
            case "1":
            case "3":
            case "5":
            case "7":
            case "8":
            case "10":
            case "12": {
                break;
            }
            case "4":
            case "6":
            case "9":
            case "11": {
                ((ActiveDegreeViewHolder) holder).day[30].setVisibility(View.GONE);
                break;
            }
            case "2": {
                if (Integer.valueOf(year) % 4 == 0 && Integer.valueOf(year) % 100 != 0) {
                    ((ActiveDegreeViewHolder) holder).day[30].setVisibility(View.GONE);
                    ((ActiveDegreeViewHolder) holder).day[29].setVisibility(View.GONE);
                    break;
                } else if (Integer.valueOf(year) % 400 == 0) {
                    ((ActiveDegreeViewHolder) holder).day[30].setVisibility(View.GONE);
                    ((ActiveDegreeViewHolder) holder).day[29].setVisibility(View.GONE);
                    break;
                } else {
                    ((ActiveDegreeViewHolder) holder).day[30].setVisibility(View.GONE);
                    ((ActiveDegreeViewHolder) holder).day[29].setVisibility(View.GONE);
                    ((ActiveDegreeViewHolder) holder).day[28].setVisibility(View.GONE);
                    break;
                }
            }

            default:
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == TYPE_ITEM) {
//            View v = LayoutInflater.from(context).inflate(R.layout.active_degree, parent, false);
//            ActiveDegreeViewHolder viewHolder = new ActiveDegreeViewHolder(v);
//            return viewHolder;
//        } else if (viewType == TYPE_FOOTER) {
//            View foot_view = LayoutInflater.from(context).inflate(R.layout.recycler_load_more_layout, parent, false);
//            FootViewHolder footViewHolder = new FootViewHolder(foot_view);
//            return footViewHolder;
//        }
        return null;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        count = User.getInstance().ownactives.size();
        return count;
    }

    @Override
    public int getItemViewType(int position) {
//        if (date ==0) {
//            if (position + 1 == getItemCount()) {
//                return TYPE_FOOTER;
//            } else {
//                return TYPE_ITEM;
//            }
//        }
//        else return TYPE_ITEM;
        //return super.getItemViewType(position);
        return 0;
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

    static class ActiveDegreeViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView[] day = new TextView[31];

        public ActiveDegreeViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            day[0] = (TextView) itemView.findViewById(R.id.day1);
            day[1] = (TextView) itemView.findViewById(R.id.day2);
            day[2] = (TextView) itemView.findViewById(R.id.day3);
            day[3] = (TextView) itemView.findViewById(R.id.day4);
            day[4] = (TextView) itemView.findViewById(R.id.day5);
            day[5] = (TextView) itemView.findViewById(R.id.day6);
            day[6] = (TextView) itemView.findViewById(R.id.day7);
            day[7] = (TextView) itemView.findViewById(R.id.day8);
            day[8] = (TextView) itemView.findViewById(R.id.day9);
            day[9] = (TextView) itemView.findViewById(R.id.day10);
            day[10] = (TextView) itemView.findViewById(R.id.day11);
            day[11] = (TextView) itemView.findViewById(R.id.day12);
            day[12] = (TextView) itemView.findViewById(R.id.day13);
            day[13] = (TextView) itemView.findViewById(R.id.day14);
            day[14] = (TextView) itemView.findViewById(R.id.day15);
            day[15] = (TextView) itemView.findViewById(R.id.day16);
            day[16] = (TextView) itemView.findViewById(R.id.day17);
            day[17] = (TextView) itemView.findViewById(R.id.day18);
            day[18] = (TextView) itemView.findViewById(R.id.day19);
            day[19] = (TextView) itemView.findViewById(R.id.day20);
            day[20] = (TextView) itemView.findViewById(R.id.day21);
            day[21] = (TextView) itemView.findViewById(R.id.day22);
            day[22] = (TextView) itemView.findViewById(R.id.day23);
            day[23] = (TextView) itemView.findViewById(R.id.day24);
            day[24] = (TextView) itemView.findViewById(R.id.day25);
            day[25] = (TextView) itemView.findViewById(R.id.day26);
            day[26] = (TextView) itemView.findViewById(R.id.day27);
            day[27] = (TextView) itemView.findViewById(R.id.day28);
            day[28] = (TextView) itemView.findViewById(R.id.day29);
            day[29] = (TextView) itemView.findViewById(R.id.day30);
            day[30] = (TextView) itemView.findViewById(R.id.day31);
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
