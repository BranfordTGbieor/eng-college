package com.taylietech.engcollege.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UserComment {

    public UserComment() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    private String comment;
    private Date commentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
