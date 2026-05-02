package org.sopt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(name = "is_anonymous", nullable = false)
    private boolean isAnonymous;

    @Enumerated(EnumType.STRING)
    @Column(name = "board_type", nullable = false)
    private BoardType boardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected Post() {
    }

    public Post(
            String title,
            String content,
            boolean isAnonymous,
            BoardType boardType,
            User user
    ) {
        this.title = title;
        this.content = content;
        this.isAnonymous = isAnonymous;
        this.boardType = boardType;
        this.user = user;
    }

    public void update(String title, String content, boolean isAnonymous) {
        this.title = title;
        this.content = content;
        this.isAnonymous = isAnonymous;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public User getUser() {
        return user;
    }
}
