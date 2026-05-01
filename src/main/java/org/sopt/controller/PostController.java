package org.sopt.controller;

import org.sopt.domain.BoardType;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.BaseResponse;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.dto.response.SuccessCode;
import org.sopt.service.PostService;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "게시글 작성",
            description = "userId로 작성자를 조회한 뒤, 해당 사용자가 작성한 게시글을 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 작성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 - 제목, 내용, userId, 익명 여부, 게시판 종류가 올바르지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자 ID로 요청한 경우")
    })
    @PostMapping
    public ResponseEntity<BaseResponse<CreatePostResponse>> createPost(
            @Valid @RequestBody CreatePostRequest request
    ) {
        CreatePostResponse response = postService.createPost(request);

        return ResponseEntity
                .status(SuccessCode.POST_CREATE_SUCCESS.getStatus())
                .body(BaseResponse.success(
                        SuccessCode.POST_CREATE_SUCCESS,
                        response
                ));
    }

    @Operation(
            summary = "게시글 목록 조회",
            description = "게시판 종류별로 게시글 목록을 페이지 단위로 조회합니다. createdAt DESC, id DESC 기준으로 정렬됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 - page, size, boardType 값이 올바르지 않은 경우")
    })
    @GetMapping
    public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPosts(
            @Parameter(description = "페이지 번호입니다. 0부터 시작합니다.", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "한 페이지에 조회할 게시글 수입니다.", example = "10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "게시판 종류입니다.", example = "FREE")
            @RequestParam(required = false) BoardType boardType
    ) {
        List<PostResponse> response = postService.getAllPosts(page, size, boardType);

        return ResponseEntity
                .status(SuccessCode.POST_LIST_SUCCESS.getStatus())
                .body(BaseResponse.success(
                        SuccessCode.POST_LIST_SUCCESS,
                        response
                ));
    }

    @Operation(
            summary = "게시글 단건 조회",
            description = "게시글 ID로 특정 게시글의 상세 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 상세 조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글 ID로 요청한 경우")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> getPost(
            @Parameter(description = "조회할 게시글 ID입니다.", example = "1")
            @PathVariable Long id
    ) {
        PostResponse response = postService.getPost(id);

        return ResponseEntity
                .status(SuccessCode.POST_DETAIL_SUCCESS.getStatus())
                .body(BaseResponse.success(
                        SuccessCode.POST_DETAIL_SUCCESS,
                        response
                ));
    }

    @Operation(
            summary = "게시글 수정",
            description = "게시글 ID로 특정 게시글의 제목, 내용, 익명 여부를 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 - 제목, 내용, 익명 여부가 올바르지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글 ID로 요청한 경우")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> updatePost(
            @Parameter(description = "수정할 게시글 ID입니다.", example = "1")
            @PathVariable Long id,

            @Valid @RequestBody UpdatePostRequest request
    ) {
        postService.updatePost(id, request);

        return ResponseEntity
                .status(SuccessCode.POST_UPDATE_SUCCESS.getStatus())
                .body(BaseResponse.success(
                        SuccessCode.POST_UPDATE_SUCCESS,
                        null
                ));
    }

    @Operation(
            summary = "게시글 삭제",
            description = "게시글 ID로 특정 게시글을 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글 ID로 요청한 경우")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @Parameter(description = "삭제할 게시글 ID입니다.", example = "1")
            @PathVariable Long id
    ) {
        postService.deletePost(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
