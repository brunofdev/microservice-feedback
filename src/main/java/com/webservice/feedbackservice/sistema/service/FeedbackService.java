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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final ModelMapper modelMapper;
    private final FeedbackValidation feedbackValidation;
    private final WebClient.Builder webClientBuilder;
    private final FeedbackMapper feedbackMapper;

    @Value("${userservice.api.url}")
    private String urlUserService;
    @Value("${api.internal.secret}")
    private String internalApiSecret;

    //este metodo utilitario serve para ser utilizado junto a biblioteca model mapper,
    //mepeando listas sem a necessidade de percorrer a lista individualmente
    private <S, T> List<T> mapListUtil(List<S> source, Class<T> targetClass){
        return source.stream()
                .map(element -> modelMapper.map(element, targetClass))
                .toList();
    }
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
        return mapListUtil(feedbackRepository.findAll(), FeedbackDTO.class);
    }
    private List<String> getUsersWithFeedback(){
        return feedbackRepository.findDistinctUserNames();
    }
    public List<UsersWithFeedbackDTO> listAllWithUserDetails() {
        List<String> userNames = feedbackRepository.findDistinctUserNames();
        List<Feedback> feedbacks = feedbackRepository.findAll();
        List<UserDTO> userDetails = requestUserDetailsForUserService(userNames);
        feedbackValidation.validateUserNames(userNames);
        return feedbackMapper.mapUsersWithFeedbackDTO(userDetails, feedbacks);
    }
    private List<UserDTO> requestUserDetailsForUserService(List<String> userNames) {
            WebClient webClient = webClientBuilder.baseUrl(urlUserService).build();
            ApiResponse<List<UserDTO>> apiResponse = webClient.post()
                    .uri("/internal/users/details") // Path corrigido que corresponde ao InternalUserController
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header("X-Internal-Secret", internalApiSecret)
                    .body(Mono.just(userNames), new ParameterizedTypeReference<List<String>>() {}) // Usa a lista correta
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> Mono.error(new UserDatailsNotFoundExcpetion("Dados dos usuarios indisponiveis no momento"))
                    )
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<List<UserDTO>>>() {})
                    .block();
            feedbackValidation.validateApiResponse(apiResponse);
            return apiResponse.getDados();
        }
}

