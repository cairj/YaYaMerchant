package com.toroke.okhttp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/3/11.
 */

public class BaseRowData<T extends Serializable> implements Serializable{

    private List<T> data;
    private int total;

    public List<T> getRows() {
        return data;
    }

    public void setRows(List<T> rows) {
        this.data = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageCount(int pageSize) {
        if (data !=null){
            float pageCount=1.0f*total/pageSize;
            return (int) Math.ceil(pageCount);
        }
        return 0;
    }
}
