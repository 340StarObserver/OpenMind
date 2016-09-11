package comfranklicm.github.openmind;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import comfranklicm.github.openmind.utils.User;
import me.relex.photodraweeview.PhotoDraweeView;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/6
 */
public class PictureViewActivity extends Activity {
    PhotoDraweeView mPhotoDraweeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_view);
        mPhotoDraweeView = (PhotoDraweeView) findViewById(R.id.image);
        Uri uri = Uri.parse(User.getInstance().getFileUrl());
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(uri);
        controller.setOldController(mPhotoDraweeView.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null || mPhotoDraweeView == null) {
                    return;
                }
                mPhotoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        controller.setAutoPlayAnimations(true);
        mPhotoDraweeView.setController(controller.build());
    }
}
