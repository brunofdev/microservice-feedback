package com.webservice.feedbackservice.sistema.controller;

import com.webservice.feedbackservice.sistema.dto.FeedbackCreateDTO;
import com.webservice.feedbackservice.sistema.dto.FeedbackDTO;
import com.webservice.feedbackservice.sistema.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController (FeedbackService feedbackService){
        this.feedbackService = feedbackService;
    }
    @GetMapping("/listar-todos")
    public ResponseEntity<List<FeedbackDTO>> listAllFeedbacks(){
        return ResponseEntity.ok().body(feedbackService.listAllExists());
    }
}