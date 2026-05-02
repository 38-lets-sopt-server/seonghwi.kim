package org.sopt.service;

import org.sopt.domain.Like;
import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.exception.BusinessException;
import org.sopt.exception.ErrorCode;
import org.sopt.exception.LikeAlreadyExistsException;
import org.sopt.exception.LikeNotFoundException;
import org.sopt.exception.PostNotFoundException;
import org.sopt.exception.UserNotFoundException;
import org.sopt.repository.LikeRepository;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public LikeService(
            LikeRepository likeRepository,
            PostRepository postRepository,
            UserRepository userRepository
    ) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addLike(Long postId, Long userId) {
        validateUserId(userId);

        User user = findUserById(userId);
        Post post = findPostById(postId);

        validateLikeNotExists(user.getId(), post.getId());

        Like like = new Like(user, post);

        try {
            // DB insert를 트랜잭션 끝까지 미루지 않고 이 시점에 반영
            likeRepository.saveAndFlush(like);
        } catch (DataIntegrityViolationException exception) {
            throw new LikeAlreadyExistsException(user.getId(), post.getId());
        }
    }

    @Transactional
    public void removeLike(Long postId, Long userId) {
        validateUserId(userId);

        // 1. 사용자가 존재하는지 확인
        User user = findUserById(userId);
        // 2. 게시글이 존재하는지 확인
        Post post = findPostById(postId);
        // 3. 해당 사용자가 해당 게시글에 좋아요를 눌렀는지 확인
        Like like = likeRepository.findByUserIdAndPostId(user.getId(), post.getId())
                .orElseThrow(() -> new LikeNotFoundException(user.getId(), post.getId()));
        // 4. 좋아요가 있으면 삭제
        likeRepository.delete(like);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    private void validateLikeNotExists(Long userId, Long postId) {
        if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new LikeAlreadyExistsException(userId, postId);
        }
    }

    private void validateUserId(Long userId) {
        if (userId == null) {
            throw new BusinessException(ErrorCode.INVALID_USER_ID);
        }
    }
}
