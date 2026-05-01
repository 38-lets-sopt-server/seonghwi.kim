package org.sopt.service;

import org.sopt.domain.Like;
import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.dto.request.LikeRequest;
import org.sopt.exception.BusinessException;
import org.sopt.exception.ErrorCode;
import org.sopt.exception.PostNotFoundException;
import org.sopt.exception.UserNotFoundException;
import org.sopt.repository.LikeRepository;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
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
    public void addLike(Long postId, LikeRequest request) {
        User user = findUserById(request.userId());
        Post post = findPostById(postId);

        validateLikeNotExists(user.getId(), post.getId());

        Like like = new Like(user, post);
        likeRepository.save(like);
    }

    @Transactional
    public void removeLike(Long postId, LikeRequest request) {
        Like like = likeRepository.findByUserIdAndPostId(request.userId(), postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.LIKE_NOT_FOUND));

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
            throw new BusinessException(ErrorCode.LIKE_ALREADY_EXISTS);
        }
    }
}
