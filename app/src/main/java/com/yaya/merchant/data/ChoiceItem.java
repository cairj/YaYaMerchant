package com.yaya.merchant.data;

/**
 * 选项列表元素
 */

public class ChoiceItem {

    private String content;//显示的内容
    private String id;
    private boolean isSelect;

    public ChoiceItem(String content, String id) {
        this.content = content;
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getContent() {
        return content;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
