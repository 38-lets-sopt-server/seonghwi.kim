package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;
import org.sopt.validator.PostValidator;

import java.util.ArrayList;
import java.util.List;

public class PostService {
    private final PostRepository postRepository = new PostRepository();
    private final PostValidator postValidator = new PostValidator();

    // CREATE
    public Long createPost(CreatePostRequest request) {
        // Service는 생성 흐름만 담당, 검사는 Validator가 담당
        postValidator.validatePost(request.title, request.content);

        String createdAt = java.time.LocalDateTime.now().toString();
        Post post = new Post(postRepository.generateId(), request.title, request.content, request.author, createdAt);
        postRepository.save(post);
        return post.getId();
    }

    // READ - 전체 📝 과제
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> responses = new ArrayList<>();

        for (Post post : posts) {
            responses.add(new PostResponse(post));
        }

        return responses;
    }

    // READ - 단건 📝 과제
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id);

        if (post == null) {
            throw new PostNotFoundException("해당 게시글을 찾을 수 없습니다.");
        }

        return new PostResponse(post);
    }

    // UPDATE 📝 과제
    public void updatePost(Long id, String newTitle, String newContent) {
       postValidator.validatePost(newTitle, newContent);

        Post post = postRepository.findById(id);

        if (post == null) {
            throw new PostNotFoundException("해당 게시글을 찾을 수 없습니다.");
        }

        post.update(newTitle, newContent);
    }

    // DELETE 📝 과제
    public void deletePost(Long id) {
        boolean deleted = postRepository.deleteById(id);

        if (!deleted) {
            throw new PostNotFoundException("해당 게시글을 찾을 수 없습니다.");
        }
    }
}