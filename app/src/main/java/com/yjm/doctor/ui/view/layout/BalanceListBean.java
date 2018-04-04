package com.yjm.doctor.ui.view.layout;

import com.yjm.doctor.model.ObjectMessage;

import java.util.List;

/**
 * Created by zs on 2017/12/16.
 */

public class BalanceListBean extends ObjectMessage {


    private ObjBean obj;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {

        private int total;
        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {

            private int adminId;
            private float amount;
            private float amountLog;
            private long createTime;
            private int id;
            private String refType;
            private String refTypeZh;
            private boolean status;
            private int userId;

            public int getAdminId() {
                return adminId;
            }

            public void setAdminId(int adminId) {
                this.adminId = adminId;
            }

            public float getAmount() {
                return amount;
            }

            public void setAmount(float amount) {
                this.amount = amount;
            }

            public float getAmountLog() {
                return amountLog;
            }

            public void setAmountLog(float amountLog) {
                this.amountLog = amountLog;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getRefType() {
                return refType;
            }

            public void setRefType(String refType) {
                this.refType = refType;
            }

            public String getRefTypeZh() {
                return refTypeZh;
            }

            public void setRefTypeZh(String refTypeZh) {
                this.refTypeZh = refTypeZh;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            @Override
            public String toString() {
                return "RowsBean{" +
                        "adminId=" + adminId +
                        ", amount=" + amount +
                        ", amountLog=" + amountLog +
                        ", createTime=" + createTime +
                        ", id=" + id +
                        ", refType='" + refType + '\'' +
                        ", refTypeZh='" + refTypeZh + '\'' +
                        ", status=" + status +
                        ", userId=" + userId +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "BalanceListBean{" +
                "obj=" + obj +
                '}';
    }
}
