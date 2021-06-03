package com.example.androidview.bytedance;

/**
 * @author lgh on 2021/5/28 20:02
 * @description
 */
public class UserBean {

    private int uid;
    private String nickName;
    private int head;
    /**
     * 座右铭
     */
    private String sign;
    /**
     * 是否已关注
     */
    private boolean isFocused;
    /**
     * 获赞数量
     */
    private int subCount;
    /**
     * 关注数量
     */
    private int focusCount;
    /**
     * 粉丝数量
     */
    private int fansCount;
    /**
     * 作品数量
     */
    private int workCount;
    /**
     * 动态数量
     */
    private int dynamicCount;
    /**
     * 喜欢数量
     */
    private int likeCount;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName == null ? "" : nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public String getSign() {
        return sign == null ? "" : sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public boolean isFocused() {
        return isFocused;
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
    }

    public int getSubCount() {
        return subCount;
    }

    public void setSubCount(int subCount) {
        this.subCount = subCount;
    }

    public int getFocusCount() {
        return focusCount;
    }

    public void setFocusCount(int focusCount) {
        this.focusCount = focusCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getWorkCount() {
        return workCount;
    }

    public void setWorkCount(int workCount) {
        this.workCount = workCount;
    }

    public int getDynamicCount() {
        return dynamicCount;
    }

    public void setDynamicCount(int dynamicCount) {
        this.dynamicCount = dynamicCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
