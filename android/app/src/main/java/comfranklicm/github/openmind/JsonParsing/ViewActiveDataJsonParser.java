package comfranklicm.github.openmind.JsonParsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import comfranklicm.github.openmind.utils.Active;
import comfranklicm.github.openmind.utils.ActiveInfo;
import comfranklicm.github.openmind.utils.User;

/**
 * 查看我的活跃记录
 */
public class ViewActiveDataJsonParser extends BaseJsonParser{
    public void ViewActiveDataJsonParsing(String strResult){
        try{
            JSONArray jsonArray1=new JSONArray(strResult);
            for(int i=0;i<jsonArray1.length();i++)
            {
                JSONObject jsonObject=(JSONObject)jsonArray1.get(i);
                ActiveInfo activeInfo=new ActiveInfo();
                activeInfo.setActive(jsonObject.getString("active"));
                activeInfo.setMonth(jsonObject.getString("month"));

                JSONArray jsonArray2=new JSONArray(jsonObject.getString("active"));
                List<Active> activeList=new ArrayList<Active>();
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
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
