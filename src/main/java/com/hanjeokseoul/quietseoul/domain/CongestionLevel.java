package com.hanjeokseoul.quietseoul.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum CongestionLevel {
    CONGESTED(1), NORMAL(3), QUIET(5);

    private final int score;

    CongestionLevel(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public static CongestionLevel fromAverage(double avg) {
        if (avg < 1.5) return QUIET;
        else if (avg < 2.5) return NORMAL;
        else return CONGESTED;
    }
}
