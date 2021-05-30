package com.example.androidview.transform;


import java.io.Serializable;
import java.util.List;

/**
 * @author lgh on 2021/5/29 14:25
 * @description
 */
public class TopBannerBean implements Serializable {


    private DataBean data;
    private boolean success;
    private int state;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean implements Serializable {

        private LblistBean lblist;

        public LblistBean getLblist() {
            return lblist;
        }

        public void setLblist(LblistBean lblist) {
            this.lblist = lblist;
        }

        public static class LblistBean implements Serializable{

            private List<FocusesBean> focuses;

            public List<FocusesBean> getFocuses() {
                return focuses;
            }

            public void setFocuses(List<FocusesBean> focuses) {
                this.focuses = focuses;
            }


            public static class FocusesBean implements Serializable{
                private int docId;
                private int docType;
                private String content;
                private String focusImageTitle;
                private List<String> focusImageUrl;
                private String h5Url;
                private String docJsonUrl;
                private String docSource;
                private String pubtime;
                private String docChannel;
                private String themeColor;

                public int getDocId() {
                    return docId;
                }

                public void setDocId(int docId) {
                    this.docId = docId;
                }

                public int getDocType() {
                    return docType;
                }

                public void setDocType(int docType) {
                    this.docType = docType;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getFocusImageTitle() {
                    return focusImageTitle;
                }

                public void setFocusImageTitle(String focusImageTitle) {
                    this.focusImageTitle = focusImageTitle;
                }

                public List<String> getFocusImageUrl() {
                    return focusImageUrl;
                }

                public void setFocusImageUrl(List<String> focusImageUrl) {
                    this.focusImageUrl = focusImageUrl;
                }

                public String getH5Url() {
                    return h5Url;
                }

                public void setH5Url(String h5Url) {
                    this.h5Url = h5Url;
                }

                public String getDocJsonUrl() {
                    return docJsonUrl;
                }

                public void setDocJsonUrl(String docJsonUrl) {
                    this.docJsonUrl = docJsonUrl;
                }

                public String getDocSource() {
                    return docSource;
                }

                public void setDocSource(String docSource) {
                    this.docSource = docSource;
                }

                public String getPubtime() {
                    return pubtime;
                }

                public void setPubtime(String pubtime) {
                    this.pubtime = pubtime;
                }

                public String getDocChannel() {
                    return docChannel;
                }

                public void setDocChannel(String docChannel) {
                    this.docChannel = docChannel;
                }

                public String getThemeColor() {
                    return themeColor;
                }

                public void setThemeColor(String themeColor) {
                    this.themeColor = themeColor;
                }

            }

        }
    }
}
