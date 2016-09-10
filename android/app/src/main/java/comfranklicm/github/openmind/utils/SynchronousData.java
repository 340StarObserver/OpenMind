package comfranklicm.github.openmind.utils;

import comfranklicm.github.openmind.Httprequests.HttpPostRunnable;
import comfranklicm.github.openmind.JsonParsing.SynchronousDataJsonParser;

/**
 * Created by Frank on 9/10/2016.
 */
public class SynchronousData {
    public static void Sychronous(){
        HttpPostRunnable runnable=new HttpPostRunnable();
        runnable.setActionId(10);
        runnable.setToken(NetUtil.getInstance().getToken());
        Thread thread=new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ((SynchronousDataJsonParser)User.getInstance().baseJsonParsers.get(9)).SynchronousDataJsonParsing(runnable.getStrResult());
    }
}
