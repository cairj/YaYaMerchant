package com.toroke.okhttp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/3/11.
 */

public class BaseRowData<T extends Serializable> implements Serializable{

    private List<T> rows;
    private int total;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageCount(int pageSize) {
        if (rows !=null){
            float pageCount=1.0f*total/pageSize;
            return (int) Math.ceil(pageCount);
        }
        return 0;
    }
}
