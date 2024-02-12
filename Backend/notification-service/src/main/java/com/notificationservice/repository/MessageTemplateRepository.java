package com.notificationservice.repository;

import com.notificationservice.entity.MessageTemplate;
import com.notificationservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Integer> {
    Optional<MessageTemplate> findByUserAndTemplateName(User user, String templateName);
    List<MessageTemplate> findAllByUserAndTemplateName(User user, String templateName);
}
