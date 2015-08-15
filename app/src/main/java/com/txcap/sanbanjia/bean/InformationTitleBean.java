package com.txcap.sanbanjia.bean;

/**
 * Created by liuhe on 15/8/12.
 */
public class InformationTitleBean {
    /*
            "id": 12,
            "news_title": "北京大学实战型新三板培训 中国金融改革与新三板上市课",
            "news_introduction": "北京大学实战型新三板培训 中国金融改革与新三板上市课",
            "news_pic": "http://file.txcap.com/2015/08/1439262943faxuamislo.jpg",
            "created_at": "2015-08-09 14:27:12",
            "created": "2015-08-09"
     */
    private Integer id;
    private String news_title;
    private String news_introduction;
    private String news_pic;
    private String created_at;
    private String created;


    public InformationTitleBean(Integer id, String news_title, String news_introduction, String news_pic, String created_at, String created) {
        this.id = id;
        this.news_title = news_title;
        this.news_introduction = news_introduction;
        this.news_pic = news_pic;
        this.created_at = created_at;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_introduction() {
        return news_introduction;
    }

    public void setNews_introduction(String news_introduction) {
        this.news_introduction = news_introduction;
    }

    public String getNews_pic() {
        return news_pic;
    }

    public void setNews_pic(String news_pic) {
        this.news_pic = news_pic;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "InformationTitleBean{" +
                "id=" + id +
                ", news_title='" + news_title + '\'' +
                ", news_introduction='" + news_introduction + '\'' +
                ", news_pic='" + news_pic + '\'' +
                ", created_at='" + created_at + '\'' +
                ", created='" + created + '\'' +
                '}';
    }
}
