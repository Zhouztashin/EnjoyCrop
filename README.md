##概述
这是一个裁剪图片的工具类，你可以通过组合已有预览框、遮罩层（甚至自己实现的预览框、遮罩层）来实现自己独特的裁剪需求。
##功能
1、默认集成了方形、圆形预览框，还可以简单的实现自己的预览框。</br>
2、默认集成了半透明的遮罩层、颜色遮罩层、图片遮罩层，你完全可以自己实现自己所需要的遮罩层</br>
3、带边界限制选择的可缩放、移动的图片视图。</br>
4、裁剪</br>
##实现原理
1、预览框实现：通过Xfermode方式、Canvas.clipPath方式实现镂空遮罩层。</br>
2、缩放&移动实现：通过Matrix&ScaleGestureDetector实现图片缩放与移动。</br>
3、裁剪实现：通过Canvas.drawBitmap实现最终的裁剪。</br>
至于更详细的实现原理可以查看[EnjoyCrop裁剪功能实现](http://blog.csdn.net/zzxzhyt/article/details/51066269) 

##实例
1、在布局文件声明裁剪容器
```
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:background="#000"
    android:layout_height="match_parent">

    <com.zhouztashin.android.enjoycrop.EnjoyCropLayout
        android:id="@+id/ll"
        android:background="#0000"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </com.zhouztashin.android.enjoycrop.EnjoyCropLayout>

</RelativeLayout>
```
2、在硬编码中设置裁剪预览框、遮罩层、裁剪参数和将要裁剪的图片。
```Java

public class MainActivity extends Activity {

    EnjoyCropLayout enjoyCropLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        enjoyCropLayout = (EnjoyCropLayout) findViewById(R.id.ll);
        enjoyCropLayout.setImageResource(R.drawable.test2);//设置裁剪原图片
        //这里提供了默认的半透明圆形裁剪参数，你也自定义整个裁剪过程的参数，具体参见下面defineCropParams方法。
        enjoyCropLayout.setDefaultCircleCrop(300);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
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
    private void defineCropParams(){
     //设置裁剪集成视图，这里通过一定的方式集成了遮罩层与预览框
     BaseLayerView layerView = new ClipPathLayerView(this);
     layerView.setMask(ColorMask.getTranslucentMask()); //设置遮罩层,这里使用半透明的遮罩层
     layerView.setShape(new ClipPathCircle(300)); //设置预览框形状
     enjoyCropLayout.setLayerView(layerView); //设置裁剪集成视图
     enjoyCropLayout.setRestrict(true); //设置边界限制，如果设置了该参数，预览框则不会超出图片
    }
}

```

##支持版本
Android 2.2版本以上(包括2.2)
##引用第三方依赖库
Android Support v4
##鸣谢
~基因*记忆~

