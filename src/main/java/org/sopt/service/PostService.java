package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.BusinessException;
import org.sopt.exception.ErrorCode;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;
import org.sopt.validator.PostValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostValidator postValidator;

    public PostService(PostRepository postRepository, PostValidator postValidator) {  // 생성자 주입
        this.postRepository = postRepository;
        this.postValidator = postValidator;
    }

    // CREATE
    public CreatePostResponse createPost(CreatePostRequest request) {
        // Service는 생성 흐름만 담당, 검사는 Validator가 담당
        postValidator.validatePost(request.title(), request.content(), request.isAnonymous());

        String createdAt = LocalDateTime.now().toString();

        Post post = new Post(
                postRepository.generateId(),
                request.title(),
                request.content(),
                request.author(),
                request.isAnonymous(),
                createdAt
        );

        postRepository.save(post);

        return new CreatePostResponse(post.getId());
    }

    // READ - 전체 조회, page/size 적용
    public List<PostResponse> getAllPosts(int page, int size) {
        validatePageRequest(page, size);

        List<Post> posts = postRepository.findAll().stream()
                .sorted(Comparator.comparing(Post::getId).reversed())  // 최신순 정렬 (id가 큰 게시글 순)
                .toList();

        int startIndex = page * size;

        if (startIndex >= posts.size()) {
            return List.of();
        }

        int endIndex = Math.min(startIndex + size, posts.size());

        return posts.subList(startIndex, endIndex).stream()
                .map(PostResponse::from)
                .toList();
    }

    // READ - 단건 조회
    public PostResponse getPost(Long id) {
        Post post = findPostById(id);

        return PostResponse.from(post);
    }

    // UPDATE
    public void updatePost(Long id, UpdatePostRequest request) {
        postValidator.validatePost(request.title(), request.content(), request.isAnonymous());

        Post post = findPostById(id);
        post.update(request.title(), request.content(), request.isAnonymous());
    }

    // DELETE
    public void deletePost(Long id) {
        boolean deleted = postRepository.deleteById(id);

        if (!deleted) {
            throw new PostNotFoundException(id);
        }
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    private void validatePageRequest(int page, int size) {
        if (page < 0) {
            throw new BusinessException(ErrorCode.INVALID_PAGE_REQUEST);
        }

        if (size <= 0) {
            throw new BusinessException(ErrorCode.INVALID_SIZE_REQUEST);
        }
    }
}
