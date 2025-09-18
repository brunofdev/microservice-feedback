package com.webservice.feedbackservice.sistema.controller;

import com.webservice.feedbackservice.sistema.dto.FeedbackCreateDTO;
import com.webservice.feedbackservice.sistema.entities.Feedback;
import com.webservice.feedbackservice.sistema.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@CrossOrigin("/")
@Controller
@RestController("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController (FeedbackService feedbackService){
        this.feedbackService = feedbackService;
    }

    @PostMapping("criar-feedback")
    public ResponseEntity<FeedbackCreateDTO> createFeedback(@Valid FeedbackCreateDTO feedBackCreateDTO){
        return ResponseEntity.ok(feedbackService.saveNewFeedback(feedBackCreateDTO));
    }
}
