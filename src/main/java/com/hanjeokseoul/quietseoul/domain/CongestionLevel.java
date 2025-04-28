package com.hanjeokseoul.quietseoul.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum CongestionLevel {
    CONGESTED(5), CROWDED(4), NORMAL(3), QUIET(1);

    private final int score;

    CongestionLevel(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public static CongestionLevel fromAverage(double avg) {
        if (avg < 2.0) return QUIET;
        else if (avg < 3.0) return NORMAL;
        else if (avg < 4.0) return CROWDED;
        else return CONGESTED;
    }
}
