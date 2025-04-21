package com.hanjeokseoul.quietseoul.dto;

import com.hanjeokseoul.quietseoul.domain.CongestionLevel;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceReviewRequest {
    private CongestionLevel congestionLevel;
    private String comment;
    private LocalDate visitDate;
}
