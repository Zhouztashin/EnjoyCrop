package com.zhouztashin.android.enjoycrop.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zhouztashin.android.enjoycrop.EnjoyCropLayout;
import com.zhouztashin.android.enjoycrop.R;
import com.zhouztashin.android.enjoycrop.core.BaseLayerView;
import com.zhouztashin.android.enjoycrop.core.clippath.ClipPathCircle;
import com.zhouztashin.android.enjoycrop.core.clippath.ClipPathLayerView;
import com.zhouztashin.android.enjoycrop.core.clippath.ClipPathSquare;
import com.zhouztashin.android.enjoycrop.core.mask.ColorMask;

/**
 * Created by Administrator on 2016/3/15.
 */
public class MainActivity extends Activity {

    EnjoyCropLayout enjoyCropLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        enjoyCropLayout = (EnjoyCropLayout) findViewById(R.id.ll);
        enjoyCropLayout.setImageResource(R.drawable.test2);
        enjoyCropLayout.setDefaultSquareCrop(600);
      //  enjoyCropLayout.setRestrict(true);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enjoyCropLayout.crop();
            }
        });
        //ImageView iv = (ImageView) findViewById(R.id.iv);
        //ImageView.ScaleType scaleType = ImageView.ScaleType.valueOf("");
       // iv.setScaleType(scaleType);
    }
}
