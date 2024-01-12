package com.notificationservice.repository;

import com.notificationservice.entity.Massage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MassageRepository extends JpaRepository<Massage, Integer> {
}
