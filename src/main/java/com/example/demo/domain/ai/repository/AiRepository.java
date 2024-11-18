package com.example.demo.domain.ai.repository;

import com.example.demo.domain.ai.entity.Ai;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AiRepository extends JpaRepository<Ai, UUID> {
    List<Ai> findAllByOwnerName(String ownerName);

}
