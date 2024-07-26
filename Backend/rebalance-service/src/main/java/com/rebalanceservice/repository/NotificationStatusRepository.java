package com.rebalanceservice.repository;

import com.rebalanceservice.entity.NotificationStatus;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Observed
public interface NotificationStatusRepository extends JpaRepository<NotificationStatus, Integer> {
    Optional<NotificationStatus> findByNameAndRecipientContact(String notificationStatusName, String recipientContact);
}
