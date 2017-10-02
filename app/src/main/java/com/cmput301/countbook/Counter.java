package com.cmput301.countbook;

import java.util.Date;

/**
 * Created by WilliamWong on 2017-09-29.
 */
// version 0.3 since 0.1
class Counter {
    public String name;
    public int num;
    public String comment;
    public int init_num;
    public Date date;
    Counter(String name, int num, String comment) {
        this.name = name;
        this.num = num;
        this.init_num = num;
        this.comment = comment;
        date = new Date();
    }
    Counter(String name, int num,int curr_num,String comment) {
        this.name = name;
        this.num = curr_num;
        this.init_num = num;
        this.comment = comment;
        date = new Date();
    }
    public Counter() {
        name = "Test";
        num = 0;
        init_num = 0;
        comment = "";
        date = new Date();
    }
    public String getName() {
        return name;
    }
    public String getNum() { return Integer.toString(num);}
    public String getInitNum() {return Integer.toString(init_num);}
    @Override
    public String toString(){
        return date.toString() + " | " + name + " | " + getNum();
    }


    public void setName(String name) {
        this.name = name;
    }
}
