package com.webservice.feedbackservice.sistema.messaging.consumer;

import com.webservice.feedbackservice.sistema.dto.FeedbackDTO;
import com.webservice.feedbackservice.sistema.service.FeedbackService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeedbackConsumer {
    private static final String QUEUE_NAME = "feedback-create.queue";

    @Autowired
    private FeedbackService feedbackService; // Injeta o serviço

    @RabbitListener(queues = QUEUE_NAME)
    public void receiveMessage(FeedbackDTO feedbackDTO) {
       try {
           System.out.println("Mensagem recebida. Delegando para o serviço de processamento.");
           feedbackService.createNewFeedback(feedbackDTO); // Delega o processamento
       }catch (Exception e){
           System.out.println ("Erro ao processar mensagem: " + e.getMessage());
       }
    }
}

