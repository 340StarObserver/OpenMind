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

import comfranklicm.github.openmind.utils.ProjectInfo;
import comfranklicm.github.openmind.utils.User;

/**
 * Created by Administrator on 2016/8/28.
 */
public class ProjectListRecyViewAdapter extends RecyclerView.Adapter<ProjectListRecyViewAdapter.ProjectViewHolder>{
      private Context context;
      private int type;

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

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        final int j=position;
         switch (type)
         {
             //全部项目
             case 0:
             {
                 holder.text_writer.setText(User.getInstance().allinfos.get(j).getOwnUser());
                 holder.text_date.setText(User.getInstance().allinfos.get(j).getPubTime());
                 holder.cardviewitem_title.setText(User.getInstance().allinfos.get(j).getProjectName());
                 holder.cardviewitem_desc.setText(User.getInstance().allinfos.get(j).getIntroduction());
                 holder.Label1.setText(User.getInstance().allinfos.get(j).getLabel1());
                 holder.Label2.setText(User.getInstance().allinfos.get(j).getLabel2());
                 holder.textView1.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                 holder.textView2.setTypeface(FontManager.getTypeface(context,FontManager.FONTAWESOME));
                 break;
             }
             //投票中项目
             case 1:
             {
                 holder.text_writer.setText(User.getInstance().voteinfos.get(j).getOwnUser());
                 holder.text_date.setText(User.getInstance().voteinfos.get(j).getPubTime());
                 holder.cardviewitem_title.setText(User.getInstance().voteinfos.get(j).getProjectName());
                 holder.cardviewitem_desc.setText(User.getInstance().voteinfos.get(j).getIntroduction());
                 holder.Label1.setText(User.getInstance().voteinfos.get(j).getLabel1());
                 holder.Label2.setText(User.getInstance().voteinfos.get(j).getLabel2());
                 holder.textView1.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                 holder.textView2.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                 break;
             }
             //自己的项目
             case 2:
             {
                 holder.text_writer.setText(User.getInstance().owninfos.get(j).getOwnUser());
                 holder.text_date.setText(User.getInstance().owninfos.get(j).getPubTime());
                 holder.cardviewitem_title.setText(User.getInstance().owninfos.get(j).getProjectName());
                 holder.cardviewitem_desc.setText(User.getInstance().owninfos.get(j).getIntroduction());
                 holder.Label1.setText(User.getInstance().owninfos.get(j).getLabel1());
                 holder.Label2.setText(User.getInstance().owninfos.get(j).getLabel2());
                 holder.textView1.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                 holder.textView2.setTypeface(FontManager.getTypeface(context, FontManager.FONTAWESOME));
                 break;
             }
             default:break;
         }
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.proj_info_item,parent,false);
        ProjectViewHolder viewHolder=new ProjectViewHolder(v);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        int count=0;
        switch (type)
        {
            case 0:
            {
                count=User.getInstance().allinfos.size();
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
}
