package com.hanjeokseoul.quietseoul.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class CurrentCongestionResponse {
    private String parkName;
    private String congestionLevel;
}
