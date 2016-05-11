package com.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.zhouztashin.android.enjoycrop.EnjoyCropLayout;
import com.zhouztashin.android.enjoycrop.R;
import com.zhouztashin.android.enjoycrop.core.BaseLayerView;
import com.zhouztashin.android.enjoycrop.core.clippath.ClipPathCircle;
import com.zhouztashin.android.enjoycrop.core.clippath.ClipPathLayerView;
import com.zhouztashin.android.enjoycrop.core.mask.ColorMask;
import com.zhouztashin.android.enjoycrop.utils.BitmapUtils;
import com.zhouztashin.android.enjoycrop.utils.FileUtils;

import java.io.File;
import java.util.Calendar;


/**
 * Created by Zhouztashin on 2016/3/15.
 */
public class MainActivity extends Activity {

    EnjoyCropLayout enjoyCropLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        enjoyCropLayout = (EnjoyCropLayout) findViewById(R.id.ll);
        enjoyCropLayout.setImageResource(R.drawable.test2);//设置裁剪原图片
        //这里提供了默认的半透明圆形裁剪参数，你也自定义整个裁剪过程的参数，具体参见下面defineCropParams方法。
     //   enjoyCropLayout.setDefaultCircleCrop(300);
        defineCropParams();
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //裁剪图片，注意这里得到的图片并未进行任何压缩，是裁剪出来的原图大小
                Bitmap bitmap = enjoyCropLayout.crop();
                //保存图片
                String folder = Environment.getExternalStorageDirectory().getPath() + "/" + "EnjoyCrop";
                FileUtils.createFolder(folder, FileUtils.MODE_UNCOVER);
                String fileName = folder + File.separator + Calendar.getInstance().getTime().toString() + ".jpg";
                FileUtils.createFile(fileName, FileUtils.MODE_COVER);
                BitmapUtils.saveBitmaps(MainActivity.this, bitmap, new File(fileName));

            }
        });

    }

    private void defineCropParams() {
        //设置裁剪集成视图，这里通过一定的方式集成了遮罩层与预览框
        BaseLayerView layerView = new ClipPathLayerView(this);
        layerView.setMask(ColorMask.getTranslucentMask()); //设置遮罩层,这里使用半透明的遮罩层
        layerView.setShape(new ClipPathCircle(300)); //设置预览框形状
        enjoyCropLayout.setLayerView(layerView); //设置裁剪集成视图
        enjoyCropLayout.setRestrict(true); //设置边界限制，如果设置了该参数，预览框则不会超出图片
    }
}
