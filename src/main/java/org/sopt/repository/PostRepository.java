package org.sopt.repository;

import org.sopt.domain.Post;

import java.util.ArrayList;
import java.util.List;

public class PostRepository {
    private final List<Post> postList = new ArrayList<>();
    private Long nextId = 1L;

    public Post save(Post post) {
        postList.add(post);
        return post;
    }

    public List<Post> findAll(){
        return postList;
    }

    public Post findById(Long id) {
        for (Post post : postList) {
            if (post.getId().equals(id))
                return post;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        for (Post post : postList) {
            if (post.getId().equals(id)) {
                postList.remove(post);
                return true;
            }
        }
        return false;
    }

    public Long generateId() {
        return nextId++;
    }
}