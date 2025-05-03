package com.hanjeokseoul.quietseoul.dto;

import com.hanjeokseoul.quietseoul.domain.CongestionLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PlaceReviewRequest {

    @Schema(description = """
    혼잡도 점수 (정수 값: 1, 3, 5만 허용됨)
    - 1: 한적함 (QUIET)
    - 3: 보통 (NORMAL)
    - 5: 매우 혼잡 (CONGESTED)
    """, example = "3")
    private int congestionScore;

    @Schema(description = "코멘트", example = "정말 한적해요!")
    private String comment;

    @Schema(description = "방문 날짜", example = "2025-04-28")
    private LocalDate visitDate;

    @Schema(description = "업로드할 이미지 파일 리스트", format = "binary")
    private List<MultipartFile> imageUrlList;
}

