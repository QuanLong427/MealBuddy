package com.mealbuddy.service.feedback;

import com.mealbuddy.exception.MealbuddyException;
import com.mealbuddy.mapper.FeedbackMapper;
import com.mealbuddy.model.FeedbackRequest;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    private final FeedbackMapper feedbackMapper;

    public FeedbackService(FeedbackMapper feedbackMapper) {
        this.feedbackMapper = feedbackMapper;
    }

    public void save(Long userId, FeedbackRequest request) {
        if (request == null || request.sessionId() == null || request.sessionId().isBlank()) {
            throw new MealbuddyException("反馈 sessionId 不能为空");
        }
        if (request.action() == null || request.action().isBlank()) {
            throw new MealbuddyException("反馈 action 不能为空");
        }
        feedbackMapper.insert(
                userId,
                request.sessionId(),
                request.itemId(),
                request.action(),
                request.rating(),
                request.reason()
        );
    }
}