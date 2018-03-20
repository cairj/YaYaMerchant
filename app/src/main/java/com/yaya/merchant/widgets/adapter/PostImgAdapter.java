package com.yaya.merchant.widgets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.luck.picture.lib.entity.LocalMedia;
import com.yaya.merchant.R;
import com.yaya.merchant.util.imageloader.GlideLoaderHelper;

import java.util.List;

/**
 * 选择图片适配器
 */
public class PostImgAdapter extends BaseAdapter {

    private List<LocalMedia> mMediaList;
    private int MAX_COUNT = 3;//最大显示数量

    private Context context;
    private LayoutInflater inflater;

    public PostImgAdapter(Context context, List<LocalMedia> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mMediaList = data;
    }

    @Override
    public int getCount() {
        if (mMediaList == null || mMediaList.isEmpty()) {
            return 1;
        }
        if (mMediaList.size() == MAX_COUNT) {
            return MAX_COUNT;
        }
        return mMediaList.size() + 1;
    }

    @Override
    public LocalMedia getItem(int position) {
        if (mMediaList.isEmpty() && position == 0) {
            return null;
        }
        if (!mMediaList.isEmpty() && mMediaList.size() < MAX_COUNT && position < getCount() - 1) {
            return mMediaList.get(position);
        }
        if (mMediaList.size() == MAX_COUNT) {
            return mMediaList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostFeedImgHolder holder;
        if (convertView == null) {
            holder = new PostFeedImgHolder();
            convertView = inflater.inflate(R.layout.item_img_selected, parent, false);
            holder.photoImg = (ImageView) convertView.findViewById(R.id.cover_img);
            holder.defaultImg = (ImageView) convertView.findViewById(R.id.default_img);
            holder.cancelBtn = (ImageButton) convertView.findViewById(R.id.cancel_btn);
            convertView.setTag(holder);
        } else {
            holder = (PostFeedImgHolder) convertView.getTag();
        }

        final LocalMedia media = getItem(position);
        if (media == null) {
            holder.defaultImg.setVisibility(View.VISIBLE);
            holder.cancelBtn.setVisibility(View.GONE);
            holder.photoImg.setVisibility(View.GONE);
        } else {
            holder.defaultImg.setVisibility(View.INVISIBLE);
            holder.cancelBtn.setVisibility(View.VISIBLE);
            holder.photoImg.setVisibility(View.VISIBLE);
            GlideLoaderHelper.loadRoundedImg(media.getPath(), holder.photoImg, R.mipmap.ic_card);
            holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMediaList.remove(media);
                    notifyDataSetChanged();
                    if (cancellistener != null) {
                        cancellistener.click();
                    }
                }
            });
        }

        holder.defaultImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.addPhoto();
            }
        });

        return convertView;
    }

    public class PostFeedImgHolder {
        public ImageView photoImg;
        public ImageView defaultImg;
        public ImageButton cancelBtn;
        private int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    public interface OnItemClickListener {
        public void addPhoto();
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnCancelClickListener {
        public void click();
    }

    private OnCancelClickListener cancellistener;

    public void setOnCancelClickListener(OnCancelClickListener listener) {
        this.cancellistener = listener;
    }

    public void setMAX_COUNT(int MAX_COUNT) {
        this.MAX_COUNT = MAX_COUNT;
    }

    /*public void resetAddPhotoImg(){
        if (mMediaList.size() < 9){
            get
        }
    }*/
}
