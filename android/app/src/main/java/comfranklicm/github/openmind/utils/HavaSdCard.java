package comfranklicm.github.openmind.utils;

import android.os.Environment;

/**
 * Created by Administrator on 9/10/2016.
 */
public class HavaSdCard {
    /**
     *
     * @return
     * true表示 有sdcard false表示没有sdcard
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
