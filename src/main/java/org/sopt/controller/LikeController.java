package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.sopt.dto.request.LikeRequest;
import org.sopt.dto.response.BaseResponse;
import org.sopt.dto.response.SuccessCode;
import org.sopt.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Like", description = "좋아요 관련 API")
@RestController
@RequestMapping("/api/v1/posts/{postId}/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @Operation(
            summary = "좋아요 추가",
            description = "특정 게시글에 사용자가 좋아요를 추가합니다. 같은 사용자는 같은 게시글에 중복 좋아요를 누를 수 없습니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "좋아요 추가 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 - userId가 없거나 요청 본문 형식이 올바르지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글 또는 사용자 ID로 요청한 경우"),
            @ApiResponse(responseCode = "409", description = "이미 좋아요를 누른 게시글인 경우")
    })
    @PostMapping
    public ResponseEntity<BaseResponse<Void>> addLike(
            @Parameter(description = "좋아요를 추가할 게시글 ID", example = "1")
            @PathVariable Long postId,

            @Valid @RequestBody LikeRequest request
    ) {
        likeService.addLike(postId, request);

        return ResponseEntity
                .status(SuccessCode.LIKE_CREATE_SUCCESS.getStatus())
                .body(BaseResponse.success(
                        SuccessCode.LIKE_CREATE_SUCCESS,
                        null
                ));
    }

    @Operation(
            summary = "좋아요 취소",
            description = "특정 게시글에 사용자가 누른 좋아요를 취소합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 취소 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 - userId가 없거나 요청 본문 형식이 올바르지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 좋아요인 경우")
    })
    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> removeLike(
            @Parameter(description = "좋아요를 취소할 게시글 ID", example = "1")
            @PathVariable Long postId,

            @Valid @RequestBody LikeRequest request
    ) {
        likeService.removeLike(postId, request);

        return ResponseEntity
                .status(SuccessCode.LIKE_DELETE_SUCCESS.getStatus())
                .body(BaseResponse.success(
                        SuccessCode.LIKE_DELETE_SUCCESS,
                        null
                ));
    }
}
