package com.lemon.entity;

/**
 * @author LyuBo
 * @create 2021/8/24 15:20
 */
public class UserResponseBean {
    private Long id;
    private String userId;
    private String name;
    private String imgUrl;

    @Override
    public String toString() {
        return "UserResponseBean{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
    public UserResponseBean() {

    }
    public UserResponseBean(Long id, String userId, String name, String imgUrl) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
