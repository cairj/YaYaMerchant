package com.yaya.merchant.util.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.toroke.qiguanbang.R;
import com.toroke.qiguanbang.common.Constants;
import com.toroke.qiguanbang.entity.member.Member;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by 魏新智 on 2015/1/21.
 */
public class ImageLoaderHelper {

    public static void init(Context context) {
    }

    //加载头像（缺省头像为灰色）
    public static void loadAvatar(String imageUrl, ImageView imageView) {
        GlideLoaderHelper.loadAvatar(imageUrl, imageView);
    }

    //加载头像（缺省头像为灰色）
    public static void loadAvatar(String imageUrl, String gender, ImageView imageView) {
        if (GlideLoaderHelper.hasStopLoadContinue(imageView)) {
            return;
        }
        GlideLoaderHelper.loadAvatar(imageUrl, imageView);
        if (imageView != null && imageView instanceof CircleImageView) {
            if ("男".equals(gender)) {
                ((CircleImageView) imageView).setBorderColor(imageView.getResources().getColor(R.color.avatar_stroke_male));
            } else if ("女".equals(gender)) {
                ((CircleImageView) imageView).setBorderColor(imageView.getResources().getColor(R.color.avatar_stroke_female));
            }
        }
    }

    //加载头像（缺省头像为灰色）
    public static void loadAvatar(Member member, ImageView imageView) {
        GlideLoaderHelper.loadAvatar(member.getAvatar(), imageView);
        if (imageView != null && imageView instanceof CircleImageView) {
            if ("男".equals(member.getGender())) {
                ((CircleImageView) imageView).setBorderColor(imageView.getResources().getColor(R.color.avatar_stroke_male));
            } else {
                ((CircleImageView) imageView).setBorderColor(imageView.getResources().getColor(R.color.avatar_stroke_female));
            }
        }
    }

    //加载头像（缺省头像为灰色）
    public static void loadAvatar(Member member, ImageView imageView, int defaultResId) {
        GlideLoaderHelper.loadImg(member.getAvatar(), imageView, defaultResId, Constants.FALSE);
        if (imageView != null && imageView instanceof CircleImageView) {
            if ("男".equals(member.getGender())) {
                ((CircleImageView) imageView).setBorderColor(imageView.getResources().getColor(R.color.avatar_stroke_male));
            } else {
                ((CircleImageView) imageView).setBorderColor(imageView.getResources().getColor(R.color.avatar_stroke_female));
            }
        }
    }

    public static void loadFeedPhoto(String imageUrl, final ImageView imageView) {
        if (GlideLoaderHelper.hasStopLoadContinue(imageView)) {
            return;
        }
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setVisibility(View.GONE);
            return;
        }
        GlideLoaderHelper.loadImg(imageUrl, imageView, R.drawable.default_large_img);
    }

    /**
     * 加载小图
     *
     * @param imageUrl
     * @param imageView
     */
    public static void loadMiniCover(String imageUrl, final ImageView imageView) {
        if (GlideLoaderHelper.hasStopLoadContinue(imageView)) {
            return;
        }
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageResource(R.drawable.default_mini_img);
            return;
        }
        GlideLoaderHelper.loadImg(imageUrl, imageView, R.drawable.default_mini_img);
    }

    public static void loadRoundCornerImg(String imageUrl, final ImageView imageView) {
        if (GlideLoaderHelper.hasStopLoadContinue(imageView)) {
            return;
        }
        if (GlideLoaderHelper.loadDefault(R.drawable.default_img, imageUrl, imageView)) {
            return;
        }
        RequestOptions options = GlideLoaderHelper.buildDefaultOptions();
        options.transform(new MultiTransformation<Bitmap>(new CenterCrop(),
                new RoundedCornersTransformation(imageView.getContext().getResources().getDimensionPixelSize(R.dimen.card_radius), 0)));
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(options)
                .into(imageView);
    }

    //加载大图
    public static void loadLargeRoundCoverImg(String imageUrl, final ImageView imageView) {
        imageView.getLayoutParams().height = DeviceParamsHelper.getLargeCoverHeight();
        loadRoundCornerImg(imageUrl, imageView, R.drawable.default_large_img);
    }

    public static void loadRoundCornerImg(String imageUrl, final ImageView imageView, int defaultImgId) {
        if (GlideLoaderHelper.hasStopLoadContinue(imageView)) {
            return;
        }
        if (GlideLoaderHelper.loadDefault(defaultImgId, imageUrl, imageView)) {
            return;
        }
        RequestOptions options = GlideLoaderHelper.buildDefaultOptions(defaultImgId);
        options.transform(new MultiTransformation<Bitmap>(new CenterCrop(),
                new RoundedCornersTransformation(imageView.getContext().getResources().getDimensionPixelSize(R.dimen.card_radius), 0)));
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(options)
                .into(imageView);
    }

    public static void loadRoundCornerImgFitCenter(String imageUrl, final ImageView imageView) {
        loadRoundCornerImgFitCenter(imageUrl, imageView, true);
    }

    public static void loadRoundCornerImgFitCenter(String imageUrl, final ImageView imageView, boolean saveDataTraffic) {
        if (GlideLoaderHelper.hasStopLoadContinue(imageView)) {
            return;
        }
        if (saveDataTraffic/*需要省流量*/ && GlideLoaderHelper.loadDefault(R.drawable.default_img, imageUrl, imageView)) {
            return;
        }
        RequestOptions options = GlideLoaderHelper.buildDefaultOptions();
        options.transform(new MultiTransformation<Bitmap>(new CenterCrop(),
                new RoundedCornersTransformation(imageView.getContext().getResources().getDimensionPixelSize(R.dimen.card_radius), 0)));

        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载大图
     *
     * @param imageUrl
     * @param imageView
     */
    public static void loadCover(String imageUrl, final ImageView imageView) {
        if (GlideLoaderHelper.hasStopLoadContinue(imageView)) {
            return;
        }
        GlideLoaderHelper.loadImg(imageUrl, imageView, R.drawable.default_large_img);
    }

    public static void loadResImg(int resId, ImageView imageView) {
        if (imageView == null || imageView.getContext() == null) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(resId)
//                .apply(GlideLoaderHelper.buildDefaultOptions())
                .into(imageView);
    }
}
