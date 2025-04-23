package com.hanjeokseoul.quietseoul.dto;

import com.hanjeokseoul.quietseoul.domain.CongestionLevel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceReviewRequest {
    private String congestionLevel;
    private String comment;
    private LocalDate visitDate;
    private String imageUrl;

    public CongestionLevel toCongestionLevel() {
        if (congestionLevel == null) {
            throw new IllegalArgumentException("혼잡도(congestionLevel)는 필수입니다.");
        }
        return CongestionLevel.valueOf(congestionLevel.toUpperCase());
    }
}
