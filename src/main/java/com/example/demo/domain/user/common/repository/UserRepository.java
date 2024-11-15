package com.example.demo.domain.user.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.user.common.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Boolean existsByUsername(String username);

	User findByUsername(String username);

}
