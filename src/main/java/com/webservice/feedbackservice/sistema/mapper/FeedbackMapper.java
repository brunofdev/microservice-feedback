package com.webservice.feedbackservice.sistema.mapper;

import com.webservice.feedbackservice.sistema.dto.FeedbackDTO;
import com.webservice.feedbackservice.sistema.dto.UserDTO;
import com.webservice.feedbackservice.sistema.dto.UsersWithFeedbackDTO;
import com.webservice.feedbackservice.sistema.entities.Feedback;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FeedbackMapper {
    public Map<String, UserDTO> mapListUserDTOtoMap (List<UserDTO>users){
        return users.stream().collect(Collectors.toMap(UserDTO::getUserName, user -> user));
    }
    public UsersWithFeedbackDTO mapUsersWithFeedbackDTO(String nome, Feedback feedback){
        return new UsersWithFeedbackDTO(
                feedback.getUserFeedback(),
                feedback.getUserRating(),
                feedback.getCreatedAt(),
                feedback.getUserName(),
                nome
        );
    }
    public List<UsersWithFeedbackDTO> mapUsersWithFeedbackDTO(List<UserDTO> usersDetails, List<Feedback> feedbacks) {
        Map<String, UserDTO> userMapByUsername = mapListUserDTOtoMap(usersDetails);
        List<UsersWithFeedbackDTO> feedbacksWithUserDetails = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            String nome = userMapByUsername.containsKey(feedback.getUserName())  ? userMapByUsername.get(feedback.getUserName()).getNome() : "Nome não encontrado";
            feedbacksWithUserDetails.add(
                    mapUsersWithFeedbackDTO(nome, feedback)
            );
        }
        return feedbacksWithUserDetails;
    }
}
