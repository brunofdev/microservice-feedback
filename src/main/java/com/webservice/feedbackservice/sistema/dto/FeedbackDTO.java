package com.webservice.feedbackservice.sistema.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public class FeedbackDTO {
    private String userFeedback ;
    private int userRating;
    private LocalDateTime time;

    public FeedbackDTO(String userFeedback, int userRating, LocalDateTime time) {
        this.userFeedback = userFeedback;
        this.userRating = userRating;
        this.time = time;
    }

    public FeedbackDTO() {
    }

    public String getUserFeedback() {
        return userFeedback;
    }

    public void setUserFeedback(String userFeedback) {
        this.userFeedback = userFeedback;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }
}
