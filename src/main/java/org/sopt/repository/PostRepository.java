package org.sopt.repository;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = "user")
    List<Post> findByBoardType(BoardType boardType, Pageable pageable);

    @EntityGraph(attributePaths = "user")
    Optional<Post> findWithUserById(Long id);
}
