package comfranklicm.github.openmind;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comfranklicm.github.openmind.utils.User;

/**
 * Created by FrankLicm on 2016/9/4.
 */
public class ChildCommentListFragment extends Fragment{
    View view;
    TextView parentname;
    TextView backbtn;
    TextView parentdate;
    TextView parentcontent;
    TextView childNum;
    SimpleDraweeView parenthead;
    ListView childCommentListView;
    ChildCommentListViewAdapter commentsListViewAdapter;
    List<Map<String, Object>> commentsListItems;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.child_comment_layout,container,false);
        parentname=(TextView)view.findViewById(R.id.user_name);
        parentdate=(TextView)view.findViewById(R.id.comment_date);
        parentcontent=(TextView)view.findViewById(R.id.comment_content);
        parenthead=(SimpleDraweeView)view.findViewById(R.id.head_image_view);
        childCommentListView=(ListView)view.findViewById(R.id.child_listview);
        backbtn=(TextView)view.findViewById(R.id.backbtn);
        childNum=(TextView)view.findViewById(R.id.childnum);
        parentname.setText(User.getInstance().getCurrentParentComment().getSendName());
        parentcontent.setText(User.getInstance().getCurrentParentComment().getContent());
        Uri uri=Uri.parse(User.getInstance().getCurrentParentComment().getSendHead());
        parenthead.setImageURI(uri);
        childNum.setText("相关回复 共" + User.getInstance().getCurrentParentComment().childCommentCount + "条");
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.getInstance().currentChildComments.clear();
                User.getInstance().currentParentComments.clear();
                MyActivity activity = (MyActivity) getActivity();
                activity.transactiontoProjectDetail();
            }
        });
        commentsListItems=getListItems();
        commentsListViewAdapter=new ChildCommentListViewAdapter(this.getContext(),commentsListItems);
        childCommentListView.setAdapter(commentsListViewAdapter);
        setListViewHeightBasedOnChildren(childCommentListView);
        return view;
    }
    private List<Map<String, Object>> getListItems() {
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        if(User.getInstance().currentChildComments.size()>0)
        {
            for(int i = 0; i <User.getInstance().currentChildComments.size(); i++) {
                if(User.getInstance().currentChildComments.get(i).getParentId().equals(User.getInstance().getCurrentParentComment().getCommentId())){
                Map<String, Object> map = new HashMap<String, Object>();
                if(!User.getInstance().currentChildComments.get(i).getSendHead().equals("0")) {
                    Uri imgUri=Uri.parse((User.getInstance().currentChildComments.get(i).getSendHead()));
                    map.put("head_image_view",imgUri);
                }else {
                    Uri uri=Uri.parse("file:///android_asset/image/head.jpg");
                    map.put("head_image_view",uri);
                }
                    map.put("user_name", User.getInstance().currentChildComments.get(i).getSendName() + " 回复 " + User.getInstance().currentChildComments.get(i).getReceiveName());
                map.put("comment_floor","");
                map.put("comment_date", User.getInstance().currentChildComments.get(i).getTime());
                map.put("comment_content", User.getInstance().currentChildComments.get(i).getContent());
                map.put("comment_num", "");
                listItems.add(map);
            }
            }
        }
        return listItems;
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {

        // 获取ListView对应的Adapter

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {

            return;

        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目

            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(0, 0); // 计算子项View 的宽高

            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        // listView.getDividerHeight()获取子项间分隔符占用的高度

        // params.height最后得到整个ListView完整显示需要的高度

        listView.setLayoutParams(params);

    }
}
