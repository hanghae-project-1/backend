package com.example.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_review")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	@NotNull
	String content;

	@NotNull
	Integer rating;

	@OneToOne
	@JoinColumn(name = "order_id")
	Order order;

}
