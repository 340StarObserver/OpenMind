package comfranklicm.github.openmind;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

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
    CommentsListViewAdapter commentsListViewAdapter;
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
        parentname.setText(User.getInstance().getCurrentParentComment().getSendName() + "(" + User.getInstance().getCurrentParentComment().getSendUser() + ")");
        parentcontent.setText(User.getInstance().getCurrentParentComment().getContent());
        Uri uri=Uri.parse(User.getInstance().getCurrentParentComment().getSendHead());
        parenthead.setImageURI(uri);
        childNum.setText(User.getInstance().getCurrentParentComment().childCommentCount);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyActivity activity=(MyActivity)getActivity();
                activity.transactiontoProjectDetail();
            }
        });

        return view;
    }
}
