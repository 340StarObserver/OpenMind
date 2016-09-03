package comfranklicm.github.openmind.Httprequests;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/9/3.
 */
public class HttpGetRunnable implements Runnable{
    Bitmap pic;
    public Bitmap getPic() {
        return pic;
    }
    String pname;
    String path;
    public void setName(String name) {this.pname = name;}
    public void setPath(String path) {
        this.path = path;
    }
    @Override
    public void run() {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(path);
            // replace with your url
            HttpResponse response;
            try {
                response = client.execute(request);
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();
                pic = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("wrong:", "wrong");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
