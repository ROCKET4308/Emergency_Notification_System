package com.rebalanceservice.repository;

import com.rebalanceservice.entity.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationStatusRepository extends JpaRepository<NotificationStatus, Integer> {

}
