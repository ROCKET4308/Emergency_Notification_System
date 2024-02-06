package com.notificationservice.repository;

import com.notificationservice.entity.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageStatusRepository extends JpaRepository<MessageStatus, Integer> {
}
