package com.taylietech.engcollege.model;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.util.Date;

@Entity
public class UserPost {

    public UserPost() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    private String title;
    private String contents;
    private String image;
    private String video;
    private Date postDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")

    private User user;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
