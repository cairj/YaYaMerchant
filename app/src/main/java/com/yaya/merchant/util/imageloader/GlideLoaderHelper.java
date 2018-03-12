package com.yaya.merchant.util.imageloader;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.EmptySignature;
import com.luck.picture.lib.widget.longimage.ImageSource;
import com.luck.picture.lib.widget.longimage.ImageViewState;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.toroke.qiguanbang.R;
import com.toroke.qiguanbang.common.Constants;
import com.toroke.qiguanbang.common.EventBusTag;
import com.toroke.qiguanbang.net.ConnectionWatcher;
import com.toroke.qiguanbang.util.EncodeBase64;
import com.toroke.qiguanbang.util.FileLoaderUtil;
import com.toroke.qiguanbang.util.LogHelper;
import com.toroke.qiguanbang.util.ToastHelper;
import com.toroke.qiguanbang.util.sp.SPKeys;
import com.toroke.qiguanbang.util.sp.SPUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.simple.eventbus.EventBus;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Response;

/**
 * Created by 魏新智 on 2016/9/19.
 */
public class GlideLoaderHelper {

    public static void init() {

    }

    public static RequestOptions buildDefaultOptions() {
        return buildDefaultOptions(R.drawable.default_img);
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
        loadImg(imgUrl, imgView, R.drawable.default_img, true);
    }

    //加载需要占满的图片
    public static void loadFitImg(String imgUrl, ImageView imgView, boolean saveDataTraffic) {
        loadImg(imgUrl, imgView, R.drawable.default_img, true,saveDataTraffic);
    }

    public static void loadImg(String imgUrl, ImageView imgView) {
        loadImg(imgUrl, imgView, R.drawable.default_img);
    }

    public static void loadAvatar(String imgUrl, ImageView imgView) {
        loadImg(imgUrl, imgView, R.drawable.default_img, Constants.FALSE);
    }

    public static void loadImg(String imgUrl, ImageView imgView, int defaultImgId) {
        loadImg(imgUrl, imgView, defaultImgId, Constants.TRUE);
    }

    public static void loadImg(String imgUrl, ImageView imgView, int defaultImgId, int saveDataTraffic/*是否要判断省流量模式*/) {
        if (hasStopLoadContinue(imgView)) {
            return;
        }
        if (saveDataTraffic == Constants.TRUE) {
            if (loadDefault(defaultImgId, imgUrl, imgView)) {
                return;
            }
        }
        Glide.with(imgView.getContext())
                .load(imgUrl)
                .apply(buildDefaultOptions(defaultImgId))
                .into(imgView);//显示gif动态图片
    }

    public static void loadImg(String imgUrl, ImageView imgView, int defaultImgId, boolean hasFitCenter) {
        loadImg(imgUrl, imgView, defaultImgId, hasFitCenter, true);
    }

    public static void loadImgAdjustBound(String imgUrl, ImageView imgView) {
        loadImg(imgUrl, imgView, R.drawable.default_rect_img, true);
    }

    public static void loadImg(String imgUrl, ImageView imgView, int defaultImgId, boolean hasFitCenter, boolean saveDataTraffic) {
        if (hasStopLoadContinue(imgView)) {
            return;
        }
        if (saveDataTraffic) {
            if (loadDefault(defaultImgId, imgUrl, imgView)) {
                return;
            }
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

    public static void loadBlurImg(String imgUrl, ImageView imgView) {
        loadBlurImg(imgUrl, imgView, true);
    }

    public static void loadBlurImg(String imgUrl, ImageView imgView, boolean saveDataTraffic) {
        if (hasStopLoadContinue(imgView)) {
            return;
        }
        if (saveDataTraffic) {
            if (loadDefault(R.drawable.default_large_img, imgUrl, imgView)) {
                return;
            }
        }
        RequestOptions options = buildDefaultOptions(R.drawable.default_large_img);
        options.transform(new MultiTransformation<Bitmap>(new CenterCrop(), new BlurTransformation(25)));
        Glide.with(imgView.getContext())
                .load(imgUrl)
                .apply(options)
                .into(imgView);
    }

    public static void loadMiniImg(String imageUrl, ImageView imageView) {
        loadImg(imageUrl, imageView, R.drawable.default_mini_img);
    }

    //在非wifi情况下，若开启了无图模式，则直接设置为默认图，并且返回true
    public static boolean loadDefault(int defaultImgId, String url, ImageView imgView) {
        if (ConnectionWatcher.getConnectedType(imgView.getContext()) /*TODO !=*/ != ConnectivityManager.TYPE_WIFI //非wifi情况下
                && SPUtil.getBoolean(SPKeys.BOOLEAN_NO_PIC_MODEL) && !isLoadCacheImage(imgView.getContext(), url)) {
            imgView.setImageResource(defaultImgId);
            return true;
        }
        return false;
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
        }catch (Exception e){
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
    public static void loadNoCacheImg(String imgUrl, ImageView imgView, int defaultImgId, boolean hasFitCenter, boolean saveDataTraffic) {
        if (hasStopLoadContinue(imgView)) {
            return;
        }
        if (saveDataTraffic) {
            if (loadDefault(defaultImgId, imgUrl, imgView)) {
                return;
            }
        }
        RequestOptions options=buildDefaultOptions(defaultImgId, hasFitCenter);
        options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(imgView.getContext())
                .load(imgUrl)
                .apply(options)
                .into(imgView);
    }

    /**
     * 加载详情页大图在ViewPager显示
     *
     * @param imgUrl
     * @param imgView
     */
    public static void loadDetailImg(final String imgUrl, final ImageView imgView, final SubsamplingScaleImageView longImg) {
        if (hasStopLoadContinue(imgView)) {
            return;
        }

        final RequestOptions options = buildDefaultOptions(R.drawable.default_large_img);
        options.fitCenter();

        Glide.with(imgView.getContext()).asBitmap().load(imgUrl).apply(options).into(new SimpleTarget<Bitmap>(480, 800) {
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                int imgHeight = resource.getHeight();
                int imgWidth = resource.getWidth();

                if (imgHeight > imgWidth * 3) {//如果高大于3倍宽，则加载长图

                    longImg.setVisibility(View.VISIBLE);
                    imgView.setVisibility(View.GONE);

                    Glide.with(imgView).load(imgUrl).downloadOnly(new SimpleTarget<File>() {
                        @Override
                        public void onResourceReady(File resource, Transition<? super File> transition) {
                            longImg.setQuickScaleEnabled(true);
                            longImg.setZoomEnabled(true);
                            longImg.setPanEnabled(true);
                            longImg.setDoubleTapZoomDuration(100);
                            longImg.setMinimumScaleType(2);
                            longImg.setDoubleTapZoomDpi(2);

                            String uri = resource.getAbsolutePath();
                            longImg.setImage(ImageSource.uri(uri), new ImageViewState(0.0F, new PointF(0.0F, 0.0F), 0));
//                            longImg.setImage(ImageSource.uri("file:///data/data/com.toroke.qiguanbang/cache/image_manager_disk_cache/856827bbce59998e71cd34562c4d802f3c7d0ca535d7846cfd18f3b03ac9f62d.0"));
//                            longImg.setImage(ImageSource.uri("file:///data/user/0/com.toroke.qiguanbang/cache/image_manager_disk_cache/856827bbce59998e71cd34562c4d802f3c7d0ca535d7846cfd18f3b03ac9f62d.0"));
                        }
                    });
                } else {

                    longImg.setVisibility(View.GONE);
                    imgView.setVisibility(View.VISIBLE);

                    options.centerInside();
                    Glide.with(imgView.getContext())
                            .load(imgUrl)
                            .apply(options)
                            .into(imgView);
                }

            }
        });
    }

    //加载adjustViewBound为true的图片
    public static void loadImgAdjustBounds(final String imgUrl, final ImageView imgView) {
        loadImg(imgUrl, imgView, R.drawable.default_img, true, true);
        if (hasStopLoadContinue(imgView)) {
            return;
        }
        if (loadDefault(R.drawable.default_img, imgUrl, imgView)) {
            return;
        }
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.default_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter();
        Glide.with(imgView.getContext())
                .load(imgUrl)
//                .apply(buildDefaultOptions(defaultImgId,hasFitCenter))
                .into(imgView);
    }

    public static void loadEmojiIcon(ImageView imgView, int resId) {
        if (hasStopLoadContinue(imgView)) {
            return;
        }
        RequestOptions options = buildDefaultOptions(R.drawable.default_img, true);
        Glide.with(imgView.getContext())
                .load(resId)
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

    //下载图片
    public static void downloadImg(Context context, final String url, final String destDir) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = OkHttpUtils.get().url(url).build().execute();
                    if (response.isSuccessful()) {
                        File dir1 = new File(destDir);
                        if (!dir1.exists()) {
                            dir1.mkdirs();
                        }
                        String filename = destDir + File.separator + FileLoaderUtil.getFileName(url);
                        LogHelper.e("------onResponse-----===" + filename);
                        //这里就不要用openFileOutput了,那个是往手机内存中写数据的
                        FileOutputStream output = null;
                        try {
                            byte[] resource = response.body().bytes();
                            output = new FileOutputStream(filename);
                            output.write(resource);//将bytes写入到输出流中
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (output != null) {
                                try {
                                    output.close();//关闭输出流
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            LogHelper.e("------finally-----===" + filename);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //下载图片，并加入图库
    public static void downloadImg(final Context context, final String url, final String destDir, final String fileName, final boolean insertImage, final boolean showDialog) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (showDialog) {
                        EventBus.getDefault().post("", EventBusTag.SHOW_LOADING_DIALOG);
                    } else {
                        ToastHelper.show("保存图片中......");
                    }
                    Response response = OkHttpUtils.get().url(url).build().execute();
                    if (response.isSuccessful()) {
                        File dir1 = new File(destDir);
                        if (!dir1.exists()) {
                            dir1.mkdirs();
                        }
                        //String filePath = destDir + File.separator + fileName;
                        File file = new File(destDir, fileName);
                        //这里就不要用openFileOutput了,那个是往手机内存中写数据的
                        FileOutputStream output = null;
                        try {
                            byte[] resource = response.body().bytes();
                            output = new FileOutputStream(file);
                            output.write(resource);//将bytes写入到输出流中
                            if (insertImage) {
                                // 其次把文件插入到系统图库
                                MediaStore.Images.Media.insertImage(context.getContentResolver(),
                                        file.getAbsolutePath(), fileName, null);
                                //保存图片后发送广播通知更新数据库
                                Uri uri = Uri.fromFile(file);
                                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                            }
                            if (showDialog) {
                                EventBus.getDefault().post("保存成功", EventBusTag.HIDE_LOADING_DIALOG);
                            } else {
                                ToastHelper.show("保存成功");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            if (showDialog) {
                                EventBus.getDefault().post("保存失败", EventBusTag.HIDE_LOADING_DIALOG);
                            } else {
                                ToastHelper.show("保存失败");
                            }
                        } finally {
                            if (output != null) {
                                try {
                                    output.close();//关闭输出流
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (showDialog) {
                        EventBus.getDefault().post("保存失败", EventBusTag.HIDE_LOADING_DIALOG);
                    } else {
                        ToastHelper.show("保存失败");
                    }
                }
            }
        }).start();
    }

    //下载资源图片，并加入图库
    public static void downloadImg(final Context context, final int resId, final String destDir, final String fileName, final boolean insertImage, final boolean showDialog) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (showDialog) {
                    EventBus.getDefault().post("", EventBusTag.SHOW_LOADING_DIALOG);
                } else {
                    ToastHelper.show("保存图片中......");
                }
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
                File dir1 = new File(destDir);
                if (!dir1.exists()) {
                    dir1.mkdirs();
                }
                File file = new File(destDir, fileName);
                //这里就不要用openFileOutput了,那个是往手机内存中写数据的
                FileOutputStream output = null;
                try {
                    output = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                    if (insertImage) {
                        // 其次把文件插入到系统图库
                        MediaStore.Images.Media.insertImage(context.getContentResolver(),
                                file.getAbsolutePath(), fileName, null);
                        //保存图片后发送广播通知更新数据库
                        Uri uri = Uri.fromFile(file);
                        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                    }
                    if (showDialog) {
                        EventBus.getDefault().post("保存成功", EventBusTag.HIDE_LOADING_DIALOG);
                    } else {
                        ToastHelper.show("保存成功");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (showDialog) {
                        EventBus.getDefault().post("保存失败", EventBusTag.HIDE_LOADING_DIALOG);
                    } else {
                        ToastHelper.show("保存失败");
                    }
                } finally {
                    if (output != null) {
                        try {
                            output.close();//关闭输出流
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    //下载图片，并加入图库
    public static void downloadBase64Img(final Context context, final String url, final String destDir, final String fileName, final boolean insertImage, final boolean showDialog) {
        if (showDialog) {
            EventBus.getDefault().post("", EventBusTag.SHOW_LOADING_DIALOG);
        }
        File dir1 = new File(destDir);
        if (!dir1.exists()) {
            dir1.mkdirs();
        }
        String filename = destDir + File.separator + fileName;
        //这里就不要用openFileOutput了,那个是往手机内存中写数据的
        FileOutputStream output = null;
        InputStream input = null;
        try {
            LogHelper.e("开始保存");
            byte[] resource = EncodeBase64.decode(url.split(",", 2)[1]);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            input = new ByteArrayInputStream(resource);
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, options);
            output = new FileOutputStream(filename);
            //output.write(resource);//将bytes写入到输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            LogHelper.e("保存成功");
            if (insertImage) {
                // 其次把文件插入到系统图库
                MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        filename, fileName, null);
                //保存图片后发送广播通知更新数据库
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filename)));
            }
            if (showDialog) {
                EventBus.getDefault().post("保存成功", EventBusTag.HIDE_LOADING_DIALOG);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (showDialog) {
                EventBus.getDefault().post("保存失败", EventBusTag.HIDE_LOADING_DIALOG);
            }
            LogHelper.e("异常：" + e.getMessage());
        } finally {
            if (output != null) {
                try {
                    output.close();//关闭输出流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (showDialog) {
                EventBus.getDefault().post("", EventBusTag.HIDE_LOADING_DIALOG);
            }
        }
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
