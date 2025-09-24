package com.webservice.feedbackservice.sistema.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public class FeedbackDTO {
    private String userFeedback;
    private int userRating;
    private LocalDateTime createdAt;

    public FeedbackDTO() {
    }

    public FeedbackDTO(String userFeedback, int userRating, LocalDateTime createdAt) {
        this.userFeedback = userFeedback;
        this.userRating = userRating;
        this.createdAt = createdAt;
    }

    public String getUserFeedback() {
        return userFeedback;
    }

    public void setUserFeedback(String userFeedback) {
        this.userFeedback = userFeedback;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

