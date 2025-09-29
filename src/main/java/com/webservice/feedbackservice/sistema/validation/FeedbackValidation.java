package com.webservice.feedbackservice.sistema.validation;

import com.webservice.feedbackservice.sistema.dto.FeedbackDTO;
import com.webservice.feedbackservice.sistema.exceptions.UserNameIsEmptyException;

public class FeedbackValidation {
    public void validationFeedback(FeedbackDTO feedbackDTO){

    }

    public void validateFeedback(FeedbackDTO dto) {
        if(dto.getUserName().trim().isBlank()){
            throw new UserNameIsEmptyException("Username não pode ser vazio");
        }
    }
}
