package com.notificationservice.repository;

import com.notificationservice.entity.MassageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MassageStatusRepository extends JpaRepository<MassageStatus, Integer> {
}
