package comfranklicm.github.openmind.JsonParsing;

import org.json.JSONException;
import org.json.JSONObject;

import comfranklicm.github.openmind.utils.User;

/**
 * 登出 Json解析
 */
public class LogoutJsonParser extends BaseJsonParser{
    public void LogoutJsonParsing(String strResult)
    {
        try {
            JSONObject jarr=new JSONObject(strResult);
            User.getInstance().setLogoutResult(jarr.getString("result"));
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
