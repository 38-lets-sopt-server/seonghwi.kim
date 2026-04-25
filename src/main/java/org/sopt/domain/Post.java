package org.sopt.domain;

import java.time.LocalDateTime;

public class Post {

    private Long id;
    private String title;
    private String content;
    private String author;
    private boolean isAnonymous;
    private BoardType boardType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Post(
            Long id,
            String title,
            String content,
            String author,
            boolean isAnonymous,
            BoardType boardType,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.isAnonymous = isAnonymous;
        this.boardType = boardType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public boolean isAnonymous() { return isAnonymous; }
    public BoardType getBoardType() { return boardType; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void update(String title, String content, boolean isAnonymous, LocalDateTime updatedAt) {
        this.title = title;
        this.content = content;
        this.isAnonymous = isAnonymous;
        this.updatedAt = updatedAt;
    }

    public String getDisplayAuthorName() {
        if (isAnonymous) {
            return "익명";
        }

        return author;
    }
}
