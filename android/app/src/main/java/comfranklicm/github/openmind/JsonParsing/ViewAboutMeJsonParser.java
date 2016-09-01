package comfranklicm.github.openmind.JsonParsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import comfranklicm.github.openmind.utils.AboutMe;
import comfranklicm.github.openmind.utils.User;

/**
 * Created by Administrator on 2016/9/1.
 */
public class ViewAboutMeJsonParser extends BaseJsonParser{
    public void ViewAboutMeJsonParsing(String strResult)
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
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
