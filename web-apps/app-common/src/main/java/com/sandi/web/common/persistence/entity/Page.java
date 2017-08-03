package com.sandi.web.common.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by dizl on 2015/5/11.
 */
public class Page implements Serializable {

    private int pageNo = 1; // 当前页码
    private int pageSize = -1; // 页面大小，设置为“-1”表示不进行分页（分页无效）
    private long count;// 总记录数，设置为“-1”表示不查询总数

    public Page() {
        this.pageSize = -1;
    }

    /**
     * 构造方法
     *
     * @param pageNo   当前页码
     * @param pageSize 分页大小
     */
    public Page(int pageNo, int pageSize) {
        this(pageNo, pageSize, 0);
    }

    /**
     * 构造方法
     *
     * @param pageNo   当前页码
     * @param pageSize 分页大小
     * @param count    数据条数
     */
    public Page(int pageNo, int pageSize, long count) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.count = count;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
        if (pageSize >= count) {
            pageNo = 1;
        }
    }

    /**
     * 获取  FirstResult
     */
    public int getFirstResult() {
        int firstResult = (getPageNo() - 1) * getPageSize();
//        if (firstResult >= getCount()) {
//            firstResult = 0;
//        }
        return firstResult;
    }

    /**
     * 获取  MaxResults
     */
    public int getMaxResults() {
        return getPageSize();
    }
}
