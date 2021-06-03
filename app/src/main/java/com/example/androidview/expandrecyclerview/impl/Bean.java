package com.example.androidview.expandrecyclerview.impl;

import java.util.List;

/**
 * @author lgh on 2021/2/23 15:29
 * @description
 */
public class Bean {

    /**
     * code : 1000
     * msg : success
     * data : {"hasReminder":true,"reminderList":[{"timeClass":"05:00","list":[{"id":"e22fdbf132e8490cbcb05774cd8d3527","guid":"67fa77c0a329b20957156e5ed36a9d4d","medicineName":"测","remindList":"[{\"remindTime\":\"05:00\",\"checkRemind\":true,\"times\":1593464400000},{\"remindTime\":\"10:00\",\"checkRemind\":false,\"times\":1593482400000}]","remindTime":"05:00","dosageAdded":"2","unit":"粒","usedDrug":false},{"id":"2a6814c090d04c89be6e98dda33849d1","guid":"67fa77c0a329b20957156e5ed36a9d4d","medicineName":"c","remindList":"[{\"remindTime\":\"05:00\",\"checkRemind\":true,\"times\":1593464400000}]","remindTime":"05:00","dosageAdded":"23","unit":"毫克","usedDrug":false}]},{"timeClass":"12:00","list":[{"id":"de466cc795fa4caa9c494d76ce29ead6","guid":"67fa77c0a329b20957156e5ed36a9d4d","medicineName":"a","remindList":"[{\"remindTime\":\"12:00\",\"checkRemind\":true,\"times\":1593489600000}]","remindTime":"12:00","dosageAdded":"2","unit":"克","usedDrug":false},{"id":"05068e1fd0764e34b12e339d43929dff","guid":"67fa77c0a329b20957156e5ed36a9d4d","medicineName":"fg","remindList":"[{\"remindTime\":\"12:00\",\"checkRemind\":true,\"times\":1593489600000}]","remindTime":"12:00","dosageAdded":"22","unit":"克","usedDrug":false}]},{"timeClass":"17:33","list":[{"id":"6139a4991fbb4da2b0db71add4b8744b","guid":"67fa77c0a329b20957156e5ed36a9d4d","medicineName":"e","remindList":"[{\"remindTime\":\"17:33\",\"checkRemind\":true,\"times\":1593509580000}]","remindTime":"17:33","dosageAdded":"2","unit":"克","usedDrug":false}]}]}
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

    public static class DataBean {
        /**
         * hasReminder : true
         * reminderList : [{"timeClass":"05:00","list":[{"id":"e22fdbf132e8490cbcb05774cd8d3527","guid":"67fa77c0a329b20957156e5ed36a9d4d","medicineName":"测","remindList":"[{\"remindTime\":\"05:00\",\"checkRemind\":true,\"times\":1593464400000},{\"remindTime\":\"10:00\",\"checkRemind\":false,\"times\":1593482400000}]","remindTime":"05:00","dosageAdded":"2","unit":"粒","usedDrug":false},{"id":"2a6814c090d04c89be6e98dda33849d1","guid":"67fa77c0a329b20957156e5ed36a9d4d","medicineName":"c","remindList":"[{\"remindTime\":\"05:00\",\"checkRemind\":true,\"times\":1593464400000}]","remindTime":"05:00","dosageAdded":"23","unit":"毫克","usedDrug":false}]},{"timeClass":"12:00","list":[{"id":"de466cc795fa4caa9c494d76ce29ead6","guid":"67fa77c0a329b20957156e5ed36a9d4d","medicineName":"a","remindList":"[{\"remindTime\":\"12:00\",\"checkRemind\":true,\"times\":1593489600000}]","remindTime":"12:00","dosageAdded":"2","unit":"克","usedDrug":false},{"id":"05068e1fd0764e34b12e339d43929dff","guid":"67fa77c0a329b20957156e5ed36a9d4d","medicineName":"fg","remindList":"[{\"remindTime\":\"12:00\",\"checkRemind\":true,\"times\":1593489600000}]","remindTime":"12:00","dosageAdded":"22","unit":"克","usedDrug":false}]},{"timeClass":"17:33","list":[{"id":"6139a4991fbb4da2b0db71add4b8744b","guid":"67fa77c0a329b20957156e5ed36a9d4d","medicineName":"e","remindList":"[{\"remindTime\":\"17:33\",\"checkRemind\":true,\"times\":1593509580000}]","remindTime":"17:33","dosageAdded":"2","unit":"克","usedDrug":false}]}]
         */

        private boolean hasReminder;
        private List<ReminderListBean> reminderList;

        public boolean isHasReminder() {
            return hasReminder;
        }

        public void setHasReminder(boolean hasReminder) {
            this.hasReminder = hasReminder;
        }

        public List<ReminderListBean> getReminderList() {
            return reminderList;
        }

        public void setReminderList(List<ReminderListBean> reminderList) {
            this.reminderList = reminderList;
        }

        public static class ReminderListBean {
            /**
             * timeClass : 05:00
             * list : [{"id":"e22fdbf132e8490cbcb05774cd8d3527","guid":"67fa77c0a329b20957156e5ed36a9d4d","medicineName":"测","remindList":"[{\"remindTime\":\"05:00\",\"checkRemind\":true,\"times\":1593464400000},{\"remindTime\":\"10:00\",\"checkRemind\":false,\"times\":1593482400000}]","remindTime":"05:00","dosageAdded":"2","unit":"粒","usedDrug":false},{"id":"2a6814c090d04c89be6e98dda33849d1","guid":"67fa77c0a329b20957156e5ed36a9d4d","medicineName":"c","remindList":"[{\"remindTime\":\"05:00\",\"checkRemind\":true,\"times\":1593464400000}]","remindTime":"05:00","dosageAdded":"23","unit":"毫克","usedDrug":false}]
             */

            private String timeClass;
            private List<ListBean> list;

            public String getTimeClass() {
                return timeClass;
            }

            public void setTimeClass(String timeClass) {
                this.timeClass = timeClass;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * id : e22fdbf132e8490cbcb05774cd8d3527
                 * guid : 67fa77c0a329b20957156e5ed36a9d4d
                 * medicineName : 测
                 * remindList : [{"remindTime":"05:00","checkRemind":true,"times":1593464400000},{"remindTime":"10:00","checkRemind":false,"times":1593482400000}]
                 * remindTime : 05:00
                 * dosageAdded : 2
                 * unit : 粒
                 * usedDrug : false
                 */

                private String id;
                private String guid;
                private String medicineName;
                private String remindList;
                private String remindTime;
                private String dosageAdded;
                private String unit;
                private boolean usedDrug;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getGuid() {
                    return guid;
                }

                public void setGuid(String guid) {
                    this.guid = guid;
                }

                public String getMedicineName() {
                    return medicineName;
                }

                public void setMedicineName(String medicineName) {
                    this.medicineName = medicineName;
                }

                public String getRemindList() {
                    return remindList;
                }

                public void setRemindList(String remindList) {
                    this.remindList = remindList;
                }

                public String getRemindTime() {
                    return remindTime;
                }

                public void setRemindTime(String remindTime) {
                    this.remindTime = remindTime;
                }

                public String getDosageAdded() {
                    return dosageAdded;
                }

                public void setDosageAdded(String dosageAdded) {
                    this.dosageAdded = dosageAdded;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public boolean isUsedDrug() {
                    return usedDrug;
                }

                public void setUsedDrug(boolean usedDrug) {
                    this.usedDrug = usedDrug;
                }
            }
        }
    }
}
