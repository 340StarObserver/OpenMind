package comfranklicm.github.openmind;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.ArrayList;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import comfranklicm.github.openmind.Httprequests.HttpPostRunnable;
import comfranklicm.github.openmind.JsonParsing.JsonParser;
import comfranklicm.github.openmind.utils.User;
import comfranklicm.github.openmind.CommentsListViewAdapter;

/**
 * Created by Lyy on 2016/9/1.
 */
public class ProjectDetailFragment extends Fragment {
    View view;
    TextView fa_star,fa_user,fa_info,fa_angle_double_down,fa_link,fa_angle_double_down2,fa_files_o,fa_angle_double_right,fa_comments;
    TextView title,writer,date,infocontent;
    ListView comments_list_view;
    TagLayout mflowLayout;
    //String[] tags = new String[] {"我是中国好儿女", "我是中国好儿女", "我是中国好儿", "我", "我是中国好儿女"};
    CommentsListViewAdapter commentsListViewAdapter;
    List<Map<String, Object>> commentsListItems;

    String[] usersname={"吴小宝","李昌懋","吕炀","dd","cc"};
    String[] comment_contents={"哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈","233333333333333333333333333333333333333333333","66666666666666666666666666666666","hh","5656"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.project_info_layout, container,false);
        initlayout();
        mflowLayout=(TagLayout)view.findViewById(R.id.labels);
        for (int i = 0; i < User.getInstance().getCurrentProject().getLabellist().size(); i++) {
            String tag = User.getInstance().getCurrentProject().getLabellist().get(i);
            TextView tv = new TextView(getContext());
            tv.setText(tag);
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setTextSize(15);
            tv.setBackgroundResource(R.drawable.label_shape);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            tv.setLayoutParams(params);
            mflowLayout.addView(tv);
        }
        return view;
    }
    private void initlayout() {

        fa_star=(TextView)view.findViewById(R.id.fa_star);
        fa_star.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_user=(TextView)view.findViewById(R.id.fa_user);
        fa_user.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_info=(TextView)view.findViewById(R.id.fa_info);
        fa_info.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_angle_double_down=(TextView)view.findViewById(R.id.cardviewitem_doubledown);
        fa_angle_double_down.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_link=(TextView)view.findViewById(R.id.fa_link);
        fa_link.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_angle_double_down2=(TextView)view.findViewById(R.id.cardviewitem_doubledown2);
        fa_angle_double_down2.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_files_o=(TextView)view.findViewById(R.id.fa_files_o);
        fa_files_o.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_angle_double_right=(TextView)view.findViewById(R.id.fa_angle_double_right);
        fa_angle_double_right.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_comments=(TextView)view.findViewById(R.id.fa_comments);
        fa_comments.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        title=(TextView)view.findViewById(R.id.cardviewitem_title);
        title.setText(User.getInstance().getCurrentProject().getProjectName());
        writer=(TextView)view.findViewById(R.id.text_writer);
        writer.setText(User.getInstance().getCurrentProject().getOwnName());
        date=(TextView)view.findViewById(R.id.text_date);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(new Date(Long.parseLong(User.getInstance().getCurrentProject().getPubTime())*1000));
        date.setText(time);

        //评论区的动态加载
        comments_list_view=(ListView)view.findViewById(R.id.CommentsListView);
        commentsListItems=getListItems();
        commentsListViewAdapter=new CommentsListViewAdapter(this.getContext(),commentsListItems);
        comments_list_view.setAdapter(commentsListViewAdapter);
        ViewGroup.LayoutParams params=comments_list_view.getLayoutParams();
        params.height=2500;
        comments_list_view.setLayoutParams(params);
//        if(!User.getInstance().isLogin()) {
//
//        }else {
//
//        }
    }



    private List<Map<String, Object>> getListItems() {
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for(int i = 0; i <10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("head_image_view",R.mipmap.head);
            map.put("user_name", "李昌懋");
            map.put("comment_floor","1");
            map.put("comment_date", "2016-9-11");
            map.put("comment_content","哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈啊哈哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神又要改数据啦");
            map.put("comment_num","10");
            listItems.add(map);
        }
        return listItems;
    }

    //下面这个方法是直接从User中读取出数据的，但因为数据库为空所以暂时没有数据
//    private List<Map<String, Object>> getListItems() {
//        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
//        for(int i = 0; i <User.getInstance().getCurrentProject().getCommentList().size(); i++) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("head_image_view",User.getInstance().getCurrentProject().getCommentList().get(i).getSendHead());
//            map.put("user_name", User.getInstance().getCurrentProject().getCommentList().get(i).getSendName());
//            map.put("comment_floor",i+1);
//            map.put("comment_date", User.getInstance().getCurrentProject().getCommentList().get(i).getTime());
//            map.put("comment_content", User.getInstance().getCurrentProject().getCommentList().get(i).getContent());
//            map.put("comment_num", User.getInstance().getCurrentProject().getCommentList().get(i).getChildCommentCount());
//           listItems.add(map);
//        }
//        return listItems;
//    }
}
