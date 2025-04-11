package com.hanjeokseoul.quietseoul.suggestion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuggestionRequest {
    private String placeName;
    private String address;
    private String description;
    private double latitude;
    private double longitude;
}
