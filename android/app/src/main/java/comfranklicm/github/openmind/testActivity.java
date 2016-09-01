/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */
package comfranklicm.github.openmind;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import comfranklicm.github.openmind.utils.DataBaseUtil;


public class testActivity extends Activity {
   WebView webView;
    SwipeRefreshLayout swipeLayout;
    private static final String TAG="OpenMind";
    private final Handler handler=new Handler();
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //actionBar = getSupportActionBar();
        swipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //重新刷新页面
                webView.loadUrl(webView.getUrl());
                swipeLayout.setRefreshing(false);
            }
        });
        //swipeLayout.setColorScheme(getResources().getColor(R.color.holo_blue_light), R.color.holo_green_light, R.color.holo_orange_light, R.color.holo_red_light);
        webView = (WebView)findViewById(R.id.webView);
       final ProgressBar progressBar=(ProgressBar)findViewById(R.id.progressBar);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new AndroidBridge(), "android"); //将那个实例化的函数类设置为"android"的js接口。
        webView.setWebViewClient(new WebViewClient() {
            //网页加载开始时调用，显示加载提示旋转进度条
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(android.view.View.VISIBLE);
                Toast.makeText(testActivity.this, "onPageStarted", Toast.LENGTH_LONG).show();
            }

            //网页加载完成时调用，隐藏加载提示旋转进度条
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                progressBar.setVisibility(android.view.View.GONE);
                Toast.makeText(testActivity.this, "onPageFinished", Toast.LENGTH_LONG).show();
            }

            //网页加载失败时调用，隐藏加载提示旋转进度条
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
                super.onReceivedError(view, errorCode, description, failingUrl);
                progressBar.setVisibility(android.view.View.GONE);
                Toast.makeText(testActivity.this, "onReceivedError", Toast.LENGTH_LONG).show();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                Log.d(TAG, "onJsAlert(" + view + "," + url + "," + message + "," + result + ")");
                Toast.makeText(testActivity.this, message, Toast.LENGTH_LONG).show();
                webView.loadUrl("javascript:callJS('hello from android')");
                result.confirm();
                return true;
            }
        });//设置可以被java截获的js事件。
        webView.loadUrl("file:///android_asset/html/menu1.html");
       final DataBaseUtil dataBaseUtil=DataBaseUtil.getInstance(this);
        //dataBaseUtil.deleteDatabase(this);
        for(int i=0;i<5;i++)
        {
            final int d=i;
            switch (d) {
                case 0: {Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (dataBaseUtil) {
                            try {
                                Object[] arrayOfObject = new Object[2];
                                arrayOfObject[0] = "username" + 1;
                                arrayOfObject[1] = "password" + 3;
                                SQLiteDatabase writedb = dataBaseUtil.getWritableDatabase();
                                Log.d("writedb",writedb.toString());
                                Log.d("table", "" + d);
                                writedb.execSQL("insert into User(username,password) values(?,?)", arrayOfObject);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });t.start();}
                case 1:{Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (dataBaseUtil) {
                            try {
                                Object[] arrayOfObject = new Object[2];
                                arrayOfObject[0] = "username" + 2;
                                arrayOfObject[1] = "password" + 4;
                                SQLiteDatabase writedb = dataBaseUtil.getWritableDatabase();
                                Log.d("writedb",writedb.toString());
                                Log.d("table", "" + d);
                                writedb.execSQL("insert into User(username,password) values(?,?)", arrayOfObject);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });t.start();}
                case 2:{Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (dataBaseUtil) {
                                try {
                                    Object[] arrayOfObject = new Object[3];
                                    arrayOfObject[0] = "password" + 5;
                                    arrayOfObject[1] = "realname" + 5;
                                    arrayOfObject[2] = "username" + 1;
                                    SQLiteDatabase writedb = dataBaseUtil.getWritableDatabase();
                                    Log.d("writedb",writedb.toString());
                                    Log.d("table", "" + d);
                                    writedb.execSQL("update User set password=?,realname=?  where username=?", arrayOfObject);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });t.start();}
                case 3:{
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (dataBaseUtil) {
                                try {
                                    Object[] arrayOfObject = new Object[3];
                                    arrayOfObject[0] = "password" + 4;
                                    arrayOfObject[1] = "realname" + 1;
                                    arrayOfObject[2] = "username" + 1;
                                    SQLiteDatabase writedb = dataBaseUtil.getWritableDatabase();
                                    Log.d("writedb",writedb.toString());
                                    Log.d("table", "" + d);
                                    writedb.execSQL("update User set password=?,realname=?  where username=?", arrayOfObject);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });t.start();
                }
            }
        }
       try {
           wait(2000);
       }catch (Exception e)
       {
           e.printStackTrace();
       }
        SQLiteDatabase readdb = dataBaseUtil.getWritableDatabase();
        //Log.d("tablesearch", );
        Cursor localCursor = readdb.rawQuery("select password,realname from User" +
                " where username=?" , new String[]{"username1"});
        localCursor.moveToFirst();
        Log.d("password", localCursor.getString(0));
        Log.d("realname", localCursor.getString(1));
       //boolean d= dataBaseUtil.deleteDatabase(this);
        //;Log.d("databaseDeleteResult:",""+d);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        else if (keyCode == KeyEvent.KEYCODE_BACK && !webView.canGoBack()){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private class AndroidBridge //这个类中提供各种js可调用的方法。
    {
        @JavascriptInterface
        public void callAndroid(final String arg)
        {
            handler.post(new Runnable() {
                public void run() {
                    Log.d(TAG, "calAndroid(" + arg + ")");
                    //textView.setText(arg);
                    Toast.makeText(testActivity.this, arg, Toast.LENGTH_LONG).show();
                    webView.loadUrl("javascript:callJS('hello from android')");
                }
            });
        }
    }
}
