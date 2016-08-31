package comfranklicm.github.openmind.JsonParsing;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import comfranklicm.github.openmind.utils.AboutMe;
import comfranklicm.github.openmind.utils.Active;
import comfranklicm.github.openmind.utils.ActiveInfo;
import comfranklicm.github.openmind.utils.Comment;
import comfranklicm.github.openmind.utils.Link;
import comfranklicm.github.openmind.utils.NetUtil;
import comfranklicm.github.openmind.utils.ProjectInfo;
import comfranklicm.github.openmind.utils.Share;
import comfranklicm.github.openmind.utils.User;

/**
 * json解析封装
 */
public class JsonParser {

    public static void ParseJson(Integer actionId,String strResult)
    {
        switch (actionId)
        {
            case 1:
            {
                try {
                    JSONObject jarr=new JSONObject(strResult);
                    String error="";
                    User.getInstance().setRegisterResult(jarr.getString("result"));
                    if(User.getInstance().getRegisterResult().equals("true"))
                    {

                    }
                    else
                    {
                        switch (jarr.getString("reason"))
                        {
                            case "1":error="用户名或密码格式错误";break;
                            case "2":error="信息不完整";break;
                            case "3":error="用户名已经存在";break;
                            default:error="其他错误";break;                        }
                       User.getInstance().setRegisterError(error);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
               break;
            }
            case 2:
            {
                try {
                    JSONObject jarr=new JSONObject(strResult);
                    String error="";
                    User.getInstance().setLoginResult(jarr.getString("result"));
                    if(User.getInstance().getLoginResult().equals("true"))
                    {
                        User.getInstance().setRealName(jarr.getString("realname"));
                        User.getInstance().setDepartment(jarr.getString("department"));
                        User.getInstance().setRegisterTime(jarr.getString("signup_time"));
                        User.getInstance().setPictureLink(jarr.getString("head"));
                        NetUtil.getInstance().setToken("token");
                    }
                    else
                    {
                        switch (jarr.getString("reason"))
                        {
                            case "1":error="用户名或密码格式错误";break;
                            case "2":error="表示用户名密码错误";break;
                            default:error="其他错误";break;                        }
                        User.getInstance().setLoginError(error);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
            case 4:
            {
                try {
                    JSONObject jarr=new JSONObject(strResult);
                    User.getInstance().setLogoutResult(jarr.getString("result"));
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
            case 7:
            {
               try{
                   JSONArray jsonArray=new JSONArray(strResult);
                   for(int i=0;i<jsonArray.length();i++)
                   {
                       JSONObject jarr=(JSONObject)jsonArray.get(i);
                       ProjectInfo projectInfo=new ProjectInfo();
                       projectInfo.setProjectId(jarr.getString("_id"));
                       projectInfo.setProjectName(jarr.getString("proj_name"));
                       projectInfo.setOwnUser(jarr.getString("own_usr"));
                       projectInfo.setOwnName(jarr.getString("own_name"));
                       projectInfo.setOwn_head(jarr.getString("own_head"));
                       projectInfo.setPubTime(jarr.getString("pub_time"));
                       projectInfo.setIntroduction(jarr.getString("introduction"));
                       projectInfo.setLabels(jarr.getString("labels"));
                       JSONArray jsonArray1=new JSONArray(jarr.getString("labels"));
                       List<String>stringList=new ArrayList<String>();
                       for (int j=0;j<jsonArray1.length();j++)
                       {
                           if (j==0)
                           {
                               projectInfo.setLabel1(jsonArray1.getString(j));
                           }
                           try {
                               if (j==1)
                               {
                                   projectInfo.setLabel2(jsonArray1.getString(j));
                               }
                           }catch (Exception e)
                           {
                               e.printStackTrace();
                           }
                           stringList.add(jsonArray1.getString(j));
                       }
                       projectInfo.setLabellist(stringList);
                       User.getInstance().allinfos.add(projectInfo);
                   }
               }catch (Exception e)
               {
                 e.printStackTrace();
               }
                break;
            }
            case 8:
            {
                try{
                    JSONArray jsonArray=new JSONArray(strResult);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jarr=(JSONObject)jsonArray.get(i);
                        ProjectInfo projectInfo=new ProjectInfo();
                        projectInfo.setProjectId(jarr.getString("_id"));
                        projectInfo.setProjectName(jarr.getString("proj_name"));
                        projectInfo.setOwnUser(jarr.getString("own_usr"));
                        projectInfo.setOwnName(jarr.getString("own_name"));
                        projectInfo.setOwn_head(jarr.getString("own_head"));
                        projectInfo.setPubTime(jarr.getString("pub_time"));
                        projectInfo.setIntroduction(jarr.getString("introduction"));
                        projectInfo.setLabels(jarr.getString("labels"));
                        JSONArray jsonArray1=new JSONArray(jarr.getString("labels"));
                        List<String>stringList=new ArrayList<String>();
                        for (int j=0;j<jsonArray1.length();j++)
                        {
                            if (j==0)
                            {
                                projectInfo.setLabel1(jsonArray1.getString(j));
                            }
                            if (j==1)
                            {
                                projectInfo.setLabel2(jsonArray1.getString(j));
                            }
                            stringList.add(jsonArray1.getString(j));
                        }
                        projectInfo.setLabellist(stringList);
                        User.getInstance().owninfos.add(projectInfo);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
            case 9:
            {
                try {
                   JSONObject jarr=new JSONObject(strResult);
                    ProjectInfo projectInfo=new ProjectInfo();
                    User.getInstance().setProjectFindResult(jarr.getString("result"));
                    if (User.getInstance().getProjectFindResult().equals("true"))
                    {
                        projectInfo.setProjectId(jarr.getString("_id"));
                        projectInfo.setProjectName(jarr.getString("proj_name"));
                        projectInfo.setOwnUser(jarr.getString("own_usr"));
                        projectInfo.setOwnName(jarr.getString("own_name"));
                        projectInfo.setOwn_head(jarr.getString("own_head"));
                        projectInfo.setPubTime(jarr.getString("pub_time"));
                        projectInfo.setIntroduction(jarr.getString("introduction"));


                        JSONArray jsonArray=new JSONArray(jarr.getString("labels"));
                        List<String>labelList=new ArrayList<String>();
                        for (int j=0;j<jsonArray.length();j++)
                        {
                            if (j==0)
                            {
                                projectInfo.setLabel1(jsonArray.getString(j));
                            }
                            if (j==1)
                            {
                                projectInfo.setLabel2(jsonArray.getString(j));
                            }
                            labelList.add(jsonArray.getString(j));
                        }
                        projectInfo.setLabellist(labelList);


                        JSONArray jsonArray1=new JSONArray(jarr.getString("links"));
                        List<Link>linkList=new ArrayList<Link>();
                        for(int k=0;k<jsonArray1.length();k++)
                        {
                            JSONObject jsonObject=(JSONObject)jsonArray1.get(k);
                            Link link=new Link();
                            link.setAdress(jsonObject.getString("address"));
                            link.setDescription(jsonObject.getString("description"));
                            linkList.add(link);
                        }
                        projectInfo.setLinkList(linkList);


                        JSONArray jsonArray2=new JSONArray(jarr.getString("shares"));
                        List<Share>shareList=new ArrayList<Share>();
                        for (int l=0;l<jsonArray2.length();l++)
                        {
                            JSONObject jsonObject=(JSONObject)jsonArray2.get(l);
                            Share share=new Share();
                            share.setName(jsonObject.getString("name"));
                            share.setTime(jsonObject.getString("time"));
                            share.setUrl(jsonObject.getString("url"));
                            shareList.add(share);
                        }
                        projectInfo.setShareList(shareList);


                        JSONArray jsonArray3=new JSONArray(jarr.getString("comments"));
                        List<Comment>commentList=new ArrayList<Comment>();
                        for (int i=0;i<jsonArray3.length();i++)
                        {
                            JSONObject jsonObject=(JSONObject)jsonArray3.get(i);
                            Comment comment=new Comment();
                            comment.setCommentId(jsonObject.getString("id"));
                            comment.setParentId(jsonObject.getString("parent_id"));
                            comment.setSendUser(jsonObject.getString("send_usr"));
                            comment.setSendName(jsonObject.getString("send_name"));
                            comment.setSendHead(jsonObject.getString("send_head"));
                            comment.setReceiveUser(jsonObject.getString("recv_usr"));
                            comment.setReceiveName(jsonObject.getString("recv_name"));
                            comment.setTime(jsonObject.getString("time"));
                            comment.setContent(jsonObject.getString("content"));
                            commentList.add(comment);
                        }
                        projectInfo.setCommentList(commentList);
                        User.getInstance().setCurrentProject(projectInfo);
                    }
                    else
                    {

                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 10:
            {
               try{
                   JSONObject jarr=new JSONObject(strResult);
                   String error="";
                   User.getInstance().setSynchronousResult(jarr.getString("result"));
                   if (User.getInstance().getSynchronousResult().equals("true")) {
                       //个人所有项目数据
                       JSONArray jsonArray=new JSONArray(jarr.getString("projects"));
                       try {
                           User.getInstance().owninfos.clear();
                       }catch (Exception e)
                       {
                           e.printStackTrace();
                       }
                       for (int i=0;i<jsonArray.length();i++)
                       {
                           JSONObject jsonObject1=(JSONObject)jsonArray.get(i);
                           ProjectInfo projectInfo=new ProjectInfo();
                           projectInfo.setProjectId(jsonObject1.getString("_id"));
                           projectInfo.setProjectName(jsonObject1.getString("proj_name"));
                           projectInfo.setOwnUser(jsonObject1.getString("own_usr"));
                           projectInfo.setOwnName(jsonObject1.getString("own_name"));
                           projectInfo.setOwn_head(jsonObject1.getString("own_head"));
                           projectInfo.setPubTime(jsonObject1.getString("pub_time"));
                           projectInfo.setIntroduction(jsonObject1.getString("introduction"));


                           JSONArray jsonArray1=new JSONArray(jsonObject1.getString("labels"));
                           List<String>labelList=new ArrayList<String>();
                           for (int j=0;j<jsonArray1.length();j++)
                           {
                               if (j==0)
                               {
                                   projectInfo.setLabel1(jsonArray1.getString(j));
                               }
                               if (j==1)
                               {
                                   projectInfo.setLabel2(jsonArray1.getString(j));
                               }
                               labelList.add(jsonArray1.getString(j));
                           }
                           projectInfo.setLabellist(labelList);


                         /*  JSONArray jsonArray2=new JSONArray(jsonObject1.getString("links"));
                           List<Link>linkList=new ArrayList<Link>();
                           for(int k=0;k<jsonArray2.length();k++)
                           {
                               JSONObject jsonObject=(JSONObject)jsonArray2.get(k);
                               Link link=new Link();
                               link.setAdress(jsonObject.getString("address"));
                               link.setDescription(jsonObject.getString("description"));
                               linkList.add(link);
                           }
                           projectInfo.setLinkList(linkList);


                           JSONArray jsonArray3=new JSONArray(jsonObject1.getString("shares"));
                           List<Share>shareList=new ArrayList<Share>();
                           for (int l=0;l<jsonArray3.length();l++)
                           {
                               JSONObject jsonObject=(JSONObject)jsonArray3.get(l);
                               Share share=new Share();
                               share.setName(jsonObject.getString("name"));
                               share.setTime(jsonObject.getString("time"));
                               share.setUrl(jsonObject.getString("url"));
                               shareList.add(share);
                           }
                           projectInfo.setShareList(shareList);


                           JSONArray jsonArray4=new JSONArray(jsonObject1.getString("comments"));
                           List<Comment>commentList=new ArrayList<Comment>();
                           for (int m=0;m<jsonArray4.length();i++)
                           {
                               JSONObject jsonObject=(JSONObject)jsonArray4.get(m);
                               Comment comment=new Comment();
                               comment.setCommentId(jsonObject.getString("id"));
                               comment.setParentId(jsonObject.getString("parent_id"));
                               comment.setSendUser(jsonObject.getString("send_usr"));
                               comment.setSendName(jsonObject.getString("send_name"));
                               comment.setSendHead(jsonObject.getString("send_head"));
                               comment.setReceiveUser(jsonObject.getString("recv_usr"));
                               comment.setReceiveName(jsonObject.getString("recv_name"));
                               comment.setTime(jsonObject.getString("time"));
                               comment.setContent(jsonObject.getString("content"));
                               commentList.add(comment);
                           }
                           projectInfo.setCommentList(commentList);*/
                           User.getInstance().owninfos.add(projectInfo);
                       }
                       //我的所有活跃数据
                       JSONArray jsonArray1=new JSONArray(jarr.getString("active_info"));
                       try {
                           User.getInstance().ownactives.clear();
                       }catch (Exception e)
                       {
                           e.printStackTrace();
                       }
                      for(int i=0;i<jsonArray1.length();i++)
                      {
                           JSONObject jsonObject=(JSONObject)jsonArray1.get(i);
                           ActiveInfo activeInfo=new ActiveInfo();
                           activeInfo.setActive(jsonObject.getString("active"));
                           activeInfo.setMonth(jsonObject.getString("month"));

                          JSONArray jsonArray2=new JSONArray(jsonObject.getString("active"));
                          List<Active>activeList=new ArrayList<Active>();
                          for (int j=0;j<jsonArray2.length();j++)
                          {
                              JSONObject jsonObject1=(JSONObject)jsonArray2.get(j);
                              Active active=new Active();
                              active.setDay(jsonObject1.getString("day"));
                              active.setDegree(jsonObject1.getString("degree"));
                              activeList.add(active);
                          }
                          activeInfo.setActiveList(activeList);
                          User.getInstance().ownactives.add(activeInfo);
                      }
                       NetUtil.getInstance().setToken(jarr.getString("token"));
                   }
                   else {
                       switch (jarr.getString("reason"))
                       {
                           case "1":error="未登陆";break;
                           case "2":error="令牌错误";break;
                       }
                       User.getInstance().setSynchronousError(error);
                   }
               }catch (Exception e)
               {
                   e.printStackTrace();
               }
                break;
            }
            case 11:
            {
                 try{
                     JSONArray jsonArray1=new JSONArray(strResult);
                     for(int i=0;i<jsonArray1.length();i++)
                     {
                         JSONObject jsonObject=(JSONObject)jsonArray1.get(i);
                         ActiveInfo activeInfo=new ActiveInfo();
                         activeInfo.setActive(jsonObject.getString("active"));
                         activeInfo.setMonth(jsonObject.getString("month"));

                         JSONArray jsonArray2=new JSONArray(jsonObject.getString("active"));
                         List<Active>activeList=new ArrayList<Active>();
                         for (int j=0;j<jsonArray2.length();j++)
                         {
                             JSONObject jsonObject1=(JSONObject)jsonArray2.get(j);
                             Active active=new Active();
                             active.setDay(jsonObject1.getString("day"));
                             active.setDegree(jsonObject1.getString("degree"));
                             activeList.add(active);
                         }
                         activeInfo.setActiveList(activeList);
                         User.getInstance().ownactives.add(activeInfo);
                     }
                 }catch (Exception e)
                 {
                     e.printStackTrace();
                 }
                break;
            }
            case 12:
            {
                try {
                    JSONObject jarr=new JSONObject(strResult);
                    String error="";
                    User.getInstance().setCommentResult(jarr.getString("result"));
                    if(User.getInstance().getCommentResult().equals("true"))
                    {

                    }
                    else
                    {
                        switch (jarr.getString("reason"))
                        {
                            case "1":error="未登陆";break;
                            case "2":error="不存在该项目";break;
                            default:error="其他错误";break;                        }
                        User.getInstance().setCommentError(error);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
            case 13:
            {
              try{
                  JSONArray jsonArray=new JSONArray(strResult);
                  for (int i=0;i<jsonArray.length();i++)
                  {
                      JSONObject jsonObject=(JSONObject)jsonArray.get(i);
                      AboutMe aboutMe=new AboutMe();
                      aboutMe.setWhoUser(jsonObject.getString("who_usr"));
                      aboutMe.setWhoName(jsonObject.getString("who_name"));
                      aboutMe.setWhoHead(jsonObject.getString("who_head"));
                      aboutMe.setTime(jsonObject.getString("time"));
                      aboutMe.setProjectId(jsonObject.getString("proj_id"));
                      aboutMe.setProjectName(jsonObject.getString("proj_name"));
                      aboutMe.setActionId(jsonObject.getString("action_id"));
                      if (aboutMe.getActionId().equals("0"))
                      {
                          aboutMe.setContent(jsonObject.getString("content"));
                      }
                      User.getInstance().aboutMeList.add(aboutMe);
                  }
              }catch (Exception e)
              {
                  e.printStackTrace();
              }
                break;
            }
            case 14:
            {
                try{
                    JSONArray jsonArray=new JSONArray(strResult);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jarr=(JSONObject)jsonArray.get(i);
                        ProjectInfo projectInfo=new ProjectInfo();
                        projectInfo.setProjectId(jarr.getString("_id"));
                        projectInfo.setProjectName(jarr.getString("proj_name"));
                        projectInfo.setOwnUser(jarr.getString("own_usr"));
                        projectInfo.setOwnName(jarr.getString("own_name"));
                        projectInfo.setOwn_head(jarr.getString("own_head"));
                        projectInfo.setPubTime(jarr.getString("pub_time"));
                        projectInfo.setIntroduction(jarr.getString("introduction"));
                        projectInfo.setLabels(jarr.getString("labels"));
                        projectInfo.setScore(jarr.getString("score"));
                        JSONArray jsonArray1=new JSONArray(jarr.getString("labels"));
                        List<String>stringList=new ArrayList<String>();
                        for (int j=0;j<jsonArray1.length();j++)
                        {
                            if (j==0)
                            {
                                projectInfo.setLabel1(jsonArray1.getString(j));
                            }
                            if (j==1)
                            {
                                projectInfo.setLabel2(jsonArray1.getString(j));
                            }
                            stringList.add(jsonArray1.getString(j));
                        }
                        projectInfo.setLabellist(stringList);
                        JSONArray jsonArray2=new JSONArray(jarr.getString("links"));
                        List<Link>linkList=new ArrayList<Link>();
                        for(int k=0;k<jsonArray2.length();k++)
                        {
                            JSONObject jsonObject=(JSONObject)jsonArray2.get(k);
                            Link link=new Link();
                            link.setAdress(jsonObject.getString("address"));
                            link.setDescription(jsonObject.getString("description"));
                            linkList.add(link);
                        }
                        projectInfo.setLinkList(linkList);
                        User.getInstance().owninfos.add(projectInfo);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
            case 15:
            {
                try {
                    JSONObject jarr=new JSONObject(strResult);
                    String error="";
                    User.getInstance().setVoteResult(jarr.getString("result"));
                    if(User.getInstance().getVoteResult().equals("true"))
                    {

                    }
                    else
                    {
                        switch (jarr.getString("reason"))
                        {
                            case "1":error="未登陆";break;
                            case "2":error="你的投票次数用完了";break;
                            case "3":error="此时投票已经结束了";break;
                            case "4":error="不存在该项目";break;
                            default:error="其他错误";break;
                        }
                        User.getInstance().setVoteError(error);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
