package com.example.androidview.pageview;

/**
 * @author lgh on 2021/3/18 17:51
 * @description 翻页动作结束
 */
public interface CurFinishListener {

    //true left finish , false right finish
    void curFinish(boolean left);

}
