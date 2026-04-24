package org.sopt.domain;

public class Post {
    private Long id;              // 게시글 상세 화면 — 특정 게시글 식별용
    private String title;         // 목록, 상세, 글쓰기 화면 — 제목
    private String content;       // 목록(미리보기), 상세(전체) 화면 — 내용
    private String author;        // 목록, 상세 화면 — 글쓴이
    private boolean isAnonymous;  // 익명 여부
    private BoardType boardType;  // 게시판 종류
    private String createdAt;     // 목록, 상세 화면 — 작성 시각
    private String updatedAt;     // 수정 시각

    public Post(
            Long id,
            String title,
            String content,
            String author,
            boolean isAnonymous,
            BoardType boardType,
            String createdAt,
            String updatedAt
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
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }

    public void update(String title, String content, boolean isAnonymous, String updatedAt) {
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

    public String getInfo() {
        return "[" + id + "] " + title + " - " + getDisplayAuthorName() + " (" + createdAt + ")\n" + content;
    }
}
