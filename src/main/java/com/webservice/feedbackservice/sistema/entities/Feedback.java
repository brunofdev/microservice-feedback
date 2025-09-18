package com.webservice.feedbackservice.sistema.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;


import java.time.LocalDateTime;



@Entity
@Table(name = "user_feedback")
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Não pode ser vazio")
    @NotBlank(message = "o feedback Não pode ser vazio")
    private String userFeedback ;
    @NotNull(message = "A nota é obrigatória")
    @Min(value = 1, message = "A nota não pode ser 0 ou negativa")
    private int userRating;
    @NotNull(message = "O horário do feedback é obrigatório")
    @PastOrPresent(message = "O horário não pode ser no futuro")
    private LocalDateTime time;

    public Feedback(){};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
