package org.sopt.service;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.BusinessException;
import org.sopt.exception.ErrorCode;
import org.sopt.exception.PostNotFoundException;
import org.sopt.exception.UserNotFoundException;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.sopt.validator.PostValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostValidator postValidator;

    public PostService(
            PostRepository postRepository,
            UserRepository userRepository,
            PostValidator postValidator
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postValidator = postValidator;
    }

    // CREATE
    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request) {
        postValidator.validateCreatePost(
                request.title(),
                request.content(),
                request.userId(),
                request.isAnonymous(),
                request.boardType()
        );

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException(request.userId()));

        Post post = new Post(
                request.title(),
                request.content(),
                request.isAnonymous(),
                request.boardType(),
                user
        );

        Post savedPost = postRepository.save(post);

        return new CreatePostResponse(savedPost.getId());
    }

    // READ - 게시판 종류별 전체 조회, page/size 적용
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(int page, int size, BoardType boardType) {
        validatePageRequest(page, size);
        validateBoardTypeRequest(boardType);

        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
                        .and(Sort.by(Sort.Direction.DESC, "id"))
        );

        return postRepository.findByBoardType(boardType, pageRequest).stream()
                .map(PostResponse::from)
                .toList();
    }

    // READ - 단건 조회
    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = findPostById(id);

        return PostResponse.from(post);
    }

    // UPDATE
    @Transactional
    public void updatePost(Long id, UpdatePostRequest request) {
        postValidator.validateUpdatePost(
                request.title(),
                request.content(),
                request.isAnonymous()
        );

        Post post = findPostById(id);

        post.update(
                request.title(),
                request.content(),
                request.isAnonymous()
        );
    }

    // DELETE
    @Transactional
    public void deletePost(Long id) {
        Post post = findPostById(id);

        postRepository.delete(post);
    }

    private Post findPostById(Long id) {
        return postRepository.findWithUserById(id)
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

    private void validateBoardTypeRequest(BoardType boardType) {
        if (boardType == null) {
            throw new BusinessException(ErrorCode.INVALID_POST_BOARD_TYPE);
        }
    }
}
