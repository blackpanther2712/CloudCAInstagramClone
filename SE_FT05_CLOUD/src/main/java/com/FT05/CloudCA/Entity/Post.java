package com.FT05.CloudCA.Entity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "posted_image")
    private String imageUrl;

    @Column(name = "caption")
    private String caption;

    @Column(name = "created_Date")
    private Date createdDatetime;

    @Column(name = "like_Count")
    private int count;

    @Column(name = "like_Indicator")
    private String likeIndicator;


    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLikeIndicator() {
        return likeIndicator;
    }

    public void setLikeIndicator(String likeIndicator) {
        this.likeIndicator = likeIndicator;
    }
}
