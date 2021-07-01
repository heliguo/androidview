package com.example.androidview;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author lgh on 2021/6/21 11:23
 * @description
 */
public class ALiToken implements Serializable {


    @SerializedName("NlsRequestId")
    private String nlsRequestId;
    @SerializedName("RequestId")
    private String requestId;
    @SerializedName("ErrMsg")
    private String errMsg;
    @SerializedName("Token")
    private TokenBean token;

    public String getNlsRequestId() {
        return nlsRequestId;
    }

    public void setNlsRequestId(String nlsRequestId) {
        this.nlsRequestId = nlsRequestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public TokenBean getToken() {
        return token;
    }

    public void setToken(TokenBean token) {
        this.token = token;
    }

    public static class TokenBean implements Serializable {
        @SerializedName("ExpireTime")
        private int expireTime;
        @SerializedName("Id")
        private String id;
        @SerializedName("UserId")
        private String userId;

        public int getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(int expireTime) {
            this.expireTime = expireTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
