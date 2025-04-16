package com.hanjeokseoul.quietseoul.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SuggestionResponse {

    private String id;
    private String placeName;
    private String description;
    private double latitude;
    private double longitude;
    private boolean approved;
}
