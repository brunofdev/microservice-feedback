package com.webservice.feedbackservice.sistema.service;

import com.webservice.feedbackservice.sistema.controller.FeedbackController;
import com.webservice.feedbackservice.sistema.dto.FeedbackCreateDTO;
import com.webservice.feedbackservice.sistema.entities.Feedback;
import com.webservice.feedbackservice.sistema.repository.FeedbackRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private  final ModelMapper modelMapper;

    public FeedbackService(FeedbackRepository feedbackRepository){
        this.feedbackRepository = feedbackRepository;
        this.modelMapper = new ModelMapper();
    }

    public FeedbackCreateDTO saveNewFeedback(FeedbackCreateDTO dto){
        Feedback feedback = modelMapper.map(dto, Feedback.class);
        feedbackRepository.save(feedback);
        return modelMapper.map(feedback, FeedbackCreateDTO.class);
    }


}

