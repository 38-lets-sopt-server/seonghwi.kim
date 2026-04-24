package org.sopt.repository;

import org.sopt.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {

    private final List<Post> postList = new ArrayList<>();
    private Long nextId = 1L;

    public Post save(Post post) {
        postList.add(post);
        return post;
    }

    public List<Post> findAll() {
        return new ArrayList<>(postList);  // 복사본 반환 (내부 리스트가 외부에서 직접 수정되는 위험 줄일 수 있음)
    }

    public Optional<Post> findById(Long id) {
        return postList.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst();
    }

    public boolean deleteById(Long id) {
        return postList.removeIf(post -> post.getId().equals(id));
    }

    public Long generateId() {
        return nextId++;
    }
}
