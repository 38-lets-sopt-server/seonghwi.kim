package org.sopt.repository;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;
import org.sopt.dto.response.PostResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
            select new org.sopt.dto.response.PostResponse(
                p.id,
                p.title,
                p.content,
                case
                    when p.isAnonymous = true then '익명'
                    else u.nickname
                end,
                p.isAnonymous,
                p.boardType,
                count(l.id),
                p.createdAt,
                p.updatedAt
            )
            from Post p
            join p.user u
            left join Like l on l.post = p
            where p.boardType = :boardType
            group by
                p.id,
                p.title,
                p.content,
                p.isAnonymous,
                p.boardType,
                p.createdAt,
                p.updatedAt,
                u.nickname
            order by p.createdAt desc, p.id desc
            """)
    List<PostResponse> findPostResponsesByBoardTypeWithLikeCount(
            @Param("boardType") BoardType boardType,
            Pageable pageable
    );

    @EntityGraph(attributePaths = "user")
    Optional<Post> findWithUserById(Long id);
}
