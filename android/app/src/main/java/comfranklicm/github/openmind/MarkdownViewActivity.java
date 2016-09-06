package comfranklicm.github.openmind;

import android.app.Activity;
import android.os.Bundle;

import comfranklicm.github.openmind.Httprequests.HttpGetFileRunnable;
import comfranklicm.github.openmind.utils.User;
import us.feras.mdv.MarkdownView;

public class MarkdownViewActivity extends Activity {
    MarkdownView markdownView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdown_view);
        markdownView = (MarkdownView) findViewById(R.id.markdownView);
        HttpGetFileRunnable runnable = new HttpGetFileRunnable();
        runnable.setPath(User.getInstance().getFileUrl());
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        markdownView.loadMarkdown(runnable.getResult());
    }
}
