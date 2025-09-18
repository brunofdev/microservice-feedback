package com.webservice.feedbackservice.sistema.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FeedbackCreateDTO {

        @NotNull(message = "Não pode ser vazio")
        @NotBlank(message = "o feedback Não pode ser vazio")
        private String userFeedback ;
        @NotNull(message = "A nota é obrigatória")
        @Min(value = 1, message = "A nota não pode ser 0 ou negativa")
        private int userRating;
        @NotNull(message = "O horário do feedback é obrigatório")
        @PastOrPresent(message = "O horário não pode ser no futuro")
        private LocalDate time;
}
