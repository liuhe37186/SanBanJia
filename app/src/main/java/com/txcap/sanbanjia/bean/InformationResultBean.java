package com.txcap.sanbanjia.bean;

import java.util.List;

/**
 * Created by liuhe on 15/8/12.
 */
public class InformationResultBean {
    /**
     * "status": "1",
     "msg": "成功",
     "data": [
     */
    private String status;
    private String msg;
    private List<InformationTitleBean> data;

    public InformationResultBean(String status, String msg, List<InformationTitleBean> list) {
        this.status = status;
        this.msg = msg;
        this.data = list;
    }

    public List<InformationTitleBean> getData() {
        return data;
    }

    public void setData(List<InformationTitleBean> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "InformationResultBean{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", list=" + data +
                '}';
    }
}
