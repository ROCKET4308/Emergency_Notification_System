package com.messagetemplateservice.repository;

import com.messagetemplateservice.entity.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Integer> {
    Optional<MessageTemplate> findByUserAndTemplateName(String email, String templateName);
    List<MessageTemplate> findAllByUserAndTemplateName(String email, String templateName);
    @Transactional
    List<MessageTemplate> deleteAllByUserAndTemplateName(String email, String templateName);
}
