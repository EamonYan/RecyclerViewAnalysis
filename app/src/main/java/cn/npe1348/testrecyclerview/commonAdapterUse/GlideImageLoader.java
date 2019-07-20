package cn.npe1348.testrecyclerview.commonAdapterUse;

import android.widget.ImageView;
import com.bumptech.glide.Glide;

import cn.npe1348.recyclerview.comadapter.ViewHolder;

public class GlideImageLoader extends ViewHolder.ImageLoader {
    public GlideImageLoader(String url) {
        super(url);
    }

    @Override
    public void displayImage(ImageView imageView) {
        Glide.with(imageView.getContext()).load(getUrl()).into(imageView);
    }
}
