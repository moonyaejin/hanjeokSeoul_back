package com.hanjeokseoul.quietseoul.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum CongestionLevel {
    QUIET(1),        // 1~1.99
    NORMAL(3),       // 2~2.99
    CROWDED(4),      // 3~3.99
    CONGESTED(5);    // 4~5

    private final int score;

    CongestionLevel(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    // 정확한 점수로부터 enum 생성 (리뷰 작성 시 사용)
    public static CongestionLevel fromExactScore(int score) {
        return switch (score) {
            case 1 -> QUIET;
            case 3 -> NORMAL;
            case 5 -> CONGESTED;
            default -> throw new IllegalArgumentException("Invalid exact congestion score: " + score);
        };
    }

    // 평균 점수로부터 enum 생성 (혼잡도 판단 시 사용)
    public static CongestionLevel fromAverageScore(double avgScore) {
        if (avgScore < 2.0) return QUIET;         // 1.0 ~ 1.99
        else if (avgScore < 3.0) return NORMAL;   // 2.0 ~ 2.99
        else if (avgScore < 4.0) return CROWDED;  // 3.0 ~ 3.99
        else return CONGESTED;                   // 4.0 ~ 5.0
    }

    // 혼잡도 레벨을 숫자로 구분하고 싶을 때 (선택)
    public int getLevel() {
        return switch (this) {
            case QUIET -> 1;
            case NORMAL -> 2;
            case CROWDED -> 3;
            case CONGESTED -> 4;
        };
    }
}
