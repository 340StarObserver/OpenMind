package comfranklicm.github.openmind.Httprequests;

/**
 * Created by Administrator on 9/9/2016.
 */
import android.util.Log;

import java.io.File;
import java.nio.charset.Charset;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONObject;
import comfranklicm.github.openmind.utils.NetUtil;
import comfranklicm.github.openmind.utils.User;

public class HttpPostImageRunnable implements Runnable{
    private String token;
    private File imgfile;
    public byte[] imageBytes;
    @Override
    public void run() {
        HttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost("http://"+ NetUtil.getInstance().getIpAddress()+":"+NetUtil.getInstance().getPort()+"/action");
        HttpEntity emEntity;
        String boundary = "-----------------------------" + UUID.randomUUID().toString();
        Log.d("boundary",boundary);
        httpPost.setHeader("Cookie", NetUtil.getInstance().getSessionId());
        httpPost.setHeader("Content-type","multipart/form-data; boundary="+boundary);
        ByteArrayBody bab = new ByteArrayBody(imageBytes, "head.png");
        try {
            emEntity= MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .setBoundary(boundary)
                    .addPart("head",bab)
                    .addPart("action_id",new StringBody("3",Charset.forName("utf-8")))
                    .addPart("token", new StringBody(token, Charset.forName("utf-8")))
                    .build();
            httpPost.setEntity(emEntity);

            HttpResponse httpResponse=httpClient.execute(httpPost);
            int code=httpResponse.getStatusLine().getStatusCode();
            if(code==200)
            {
               String result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
                Log.d("result",result);
                JSONObject jsonObject =new JSONObject(result);
                User.getInstance().setUpLoadImageResult(jsonObject.getString("result"));
                if(User.getInstance().getUpLoadImageResult().equals("true"))
                {
                    NetUtil.getInstance().setToken(jsonObject.getString("token"));
                }else
                {
                    String error;
                    switch (jsonObject.getString("reason"))
                    {
                        case "1":error="未登陆";break;
                        case "2":error="令牌错误";break;
                        default:error="其他错误";break;
                    }
                    User.getInstance().setUpLoadImageError(error);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public File getImgfile() {
        return imgfile;
    }

    public void setImgfile(File imgfile) {
        this.imgfile = imgfile;
    }
}
