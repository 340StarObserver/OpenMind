package comfranklicm.github.openmind.Httprequests;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import comfranklicm.github.openmind.utils.NetUtil;

/**
 * 请求封装
 * action_id:{
 *     1:创建新用户
 *     2:创建新用户
 *     4:注销
 *     7:浏览所有项目的概要信息
 *     8:浏览自己的所有项目的概要信息
 *     9:浏览具体的一个项目的详细信息
 *     10:同步数据
 *     11:查看我的活跃记录
 *     12:发表评论和建议
 *     13:查看与我相关的消息
 *     14:查看投票栏
 *     15:为喜爱的项目投票
 * }
 */
public class HttpPostRunnable implements Runnable{
    private Integer actionId;//
    private String username;//
    private String password;//
    private String realname;//
    private String department;//
    private String month;
    private String num;
    private String projectId;//8,11,14
    private String projectName;//11
    private String projectOwnerUser;//11
    private String projectOwnerName;//11
    private String parentId;//11
    private String content;//11
    private String token;//9
    private String pageSize;//12
    private String time_max;//12
    private String strResult;//返回的字符串
    private Context context;

    @Override
    public void run() {
        try {
            List<NameValuePair> pairList = new ArrayList<NameValuePair>();
            // replace with your url
            switch (actionId){
                /**
                 * 创建新用户
                 */
                case 1:{
                    NameValuePair pair1 = new BasicNameValuePair("action_id","1");
                    NameValuePair pair2 = new BasicNameValuePair("username", username);
                    NameValuePair pair3 = new BasicNameValuePair("password", password);
                    NameValuePair pair4 = new BasicNameValuePair("realname",realname);
                    NameValuePair pair5 = new BasicNameValuePair("department",department);
                    pairList.add(pair1);
                    pairList.add(pair2);
                    pairList.add(pair3);
                    pairList.add(pair4);
                    pairList.add(pair5);
                    break;
                }
                /**
                 * 登陆认证
                 */
                case 2:{
                    NameValuePair pair1 = new BasicNameValuePair("action_id","2");
                    NameValuePair pair2 = new BasicNameValuePair("username", username);
                    NameValuePair pair3 = new BasicNameValuePair("password", password);
                    pairList.add(pair1);
                    pairList.add(pair2);
                    pairList.add(pair3);
                    break;
                }
                /**
                 * 注销
                 */
                case 4:{
                    NameValuePair pair1 = new BasicNameValuePair("action_id","4");
                    pairList.add(pair1);
                    break;
                }
                /**
                 * 浏览所有项目的概要信息
                 */
                case 7:{
                    NameValuePair pair1 = new BasicNameValuePair("action_id","7");
                    NameValuePair pair2 = new BasicNameValuePair("page_size",pageSize);
                    NameValuePair pair3 = new BasicNameValuePair("time_max",time_max);
                    pairList.add(pair1);
                    pairList.add(pair2);
                    pairList.add(pair3);
                    break;
                }
                /**
                 * 浏览自己的所有项目的概要信息
                 */
                case 8:{
                    NameValuePair pair1 = new BasicNameValuePair("action_id","8");
                    pairList.add(pair1);
                    break;
                }
                /**
                 * 浏览具体的一个项目的详细信息
                 */
                case 9:{
                    NameValuePair pair1 = new BasicNameValuePair("action_id","9");
                    NameValuePair pair2 = new BasicNameValuePair("proj_id",projectId);
                    pairList.add(pair1);
                    pairList.add(pair2);
                    break;
                }
                /**
                 *同步数据
                 */
                case 10:{
                    NameValuePair pair1 = new BasicNameValuePair("action_id","10");
                    NameValuePair pair2 = new BasicNameValuePair("token",token);
                    pairList.add(pair1);
                    pairList.add(pair2);
                    break;
                }
                /**
                 * 查看我的活跃记录
                 */
                case 11:{
                    NameValuePair pair1 = new BasicNameValuePair("action_id","11");
                    NameValuePair pair2 = new BasicNameValuePair("month",month);
                    NameValuePair pair3 = new BasicNameValuePair("num",num);
                    pairList.add(pair1);
                    pairList.add(pair2);
                    pairList.add(pair3);
                    break;
                }
                /**
                 *  发表评论和建议
                 */
                case 12:{
                    NameValuePair pair1 = new BasicNameValuePair("action_id","12");
                    NameValuePair pair2 = new BasicNameValuePair("proj_id",projectId);
                    NameValuePair pair3 = new BasicNameValuePair("proj_name",projectName);
                    NameValuePair pair4 = new BasicNameValuePair("own_usr",projectOwnerUser);
                    NameValuePair pair5 = new BasicNameValuePair("own_name",projectOwnerName);
                    NameValuePair pair6 = new BasicNameValuePair("parent_id",parentId);
                    NameValuePair pair7 = new BasicNameValuePair("content",content);
                    pairList.add(pair1);
                    pairList.add(pair2);
                    pairList.add(pair3);
                    pairList.add(pair4);
                    pairList.add(pair5);
                    pairList.add(pair6);
                    pairList.add(pair7);
                    break;
                }
                /**
                 * 查看与我相关的消息
                 */
                case 13:{
                    NameValuePair pair1 = new BasicNameValuePair("action_id","13");
                    NameValuePair pair2 = new BasicNameValuePair("page_size",pageSize);
                    NameValuePair pair3 = new BasicNameValuePair("time_max",time_max);
                    pairList.add(pair1);
                    pairList.add(pair2);
                    pairList.add(pair3);
                    break;
                }
                /**
                 * 查看投票栏
                 */
                case 14:{
                    NameValuePair pair1 = new BasicNameValuePair("action_id","14");
                    pairList.add(pair1);
                    break;
                }
                /**
                 * 为喜爱的项目投票
                 */
                case 15:{
                    NameValuePair pair1 = new BasicNameValuePair("action_id","15");
                    NameValuePair pair2 = new BasicNameValuePair("proj_id",projectId);
                    pairList.add(pair1);
                    pairList.add(pair2);
                    break;
                }
                default:{
                    break;
                }
            }
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost("http://"+ NetUtil.getInstance().getIpAddress()+":"+NetUtil.getInstance().getPort()+"/action");
                request.setHeader("Cookie","");
                HttpResponse response;
                HttpEntity requestHttpEntity = new UrlEncodedFormEntity(pairList, HTTP.UTF_8);
                request.setEntity(requestHttpEntity);
                if (actionId!=2&&actionId!=1)
                {
                    request.setHeader("Cookie",NetUtil.getInstance().getSessionId());
                }
                response = client.execute(request);
                if (response.getStatusLine().getStatusCode() == 200) {
                    try {
                        try {
                            if (actionId == 2&&actionId==5&&actionId==6&&actionId==10) {
                                Header it = response.getFirstHeader("Set-Cookie");
                                String session = it.toString();
                                String[] heads = session.split(";");
                                session = heads[0];
                                NetUtil.getInstance().setSessionId(session);
                            }
                        }catch (NullPointerException e)
                        {
                            e.printStackTrace();
                        }
                        /**读取服务器返回过来的json字符串数据**/
                        strResult = EntityUtils.toString(response.getEntity());
                        Log.d("strResult", strResult);
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(context, "POST提交失败", Toast.LENGTH_SHORT).show();
                }
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                Log.d("wrong:", "wrong");
                //e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.d("wrong2:", "wrong2");
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectOwnerUser() {
        return projectOwnerUser;
    }

    public void setProjectOwnerUser(String projectOwnerUser) {
        this.projectOwnerUser = projectOwnerUser;
    }

    public String getProjectOwnerName() {
        return projectOwnerName;
    }

    public void setProjectOwnerName(String projectOwnerName) {
        this.projectOwnerName = projectOwnerName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTime_max() {
        return time_max;
    }

    public void setTime_max(String time_max) {
        this.time_max = time_max;
    }

    public String getStrResult() {
        return strResult;
    }

    public void setStrResult(String stringResult) {
        this.strResult = stringResult;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
