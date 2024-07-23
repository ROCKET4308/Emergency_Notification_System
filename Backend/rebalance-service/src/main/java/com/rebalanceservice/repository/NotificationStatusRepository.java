package com.rebalanceservice.repository;

import com.rebalanceservice.entity.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationStatusRepository extends JpaRepository<NotificationStatus, Integer> {
    Optional<NotificationStatus> findByNameAndRecipientContact(String notificationStatusName, String recipientContact);
}
