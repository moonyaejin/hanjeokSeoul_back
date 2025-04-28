package com.hanjeokseoul.quietseoul.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum CongestionLevel {
    CONGESTED(5), NORMAL(3), QUIET(1);

    private final int score;

    CongestionLevel(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
