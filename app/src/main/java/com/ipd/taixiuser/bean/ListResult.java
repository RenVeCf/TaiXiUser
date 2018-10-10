package com.ipd.taixiuser.bean;

import java.util.List;

public class ListResult<T> {

    /**
     * total : 4
     * per_page : 2
     * current_page : 1
     * last_page : 2
     * next_page_url : http://www.taixi.com/api/home/sysnews?page=2
     * prev_page_url : null
     * from : 1
     * to : 2
     * data : [{"id":1,"content":"即可噶胡椒粉嘎哈是大V","user_id":3,"pushtime":1537228382,"is_read":0},{"id":2,"content":"看见爱上股份哈吉斯GV按计划大金黄色的八九十的","user_id":3,"pushtime":1537311382,"is_read":0}]
     */

    public int total;
    public String per_page;
    public int current_page;
    public int last_page;
    public String next_page_url;
    public String prev_page_url;
    public int from;
    public int to;
    public List<T> data;

}
