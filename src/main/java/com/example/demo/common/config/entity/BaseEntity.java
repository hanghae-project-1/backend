package com.example.demo.common.config.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@NotNull
	@Builder.Default
	@ColumnDefault("true")
	Boolean isPublic = true;

	@NotNull
	@Builder.Default
	@ColumnDefault("false")
	Boolean isDelete = false;

	@CreatedDate
	@Column(updatable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	LocalDateTime createdAt;

	@CreatedBy
	@Column(updatable = false)
	String createdBy;

	@LastModifiedDate
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	LocalDateTime updatedAt;

	@LastModifiedBy
	String updatedBy;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	LocalDateTime deletedAt;

	String deletedBy;

	public void markAsDelete() {
		this.isDelete = true;
		this.isPublic = false;
		this.deletedAt = LocalDateTime.now();
	}

}
