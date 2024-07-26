package com.messageservice.repository;

import com.messageservice.entity.Message;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Observed
public interface MessageRepository extends JpaRepository<Message, Integer> {
    Optional<Message> findByEmailAndName(String email, String name);
    List<Message> findAllByEmailAndName(String email, String name);
    List<Message> findAllByEmail(String email);
    @Transactional
    List<Message> deleteAllByEmailAndName(String email, String name);
}
