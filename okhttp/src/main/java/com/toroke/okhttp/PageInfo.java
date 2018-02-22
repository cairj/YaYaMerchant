package com.toroke.okhttp;

/**
 * Created by 魏新智 on 2017/6/16.
 */
public class PageInfo {

    private int curPos;

    private int pageSize;

    private int total;

    private boolean isLast;//是否已经没有更多数据

    public int getCurPos() {
        return curPos;
    }

    public void setCurPos(int curPos) {
        this.curPos = curPos;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}
