package com.hanjeokseoul.quietseoul.domain;

public enum CongestionLevel {
    CONGESTED(1), NORMAL(3), QUIET(5);

    private final int score;

    CongestionLevel(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
