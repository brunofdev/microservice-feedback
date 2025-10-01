package com.webservice.feedbackservice.sistema.mapper;

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

    public List<UsersWithFeedbackDTO> mapUsersWithFeedbackDTO(List<UserDTO> usersDetails, List<Feedback> feedbacks) {
        Map<String, UserDTO> userMapByUsername = usersDetails.stream().collect(Collectors.toMap(UserDTO::getUserName, user -> user));
        List<UsersWithFeedbackDTO> feedbacksWithUserDetails = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            String nome = userMapByUsername.containsKey(feedback.getUserName())
                    ? userMapByUsername.get(feedback.getUserName()).getNome()
                    : "Nome não encontrado";
            feedbacksWithUserDetails.add(new UsersWithFeedbackDTO(
                    feedback.getUserFeedback(),
                    feedback.getUserRating(),
                    feedback.getCreatedAt(),
                    feedback.getUserName(),
                    nome
            ));
        }
        return feedbacksWithUserDetails;
    }
}
