package org.sopt.controller;

import org.sopt.domain.BoardType;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // POST /posts
    @PostMapping
    public ResponseEntity<ApiResponse<CreatePostResponse>> createPost(
            @RequestBody CreatePostRequest request
    ) {
        CreatePostResponse response = postService.createPost(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "POST_CREATE_SUCCESS",
                        "게시글 작성에 성공했습니다.",
                        response
                ));
    }

    // GET /posts?page=0&size=10  게시판 종류별 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) BoardType boardType
    ) {
        List<PostResponse> response = postService.getAllPosts(page, size, boardType);

        return ResponseEntity
                .ok(ApiResponse.success(
                        "POST_LIST_SUCCESS",
                        "게시글 목록 조회에 성공했습니다.",
                        response
                ));
    }

    // GET /posts/{id} 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(
            @PathVariable Long id
    ) {
        PostResponse response = postService.getPost(id);

        return ResponseEntity
                .ok(ApiResponse.success(
                        "POST_DETAIL_SUCCESS",
                        "게시글 상세 조회에 성공했습니다.",
                        response
                ));
    }

    // PATCH /posts/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
            @PathVariable Long id,
            @RequestBody UpdatePostRequest request
    ) {
        postService.updatePost(id, request);

        return ResponseEntity
                .ok(ApiResponse.success(
                        "POST_UPDATE_SUCCESS",
                        "게시글 수정에 성공했습니다.",
                        null
                ));
    }

    // DELETE /posts/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id
    ) {
        postService.deletePost(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
