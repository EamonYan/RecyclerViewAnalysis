package cn.npe1348.recyclerview.comadapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    private View getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null){
            view = itemView.findViewById(viewId);
        }
        return view;
    }

    /**
     * 这里面返回this是为了我们可以进行链式调用
    * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId,String text){
        View view = getView(viewId);
        if (null != view && view instanceof TextView){
            ((TextView) view).setText(text);
        }
        return this;
    }

    public ViewHolder setImageResource(int viewId,int imageRes){
        View view = getView(viewId);
        if (null != view && view instanceof ImageView){
            ((ImageView) view).setImageResource(imageRes);
        }
        return this;
    }

    public ViewHolder setImageByUrl(int viewId,ImageLoader imageLoader){
        View view = getView(viewId);
        if (null != view && view instanceof ImageView){
            imageLoader.displayImage((ImageView)view);
        }
        return this;
    }

    public ViewHolder setVisibility(int viewId,int visibility){
        getView(viewId).setVisibility(visibility);
        return this;
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener,final int position){
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    public void setOnItemLongClickListener(final OnItemLongClickListener onItemLongClickListener,final int position){
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return onItemLongClickListener.onItemLongClick(position);
            }
        });
    }

    public static interface OnItemClickListener{
        public void onItemClick(int position);
    }

    public static interface OnItemLongClickListener{
        public boolean onItemLongClick(int position);
    }


    public  static abstract class ImageLoader {
        private String url;
        public ImageLoader(String url){
            this.url = url;
        }

        public String getUrl(){
            return this.url;
        }
        public abstract void displayImage(ImageView imageView);
    }
}
