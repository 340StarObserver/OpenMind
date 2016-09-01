package comfranklicm.github.openmind.JsonParsing;

import org.json.JSONException;
import org.json.JSONObject;

import comfranklicm.github.openmind.utils.User;

/**
 * 发表评论和建议
 */
public class CommentJsonParser extends BaseJsonParser{

    public void CommentJsonParsing(String strResult)
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
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
