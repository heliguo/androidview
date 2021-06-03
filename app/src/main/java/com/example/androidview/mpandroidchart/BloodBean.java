package com.example.androidview.mpandroidchart;

import java.io.Serializable;
import java.util.List;

/**
 * @author lgh on 2021/1/20 19:56
 * @description
 */
public class BloodBean implements Serializable {


    /**
     * code : 1000
     * msg : success
     * data : {"totalDay":2,"list":[{"type":"2021-01-18","list":[{"id":"60055560f4e40f5c48b3c7c0","value":6,"guid":"test","measureTs":1610959110000,"status":1,"createTs":1610962272056,"updateTs":1610962272056},{"id":"6008dde58e4f8d4e0c70784a","value":6.1,"guid":"test","measureTs":1610973072000,"status":1,"createTs":1610973072000,"updateTs":1610973072000}]},{"type":"2021-01-20","list":[{"id":"6007fa9b8e4f8d0930e34ad8","value":7,"guid":"test","measureTs":1611135586000,"status":2,"createTs":1611135643351,"updateTs":1611135643351}]}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * totalDay : 2
         * list : [{"type":"2021-01-18","list":[{"id":"60055560f4e40f5c48b3c7c0","value":6,"guid":"test","measureTs":1610959110000,"status":1,"createTs":1610962272056,"updateTs":1610962272056},{"id":"6008dde58e4f8d4e0c70784a","value":6.1,"guid":"test","measureTs":1610973072000,"status":1,"createTs":1610973072000,"updateTs":1610973072000}]},{"type":"2021-01-20","list":[{"id":"6007fa9b8e4f8d0930e34ad8","value":7,"guid":"test","measureTs":1611135586000,"status":2,"createTs":1611135643351,"updateTs":1611135643351}]}]
         */

        private int totalDay;
        private List<ListBean> list;

        public int getTotalDay() {
            return totalDay;
        }

        public void setTotalDay(int totalDay) {
            this.totalDay = totalDay;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            /**
             * type : 2021-01-18
             * list : [{"id":"60055560f4e40f5c48b3c7c0","value":6,"guid":"test","measureTs":1610959110000,"status":1,"createTs":1610962272056,"updateTs":1610962272056},{"id":"6008dde58e4f8d4e0c70784a","value":6.1,"guid":"test","measureTs":1610973072000,"status":1,"createTs":1610973072000,"updateTs":1610973072000}]
             */

            private String type;
            private List<ListBean1> list;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<ListBean1> getList() {
                return list;
            }

            public void setList(List<ListBean1> list) {
                this.list = list;
            }

            public static class ListBean1 implements Serializable {
                /**
                 * id : 60055560f4e40f5c48b3c7c0
                 * value : 6.0
                 * guid : test
                 * measureTs : 1610959110000
                 * status : 1
                 * createTs : 1610962272056
                 * updateTs : 1610962272056
                 */

                private String id;
                private double value;
                private String guid;
                private long measureTs;
                private int status;
                private long createTs;
                private long updateTs;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public double getValue() {
                    return value;
                }

                public void setValue(double value) {
                    this.value = value;
                }

                public String getGuid() {
                    return guid;
                }

                public void setGuid(String guid) {
                    this.guid = guid;
                }

                public long getMeasureTs() {
                    return measureTs;
                }

                public void setMeasureTs(long measureTs) {
                    this.measureTs = measureTs;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public long getCreateTs() {
                    return createTs;
                }

                public void setCreateTs(long createTs) {
                    this.createTs = createTs;
                }

                public long getUpdateTs() {
                    return updateTs;
                }

                public void setUpdateTs(long updateTs) {
                    this.updateTs = updateTs;
                }
            }
        }
    }
}
