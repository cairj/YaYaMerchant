package com.yaya.merchant.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.yaya.merchant.util.TagAliasOperatorHelper.ACTION_CLEAN;
import static com.yaya.merchant.util.TagAliasOperatorHelper.ACTION_DELETE;
import static com.yaya.merchant.util.TagAliasOperatorHelper.ACTION_SET;
import static com.yaya.merchant.util.TagAliasOperatorHelper.sequence;

/**
 * Created by admin on 2018/3/18.
 */

public class JPushUtil {
    //设置别名和标签
    public static void setAliasAndTag(Context context,String alias, String tags){
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_SET;
        sequence++;
        tagAliasBean.alias = getAlias(alias);
        tagAliasBean.tags = getTags(tags);
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(context,sequence,tagAliasBean);
    }

    //删除别名
    public static void deleteAlias(Context context){
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_DELETE;
        sequence++;
        tagAliasBean.alias = " ";
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(context,sequence,tagAliasBean);
    }

    //清空标签
    public static void cleanTags(Context context){
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_CLEAN;
        sequence++;
        tagAliasBean.tags = getTags("a");
        tagAliasBean.isAliasAction = false;
        TagAliasOperatorHelper.getInstance().handleAction(context,sequence,tagAliasBean);
    }

    /**
     * 获取输入的alias
     * */
    private static String getAlias(String alias){
        if (TextUtils.isEmpty(alias)) {
            ToastUtil.toast("别名不能为空");
            return null;
        }
        if (!StringsUtil.isValidTagAndAlias(alias)) {
            ToastUtil.toast("别名不符合");
            return null;
        }
        return alias;
    }
    /**
     * 获取输入的tags
     * */
    private static Set<String> getTags(String tag){
        // 检查 tag 的有效性
        if (TextUtils.isEmpty(tag)) {
            ToastUtil.toast("标签不能为空");
            return null;
        }

        // ","隔开的多个 转换成 Set
        String[] sArray = tag.split(",");
        Set<String> tagSet = new LinkedHashSet<String>();
        for (String sTagItme : sArray) {
            if (!StringsUtil.isValidTagAndAlias(sTagItme)) {
                ToastUtil.toast("标签不符合");
                return null;
            }
            tagSet.add(sTagItme);
        }
        if(tagSet.isEmpty()){
            ToastUtil.toast("标签不能为空");
            return null;
        }
        return tagSet;
    }

}
