package com.webservice.feedbackservice.sistema.service;

import com.webservice.feedbackservice.sistema.dto.FeedbackCreateDTO;
import com.webservice.feedbackservice.sistema.dto.FeedbackDTO;
import com.webservice.feedbackservice.sistema.entities.Feedback;
import com.webservice.feedbackservice.sistema.repository.FeedbackRepository;
import com.webservice.feedbackservice.sistema.validation.FeedbackValidation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private  final ModelMapper modelMapper;
    private final FeedbackValidation feedbackValidation;

    public FeedbackService(FeedbackRepository feedbackRepository, FeedbackValidation feedbackValidation){
        this.feedbackRepository = feedbackRepository;
        this.modelMapper = new ModelMapper();
        this.feedbackValidation = feedbackValidation;
    }

    public void saveNewFeedback(FeedbackDTO dto){
        feedbackValidation.validateFeedback(dto);
        Feedback feedback = modelMapper.map(dto, Feedback.class);
        feedbackRepository.save(feedback);
    }
    public  List<FeedbackDTO> listAllExists(){
        List<Feedback> feedbackList = feedbackRepository.findAll();
        List<FeedbackDTO> feedbackDTOS = new ArrayList<>();
        for(Feedback feedback : feedbackList){
            feedbackDTOS.add(modelMapper.map(feedback, FeedbackDTO.class));
        }
        return feedbackDTOS;
    }



}

