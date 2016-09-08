package comfranklicm.github.openmind;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comfranklicm.github.openmind.Httprequests.HttpPostRunnable;
import comfranklicm.github.openmind.JsonParsing.CommentJsonParser;
import comfranklicm.github.openmind.JsonParsing.ViewProjectDetailJsonParser;
import comfranklicm.github.openmind.JsonParsing.VoteJsonParser;
import comfranklicm.github.openmind.utils.User;

/**
 * Created by FrankLicm on 2016/9/7.
 */
public class VoteProjectDetailFragment extends Fragment {
    View view;
    TextView fa_star, fa_user, fa_info, fa_angle_double_down, fa_link, fa_angle_double_down2, fa_files_o, fa_angle_double_right, fa_comments;
    TextView title, writer, date, infocontent, commentsnum, fa_thumb, votenum;
    ListView comments_list_view;
    TagLayout mflowLayout, mflowLayout2;
    //String[] tags = new String[] {"我是中国好儿女", "我是中国好儿女", "我是中国好儿", "我", "我是中国好儿女"};
    CommentsListViewAdapter commentsListViewAdapter;
    List<Map<String, Object>> commentsListItems;
    Button commentButton, submitComment;
    EditText disInputText;
    LinearLayout scalingcontent, scalinglinks, filelayout;
    RelativeLayout vote_area;

    boolean iscontentscaling = false, islinksscaling = false;
//    String[] usersname={"吴小宝","李昌懋","吕炀","dd","cc"};
//    String[] comment_contents={"哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈","233333333333333333333333333333333333333333333","66666666666666666666666666666666","hh","5656"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.project_voting_info_layout, container, false);
        vote_area = (RelativeLayout) view.findViewById(R.id.vote_area);
        fa_thumb = (TextView) view.findViewById(R.id.fa_thumbs_up);
        if (User.getInstance().getCurrentProject().getEverVoted().equals("true")) {
            fa_thumb.setText(R.string.fa_thumbs_up);
            fa_thumb.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
        } else {
            fa_thumb.setText(R.string.fa_thumbs_o_up);
            fa_thumb.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
        }

        votenum = (TextView) view.findViewById(R.id.vote_num);
        votenum.setText("" + User.getInstance().getCurrentVotenum());
        commentButton = (Button) view.findViewById(R.id.button2);
        disInputText = (EditText) view.findViewById(R.id.group_discuss);
        disInputText.setVisibility(View.GONE);
        submitComment = (Button) view.findViewById(R.id.group_discuss_submit);
        submitComment.setVisibility(View.GONE);
        HttpPostRunnable runnable = new HttpPostRunnable();
        runnable.setActionId(9);
        runnable.setProjectId(User.getInstance().getCurrentProject().getProjectId());
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ((ViewProjectDetailJsonParser) User.getInstance().baseJsonParsers.get(8)).ViewProjectDetailJsonParsing(runnable.getStrResult());
        vote_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpPostRunnable r = new HttpPostRunnable();
                r.setActionId(15);
                r.setProjectId(User.getInstance().getCurrentProject().getProjectId());
                Thread t = new Thread(r);
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ((VoteJsonParser) User.getInstance().baseJsonParsers.get(14)).VoteJsonParsing(r.getStrResult());
                if (User.getInstance().getVoteResult().equals("true")) {
                    vote_area.setClickable(true);
                    Log.d("score", "" + User.getInstance().getCurrentVotenum());
                    Integer jj = User.getInstance().getCurrentVotenum() + 1;
                    User.getInstance().setCurrentVotenum(jj);
                    votenum.setText("" + User.getInstance().getCurrentVotenum());
                    fa_thumb.setText(R.string.fa_thumbs_up);
                    fa_thumb.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
                } else if (User.getInstance().getVoteResult().equals("false") && User.getInstance().getVoteError().equals("2")) {
                    vote_area.setClickable(true);
                    Integer jj = User.getInstance().getCurrentVotenum() - 1;
                    User.getInstance().setCurrentVotenum(jj);
                    votenum.setText("" + User.getInstance().getCurrentVotenum());
                    fa_thumb.setText(R.string.fa_thumbs_o_up);
                    fa_thumb.setTypeface(FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME));
                } else {
                    Toast.makeText(getActivity(), "操作失败:" + User.getInstance().getVoteError(), Toast.LENGTH_LONG).show();
                }
            }
        });
        seperateComment();
        getChildCommentNumber();
        initlayout();
        mflowLayout = (TagLayout) view.findViewById(R.id.labels);
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
        mflowLayout2 = (TagLayout) view.findViewById(R.id.links);
//        if(User.getInstance().getCurrentProject().getLinkList().size()>2) {
//            for (int i = 0; i < 2; i++) {
//                final int j = i;
//                String tag = User.getInstance().getCurrentProject().getLinkList().get(i).getDescription();
//                TextView tv = new TextView(getContext());
//                tv.setText(tag);
//                tv.setTextColor(Color.parseColor("#ffffff"));
//                tv.setTextSize(15);
//                tv.setBackgroundResource(R.drawable.label_shape);
//                tv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent();
//                        intent.setAction("android.intent.action.VIEW");
//                        Uri content_url = Uri.parse("http://" + User.getInstance().getCurrentProject().getLinkList().get(j).getAdress());
//                        intent.setData(content_url);
//                        startActivity(intent);
//                    }
//                });
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                params.setMargins(10, 10, 10, 10);
//                tv.setLayoutParams(params);
//                mflowLayout2.addView(tv);
//            }
//        }else {
//            for (int i = 0; i < User.getInstance().getCurrentProject().getLinkList().size(); i++) {
//                final int j = i;
//                String tag = User.getInstance().getCurrentProject().getLinkList().get(i).getDescription();
//                TextView tv = new TextView(getContext());
//                tv.setText(tag);
//                tv.setTextColor(Color.parseColor("#ffffff"));
//                tv.setTextSize(15);
//                tv.setBackgroundResource(R.drawable.label_shape);
//                tv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent();
//                        intent.setAction("android.intent.action.VIEW");
//                        Uri content_url = Uri.parse("http://" + User.getInstance().getCurrentProject().getLinkList().get(j).getAdress());
//                        intent.setData(content_url);
//                        startActivity(intent);
//                    }
//                });
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                params.setMargins(10, 10, 10, 10);
//                tv.setLayoutParams(params);
//                mflowLayout2.addView(tv);
//            }
//        }
        scalinglinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (islinksscaling) {
                    mflowLayout2.removeAllViews();
//                    if(User.getInstance().getCurrentProject().getLinkList().size()>2) {
//                        for (int i = 0; i < 2; i++) {
//                            final int j = i;
//                            String tag = User.getInstance().getCurrentProject().getLinkList().get(i).getDescription();
//                            TextView tv = new TextView(getContext());
//                            tv.setText(tag);
//                            tv.setTextColor(Color.parseColor("#ffffff"));
//                            tv.setTextSize(15);
//                            tv.setBackgroundResource(R.drawable.label_shape);
//                            tv.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Intent intent = new Intent();
//                                    intent.setAction("android.intent.action.VIEW");
//                                    Uri content_url = Uri.parse("http://" + User.getInstance().getCurrentProject().getLinkList().get(j).getAdress());
//                                    intent.setData(content_url);
//                                    startActivity(intent);
//                                }
//                            });
//                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                            params.setMargins(10, 10, 10, 10);
//                            tv.setLayoutParams(params);
//                            mflowLayout2.addView(tv);
//                        }
//                    }else {
//                        //mflowLayout2.removeAllViews();
//                        for (int i = 0; i < User.getInstance().getCurrentProject().getLinkList().size(); i++) {
//                            final int j = i;
//                            String tag = User.getInstance().getCurrentProject().getLinkList().get(i).getDescription();
//                            TextView tv = new TextView(getContext());
//                            tv.setText(tag);
//                            tv.setTextColor(Color.parseColor("#ffffff"));
//                            tv.setTextSize(15);
//                            tv.setBackgroundResource(R.drawable.label_shape);
//                            tv.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Intent intent = new Intent();
//                                    intent.setAction("android.intent.action.VIEW");
//                                    Uri content_url = Uri.parse("http://" + User.getInstance().getCurrentProject().getLinkList().get(j).getAdress());
//                                    intent.setData(content_url);
//                                    startActivity(intent);
//                                }
//                            });
//                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                            params.setMargins(10, 10, 10, 10);
//                            tv.setLayoutParams(params);
//                            mflowLayout2.addView(tv);
//                        }
//                    }
                    fa_angle_double_down2.setText(R.string.fa_angle_double_down);
                    fa_angle_double_down2.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
                    islinksscaling = false;
                } else {
                    //mflowLayout2.removeAllViews();
                    for (int i = 0; i < User.getInstance().getCurrentProject().getLinkList().size(); i++) {
                        final int j = i;
                        String tag = User.getInstance().getCurrentProject().getLinkList().get(i).getDescription();
                        TextView tv = new TextView(getContext());
                        tv.setText(tag);
                        tv.setTextColor(Color.parseColor("#ffffff"));
                        tv.setTextSize(15);
                        tv.setBackgroundResource(R.drawable.label_shape);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri content_url = Uri.parse(User.getInstance().getCurrentProject().getLinkList().get(j).getAdress());
                                intent.setData(content_url);
                                startActivity(intent);
                            }
                        });
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(10, 10, 10, 10);
                        tv.setLayoutParams(params);
                        mflowLayout2.addView(tv);
                    }
                    fa_angle_double_down2.setText(R.string.fa_angle_double_up);
                    fa_angle_double_down2.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
                    islinksscaling = true;
                }
            }
        });
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitComment.setVisibility(View.VISIBLE);
                disInputText.setVisibility(View.VISIBLE);
                disInputText.setHint("回复 " + User.getInstance().getCurrentProject().getOwnName());
                disInputText.requestFocus();
            }
        });
        submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!disInputText.getText().toString().equals("")) {
                    HttpPostRunnable r = new HttpPostRunnable();
                    r.setActionId(12);
                    r.setProjectId(User.getInstance().getCurrentProject().getProjectId());
                    r.setProjectName(User.getInstance().getCurrentProject().getProjectName());
                    r.setProjectOwnerUser(User.getInstance().getCurrentProject().getOwnUser());
                    r.setProjectOwnerName(User.getInstance().getCurrentProject().getOwnName());
                    r.setReceiveuser(User.getInstance().getCurrentProject().getOwnUser());
                    r.setReceivename(User.getInstance().getCurrentProject().getOwnName());
                    r.setParentId("0");
                    r.setContent(disInputText.getText().toString());
                    Thread t = new Thread(r);
                    t.start();
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ((CommentJsonParser) User.getInstance().baseJsonParsers.get(11)).CommentJsonParsing(r.getStrResult());
                    if (User.getInstance().getCommentResult().equals("true")) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        if (!User.getInstance().getCommentadded().getSendHead().equals("0")) {
                            Uri imgUri = Uri.parse((User.getInstance().getCommentadded().getSendHead()));
                            map.put("head_image_view", imgUri);
                        } else {
                            Uri uri = Uri.parse("file:///android_asset/image/head.jpg");
                            map.put("head_image_view", uri);
                        }
                        map.put("user_name", User.getInstance().getCommentadded().getSendName());
                        map.put("comment_floor", commentsListItems.size() + 1);
                        map.put("comment_date", User.getInstance().getCommentadded().getTime());
                        map.put("comment_content", User.getInstance().getCommentadded().getContent());
                        map.put("comment_num", User.getInstance().getCommentadded().childCommentCount);
                        commentsListViewAdapter.listItems.add(map);
                        comments_list_view.setAdapter(commentsListViewAdapter);
                        commentsListViewAdapter.notifyDataSetChanged();
                        setListViewHeightBasedOnChildren(comments_list_view);
                        disInputText.setVisibility(View.GONE);
                        submitComment.setVisibility(View.GONE);
                        User.getInstance().currentParentComments.add(User.getInstance().getCommentadded());
                        commentsnum.setText("共" + User.getInstance().currentParentComments.size() + "条评论");
                    } else {
                        Toast.makeText(getActivity(), "评论失败:" + User.getInstance().getCommentError(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return view;
    }

    private void initlayout() {

        fa_star = (TextView) view.findViewById(R.id.fa_star);
        fa_star.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_user = (TextView) view.findViewById(R.id.fa_user);
        fa_user.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_info = (TextView) view.findViewById(R.id.fa_info);
        fa_info.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_angle_double_down = (TextView) view.findViewById(R.id.cardviewitem_doubledown);
        fa_angle_double_down.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_link = (TextView) view.findViewById(R.id.fa_link);
        fa_link.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_angle_double_down2 = (TextView) view.findViewById(R.id.cardviewitem_doubledown2);
        fa_angle_double_down2.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_files_o = (TextView) view.findViewById(R.id.fa_files_o);
        fa_files_o.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_angle_double_right = (TextView) view.findViewById(R.id.fa_angle_double_right);
        fa_angle_double_right.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        fa_comments = (TextView) view.findViewById(R.id.fa_comments);
        fa_comments.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
        title = (TextView) view.findViewById(R.id.cardviewitem_title);
        title.setText(User.getInstance().getCurrentProject().getProjectName());
        writer = (TextView) view.findViewById(R.id.text_writer);
        writer.setText(User.getInstance().getCurrentProject().getOwnName());
        date = (TextView) view.findViewById(R.id.text_date);
        scalinglinks = (LinearLayout) view.findViewById(R.id.scalinglink);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(new Date(Long.parseLong(User.getInstance().getCurrentProject().getPubTime()) * 1000));
        date.setText(time);
//        User.getInstance().getCurrentProject().setIntroduction("1985年，Adobe公司在由苹果公司LaserWriter打印机带领下的PostScript桌面出版革命中扮演了重要的角色，公司名称“Adobe”来自于奥多比溪：这条河在公司原位于加州山景城的办公室不远处。2005年4月18日，Adobe系统公司以34亿美元的价格收购了原先最大的竞争对手Macromedia公司，这一收购极大丰富了Adobe的产品线，提高了其在多媒体和网络出版业的能力，这宗交易在2005年12月完成。2006年12月，Adobe宣布全线产品采用新图示，以彩色的背景配搭该程序的简写，例如：蓝色配搭Ps是Photoshop，红色配搭Fl是Flash，感觉像是元素符号，引起社会极大回响。2008年，Adobe公司在Adobe cs3基础上推出Adobe CS4，Ado\n" +
//                "市值达数百亿美元的软件公司 Adobe Systems Incorporated（纳斯达克股票代码：ADBE），20 多年来一直致力于帮助用户和企业以更好的成本效益，通过更好的方式表达图像、信息和思想。 公司在数码成像、设计和文档技术方面的创新成果，在这些领域树立了杰出的典范，使数以百万计的人们体会到视觉信息交流的强大魅力。");
        infocontent = (TextView) view.findViewById(R.id.intro_content);
        if (User.getInstance().getCurrentProject().getIntroduction().length() > 100) {
            String s = User.getInstance().getCurrentProject().getIntroduction().substring(0, 100) + "...";
            infocontent.setText(s);
        } else {
            infocontent.setText(User.getInstance().getCurrentProject().getIntroduction());
        }
        scalingcontent = (LinearLayout) view.findViewById(R.id.scalingcontent);
        scalingcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iscontentscaling) {
                    if (User.getInstance().getCurrentProject().getIntroduction().length() > 100) {
                        String s = User.getInstance().getCurrentProject().getIntroduction().substring(0, 100) + "...";
                        infocontent.setText(s);
                    } else {
                        infocontent.setText(User.getInstance().getCurrentProject().getIntroduction());
                    }
                    fa_angle_double_down.setText(R.string.fa_angle_double_down);
                    fa_angle_double_down.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
                    iscontentscaling = false;
                } else {
                    infocontent.setText(User.getInstance().getCurrentProject().getIntroduction());
                    fa_angle_double_down.setText(R.string.fa_angle_double_up);
                    fa_angle_double_down.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
                    iscontentscaling = true;
                }
            }
        });
        infocontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iscontentscaling) {
                    if (User.getInstance().getCurrentProject().getIntroduction().length() > 100) {
                        String s = User.getInstance().getCurrentProject().getIntroduction().substring(0, 100) + "...";
                        infocontent.setText(s);
                    } else {
                        infocontent.setText(User.getInstance().getCurrentProject().getIntroduction());
                    }
                    fa_angle_double_down.setText(R.string.fa_angle_double_down);
                    fa_angle_double_down.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
                    iscontentscaling = false;
                } else {
                    infocontent.setText(User.getInstance().getCurrentProject().getIntroduction());
                    fa_angle_double_down.setText(R.string.fa_angle_double_up);
                    fa_angle_double_down.setTypeface(FontManager.getTypeface(getContext(), FontManager.FONTAWESOME));
                    iscontentscaling = true;
                }
            }
        });
        commentsnum = (TextView) view.findViewById(R.id.comments_num);
        commentsnum.setText("共" + User.getInstance().currentParentComments.size() + "条评论");
        //评论区的动态加载
        comments_list_view = (ListView) view.findViewById(R.id.CommentsListView);
        commentsListItems = getListItems();
        commentsListViewAdapter = new CommentsListViewAdapter(this.getContext(), commentsListItems);
        comments_list_view.setAdapter(commentsListViewAdapter);
        setListViewHeightBasedOnChildren(comments_list_view);
        comments_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (User.getInstance().currentParentComments.size() > 0) {
                    User.getInstance().setCurrentParentComment(User.getInstance().currentParentComments.get(position));
                    MyActivity activity = (MyActivity) getActivity();
                    activity.transactiontoChildComment();
                }
            }
        });
        filelayout = (LinearLayout) view.findViewById(R.id.filelayout);
        filelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyActivity activity = (MyActivity) getActivity();
                activity.transactiontoFileView();
            }
        });
    }


//    private List<Map<String, Object>> getListItems() {
//        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
//        for(int i = 0; i <10; i++) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("head_image_view",R.mipmap.head);
//            map.put("user_name", "李昌懋");
//            map.put("comment_floor","1");
//            map.put("comment_date", "2016-9-11");
//            map.put("comment_content","哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈啊哈哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神哈哈吕神又要改数据啦");
//            map.put("comment_num","10");
//            listItems.add(map);
//        }
//        return listItems;
//    }

    //下面这个方法是直接从User中读取出数据的，但因为数据库为空所以暂时没有数据
    private List<Map<String, Object>> getListItems() {
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        if (User.getInstance().currentParentComments.size() > 0) {
            for (int i = 0; i < User.getInstance().currentParentComments.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                if (!User.getInstance().currentParentComments.get(i).getSendHead().equals("0")) {
                    Uri imgUri = Uri.parse((User.getInstance().currentParentComments.get(i).getSendHead()));
                    map.put("head_image_view", imgUri);
                } else {
                    Uri uri = Uri.parse("file:///android_asset/image/head.jpg");
                    map.put("head_image_view", uri);
                }
                map.put("user_name", User.getInstance().currentParentComments.get(i).getSendName());
                map.put("comment_floor", i + 1);
                map.put("comment_date", User.getInstance().currentParentComments.get(i).getTime());
                map.put("comment_content", User.getInstance().currentParentComments.get(i).getContent());
                map.put("comment_num", User.getInstance().currentParentComments.get(i).childCommentCount);
                listItems.add(map);
            }
        }
//        else {
//            Map<String, Object> map = new HashMap<String, Object>();
//            Uri uri=Uri.parse("file:///android_asset/image/head.jpg");
//            map.put("head_image_view",uri);
//            map.put("user_name", "暂无评论");
//            map.put("comment_floor",0);
//            map.put("comment_date", "暂无评论");
//            map.put("comment_content", "暂无评论");
//            map.put("comment_num", 0);
//            listItems.add(map);
//        }
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

    public void seperateComment() {
        for (int i = 0; i < User.getInstance().getCurrentProject().getCommentList().size(); i++) {
            if (User.getInstance().getCurrentProject().getCommentList().get(i).getParentId().equals("0")) {
                User.getInstance().currentParentComments.add(User.getInstance().getCurrentProject().getCommentList().get(i));
            } else {
                User.getInstance().currentChildComments.add(User.getInstance().getCurrentProject().getCommentList().get(i));
            }
        }
    }

    public void getChildCommentNumber() {
        for (int i = 0; i < User.getInstance().currentParentComments.size(); i++) {
            for (int j = 0; j < User.getInstance().currentChildComments.size(); j++) {
                if (User.getInstance().currentChildComments.get(j).getParentId().equals(User.getInstance().currentParentComments.get(i).getCommentId())) {
                    User.getInstance().currentParentComments.get(i).childCommentCount++;
                }
            }
        }
    }
}
