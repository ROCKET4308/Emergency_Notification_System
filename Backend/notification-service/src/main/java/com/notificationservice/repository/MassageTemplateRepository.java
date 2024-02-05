package com.notificationservice.repository;

import com.notificationservice.entity.MassageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MassageTemplateRepository extends JpaRepository<MassageTemplate, Integer> {
}
