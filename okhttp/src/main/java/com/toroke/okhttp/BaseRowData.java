package com.toroke.okhttp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/3/11.
 */

public class BaseRowData<T extends Serializable> implements Serializable{

    private List<T> rows;
    private int total;
    private int pageSize;

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

    public int getPageSize() {
        if (rows !=null){
            float pageSize=1.0f*total/rows.size();
            return (int) Math.ceil(pageSize);
        }
        return 0;
    }
}
