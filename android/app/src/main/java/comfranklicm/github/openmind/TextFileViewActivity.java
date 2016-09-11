package comfranklicm.github.openmind;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import comfranklicm.github.openmind.Httprequests.HttpGetFileRunnable;
import comfranklicm.github.openmind.utils.User;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/6
 */
public class TextFileViewActivity extends Activity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_file_view);
        textView = (TextView) findViewById(R.id.textView);
        HttpGetFileRunnable r = new HttpGetFileRunnable();
        r.setPath(User.getInstance().getFileUrl());
        Thread t = new Thread(r);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textView.setText(r.getResult());
        //r.getResult();
    }
}
