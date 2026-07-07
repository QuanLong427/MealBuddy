package com.mealbuddy.controller.session;

import com.mealbuddy.constants.MealbuddyConstants;
import com.mealbuddy.model.CreateSessionResponse;
import com.mealbuddy.service.session.SessionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mealbuddy/sessions")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public CreateSessionResponse create(@RequestHeader(value = MealbuddyConstants.USER_ID, defaultValue = "1") Long userId) {
        return new CreateSessionResponse(sessionService.createSession(userId));
    }
}