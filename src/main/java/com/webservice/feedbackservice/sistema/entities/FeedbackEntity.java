package com.webservice.feedbackservice.sistema.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Data
@Entity
@Table(name = "user_feedback")
public class FeedbackEntity {
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
    private LocalDate time;

    public FeedbackEntity(){};
}
