package com.FT05.CloudCA.Entity;

import javax.persistence.*;
import java.time.ZonedDateTime;

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

    @Column(name = "created_Date")
    private ZonedDateTime createdDatetime;


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

    public ZonedDateTime getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(ZonedDateTime createdDatetime) {
        this.createdDatetime = createdDatetime;
    }
}
