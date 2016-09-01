package comfranklicm.github.openmind.JsonParsing;

import org.json.JSONException;
import org.json.JSONObject;

import comfranklicm.github.openmind.utils.User;

/**
 * 为喜爱的项目投票
 */
public class VoteJsonParser extends BaseJsonParser{
    public void VoteJsonParsing(String strResult)
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
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
