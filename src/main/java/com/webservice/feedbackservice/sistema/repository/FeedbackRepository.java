package com.webservice.feedbackservice.sistema.repository;

import com.webservice.feedbackservice.sistema.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("SELECT DISTINCT f.userName FROM Feedback f")
    List<String> findDistinctUserNames();
}
