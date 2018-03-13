package com.yaya.merchant.util.imageloader;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.EmptySignature;
import com.yaya.merchant.R;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by 魏新智 on 2016/9/19.
 */
public class GlideLoaderHelper {

    public static void init() {
    }

    public static RequestOptions buildDefaultOptions() {
        return buildDefaultOptions(R.mipmap.tab_ic_my_nor);
    }

    public static RequestOptions buildDefaultOptions(int defaultImgId) {
        return buildDefaultOptions(defaultImgId, false);
    }

    //hasFitCenter为true，占满
    public static RequestOptions buildDefaultOptions(int defaultImgId, boolean hasFitCenter) {
        RequestOptions options = new RequestOptions();
        options
                .placeholder(defaultImgId)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        if (hasFitCenter) {
            options.fitCenter();
        } else {
            options.centerCrop();
        }

        return options;
    }

    //加载需要占满的图片
    public static void loadFitImg(String imgUrl, ImageView imgView) {
        loadImg(imgUrl, imgView, R.mipmap.tab_ic_my_nor, true);
    }

    public static void loadImg(String imgUrl, ImageView imgView) {
        loadImg(imgUrl, imgView, R.mipmap.tab_ic_my_nor);
    }

    public static void loadAvatar(String imgUrl, ImageView imgView) {
        loadCircleImg(imgUrl, imgView, R.mipmap.tab_ic_my_nor);
    }

    public static void loadImg(String imgUrl, ImageView imgView, int defaultImgId) {
        if (hasStopLoadContinue(imgView)) {
            return;
        }
        Glide.with(imgView.getContext())
                .load(imgUrl)
                .apply(buildDefaultOptions(defaultImgId))
                .into(imgView);//显示gif动态图片
    }

    public static void loadImgAdjustBound(String imgUrl, ImageView imgView) {
        loadImg(imgUrl, imgView, R.mipmap.tab_ic_my_nor, true);
    }

    public static void loadImg(String imgUrl, ImageView imgView, int defaultImgId, boolean hasFitCenter) {
        if (hasStopLoadContinue(imgView)) {
            return;
        }
        Glide.with(imgView.getContext())
                .load(imgUrl)
                .apply(buildDefaultOptions(defaultImgId, hasFitCenter))
                .into(imgView);
    }

    public static void loadImg(String imgUrl, ImageView imgView, int defaultImgId, RequestListener listener) {
        if (hasStopLoadContinue(imgView)) {
            return;
        }
        Glide.with(imgView.getContext())
                .load(imgUrl)
                .listener(listener)
                .apply(buildDefaultOptions(defaultImgId))
                .into(imgView);//显示gif动态图片
    }

    public static void loadBlurImg(String imgUrl, ImageView imgView, int defaultImgId) {
        loadBlurImg(imgUrl, imgView, defaultImgId, 25);
    }

    public static void loadBlurImg(String imgUrl, ImageView imgView, int defaultImgId, int radius) {
        if (hasStopLoadContinue(imgView)) {
            return;
        }
        RequestOptions options = buildDefaultOptions(defaultImgId);
        options.transform(new MultiTransformation<Bitmap>(new CenterCrop(), new BlurTransformation(radius)));
        Glide.with(imgView.getContext())
                .load(imgUrl)
                .apply(options)
                .into(imgView);
    }

    public static void loadRoundedImg(String imgUrl, ImageView imgView, int defaultImgId){
        loadRoundedImg(imgUrl,imgView,defaultImgId,25);
    }

    public static void loadRoundedImg(String imgUrl, ImageView imgView, int defaultImgId, int radius){
        if (hasStopLoadContinue(imgView)) {
            return;
        }
        RequestOptions options = buildDefaultOptions(defaultImgId);
        options.transform(new MultiTransformation<Bitmap>(new CenterCrop(), new RoundedCornersTransformation(radius,0)));
        Glide.with(imgView.getContext())
                .load(imgUrl)
                .apply(options)
                .into(imgView);
    }

    public static void loadCircleImg(String imgUrl, ImageView imgView, int defaultImgId){
        if (hasStopLoadContinue(imgView)) {
            return;
        }
        RequestOptions options = buildDefaultOptions(defaultImgId);
        options.transform(new MultiTransformation<Bitmap>(new CenterCrop(), new CircleCrop()));
        Glide.with(imgView.getContext())
                .load(imgUrl)
                .apply(options)
                .into(imgView);
    }

    //判断url是否被缓存了
    private static boolean isLoadCacheImage(Context context, String url) {
        // 寻找缓存图片
        try {
            File file = DiskLruCacheWrapper.get(Glide.getPhotoCacheDir(context), 250 * 1024 * 1024)
                    .get(new OriginalKey(url, EmptySignature.obtain()));
            if (file != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //如果imgView为空，或者有界面被finish了，则返回true,不继续加载图片了
    public static boolean hasStopLoadContinue(ImageView imgView) {
        return imgView == null || imgView.getContext() == null ||
                //判断当前ImageView对应的Activity是否被销毁
                (imgView.getContext() instanceof Activity
                        && ((Activity) imgView.getContext()).isFinishing()) ||
                (imgView.getContext() instanceof ContextWrapper
                        && ((ContextWrapper) imgView.getContext()).getBaseContext() != null
                        && ((ContextWrapper) imgView.getContext()).getBaseContext() instanceof Activity
                        && ((Activity) ((ContextWrapper) imgView.getContext()).getBaseContext()).isFinishing());
    }

    //加载图片时，不缓存图片
    public static void loadNoCacheImg(String imgUrl, ImageView imgView, int defaultImgId, boolean hasFitCenter) {
        if (hasStopLoadContinue(imgView)) {
            return;
        }
        RequestOptions options = buildDefaultOptions(defaultImgId, hasFitCenter);
        options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(imgView.getContext())
                .load(imgUrl)
                .apply(options)
                .into(imgView);
    }

    public static void loadImg(ImageView imgView, File file, int defaultImgId) {
        if (hasStopLoadContinue(imgView)) {
            return;
        }
        Glide.with(imgView.getContext())
                .load(file)
                .apply(buildDefaultOptions(defaultImgId))
                .into(imgView);
    }

    /**
     * Glide原图缓存Key
     */
    private static class OriginalKey implements Key {

        private final String id;
        private final Key signature;

        private OriginalKey(String id, Key signature) {
            this.id = id;
            this.signature = signature;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            if (id == null || signature == null) {
                return false;
            }

            OriginalKey that = (OriginalKey) o;

            return id.equals(that.id) && signature.equals(that.signature);
        }

        @Override
        public int hashCode() {
            if (id == null) {
                return 0;
            }
            int result = id.hashCode();
            result = 31 * result + signature.hashCode();
            return result;
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {
            if (id == null) {
                return;
            }
            try {
                messageDigest.update(id.getBytes(STRING_CHARSET_NAME));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            signature.updateDiskCacheKey(messageDigest);
        }
    }

}
