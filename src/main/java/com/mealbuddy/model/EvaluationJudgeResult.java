package com.mealbuddy.model;

public record EvaluationJudgeResult(
        double explanationQuality,
        double naturalness,
        String reason
) {
}