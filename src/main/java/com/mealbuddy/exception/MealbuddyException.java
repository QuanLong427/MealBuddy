package com.mealbuddy.exception;

public class MealbuddyException extends RuntimeException {
    public MealbuddyException(String message) {
        super(message);
    }

    public MealbuddyException(String message, Throwable cause) {
        super(message, cause);
    }
}




