package org.sopt.domain;

public class Post {
    private Long id;              // 게시글 상세 화면 — 특정 게시글 식별용
    private String title;         // 목록, 상세, 글쓰기 화면 — 제목
    private String content;       // 목록(미리보기), 상세(전체) 화면 — 내용
    private String author;        // 목록, 상세 화면 — 글쓴이
    private boolean isAnonymous;  // 익명 여부
    private String createdAt;     // 목록, 상세 화면 — 작성 시각

    public Post(Long id, String title, String content, String author, boolean isAnonymous, String createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.isAnonymous = isAnonymous;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public boolean isAnonymous() { return isAnonymous; }
    public String getCreatedAt() { return createdAt; }

    public void update(String title, String content, boolean isAnonymous) {
        this.title = title;
        this.content = content;
        this.isAnonymous = isAnonymous;
    }

    public String getDisplayAuthorName() {
        if (isAnonymous) {
            return "익명";
        }

        return author;
    }

    public String getInfo() {
        return "[" + id + "] " + title + " - " + getDisplayAuthorName() + " (" + createdAt + ")\n" + content;
    }
}
