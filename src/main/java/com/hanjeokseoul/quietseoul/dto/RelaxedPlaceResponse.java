package com.hanjeokseoul.quietseoul.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RelaxedPlaceResponse {
    private String name;
    private String type;
    private String level;
    private String imageUrl;
    private String description;
}

