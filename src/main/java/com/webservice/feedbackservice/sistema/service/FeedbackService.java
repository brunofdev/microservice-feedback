package com.webservice.feedbackservice.sistema.service;

import com.webservice.feedbackservice.sistema.dto.FeedbackDTO;
import com.webservice.feedbackservice.sistema.dto.UserDTO;
import com.webservice.feedbackservice.sistema.dto.UsersWithFeedbackDTO;
import com.webservice.feedbackservice.sistema.dto.apiresponse.ApiResponse;
import com.webservice.feedbackservice.sistema.entities.Feedback;
import com.webservice.feedbackservice.sistema.exceptions.UserDatailsNotFoundExcpetion;
import com.webservice.feedbackservice.sistema.mapper.FeedbackMapper;
import com.webservice.feedbackservice.sistema.repository.FeedbackRepository;
import com.webservice.feedbackservice.sistema.validation.FeedbackValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private  final ModelMapper modelMapper;
    private final FeedbackValidation feedbackValidation;
    private final WebClient.Builder webClientBuilder;
    private final FeedbackMapper feedbackMapper;

    @Value("${userservice.api.url}")
    private String urlUserService;

    @Value("$internal.api.secret")
    private String internalApiSecret;

    public FeedbackService(FeedbackRepository feedbackRepository, FeedbackValidation feedbackValidation,
                            ModelMapper modelMapper, WebClient.Builder webClientBuilder, FeedbackMapper feedbackMapper){
        this.feedbackRepository = feedbackRepository;
        this.modelMapper = modelMapper;
        this.feedbackValidation = feedbackValidation;
        this.webClientBuilder = webClientBuilder;
        this.feedbackMapper = feedbackMapper;
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
    private List<String> getUsersWithFeedback(){
        return feedbackRepository.findDistinctUserNames();
    }
    public List<UserDTO> requestUserInfo(List<String> userName){
        WebClient webClient = webClientBuilder.baseUrl(urlUserService).build();
        List<String> usersNeedingDetails = getUsersWithFeedback();
        ApiResponse<List<UserDTO>> apiResponse = webClient.post()
                .uri("/internal/users/details")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("X-Internal-Secret", internalApiSecret)
                .body(Mono.just(usersNeedingDetails), new ParameterizedTypeReference<List<String>>() {})
                .retrieve()
                //regra para erros
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(new UserDatailsNotFoundExcpetion("Dados dos usuarios indisponiveis no momento") )
                )
                //o que fazer em caso de sucesso na resposta
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<List<UserDTO>>>() {})
                //bloqueia a execucao ate que a resposta chegue
                .block();
        if(apiResponse != null && apiResponse.getStatus()){
            return  apiResponse.getDados();
        }
        return Collections.emptyList();
    }
    public List<UsersWithFeedbackDTO> getUsersWithHaveFeedback(){
        List<Feedback>  feedbacks = feedbackRepository.findAll();
        List<UserDTO> users = requestUserInfo(getUsersWithFeedback());
        return feedbackMapper.mapUsersWithFeedbackDTO(users, feedbacks);
    }


}

